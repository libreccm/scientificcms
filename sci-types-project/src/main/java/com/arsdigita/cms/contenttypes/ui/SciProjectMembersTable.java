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
import com.arsdigita.bebop.table.TableCellRenderer;
import com.arsdigita.bebop.table.TableColumn;
import com.arsdigita.bebop.table.TableColumnModel;
import com.arsdigita.bebop.table.TableModel;
import com.arsdigita.bebop.table.TableModelBuilder;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.util.LockableImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.configuration.ConfigurationManager;
import org.libreccm.security.PermissionChecker;
import org.librecms.CmsConstants;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.contenttypes.sciproject.Membership;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConfig;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Jens Pelzetter
 * @version $Id: SciProjectMembersTable.java 1170 2011-10-17 19:23:23Z jensp $
 */
public class SciProjectMembersTable
    extends Table
    implements TableActionListener {

    private static final Logger LOGGER = LogManager
        .getLogger(SciProjectMembersTable.class);

    private static final String TABLE_COL_EDIT = "table_col_edit";

    private static final String TABLE_COL_EDIT_LINK = "table_col_edit_link";

    private static final String TABLE_COL_DEL = "table_col_del";

    private final ItemSelectionModel itemModel;

    private final SciProjectMembersStep membersStep;

    public SciProjectMembersTable(final ItemSelectionModel itemModel,
                                  final SciProjectMembersStep membersStep) {

        super();

        this.itemModel = itemModel;
        this.membersStep = membersStep;

        setEmptyView(new Label(new GlobalizedMessage(
            "cms.contenttypes.ui.sciproject.members.none",
            SciProjectConstants.SCI_PROJECT_BUNDLE)));

        final TableColumnModel columnModel = getColumnModel();
        columnModel.add(new TableColumn(
            0,
            new GlobalizedMessage(
                "cms.contenttypes.ui.sciproject.persons.name",
                SciProjectConstants.SCI_PROJECT_BUNDLE
            ),
            TABLE_COL_EDIT));
        columnModel.add(
            new TableColumn(
                1,
                new GlobalizedMessage(
                    "cms.contenttypes.ui.sciproject.persons.role",
                    SciProjectConstants.SCI_PROJECT_BUNDLE
                )
            )
        );
        columnModel.add(
            new TableColumn(
                2,
                new GlobalizedMessage(
                    "cms.contenttypes.ui.sciproject.persons.status",
                    SciProjectConstants.SCI_PROJECT_BUNDLE)
            )
        );
        columnModel.add(
            new TableColumn(
                3,
                new GlobalizedMessage(
                    "cms.contenttypes.ui.genericorganunit.persons.edit",
                    SciProjectConstants.SCI_PROJECT_BUNDLE
                ),
                TABLE_COL_EDIT_LINK)
        );
        columnModel.add(
            new TableColumn(
                4,
                new GlobalizedMessage(
                    "cms.contenttypes.ui.sciproject.persons.delete",
                    SciProjectConstants.SCI_PROJECT_BUNDLE
                ),
                TABLE_COL_DEL)
        );

        setModelBuilder(new SciProjectMembersTableModelBuilder(itemModel));

        columnModel.get(0).setCellRenderer(new EditCellRenderer());
        columnModel.get(3).setCellRenderer(new EditLinkCellRenderer());
        columnModel.get(4).setCellRenderer(new DeleteCellRenderer());

        addTableActionListener(this);
    }

    private class SciProjectMembersTableModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;

        public SciProjectMembersTableModelBuilder(
            final ItemSelectionModel itemModel) {

            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {

            table.getRowSelectionModel().clearSelection(state);
            final SciProject selected = (SciProject) itemModel
                .getSelectedItem(state);
            return new SciProjectMembersTableModel(table, state, selected);
        }

    }

    private class SciProjectMembersTableModel implements TableModel {

        private final Table table;

        private final Iterator<Map<String, Object>> iterator;

        private Map<String, Object> currentRow;

        public SciProjectMembersTableModel(final Table table,
                                           final PageState state,
                                           final SciProject selected) {

            this.table = table;

            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);
            iterator = controller.getMembers(selected.getObjectId()).iterator();
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
                case 0:
                    return currentRow.get(SciProjectController.MEMBER_NAME);
                case 1:
                    final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
                    final ConfigurationManager confManager = cdiUtil
                        .findBean(ConfigurationManager.class);
                    final SciProjectConfig config = confManager
                        .findConfiguration(SciProjectConfig.class);
                    final String role = (String) currentRow
                        .get(SciProjectController.MEMBER_ROLE);
                    return new GlobalizedMessage(role,
                                                 config
                                                     .getMemberRolesBundleName());
                case 2:
                    return currentRow.get(SciProjectController.MEMBER_STATUS);
                case 3:
                    return new Label(new GlobalizedMessage(
                        "cms.ui.edit_assoc", CmsConstants.CMS_BUNDLE));
                case 4:
                    return new Label(new GlobalizedMessage(
                        "cms.ui.delete", CmsConstants.CMS_BUNDLE));
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {

            return currentRow.get(SciProjectController.MEMBERSHIP_ID);
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
            final int col) {

            final SciProject selected = (SciProject) itemModel
                .getSelectedItem(state);
            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);
            final Membership membership = controller
                .findMembership(selected.getObjectId(), key)
                .get();

            return new Text(String.format(
                "%s, %s",
                membership.getMember().getPersonName().getSurname(),
                membership.getMember().getPersonName().getGivenName()
            ));
        }

    }

    private class EditLinkCellRenderer
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
            final int col) {

            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final PermissionChecker permissionChecker = cdiUtil
                .findBean(PermissionChecker.class);

            final SciProject selected = (SciProject) itemModel
                .getSelectedItem(state);
            final boolean canEdit = permissionChecker
                .isPermitted(ItemPrivileges.EDIT, selected);

            if (canEdit) {
                return new ControlLink((Component) value);
            } else {
                return new Text("");
            }

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
            final int col) {

            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final PermissionChecker permissionChecker = cdiUtil
                .findBean(PermissionChecker.class);

            final SciProject selected = (SciProject) itemModel
                .getSelectedItem(state);
            final boolean canEdit = permissionChecker
                .isPermitted(ItemPrivileges.EDIT, selected);

            if (canEdit) {
                final ControlLink link = new ControlLink((Component) value);
                link.setConfirmation(new GlobalizedMessage(
                    "cms.contenttypes.ui.sciproject.confirm_delete",
                    SciProjectConstants.SCI_PROJECT_BUNDLE
                ));
                return link;
            } else {
                return new Text("");
            }
        }

    }

    @Override
    public void cellSelected(final TableActionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();
        final SciProject selected = (SciProject) itemModel
            .getSelectedItem(state);
        final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
        final SciProjectController controller = cdiUtil
            .findBean(SciProjectController.class);

        final Membership membership = controller
            .findMembership(selected.getObjectId(), event.getRowKey())
            .get();

        final TableColumn column = getColumnModel()
            .get(event.getColumn().intValue());

        if (TABLE_COL_EDIT.equals(column.getHeaderKey())) {
            // Nothing
        } else if (TABLE_COL_EDIT_LINK.equals(column.getHeaderKey())) {
            membersStep.setSelectedMember(state, membership.getMember());
            membersStep.setSelectedMemberRole(state, membership.getRole());
            membersStep.setSelectedMemberStatus(
                state,
                membership.getStatus().toString()
            );
            membersStep
                .showComponent(state,
                               SciProjectUiConstants.ADD_MEMBER_SHEET_NAME);
        } else if (TABLE_COL_DEL.equals(column.getHeaderKey())) {
            controller.removeMember(selected.getObjectId(),
                                    membership.getMembershipId());
        }
    }

    @Override
    public void headSelected(final TableActionEvent event) {

        // Nothing
    }

}
