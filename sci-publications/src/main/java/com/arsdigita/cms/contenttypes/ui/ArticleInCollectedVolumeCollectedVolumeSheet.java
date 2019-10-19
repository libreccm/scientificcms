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

import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.security.PermissionChecker;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ArticleInCollectedVolumeItem;

import java.util.Locale;

/**
 * Sheet which displays the collected volume to which an article in a collected
 * volume is associated to.
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ArticleInCollectedVolumeCollectedVolumeSheet
    extends Table
    implements TableActionListener {

    private final String TABLE_COL_EDIT = "table_col_edit";

    private final String TABLE_COL_DEL = "table_col_del";

    private ItemSelectionModel itemModel;

    public ArticleInCollectedVolumeCollectedVolumeSheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        super();
        this.itemModel = itemModel;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.articleInCollectedVolume.collectedVolume.none",
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
                        "publications.ui.articleInCollectedVolume.collectedVolume",
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
                        "publications.ui.articleInCollectedVolume.collectedVolume.remove",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_DEL
            )
        );

        setModelBuilder(
            new ArticleInCollectedVolumeCollectedVolumeSheetModelBuilder(
                itemModel, selectedLangParam
            )
        );
        colModel.get(0).setCellRenderer(new EditCellRenderer());
        colModel.get(1).setCellRenderer(new DeleteCellRenderer());

        addTableActionListener(this);

    }

    @Override
    public void cellSelected(final TableActionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();

        final ArticleInCollectedVolumeItem articleItem
                                               = (ArticleInCollectedVolumeItem) itemModel
                .getSelectedObject(state);

        final TableColumn column = getColumnModel().get(
            event.getColumn().intValue()
        );

        if (column.getHeaderKey().toString().equals(TABLE_COL_EDIT)) {
            // Nothing
        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DEL)) {
            final ArticleInCollectedVolumeController controller = CdiUtil
                .createCdiUtil()
                .findBean(ArticleInCollectedVolumeController.class);
            controller.unsetCollectedVolume(
                articleItem.getPublication().getPublicationId()
            );
        }

    }

    @Override
    public void headSelected(TableActionEvent event) {
        // Nothing
    }

    private class ArticleInCollectedVolumeCollectedVolumeSheetModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;
        private final StringParameter selectedLangParam;

        public ArticleInCollectedVolumeCollectedVolumeSheetModelBuilder(
            final ItemSelectionModel itemModel,
            final StringParameter selectedLangParam
        ) {
            this.itemModel = itemModel;
            this.selectedLangParam = selectedLangParam;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {
            table.getRowSelectionModel().clearSelection(state);
            final ArticleInCollectedVolumeItem article
                                                   = (ArticleInCollectedVolumeItem) itemModel
                    .getSelectedObject(state);
            return new ArticleInCollectedVolumeCollectedVolumeSheetModel(
                table, state, article, selectedLangParam
            );

        }

    }

    private class ArticleInCollectedVolumeCollectedVolumeSheetModel
        implements TableModel {

        private final Table table;

        private final String collectedVolumeTitle;

        private final Long collectedVolumeId;

        private boolean done;

        public ArticleInCollectedVolumeCollectedVolumeSheetModel(
            final Table table,
            final PageState state,
            final ArticleInCollectedVolumeItem articleItem,
            final StringParameter selectedLangParam
        ) {
            this.table = table;
            final CollectedVolume collectedVolume = articleItem
                .getPublication()
                .getCollectedVolume();
            if (collectedVolume == null) {
                done = false;
                collectedVolumeTitle = null;
                collectedVolumeId = null;
            } else {
                done = true;
                final Locale selectedLang = SelectedLanguageUtil.selectedLocale(
                    state, selectedLangParam
                );
                collectedVolumeTitle = collectedVolume.getTitle().getValue(
                    selectedLang
                );
                collectedVolumeId = collectedVolume.getPublicationId();
            }
        }

        @Override
        public int getColumnCount() {
            return table.getColumnModel().size();
        }

        @Override
        public boolean nextRow() {
            boolean ret;

            if (done) {
                ret = true;
                done = false;
            } else {
                ret = false;
            }

            return ret;
        }

        @Override
        public Object getElementAt(final int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return collectedVolumeTitle;
                case 1:
                    return new Label(
                        new GlobalizedMessage(
                            "publications.ui.articleInCollectedVolume.collectedVolume.remove",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(int columnIndex) {
            return collectedVolumeId;
        }

    }

    private class EditCellRenderer
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
            final Object key,
            final int row,
            final int column
        ) {
            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final PermissionChecker permissionChecker = cdiUtil
                .findBean(PermissionChecker.class);
            final ArticleInCollectedVolumeItem articleItem
                                                   = (ArticleInCollectedVolumeItem) itemModel
                    .getSelectedObject(state);
            final boolean canEdit = permissionChecker.isPermitted(
                ItemPrivileges.EDIT, articleItem
            );

            if (canEdit) {
                final ControlLink link = new ControlLink((Component) value);
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publications.ui.articleInCollectedVolume.collectedVolume."
                        + "confirm_remove",
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
