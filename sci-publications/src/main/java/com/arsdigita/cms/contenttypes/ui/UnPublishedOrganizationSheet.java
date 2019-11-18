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
import com.arsdigita.bebop.table.TableCellRenderer;
import com.arsdigita.bebop.table.TableColumn;
import com.arsdigita.bebop.table.TableColumnModel;
import com.arsdigita.bebop.table.TableModel;
import com.arsdigita.bebop.table.TableModelBuilder;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.dispatcher.Utilities;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.util.LockableImpl;

import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.security.PermissionChecker;
import org.librecms.assets.Organization;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.UnPublished;
import org.scientificcms.publications.contenttypes.PublicationItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class UnPublishedOrganizationSheet
    extends Table
    implements TableActionListener {

    private static final String TABLE_COL_EDIT = "table_col_edit";

    private static final String TABLE_COL_DELETE = "table_col_delete";

    private final ItemSelectionModel itemModel;

    public UnPublishedOrganizationSheet(final ItemSelectionModel itemModel) {
        super();
        this.itemModel = itemModel;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.unpublished.organization.none",
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
                        "publications.ui.unpublished.organization",
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
                        "publications.ui.unpublished.organization.remove",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_DELETE
            )
        );

        setModelBuilder(
            new UnPublishedOrganizationSheetModelBuilder(itemModel)
        );
        columnModel.get(0).setCellRenderer(new EditCellRenderer());
        columnModel.get(1).setCellRenderer(new DeleteCellRenderer());

        addTableActionListener(this);
    }

    @Override
    public void cellSelected(final TableActionEvent event) {
        PageState state = event.getPageState();

        final PublicationItem<?> publicationItem = (PublicationItem) itemModel
            .getSelectedItem(state);

        final TableColumn column = getColumnModel().get(event.getColumn());
        if (column.getHeaderKey().toString().equals(TABLE_COL_EDIT)) {
            // Nothing to do
        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DELETE)) {
            final UnPublishedController controller = CdiUtil
                .createCdiUtil()
                .findBean(UnPublishedController.class);
            controller.unsetOrganization(
                publicationItem.getPublication().getPublicationId()
            );
        }
    }

    @Override
    public void headSelected(final TableActionEvent event) {
        //Nothing to do
    }

    private class UnPublishedOrganizationSheetModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;

        public UnPublishedOrganizationSheetModelBuilder(
            final ItemSelectionModel itemModel) {
            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {
            table.getRowSelectionModel().clearSelection(state);
            final PublicationItem<?> publicationItem
                                         = (PublicationItem) itemModel
                    .getSelectedItem(state);
            final UnPublished unPublished = (UnPublished) publicationItem
                .getPublication();
            return new UnPublishedOrganizationSheetModel(
                table, state, unPublished
            );
        }

    }

    private class UnPublishedOrganizationSheetModel implements TableModel {

        private final Table table;
        private final Organization orga;
        private boolean done;

        public UnPublishedOrganizationSheetModel(
            final Table table,
            final PageState state,
            final UnPublished unPublished
        ) {
            this.table = table;
            orga = unPublished.getOrganization();
            done = orga != null;
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
                    return orga.getTitle();
                case 1:
                    return new Label(
                        new GlobalizedMessage(
                            "publications.ui.unpublished.organization.remove",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return orga.getObjectId();
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

            final PublicationItem<?> publicationItem
                                         = (PublicationItem) itemModel
                    .getSelectedItem(state);
            final boolean canEdit = permissionChecker.isPermitted(
                ItemPrivileges.EDIT, publicationItem
            );

            if (canEdit) {
                final ControlLink link = new ControlLink((Label) value);
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publications.ui.unpublished.organization.confirm_remove",
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
