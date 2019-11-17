/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.Component;
import com.arsdigita.bebop.ControlLink;
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
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.util.LockableImpl;

import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.security.PermissionChecker;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.publications.contenttypes.PublicationWithPublisherItem;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.Publisher;
import org.scientificcms.publications.PublicationWithPublisher;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationWithPublisherSetPublisherSheet
    extends Table
    implements TableActionListener {

    private final String TABLE_COL_EDIT = "table_col_edit";
    private final String TABLE_COL_DEL = "table_col_del";
    private final ItemSelectionModel itemModel;

    public PublicationWithPublisherSetPublisherSheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {

        this.itemModel = itemModel;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.with_publisher.publisher.none",
                    SciPublicationsConstants.BUNDLE
                )
            )
        );

        final TableColumnModel columnModel = getColumnModel();
        columnModel.add(
            new TableColumn(
                0,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.with_publisher.publisher",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_EDIT
            )
        );
        columnModel.add(
            new TableColumn(
                1,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.with_publisher.publisher.remove",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_DEL
            )
        );

        setModelBuilder(
            new PublicationWithPublisherSetPublisherSheetModelBuilder(
                itemModel
            )
        );
        columnModel.get(0).setCellRenderer(new EditCellRenderer());
        columnModel.get(1).setCellRenderer((new DeleteCellRenderer()));

        addTableActionListener(this);
    }

    @Override
    public void cellSelected(final TableActionEvent event) {
        PageState state = event.getPageState();

        final PublicationWithPublisherItem<?> publicationItem
                                                  = (PublicationWithPublisherItem) itemModel
                .getSelectedObject(state);

        final TableColumn column = getColumnModel()
            .get(event.getColumn()
                .intValue());

        if (column.getHeaderKey().toString().equals(TABLE_COL_EDIT)) {
            // Nothing
        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DEL)) {
            final SciPublicationsWithPublisherController controller = CdiUtil
                .createCdiUtil()
                .findBean(SciPublicationsWithPublisherController.class);
            controller.unsetPublisher(
                publicationItem.getPublication().getPublicationId(),
                publicationItem.getPublication().getPublisher().getPublisherId()
            );
        }

    }

    @Override
    public void headSelected(final TableActionEvent event) {
        //Nothing to do
    }

    private class PublicationWithPublisherSetPublisherSheetModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private ItemSelectionModel itemModel;

        public PublicationWithPublisherSetPublisherSheetModelBuilder(
            final ItemSelectionModel itemModel) {
            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {
            table.getRowSelectionModel().clearSelection(state);
            final PublicationWithPublisherItem<?> publicationItem
                                                      = (PublicationWithPublisherItem) itemModel
                    .getSelectedObject(state);
            return new PublicationWithPublisherSetPublisherSheetModel(
                table, state, publicationItem.getPublication()
            );
        }

    }

    private class PublicationWithPublisherSetPublisherSheetModel
        implements TableModel {

        private final Table table;
        private final Publisher publisher;
        private boolean m_done;

        public PublicationWithPublisherSetPublisherSheetModel(
            final Table table,
            final PageState state,
            final PublicationWithPublisher publication
        ) {
            this.table = table;
            publisher = publication.getPublisher();
            if (publisher == null) {
                m_done = false;
            } else {
                m_done = true;
            }
        }

        @Override
        public int getColumnCount() {
            return table.getColumnModel().size();
        }

        @Override
        public boolean nextRow() {
            boolean ret;

            if (m_done) {
                ret = true;
                m_done = false;
            } else {
                ret = false;
            }

            return ret;
        }

        @Override
        public Object getElementAt(final int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return publisher.getName();
                case 1:
                    return new Label(
                        new GlobalizedMessage(
                            "publications.ui.with_publisher.publisher.remove",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return publisher.getPublisherId();
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
            final int col
        ) {
            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final PermissionChecker permissionChecker = cdiUtil
                .findBean(PermissionChecker.class);
            final PublicationWithPublisherItem<?> publicationItem
                                                      = (PublicationWithPublisherItem) itemModel
                    .getSelectedItem(state);
            final boolean canEdit = permissionChecker.isPermitted(
                ItemPrivileges.EDIT, publicationItem
            );

            if (canEdit) {
                final ControlLink link = new ControlLink((Label) value);
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publications.ui.with_publisher.publisher.remove.confirm",
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
