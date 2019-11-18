/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.Component;
import com.arsdigita.bebop.ControlLink;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.Link;
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

import org.hibernate.secure.spi.PermissionCheckEntityInformation;
import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.security.PermissionChecker;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.publications.InProceedings;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.InProceedingsItem;

import java.math.BigDecimal;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class InProceedingsProceedingsSheet
    extends Table
    implements TableActionListener {

    private static final String TABLE_COL_EDIT = "table_col_edit";

    private static final String TABLE_COL_DEL = "table_col_del";

    private final ItemSelectionModel itemModel;

    public InProceedingsProceedingsSheet(final ItemSelectionModel itemModel) {
        super();
        this.itemModel = itemModel;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.inProceedings.proceedings.none",
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
                        "publications.ui.inProceedings.proceedings",
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
                        "publications.ui.inProceedings.proceedings.remove",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_DEL
            )
        );

        setModelBuilder(new InProceedingsProceedingsSheetModelBuilder(
            itemModel));
        columnModel.get(0).setCellRenderer(new EditCellRenderer());
        columnModel.get(1).setCellRenderer(new DeleteCellRenderer());

        addTableActionListener(this);
    }

    @Override
    public void cellSelected(final TableActionEvent event) {
        PageState state = event.getPageState();

        final InProceedingsItem inProceedingsItem
                                    = (InProceedingsItem) itemModel.
                getSelectedItem(state);
        final InProceedings inProceedings = inProceedingsItem.getPublication();

        final TableColumn column = getColumnModel().get(event.getColumn());

        if (column.getHeaderKey().toString().equals(TABLE_COL_EDIT)) {
            // Nothing
        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DEL)) {
            final InProceedingsController controller = CdiUtil
                .createCdiUtil()
                .findBean(InProceedingsController.class);
            controller.unsetProcceedings(
                inProceedings.getPublicationId(),
                inProceedings.getProceedings().getPublicationId()
            );
        }
    }

    @Override
    public void headSelected(final TableActionEvent event) {
        //Nothing to do
    }

    private class InProceedingsProceedingsSheetModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private ItemSelectionModel itemModel;

        public InProceedingsProceedingsSheetModelBuilder(
            ItemSelectionModel itemModel) {
            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {
            table.getRowSelectionModel().clearSelection(state);
            final InProceedingsItem inProceedingsItem
                                        = (InProceedingsItem) itemModel.
                    getSelectedItem(state);
            return new InProceedingsProceedingsSheetModel(
                table,
                state,
                inProceedingsItem.getPublication()
            );
        }

    }

    private class InProceedingsProceedingsSheetModel implements TableModel {

        private final Table table;
        private final Proceedings proceedings;
        private boolean done;

        public InProceedingsProceedingsSheetModel(
            final Table table,
            final PageState state,
            final InProceedings inProceedings
        ) {
            this.table = table;
            this.proceedings = inProceedings.getProceedings();
            if (proceedings == null) {
                done = false;
            } else {
                done = true;
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
                    return proceedings.getTitle();
                case 1:
                    return new Label(new GlobalizedMessage(
                        "publications.ui.inProceedings.proceedings.remove"));
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return proceedings.getPublicationId();
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
            final PermissionChecker permissionChecker = cdiUtil.findBean(
                PermissionChecker.class
            );

            final InProceedingsItem inProceedingsItem
                                        = (InProceedingsItem) itemModel.
                    getSelectedItem(state);

            final boolean canEdit = permissionChecker
                .isPermitted(ItemPrivileges.EDIT, inProceedingsItem);

            if (canEdit) {
                ControlLink link = new ControlLink((Component) value);
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publications.ui.inProceedings.proceedings.confirm_remove",
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
