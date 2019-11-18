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
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.util.LockableImpl;

import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.security.PermissionChecker;
import org.librecms.assets.Organization;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.publications.Expertise;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ExpertiseItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ExpertiseOrdererSheet
    extends Table
    implements TableActionListener {

    private static final String TABLE_COL_EDIT = "table_col_edit";

    private static final String TABLE_COL_DEL = "table_col_del";

    private final ItemSelectionModel itemModel;

    public ExpertiseOrdererSheet(final ItemSelectionModel itemModel) {
        super();

        this.itemModel = itemModel;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.expertise.orderer.none",
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
                        "publications.ui.expertise.orderer",
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
                        "publications.ui.expertise.orderer.remove",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_DEL
            )
        );

        setModelBuilder(new ExpertiseOrganizationSheetModelBuilder(itemModel));
        columnModel.get(0).setCellRenderer(new EditCellRenderer());
        columnModel.get(1).setCellRenderer(new DeleteCellRenderer());

        addTableActionListener(this);
    }

    @Override
    public void cellSelected(final TableActionEvent event) {
        final PageState state = event.getPageState();

        final ExpertiseItem expertiseItem = (ExpertiseItem) itemModel
            .getSelectedItem(state);

        final TableColumn column = getColumnModel().get(event.getColumn());

        if (column.getHeaderKey().toString().equals(TABLE_COL_EDIT)) {
            // Nothing
        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DEL)) {
            final ExpertiseController controller = CdiUtil
                .createCdiUtil()
                .findBean(ExpertiseController.class);
            controller.unsetOrderer(
                expertiseItem.getPublication().getPublicationId()
            );
        }
    }

    @Override
    public void headSelected(final TableActionEvent event) {
        //Nothing to do
    }

    private class ExpertiseOrganizationSheetModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;

        public ExpertiseOrganizationSheetModelBuilder(
            final ItemSelectionModel itemModel
        ) {
            this.itemModel = itemModel;
        }

        @Override
        public final TableModel makeModel(
            final Table table, final PageState state
        ) {
            table.getRowSelectionModel().clearSelection(state);
            final ExpertiseItem expertiseItem = (ExpertiseItem) itemModel
                .getSelectedItem(state);
            return new ExpertiseOrganizationSheetModel(
                table, state, expertiseItem.getPublication()
            );
        }

    }

    private class ExpertiseOrganizationSheetModel implements TableModel {

        private final Table table;
        private final Organization orderer;
        private boolean done;

        public ExpertiseOrganizationSheetModel(
            final Table table,
            final PageState state,
            final Expertise expertise
        ) {
            this.table = table;
            orderer = expertise.getOrderer();
            done = orderer != null;
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
                    return orderer.getTitle();
                case 1:
                    return new Label(
                        new GlobalizedMessage(
                            "publications.ui.expertise.orderer.remove",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return orderer.getObjectId();
        }

    }

    private class EditCellRenderer
        extends LockableImpl
        implements TableCellRenderer {

        @Override
        public final Component getComponent(
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

            final ExpertiseItem expertiseItem = (ExpertiseItem) itemModel
                .getSelectedItem(state);

            final boolean canEdit = permissionChecker.isPermitted(
                ItemPrivileges.DELETE, expertiseItem
            );

            if (canEdit) {
                final ControlLink link = new ControlLink((Component) value);
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publication.ui.expertise.orderer.remove.confirm",
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
