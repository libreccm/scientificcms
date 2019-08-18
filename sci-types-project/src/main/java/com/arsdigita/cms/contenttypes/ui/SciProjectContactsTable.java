/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.BoxPanel;
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
import org.scientificcms.contenttypes.sciproject.Contact;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConfig;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectContactsTable
    extends Table
    implements TableActionListener {

    private static final Logger LOGGER = LogManager
        .getLogger(SciProjectContactsTable.class);

    private final static String TABLE_COL_EDIT = "table_col_edit";

    private final static String TABLE_COL_EDIT_ASSOC = "table_col_edit_assoc";

    private final static String TABLE_COL_DEL = "table_col_del";

    private final static String TABLE_COL_UP = "table_col_up";

    private final static String TABLE_COL_DOWN = "table_col_down";

    private final ItemSelectionModel itemModel;

    private final SciProjectContactsStep editStep;

    public SciProjectContactsTable(final ItemSelectionModel itemModel,
                                   final SciProjectContactsStep editStep) {

        super();

        this.itemModel = itemModel;
        this.editStep = editStep;

        setEmptyView(new Label(new GlobalizedMessage(
            "cms.contenttypes.ui.sciproject.contacts.none",
            SciProjectConstants.SCI_PROJECT_BUNDLE)));

        final TableColumnModel columnModel = getColumnModel();
        columnModel.add(new TableColumn(
            0,
            new GlobalizedMessage(
                "cms.contenttypes.ui.sciproject.contact.type",
                SciProjectConstants.SCI_PROJECT_BUNDLE)));
        columnModel.add(new TableColumn(
            1,
            new GlobalizedMessage(
                "cms.contenttypes.ui.sciproject.contact.title",
                SciProjectConstants.SCI_PROJECT_BUNDLE),
            TABLE_COL_EDIT));
        columnModel.add(new TableColumn(
            2,
            new GlobalizedMessage(
                "cms.contenttypes.ui.sciproject.contact.edit",
                SciProjectConstants.SCI_PROJECT_BUNDLE),
            TABLE_COL_EDIT_ASSOC));
        columnModel.add(new TableColumn(
            3,
            new GlobalizedMessage(
                "cms.contenttypes.ui.sciproject.contact.action",
                SciProjectConstants.SCI_PROJECT_BUNDLE),
            TABLE_COL_DEL));
        columnModel.add(new TableColumn(
            4,
            new GlobalizedMessage(
                "cms.contenttypes.ui.sciproject.contact.up",
                SciProjectConstants.SCI_PROJECT_BUNDLE),
            TABLE_COL_UP));
        columnModel.add(new TableColumn(
            5,
            new GlobalizedMessage(
                "cms.contenttypes.ui.sciproject.contact.down",
                SciProjectConstants.SCI_PROJECT_BUNDLE),
            TABLE_COL_DOWN));

        setModelBuilder(new SciProjectContactsTableModelBuilder(itemModel));

        columnModel.get(1).setCellRenderer(new EditCellRenderer());
        columnModel.get(2).setCellRenderer(new EditAssocCellRenderer());
        columnModel.get(3).setCellRenderer(new DeleteCellRenderer());
        columnModel.get(4).setCellRenderer(new UpCellRenderer());
        columnModel.get(5).setCellRenderer(new DownCellRenderer());

        addTableActionListener(this);
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
        final Contact contact = controller.findContact(selected.getObjectId(),
                                                       event.getRowKey())
            .get();

        final TableColumn column = getColumnModel()
            .get(event.getColumn().intValue());

        if (TABLE_COL_EDIT_ASSOC.equals(column.getHeaderKey())) {
            editStep.setSelectedContact(contact.getContactable());
            editStep.setSelectedContactType(contact.getContactType());

            editStep.showComponent(state,
                                   SciProjectUiConstants.ADD_CONTACT_SHEET_NAME);
        } else if (TABLE_COL_DEL.equals(column.getHeaderKey())) {
            controller.removeContact(selected.getObjectId(),
                                     contact.getContactId());
        } else if (TABLE_COL_UP.equals(column.getHeaderKey())) {
            controller.swapWithPreviousContact(selected.getObjectId(),
                                               contact.getContactId());
        } else if (TABLE_COL_DOWN.equals(column.getHeaderKey())) {
            controller.swapWithNextContact(selected.getObjectId(),
                                           contact.getContactId());
        }
    }

    @Override
    public void headSelected(final TableActionEvent event) {

        // Nothing
    }

    private class SciProjectContactsTableModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;

        public SciProjectContactsTableModelBuilder(
            final ItemSelectionModel itemModel) {

            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {

            table.getRowSelectionModel().clearSelection(state);
            final SciProject selected = (SciProject) itemModel
                .getSelectedItem(state);

            return new SciProjectContactsTableModel(table, state, selected);
        }

    }

    private class SciProjectContactsTableModel implements TableModel {

        private final Table table;

        private final Iterator<Map<String, Object>> iterator;

        private Map<String, Object> currentRow;

        public SciProjectContactsTableModel(final Table table,
                                            final PageState state,
                                            final SciProject selected) {

            this.table = table;

            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);
            iterator = controller.getContacts(selected.getObjectId()).iterator();
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
                    final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
                    final ConfigurationManager confManager = cdiUtil
                        .findBean(ConfigurationManager.class);
                    final SciProjectConfig config = confManager
                        .findConfiguration(SciProjectConfig.class);
                    final String type = (String) currentRow
                        .get(SciProjectController.CONTACT_TYPE);
                    return new GlobalizedMessage(type,
                                                 config
                                                     .getContactTypesBundleName());
                }
                case 1:
                    return currentRow.get(SciProjectController.CONTACT_NAME);
                case 2:
                    return new Label(new GlobalizedMessage(
                        "cms.ui.edit_assoc", CmsConstants.CMS_BUNDLE));
                case 3:
                    return new Label(new GlobalizedMessage(
                        "cms.ui.delete", CmsConstants.CMS_BUNDLE));
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return currentRow.get(SciProjectController.CONTACT_ID);
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

//            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
//            final PermissionChecker permissionChecker = cdiUtil
//                .findBean(PermissionChecker.class);
//
//            final SciProject selected = (SciProject) itemModel
//                .getSelectedItem(state);
//            final boolean canEdit = permissionChecker
//                .isPermitted(ItemPrivileges.EDIT, selected);
//
//            if (canEdit) {
//                final SciProjectController controller = cdiUtil
//                    .findBean(SciProjectController.class);
//                final ContentItemManager itemManager = cdiUtil
//                    .findBean(ContentItemManager.class);
//                final ContentSectionManager sectionManager = cdiUtil
//                    .findBean(ContentSectionManager.class);
//                final Optional<Contact> contact = controller
//                    .findContact(selected.getObjectId(), key);
//
//                final ContentSection section = itemManager
//                    .getItemFolder(selected)
//                    .get()
//                    .getSection();
//                final ItemResolver resolver = sectionManager
//                    .getItemResolver(section);
//            } else {
//
//            }
            final SciProject selected = (SciProject) itemModel
                .getSelectedItem(state);
            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);
            final Optional<Contact> contact = controller
                .findContact(selected.getObjectId(), key);
            final String name = contact.get().getContactable().getDisplayName();
            final String type = contact.get().getContactType();

            final Label typeLabel = new Label(new GlobalizedMessage(
                type, controller.getContactTypesBundleName()));

            final Text nameText = new Text(name);

            final BoxPanel panel = new BoxPanel(BoxPanel.HORIZONTAL);
            panel.add(nameText);
            panel.add(typeLabel);

            return panel;
        }

    }

    private class EditAssocCellRenderer
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

    private class UpCellRenderer extends LockableImpl implements
        TableCellRenderer {

        @Override
        public Component getComponent(
            Table table,
            PageState state,
            Object value,
            boolean isSelected,
            Object key,
            int row,
            int col) {

            if (0 == row) {
                return new Label();
            } else {
                ControlLink link = new ControlLink("up");
                return link;
            }

        }

    }

    private class DownCellRenderer extends LockableImpl implements
        TableCellRenderer {

        @Override
        public Component getComponent(
            Table table,
            PageState state,
            Object value,
            boolean isSelected,
            Object key,
            int row,
            int col) {

            final SciProject selected = (SciProject) itemModel
                .getSelectedItem(state);
            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);
            final List<Map<String, Object>> contacts = controller
                .getContacts(selected.getObjectId());

            if ((contacts.size() - 1) == row) {
                return new Label();
            } else {
                ControlLink link = new ControlLink("down");
                return link;
            }
        }

    }

}
