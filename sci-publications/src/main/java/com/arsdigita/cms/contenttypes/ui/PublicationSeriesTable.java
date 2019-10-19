/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.Component;
import com.arsdigita.bebop.ControlLink;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.Table;
import com.arsdigita.bebop.Text;
import com.arsdigita.bebop.event.TableActionEvent;
import com.arsdigita.bebop.event.TableActionListener;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.bebop.table.TableCellRenderer;
import com.arsdigita.bebop.table.TableColumn;
import com.arsdigita.bebop.table.TableColumnModel;
import com.arsdigita.bebop.table.TableModel;
import com.arsdigita.bebop.table.TableModelBuilder;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.util.LockableImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.l10n.LocalizedString;
import org.libreccm.security.PermissionChecker;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.VolumeInSeries;
import org.scientificcms.publications.contenttypes.PublicationItem;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationSeriesTable
    extends Table
    implements TableActionListener {

    private static final Logger LOGGER = LogManager.getLogger(
        PublicationSeriesTable.class);

    private final static String TABLE_COL_EDIT = "table_col_edit";

    private final static String TABLE_COL_NUMBER = "table_col_num";

    private final static String TABLE_COL_DEL = "table_col_del";

    private final ItemSelectionModel itemModel;

    private final PublicationSeriesPropertyStep editStep;

    private final StringParameter selectedLangParam;

    public PublicationSeriesTable(
        final ItemSelectionModel itemModel,
        final PublicationSeriesPropertyStep editStep,
        final StringParameter selectedLangParam
    ) {

        super();
        this.itemModel = itemModel;
        this.editStep = editStep;
        this.selectedLangParam = selectedLangParam;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.series.none",
                    SciPublicationsConstants.BUNDLE
                )
            )
        );

        final TableColumnModel colModel = getColumnModel();
        colModel.add(
            new TableColumn(
                0,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.series.title",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_EDIT
            )
        );
        colModel.add(
            new TableColumn(
                1,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.series.number",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_NUMBER
            )
        );
        colModel.add(
            new TableColumn(
                2,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.series.remove",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_DEL
            )
        );

        setModelBuilder(
            new PublicationSeriesTableModelBuilder(
                itemModel, selectedLangParam
            )
        );

        colModel.get(0).setCellRenderer(new EditCellRenderer());
        colModel.get(1).setCellRenderer(new NumberCellRenderer());
        colModel.get(2).setCellRenderer(new DeleteCellRenderer());

        LOGGER.info("Adding table action listener...");
        addTableActionListener(this);
    }

    @Override
    public void cellSelected(final TableActionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();
        final PublicationItem<?> selected = (PublicationItem<?>) itemModel
            .getSelectedItem(state);
        final SciPublicationsController controller = CdiUtil
            .createCdiUtil()
            .findBean(SciPublicationsController.class);
        final VolumeInSeries volumeInSeries = controller.findVolumeInSeries(
            selected.getPublication().getPublicationId(),
            event.getRowKey()
        ).get();

        final TableColumn column = getColumnModel().get(event.getColumn());

        if (TABLE_COL_EDIT.equals(column.getHeaderKey().toString())) {
            // Nothing for now
        } else if (TABLE_COL_DEL.equals(column.getHeaderKey().toString())) {
            controller.removeSeries(
                selected.getPublication().getPublicationId(),
                volumeInSeries.getVolumeId()
            );
        }
    }

    @Override
    public void headSelected(final TableActionEvent event) {

        // Nothing
    }

    private class PublicationSeriesTableModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;

        public PublicationSeriesTableModelBuilder(
            final ItemSelectionModel itemModel,
            final StringParameter selectedLangParam
        ) {
            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {

            table.getRowSelectionModel().clearSelection(state);
            final PublicationItem<?> publication
                                         = (PublicationItem<?>) itemModel
                    .getSelectedItem(state);
            return new PublicationSeriesTableModel(
                table, state, selectedLangParam, publication
            );
        }

    }

    private class PublicationSeriesTableModel implements TableModel {

        private final Table table;

        private final PageState state;

        private final StringParameter selectedLangParam;

        private final Iterator<Map<String, Object>> iterator;

        private Map<String, Object> currentRow;

        public PublicationSeriesTableModel(
            final Table table,
            final PageState state,
            final StringParameter selectedLangParam,
            final PublicationItem<?> publicationItem
        ) {
            this.table = table;
            this.state = state;
            this.selectedLangParam = selectedLangParam;

            final SciPublicationsController controller = CdiUtil
                .createCdiUtil()
                .findBean(SciPublicationsController.class);
            iterator = controller.getVolumesInSeries(
                publicationItem.getPublication().getPublicationId()
            ).iterator();
        }

        @Override
        public int getColumnCount() {
            return table.getColumnModel().size();
        }

        @Override
        public boolean nextRow() {
            if (iterator.hasNext()) {
                currentRow = iterator.next();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Object getElementAt(final int columnIndex) {
            switch (columnIndex) {
                case 0: {
                    final LocalizedString title = (LocalizedString) currentRow
                        .get(SciPublicationsController.VOLUME_IN_SERIES_TITLE);
                    final Locale selectedLocale = SelectedLanguageUtil
                        .selectedLocale(state, selectedLangParam);
                    return title.getValue(selectedLocale);
                }
                case 1: {
                    return currentRow.get(
                        SciPublicationsController.VOLUME_IN_SERIES_VOLUME
                    );
                }
                case 2:
                    return new Label(
                        new GlobalizedMessage(
                            "publications.ui.series.remove",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return currentRow.get(
                SciPublicationsController.VOLUME_IN_SERIES_ID
            );
        }

    }

    private class EditCellRenderer
        extends LockableImpl
        implements
        TableCellRenderer {

        @Override
        public Component getComponent(
            final Table table,
            final PageState state,
            final Object value,
            final boolean isSelected,
            final Object key,
            final int row,
            final int column
        ) {
            return new Text((String) value);
        }

    }

    private class NumberCellRenderer
        extends LockableImpl
        implements TableCellRenderer {

        @Override
        public Component getComponent(
            final Table table,
            final PageState state,
            final Object value,
            final boolean isSelected,
            final Object key,
            final int row,
            final int column
        ) {
            return new Text((String) value);
        }

    }

    private class DeleteCellRenderer
        extends LockableImpl
        implements TableCellRenderer {

        @Override
        public Component getComponent(
            final Table table,
            final PageState state,
            final Object value,
            final boolean isSelected,
            final Object key, final int row,
            final int column
        ) {
            final PermissionChecker permissionChecker = CdiUtil
                .createCdiUtil()
                .findBean(PermissionChecker.class);

            final PublicationItem<?> selected = (PublicationItem<?>) itemModel
                .getSelectedItem(state);
            final boolean canEdit = permissionChecker
                .isPermitted(ItemPrivileges.EDIT, selected);

            if (canEdit) {
                final ControlLink link = new ControlLink(
                    (Component) value
                );
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publications.ui.series.confirm_remove",
                        SciPublicationsConstants.BUNDLE
                    )
                );
                return link;
            } else {
                return new Text("");
            }
        }

    }

}
