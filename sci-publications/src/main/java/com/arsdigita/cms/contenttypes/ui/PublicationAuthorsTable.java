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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.security.PermissionChecker;
import org.librecms.assets.Person;
import org.librecms.contentsection.privileges.ItemPrivileges;
import org.scientificcms.publications.Authorship;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.PublicationItem;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationAuthorsTable
    extends Table
    implements TableActionListener {

    private static final Logger LOGGER = LogManager.getLogger(
        PublicationAuthorsTable.class
    );

    private final String TABLE_COL_EDIT = "table_col_edit";

    private final String TABLE_COL_EDIT_ASSOC = "table_col_edit_assoc";

    private final String TABLE_COL_DEL = "table_col_del";

    private final String TABLE_COL_UP = "table_col_up";

    private final String TABLE_COL_DOWN = "table_col_down";

    public static final String SELECTED_PUBLICATION
                                   = "selected_publication_author_association_publication";

    public static final String SELECTED_AUTHOR
                                   = "selected_publication_author_association_author";

    private final ItemSelectionModel itemModel;

    private final PublicationAuthorsPropertyStep editStep;

    public PublicationAuthorsTable(
        final ItemSelectionModel itemModel, 
        final PublicationAuthorsPropertyStep editStep
    ) {
        super();
        this.itemModel = itemModel;
        this.editStep = editStep;

        setEmptyView(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.authors.none",
                    SciPublicationsConstants.BUNDLE
                )
            )
        );

        final TableColumnModel colModel = getColumnModel();
        colModel.add(
            new TableColumn(
                0,
                new GlobalizedMessage(
                    "publications.ui.authors.author.name",
                    SciPublicationsConstants.BUNDLE
                ),
                TABLE_COL_EDIT
            )
        );
        colModel.add(
            new TableColumn(
                1,
                new GlobalizedMessage(
                    "publications.ui.authors.author.isEditor",
                    SciPublicationsConstants.BUNDLE
                )
            )
        );
        colModel.add(
            new TableColumn(
                2,
                new GlobalizedMessage(
                    "publications.ui.authors.edit_assoc",
                    SciPublicationsConstants.BUNDLE
                ),
                TABLE_COL_EDIT_ASSOC
            )
        );
        colModel.add(
            new TableColumn(
                3,
                new GlobalizedMessage(
                    "publications.ui.authors.author.delete",
                    SciPublicationsConstants.BUNDLE
                ),
                TABLE_COL_DEL
            )
        );
        colModel.add(
            new TableColumn(
                4,
                new GlobalizedMessage(
                    "publications.ui.authors.author.up",
                    SciPublicationsConstants.BUNDLE
                ),
                TABLE_COL_UP));
        colModel.add(
            new TableColumn(
                5,
                new GlobalizedMessage(
                    "publications.ui.authors.author.down",
                    SciPublicationsConstants.BUNDLE
                ),
                TABLE_COL_DOWN
            )
        );

        setModelBuilder(
            new PublicationAuthorsTableModelBuilder(itemModel));

        colModel.get(0).setCellRenderer(new EditCellRenderer());
        colModel.get(2).setCellRenderer(new EditAssocCellRenderer());
        colModel.get(3).setCellRenderer(new DeleteCellRenderer());
        colModel.get(4).setCellRenderer(new UpCellRenderer());
        colModel.get(5).setCellRenderer(new DownCellRenderer());

        LOGGER.info("Adding table action listener...");
        addTableActionListener(this);
    }

    private class PublicationAuthorsTableModelBuilder
        extends LockableImpl
        implements TableModelBuilder {

        private ItemSelectionModel itemModel;

        public PublicationAuthorsTableModelBuilder(
            final ItemSelectionModel itemModel
        ) {
            this.itemModel = itemModel;
        }

        @Override
        public TableModel makeModel(final Table table, final PageState state) {

            table.getRowSelectionModel().clearSelection(state);
            final PublicationItem<?> publication = (PublicationItem) itemModel
                .getSelectedObject(state);
            return new PublicationAuthorsTableModel(table, state, publication);
        }

    }

    @Override
    public void cellSelected(final TableActionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();
        final PublicationItem<?> selected = (PublicationItem<?>) itemModel
            .getSelectedItem(state);
        final SciPublicationsController controller = CdiUtil
            .createCdiUtil()
            .findBean(SciPublicationsController.class);
        final Authorship authorship = controller.findAuthorship(
            selected.getPublication().getPublicationId(), event.getRowKey()
        ).get();

        final TableColumn column = getColumnModel().get(event.getColumn());

        if (TABLE_COL_EDIT_ASSOC.equals(column.getHeaderKey())) {
            editStep.setSelectedAuthor(authorship.getAuthor());
            editStep.setSelectedAuthorEditor(authorship.isEditor());
            
            editStep.showComponent(state, "AuthorsEntryForm");
        } else if (TABLE_COL_DEL.equals(column.getHeaderKey())) {
            controller.removeAuthor(
                selected.getPublication().getPublicationId(), 
                authorship.getAuthorshipId()
            );
        } else if (TABLE_COL_UP.equals(column.getHeaderKey())) {
            controller.swapWithPrevAuthorship(
                selected.getPublication().getPublicationId(), 
                authorship.getAuthorshipId()
            );
        } else if (TABLE_COL_DOWN.equals(column.getHeaderKey())) {
            controller.swapWithNextAuthorship(
                selected.getPublication().getPublicationId(), 
                authorship.getAuthorshipId()
            );

        }
    }

    @Override
    public void headSelected(final TableActionEvent event) {

        // Nothing
    }

    private class PublicationAuthorsTableModel implements TableModel {

        private static final int MAX_DESC_LENGTH = 25;

        private final Table table;

        private final Iterator<Map<String, Object>> iterator;

        private Map<String, Object> currentRow;

        public PublicationAuthorsTableModel(
            final Table table,
            final PageState state,
            final PublicationItem<?> publicationItem
        ) {
            this.table = table;

            final SciPublicationsController controller = CdiUtil
                .createCdiUtil()
                .findBean(SciPublicationsController.class);
            iterator = controller.getAuthors(
                publicationItem.getPublication().getPublicationId()
            ).iterator();
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
                    return currentRow.get(
                        SciPublicationsController.AUTHOR_NAME
                    );
                case 1: {
                    final boolean isEditor = (boolean) currentRow
                        .get(SciPublicationsController.AUTHORSHIP_IS_EDITOR
                        );
                    if (isEditor) {
                        return new Label(
                            new GlobalizedMessage(
                                "publications.ui.authors.author.is_editor_true",
                                SciPublicationsConstants.BUNDLE
                            )
                        );
                    } else {
                        return new Label(
                            new GlobalizedMessage(
                                "publications.ui.authors.author.is_not_editor",
                                SciPublicationsConstants.BUNDLE
                            )
                        );
                    }
                }
                case 2:
                    return new Label(
                        new GlobalizedMessage(
                            "publications.ui.authors.edit_assoc",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                case 3:
                    return new Label(
                        new GlobalizedMessage(
                            "publications.ui.authors.author.remove",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                default:
                    return null;
            }
        }

        @Override
        public Object getKeyAt(final int columnIndex) {
            return currentRow.get(SciPublicationsController.AUTHORSHIP_ID);
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
            final PublicationItem<?> selected = (PublicationItem<?>) itemModel
                .getSelectedItem(state);
            final SciPublicationsController controller = CdiUtil
                .createCdiUtil()
                .findBean(SciPublicationsController.class);
            final Authorship authorship = controller
                .findAuthorship(selected.getPublication().getPublicationId(),
                                key)
                .get();

            return new Text(
                String.format(
                    "%s, %s",
                    authorship.getAuthor().getPersonName().getSurname(),
                    authorship.getAuthor().getPersonName().getGivenName()
                )
            );
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
            final int col
        ) {
            final PermissionChecker permissionChecker = CdiUtil
                .createCdiUtil()
                .findBean(PermissionChecker.class);

            final PublicationItem<?> selected = (PublicationItem<?>) itemModel
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
            final int col
        ) {
            final PermissionChecker permissionChecker = CdiUtil
                .createCdiUtil()
                .findBean(PermissionChecker.class);

            final PublicationItem<?> selected = (PublicationItem<?>) itemModel
                .getSelectedItem(state);
            final boolean canEdit = permissionChecker
                .isPermitted(ItemPrivileges.EDIT, selected);

            if (canEdit) {
                final ControlLink link = new ControlLink(
                    (Component) value
                );
                link.setConfirmation(
                    new GlobalizedMessage(
                        "publications.ui.authors.author.confirm_remove",
                        SciPublicationsConstants.BUNDLE
                    )
                );
                return link;
            } else {
                return new Text("");
            }
        }

    }

    private class UpCellRenderer
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
            if (0 == row) {
                return new Label();
            } else {
                final ControlLink link = new ControlLink("up");
                return link;
            }
        }

    }

    private class DownCellRenderer
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
            final PublicationItem<?> selected = (PublicationItem<?>) itemModel
                .getSelectedItem(state);
            final SciPublicationsController controller = CdiUtil
                .createCdiUtil()
                .findBean(SciPublicationsController.class);
            final List<Map<String, Object>> authorships = controller
                .getAuthors(selected.getPublication().getPublicationId()
                );

            if ((authorships.size() - 1) == row) {
                return new Label();
            } else {
                final ControlLink link = new ControlLink("down");
                return link;
            }
        }

    }

}
