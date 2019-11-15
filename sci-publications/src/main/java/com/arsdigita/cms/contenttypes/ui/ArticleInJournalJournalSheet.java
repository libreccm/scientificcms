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
import org.scientificcms.publications.Journal;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ArticleInJournalItem;


/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ArticleInJournalJournalSheet
    extends Table
    implements TableActionListener {

    private final String TABLE_COL_EDIT = "table_col_edit";

    private final String TABLE_COL_DEL = "table_col_del";

    private ItemSelectionModel itemSelectionModel;

    public ArticleInJournalJournalSheet(
        final ItemSelectionModel itemSelectionModel,
        final StringParameter selectedLangParam
    ) {
        super();
        this.itemSelectionModel = itemSelectionModel;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.articleInJournal.journal.none",
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
                        "publications.ui.articleInJournal.journal",
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
                        "publications.ui.articleInJournal.journal.remove",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_DEL
            )
        );

        setModelBuilder(
            new ArticleInJournalJournalSheetModelBuilder(
                itemSelectionModel, selectedLangParam
            )
        );
        columnModel.get(0).setCellRenderer(new EditCellRenderer());
        columnModel.get(1).setCellRenderer(new DeleteCellRenderer());

        addTableActionListener(this);
    }

    @Override
    public void cellSelected(final TableActionEvent event) {
        final PageState state = event.getPageState();

        final ArticleInJournalItem article
                                       = (ArticleInJournalItem) itemSelectionModel
                .getSelectedObject(state);

        final TableColumn column = getColumnModel().get(event.getColumn());

        if (column.getHeaderKey().toString().equals(TABLE_COL_EDIT)) {
            // Nothing
        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DEL)) {
            final ArticleInJournalController controller = CdiUtil
                .createCdiUtil()
                .findBean(ArticleInJournalController.class);
            controller.unsetJournal(article.getPublication().getPublicationId());
        }
    }

    @Override
    public void headSelected(TableActionEvent event) {
        //Nothing to do
    }

    private class ArticleInJournalJournalSheetModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;
        private final StringParameter selectedLangParam;

        public ArticleInJournalJournalSheetModelBuilder(
            final ItemSelectionModel itemModel,
            final StringParameter selectedLangParam
        ) {
            this.itemModel = itemModel;
            this.selectedLangParam = selectedLangParam;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {
            table.getRowSelectionModel().clearSelection(state);
            final ArticleInJournalItem article
                                           = (ArticleInJournalItem) itemModel.
                    getSelectedObject(state);
            return new ArticleInJournalJournalSheetModel(
                table, state, article, selectedLangParam);
        }

    }

    private class ArticleInJournalJournalSheetModel implements TableModel {

        private final Table table;

        private final String journalTitle;

        private final Long journalId;

        private boolean done;

        public ArticleInJournalJournalSheetModel(
            final Table table,
            final PageState state,
            final ArticleInJournalItem articleItem,
            final StringParameter selectedLangParam
        ) {
            this.table = table;
            final Journal journal = articleItem
                .getPublication()
                .getJournal();
            if (journal == null) {
                done = false;
                journalTitle = null;
                journalId = null;
            } else {
                done = true;
                journalTitle = journal.getTitle();
                journalId = journal.getJournalId();
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
                    return journalTitle;
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
        public Object getKeyAt(final int columnIndex) {
            return journalId;
        }

    }

    private class EditCellRenderer
        extends LockableImpl
        implements TableCellRenderer {

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
            final ArticleInJournalItem articleItem
                                           = (ArticleInJournalItem) itemSelectionModel
                    .getSelectedItem(state);
            final boolean canEdit = permissionChecker.isPermitted(
                ItemPrivileges.EDIT, articleItem
            );

            if (canEdit) {
                final ControlLink link = new ControlLink((Component) value);
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publication.ui.articleInJournal.journal.confirm_remove",
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
