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
import org.scientificcms.publications.InternetArticle;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.InternetArticleItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class InternetArticleOrganizationSheet
    extends Table
    implements TableActionListener {

    private static final String TABLE_COL_EDIT = "table_col_edit";

    private static final String TABLE_COL_DEL = "table_col_del";

    private final ItemSelectionModel itemModel;

    public InternetArticleOrganizationSheet(final ItemSelectionModel itemModel) {
        super();
        this.itemModel = itemModel;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.internetarticle.organization.none",
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
                        "publications.ui.internetarticle.organization",
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
                        "publications.ui.internetarticle.organization.remove",
                        SciPublicationsConstants.BUNDLE
                    )
                ),
                TABLE_COL_DEL));

        setModelBuilder(
            new InternetArticleOrganizationSheetModelBuilder(itemModel)
        );
        columnModel.get(0).setCellRenderer(new EditCellRenderer());
        columnModel.get(1).setCellRenderer(new DeleteCellRenderer());

        addTableActionListener(this);
    }

    @Override
    public void cellSelected(final TableActionEvent event) {
        final PageState state = event.getPageState();

        final InternetArticleItem articleItem = (InternetArticleItem) itemModel
            .getSelectedObject(state);

        final TableColumn column = getColumnModel().get(event.getColumn());

        if (column.getHeaderKey().toString().equals(TABLE_COL_EDIT)) {
            // Nothing
        } else if (column.getHeaderKey().toString().equals(TABLE_COL_DEL)) {
            final InternetArticleController controller = CdiUtil
                .createCdiUtil()
                .findBean(InternetArticleController.class);
            controller.unsetOrganization(
                articleItem.getPublication().getPublicationId()
            );
        }
    }

    @Override
    public void headSelected(final TableActionEvent event) {
        //Nothing to do
    }

    private class InternetArticleOrganizationSheetModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private final ItemSelectionModel itemModel;

        public InternetArticleOrganizationSheetModelBuilder(
            final ItemSelectionModel itemModel
        ) {
            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {
            table.getRowSelectionModel().clearSelection(state);
            final InternetArticleItem articleItem
                                          = (InternetArticleItem) itemModel.
                    getSelectedObject(state);
            return new InternetArticleOrganizationSheetModel(
                table, state, articleItem.getPublication()
            );
        }

    }

    private class InternetArticleOrganizationSheetModel
        implements TableModel {

        private final Table table;

        private final Organization orga;

        private boolean done;

        public InternetArticleOrganizationSheetModel(
            final Table table,
            final PageState state,
            final InternetArticle article
        ) {
            this.table = table;
            orga = article.getOrganization();
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
                            "publication.ui.internetarticle.organization.remove",
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
            final int col
        ) {
            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final PermissionChecker permissionChecker = cdiUtil.findBean(
                PermissionChecker.class
            );

            final InternetArticleItem articleItem
                                          = (InternetArticleItem) itemModel
                    .getSelectedObject(state);
            final boolean canEdit = permissionChecker
                .isPermitted(ItemPrivileges.EDIT, articleItem);

            if (canEdit) {
                final ControlLink link = new ControlLink((Component) value);
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publications.ui.internetarticle.organization.remove.confirm",
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
