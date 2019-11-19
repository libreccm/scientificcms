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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.security.PermissionChecker;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.publications.ArticleInCollectedVolume;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.CollectedVolumeItem;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class CollectedVolumeArticlesTable
    extends Table
    implements TableActionListener {

    private static final Logger LOGGER = LogManager.getLogger(
        CollectedVolumeArticlesTable.class
    );

    private final String TABLE_COL_EDIT = "table_col_edit";

    private final String TABLE_COL_DEL = "table_col_del";

    private final ItemSelectionModel itemModel;

    public CollectedVolumeArticlesTable(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        super();
        this.itemModel = itemModel;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.collected_volume.no_articles",
                    SciPublicationsConstants.BUNDLE
                )
            )
        );

        TableColumnModel colModel = getColumnModel();
        colModel.add(new TableColumn(
            0,
            new Label(
                new GlobalizedMessage(
                    "publications.ui.collected_volume.article",
                    SciPublicationsConstants.BUNDLE
                )),
            TABLE_COL_EDIT));
        colModel.add(new TableColumn(
            1,
            new Label(
                new GlobalizedMessage(
                    "publications.ui.collected_volume.article.remove",
                    SciPublicationsConstants.BUNDLE
                )
            ),
            TABLE_COL_DEL));

        setModelBuilder(
            new CollectedVolumeArticlesTableModelBuilder(itemModel));

        colModel.get(0).setCellRenderer(new EditCellRenderer());
        colModel.get(1).setCellRenderer(new DeleteCellRenderer());

        addTableActionListener(this);
    }

    @Override
    public void cellSelected(final TableActionEvent event) {
        final PageState state = event.getPageState();

        final CollectedVolumeController controller = CdiUtil
            .createCdiUtil()
            .findBean(CollectedVolumeController.class);

        final ArticleInCollectedVolume article = controller
            .findArticle((Long) event.getRowKey());

        final CollectedVolumeItem collectedVolumeItem
                                      = (CollectedVolumeItem) itemModel
                .getSelectedObject(state);
        final CollectedVolume collectedVolume = collectedVolumeItem
            .getPublication();

        final TableColumn column = getColumnModel().get(event.getColumn());

        if (column.getHeaderKey().toString().equals(TABLE_COL_EDIT)) {
            // Nothing
        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DEL)) {
            controller.removeArticle(
                collectedVolume.getPublicationId(),
                article.getPublicationId()
            );
        }
//        else if (column.getHeaderKey().toString().equals(TABLE_COL_UP)) {
//            controller.swapWithPreviousArticle(
//                collectedVolume.getPublicationId(),
//                article.getPublicationId()
//            );
//        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DOWN)) {
//            controller.swapWithNextArticle(
//                collectedVolume.getPublicationId(),
//                article.getPublicationId()
//            );
//        }
    }

    @Override
    public void headSelected(TableActionEvent event) {
        //Nothing to do.
    }

    private class CollectedVolumeArticlesTableModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;

        public CollectedVolumeArticlesTableModelBuilder(
            final ItemSelectionModel itemModel
        ) {
            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {
            table.getRowSelectionModel().clearSelection(state);
            final CollectedVolumeItem collectedVolumeItem
                                          = (CollectedVolumeItem) itemModel
                    .getSelectedItem(state);
            return new CollectedVolumeArticlesTableModel(
                table, state, collectedVolumeItem.getPublication()
            );
        }

    }

    private class CollectedVolumeArticlesTableModel implements TableModel {

        private final Table table;

        private final Iterator<ArticleInCollectedVolume> articles;

        private ArticleInCollectedVolume article;

        private CollectedVolumeArticlesTableModel(
            final Table table,
            final PageState state,
            final CollectedVolume collectedVolume
        ) {
            this.table = table;
            articles = collectedVolume.getArticles().iterator();
        }

        @Override
        public int getColumnCount() {
            return table.getColumnModel().size();
        }

        @Override
        public boolean nextRow() {
            if (articles != null && articles.hasNext()) {
                article = articles.next();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Object getElementAt(final int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return article.getTitle();
                case 1:
                    return new Label(
                        new GlobalizedMessage(
                            "publications.ui.collected_volume.article.remove",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return article.getPublicationId();
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
//            SecurityManager securityManager = Utilities
//                .getSecurityManager(state);
//            CollectedVolume collectedVolume = (CollectedVolume) m_itemModel.
//                getSelectedObject(state);
//
//            boolean canEdit = securityManager.canAccess(
//                state.getRequest(),
//                SecurityManager.EDIT_ITEM,
//                collectedVolume);
//
//            if (canEdit) {
//                ArticleInCollectedVolume article;
//                try {
//                    article = new ArticleInCollectedVolume((BigDecimal) key);
//                } catch (ObjectNotFoundException ex) {
//                    s_log.warn(String.format("No object with key '%s' found.",
//                                             key),
//                               ex);
//                    return new Label(value.toString());
//                }
//
//                ContentSection section = article.getContentSection();//CMS.getContext().getContentSection();
//                ItemResolver resolver = section.getItemResolver();
//                Link link = new Link(value.toString(),
//                                     resolver.generateItemURL(state,
//                                                              article,
//                                                              section,
//                                                              article
//                                                                  .getVersion()));
//
//                return link;
//            } else {
//                ArticleInCollectedVolume article;
//                try {
//                    article = new ArticleInCollectedVolume((BigDecimal) key);
//                } catch (ObjectNotFoundException ex) {
//                    s_log.warn(String.format("No object with key '%s' found.",
//                                             key),
//                               ex);
//                    return new Label(value.toString());
//                }
//
//                Label label = new Label(value.toString());
//                return label;
//            }
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
            final PermissionChecker permissionChecker = CdiUtil
                .createCdiUtil()
                .findBean(PermissionChecker.class);

            final CollectedVolumeItem collectedVolumeItem
                                          = (CollectedVolumeItem) itemModel
                    .getSelectedObject(state);

            boolean canEdit = permissionChecker
                .isPermitted(ItemPrivileges.DELETE, collectedVolumeItem);

            if (canEdit) {
                ControlLink link = new ControlLink((Label) value);
                link.setConfirmation(new GlobalizedMessage(
                    "publications.ui.collected_volume.articles.confirm_remove"));
                return link;
            } else {
                return new Text("");
            }
        }

    }

}
