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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.security.PermissionChecker;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.publications.InProceedings;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ProceedingsItem;

import java.util.Iterator;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ProceedingsPapersTable
    extends Table
    implements TableActionListener {

    private final Logger LOGGER = LogManager.getLogger(
        ProceedingsPapersTable.class);

    private final String TABLE_COL_EDIT = "table_col_edit";

    private final String TABLE_COL_DEL = "table_col_del";

    private final String TABLE_COL_UP = "table_col_up";

    private final String TABLE_COL_DOWN = "table_col_down";

    private final ItemSelectionModel itemModel;

    public ProceedingsPapersTable(final ItemSelectionModel itemModel) {
        super();
        this.itemModel = itemModel;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.procreedings.no_papers",
                    SciPublicationsConstants.BUNDLE
                )
            )
        );

        TableColumnModel colModel = getColumnModel();
        colModel.add(
            new TableColumn(
                0,
                new Label(new GlobalizedMessage(
                    "publications.ui.proceedings.paper",
                    SciPublicationsConstants.BUNDLE
                )
                ),
                TABLE_COL_EDIT));
        colModel.add(
            new TableColumn(
                1,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.proceedings.paper.remove",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_DEL));

        setModelBuilder(
            new ProceedingsPapersTableModelBuilder(itemModel));

        colModel.get(0).setCellRenderer(new EditCellRenderer());
        colModel.get(1).setCellRenderer(new DeleteCellRenderer());

        addTableActionListener(this);
    }

    @Override
    public void cellSelected(final TableActionEvent event) {
        final PageState state = event.getPageState();

        final ProceedingsController controller = CdiUtil
            .createCdiUtil()
            .findBean(ProceedingsController.class);

        final InProceedings paper = controller
            .findPaper((Long) event.getRowKey());

        final ProceedingsItem proceedingsItem = (ProceedingsItem) itemModel
            .getSelectedItem(state);
        final Proceedings proceedings = proceedingsItem.getPublication();

        TableColumn column = getColumnModel().get(event.getColumn());

        if (column.getHeaderKey().toString().equals(TABLE_COL_EDIT)) {
            // Nothing
        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DEL)) {
            controller.removePaper(
                proceedings.getPublicationId(),
                paper.getPublicationId()
            );
        }
    }

    @Override
    public void headSelected(TableActionEvent event) {
        //Noting to do
    }

    private class ProceedingsPapersTableModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;

        public ProceedingsPapersTableModelBuilder(
            final ItemSelectionModel itemModel
        ) {
            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {
            table.getRowSelectionModel().clearSelection(state);
            final ProceedingsItem proceedings = (ProceedingsItem) itemModel
                .getSelectedObject(state);
            return new ProceedingsPapersTableModel(
                table, state, proceedings.getPublication()
            );
        }

    }

    private class ProceedingsPapersTableModel implements TableModel {

        private final Table table;

        private final Iterator<InProceedings> papers;

        private InProceedings paper;

        private ProceedingsPapersTableModel(
            final Table table,
            final PageState state,
            final Proceedings proceedings
        ) {
            this.table = table;
            papers = proceedings.getPapers().iterator();
        }

        @Override
        public int getColumnCount() {
            return table.getColumnModel().size();
        }

        @Override
        public boolean nextRow() {
            if (papers != null && papers.hasNext()) {
                paper = papers.next();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Object getElementAt(final int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return paper.getTitle();
                case 1:
                    return new Label(
                        new GlobalizedMessage(
                            "publications.ui.proceedings.paper.remove",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return paper.getPublicationId();
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
            final int col
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

            final ProceedingsItem proceedingsItem = (ProceedingsItem) itemModel
                .getSelectedItem(state);

            final boolean canEdit = permissionChecker
                .isPermitted(ItemPrivileges.EDIT, proceedingsItem
                );

            if (canEdit) {
                final ControlLink link = new ControlLink((Component) value);
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publications.ui.proceedings.paper.confirm_remove",
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
