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
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ProceedingsItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ProceedingsOrganizerSheet
    extends Table
    implements TableActionListener {

    private static final String TABLE_COL_EDIT = "table_col_edit";

    private static final String TABLE_COL_DEL = "table_col_del";

    private final ItemSelectionModel itemModel;

    public ProceedingsOrganizerSheet(final ItemSelectionModel itemModel) {
        super();

        this.itemModel = itemModel;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.proceedings.organizer.none",
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
                        "publications.ui.proceedings.organizer",
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
                        "publications.ui.proceedings.organizer.remove",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_DEL
            )
        );

        setModelBuilder(new ProceedingsOrganizerSheetModelBuilder(itemModel));
        columnModel.get(0).setCellRenderer(new EditCellRenderer());
        columnModel.get(1).setCellRenderer(new DeleteCellRenderer());

        addTableActionListener(this);
    }

    @Override
    public void cellSelected(final TableActionEvent event) {
        final PageState state = event.getPageState();

        final ProceedingsItem proceedingsItem = (ProceedingsItem) itemModel
            .getSelectedItem(state);

        final TableColumn column = getColumnModel().get(event.getColumn());

        if (column.getHeaderKey().toString().equals(TABLE_COL_EDIT)) {
            // Nothing
        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DEL)) {
            final ProceedingsController controller = CdiUtil
                .createCdiUtil()
                .findBean(ProceedingsController.class);
            controller.unsetOrganizier(
                proceedingsItem.getPublication().getPublicationId()
            );
        }
    }

    @Override
    public void headSelected(final TableActionEvent event) {
        //Nothing to do
    }
    
    private class ProceedingsOrganizerSheetModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;

        public ProceedingsOrganizerSheetModelBuilder(
            final ItemSelectionModel itemModel
        ) {
            this.itemModel = itemModel;
        }

        @Override
        public final TableModel makeModel(
            final Table table, final PageState state
        ) {
            table.getRowSelectionModel().clearSelection(state);
            final ProceedingsItem proceedingsItem = (ProceedingsItem) itemModel
                .getSelectedItem(state);
            return new ProceedingsOrganizerSheetModel(
                table, state, proceedingsItem.getPublication()
            );
        }

    }

    private class ProceedingsOrganizerSheetModel implements TableModel {

        private final Table table;

        private final Organization organizer;

        private boolean done;

        public ProceedingsOrganizerSheetModel(
            final Table table,
            final PageState state,
            final Proceedings proceedings
        ) {
            this.table = table;
            organizer = proceedings.getOrganizer();
            done = organizer != null;
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
                    return organizer.getTitle();
                case 1:
                    return new Label(
                        new GlobalizedMessage(
                            "publications.ui.proceedings.organizer.remove",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return organizer.getObjectId();
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

            final ProceedingsItem proceedingsItem = (ProceedingsItem) itemModel
                .getSelectedItem(state);

            final boolean canEdit = permissionChecker.isPermitted(
                ItemPrivileges.DELETE, proceedingsItem
            );

            if (canEdit) {
                final ControlLink link = new ControlLink((Component) value);
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publication.ui.proceedings.organizer.remove.confirm",
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
