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
import com.arsdigita.bebop.table.TableCellRenderer;
import com.arsdigita.bebop.table.TableColumn;
import com.arsdigita.bebop.table.TableColumnModel;
import com.arsdigita.bebop.table.TableModel;
import com.arsdigita.bebop.table.TableModelBuilder;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.util.LockableImpl;

import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.security.PermissionChecker;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.contenttypes.sciproject.Contact;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;
import org.scientificcms.contenttypes.sciproject.Sponsoring;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectSponsorSheet
    extends Table implements TableActionListener {

    private final String TABLE_COL_EDIT = "table_col_edit";

    private final String TABLE_COL_EDIT_ASSOC = "table_col_edit_assoc";

    private final String TABLE_COL_DEL = "table_col_del";

    private final String TABLE_COL_UP = "table_col_up";

    private final String TABLE_COL_DOWN = "table_col_down";

    private final String SELECTED_PROJECT
                             = "selected_project_sponsor_association_project";

    private final String SELECTED_SPONSOR
                             = "selected_project_sponsor_association_sponsor";

    private final ItemSelectionModel itemModel;

    private final SciProjectSponsorsStep editStep;

    public SciProjectSponsorSheet(final ItemSelectionModel itemModel,
                                  final SciProjectSponsorsStep editStep) {

        super();
        this.itemModel = itemModel;
        this.editStep = editStep;

        setEmptyView(new Label(new GlobalizedMessage(
            "sciproject.ui.sponsor_none",
            SciProjectConstants.SCI_PROJECT_BUNDLE)));

        final TableColumnModel columnModel = getColumnModel();
        columnModel.add(new TableColumn(
            0,
            new GlobalizedMessage("sciproject.ui.sponsor_name",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE),
            TABLE_COL_EDIT));
        columnModel.add(new TableColumn(
            1,
            new GlobalizedMessage("sciproject.ui.sponsor_fundingcode",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE)));
        columnModel.add(new TableColumn(
            2,
            new GlobalizedMessage("sciproject.ui.sponsor_edit_assoc",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE),
            TABLE_COL_EDIT_ASSOC));
        columnModel.add(new TableColumn(
            3,
            new GlobalizedMessage("sciproject.ui.sponsor_remove",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE),
            TABLE_COL_DEL));
        columnModel.add(new TableColumn(
            4,
            new GlobalizedMessage(
                "sciproject.ui.sponsor.up",
                SciProjectConstants.SCI_PROJECT_BUNDLE),
            TABLE_COL_UP));
        columnModel.add(new TableColumn(
            5,
            new GlobalizedMessage(
                "sciproject.ui.sponsor.down",
                SciProjectConstants.SCI_PROJECT_BUNDLE),
            TABLE_COL_DOWN));

        setModelBuilder(new ModelBuilder(itemModel));

        columnModel.get(0).setCellRenderer(new EditCellRenderer());
        columnModel.get(2).setCellRenderer(new EditAssocCellRenderer());
        columnModel.get(3).setCellRenderer(new DeleteCellRenderer());
        columnModel.get(4).setCellRenderer(new UpCellRenderer());
        columnModel.get(5).setCellRenderer(new DownCellRenderer());

        addTableActionListener(this);

    }

    @Override
    public void cellSelected(final TableActionEvent event) throws
        FormProcessException {

        final PageState state = event.getPageState();
        final SciProject selected = (SciProject) itemModel
            .getSelectedItem(state);
        final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
        final SciProjectController controller = cdiUtil
            .findBean(SciProjectController.class);
        final Sponsoring sponsoring = controller
            .findSponsoring(selected.getObjectId(), event.getRowKey())
            .get();

        final TableColumn column = getColumnModel().get(event.getColumn());

        if (TABLE_COL_EDIT_ASSOC.equals(column.getHeaderKey())) {
            editStep.setSelectedSponsor(sponsoring.getSponsor());
            editStep.setSelectedSponsorFundingCode(sponsoring.getFundingCode());

            editStep.showComponent(state,
                                   SciProjectUiConstants.ADD_CONTACT_SHEET_NAME);
        } else if (TABLE_COL_DEL.equals(column.getHeaderKey())) {
            controller.removeContact(selected.getObjectId(),
                                     sponsoring.getSponsoringId());
        } else if (TABLE_COL_UP.equals(column.getHeaderKey())) {
            controller.swapWithPreviousContact(selected.getObjectId(),
                                        sponsoring.getSponsoringId());
        } else if (TABLE_COL_DOWN.equals(column.getHeaderKey())) {
            controller.swapWithNextContact(selected.getObjectId(),
                                    sponsoring.getSponsoringId());
        }
    }

    @Override
    public void headSelected(final TableActionEvent event) {

        // Nothing
    }

    private class ModelBuilder
        extends LockableImpl implements TableModelBuilder {

        private final ItemSelectionModel itemModel;

        public ModelBuilder(final ItemSelectionModel itemModel) {
            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {

            table.getRowSelectionModel().clearSelection(state);
            final SciProject project = (SciProject) itemModel
                .getSelectedObject(state);
            return new Model(table, state, project);
        }

    }

    private class Model implements TableModel {

        private final Table table;

        private final Iterator<Map<String, Object>> sponsors;

        private Map<String, Object> currentRow;

        public Model(final Table table, final PageState state,
                     final SciProject project) {
            this.table = table;

            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);

            sponsors = controller.getSponsors(project.getObjectId()).iterator();
        }

        @Override
        public int getColumnCount() {
            return table.getColumnModel().size();
        }

        @Override
        public boolean nextRow() {

            if (sponsors.hasNext()) {
                currentRow = sponsors.next();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Object getElementAt(final int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return currentRow.get(SciProjectController.SPONSOR_NAME);
                case 1:
                    return currentRow
                        .get(SciProjectController.SPONSOR_FUNDING_CODE);
                case 2:
                    return new Label(new GlobalizedMessage(
                        "sciproject.ui.sponsor.edit_assoc",
                        SciProjectConstants.SCI_PROJECT_BUNDLE));
                case 3:
                    return new Label(new GlobalizedMessage(
                        "sciproject.ui.sponsor.remove",
                        SciProjectConstants.SCI_PROJECT_BUNDLE));
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return currentRow.get(SciProjectController.SPONSOR_ID);
        }

    }

    private class EditCellRenderer
        extends LockableImpl
        implements TableCellRenderer {

        @Override
        public Component getComponent(final Table table,
                                      final PageState state,
                                      final Object value,
                                      final boolean isSelected,
                                      final Object key,
                                      final int row,
                                      final int column) {

            final SciProject selected = (SciProject) itemModel
                .getSelectedItem(state);
            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);
            final Optional<Sponsoring> sponsoring = controller
                .findSponsoring(selected.getObjectId(), key);

            return new Text(sponsoring.get().getSponsor().getName());
        }

    }

    private class EditAssocCellRenderer
        extends LockableImpl
        implements TableCellRenderer {

        @Override
        public Component getComponent(final Table table,
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
            Table table,
            PageState state,
            Object value,
            boolean isSelected,
            Object key,
            int row,
            int col) {

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
