/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.Text;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.event.FormSubmissionListener;
import com.arsdigita.bebop.form.CheckboxGroup;
import com.arsdigita.bebop.form.Option;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.assets.AssetSearchWidget;
import com.arsdigita.cms.ui.assets.ItemSearchWidget;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.globalization.GlobalizedMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.librecms.assets.Person;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.SciPublicationsConfig;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.PublicationItem;

import java.util.Objects;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationAuthorAddForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener, FormSubmissionListener {

    private static final Logger LOGGER = LogManager.getLogger(
        PublicationAuthorAddForm.class
    );

    private final static String AUTHOR_SEARCH = "authors";

    private final static SciPublicationsConfig CONFIG = SciPublicationsConfig
        .getConfig();

    private PublicationPropertiesStep step;

    private AssetSearchWidget authorSearchWidget;

    private final ItemSelectionModel itemModel;

    private final SimpleEditStep editStep;

    private Text selectedAuthorLabel;

    private CheckboxGroup isEditor;

    public PublicationAuthorAddForm(
        final ItemSelectionModel itemModel,
        final SimpleEditStep editStep,
        final StringParameter selectedLanguageParam
    ) {
        super("AuthorsEntryForm", itemModel, selectedLanguageParam);
        this.itemModel = itemModel;
        this.editStep = editStep;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        authorSearchWidget = new AssetSearchWidget(
            AUTHOR_SEARCH, Person.class
        );
        authorSearchWidget.setLabel(
            new GlobalizedMessage(
                "publications.ui.authors.selectAuthor",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(authorSearchWidget);

        selectedAuthorLabel = new Text();
        add(selectedAuthorLabel);

        isEditor = new CheckboxGroup("isEditorGroup");
        isEditor.addOption(
            new Option(
                SciPublicationsController.AUTHORSHIP_IS_EDITOR,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.authors.author.is_editor",
                        SciPublicationsConstants.BUNDLE
                    )
                )
            )
        );
        add(isEditor);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();

        final Person author;
        final Boolean editor;

        author = ((PublicationAuthorsPropertyStep) editStep).getSelectedAuthor();
        editor = ((PublicationAuthorsPropertyStep) editStep)
            .isSelectedAuthorEditor();

        if (author == null) {
            LOGGER.warn("No author selected.");

            authorSearchWidget.setVisible(state, true);
            selectedAuthorLabel.setVisible(state, false);
        } else {
            LOGGER.warn(
                String.format(
                    "Author is here: %s", Objects.toString(author)
                )
            );

            formData.put(AUTHOR_SEARCH, author);
            if ((editor != null) && editor) {
                isEditor.setValue(
                    state,
                    new String[]{
                        SciPublicationsController.AUTHORSHIP_IS_EDITOR
                    }
                );
            } else {
                isEditor.setValue(state, null);
            }

            authorSearchWidget.setVisible(state, false);
            selectedAuthorLabel.setText(
                String.format("%s, %s",
                              author.getPersonName().getSurname(),
                              author.getPersonName().getGivenName()
                )
            );
            selectedAuthorLabel.setVisible(state, true);
        }

        setVisible(state, true);
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();

        final PublicationItem<?> item = (PublicationItem) itemModel
            .getSelectedItem(state);
        final Publication publication = item.getPublication();

        if (getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Person author = ((PublicationAuthorsPropertyStep) editStep)
                .getSelectedAuthor();

            final Boolean editor;
            if (isEditor.getValue(state) == null) {
                editor = Boolean.FALSE;
            } else {
                editor = Boolean.TRUE;
            }

            final SciPublicationsController controller = CdiUtil
                .createCdiUtil()
                .findBean(SciPublicationsController.class);
            if (author == null) {
                final Person authorToAdd = (Person) formData.get(
                    AUTHOR_SEARCH
                );

                controller.addAuthor(
                    publication.getPublicationId(),
                    authorToAdd.getObjectId(),
                    editor
                );
            } else {

                controller.updateAuthorship(publication.getPublicationId(),
                                            author.getObjectId(),
                                            editor);

                ((PublicationAuthorsPropertyStep) editStep)
                    .setSelectedAuthor(null);
                ((PublicationAuthorsPropertyStep) editStep)
                    .setSelectedAuthorEditor(null);

            }
        }

        init(event);
    }

    @Override
    public void submitted(final FormSectionEvent fse) throws
        FormProcessException {
        if (getSaveCancelSection().getCancelButton()
            .isSelected(fse.getPageState())) {
            ((PublicationAuthorsPropertyStep) editStep)
                .setSelectedAuthor(null);
            ((PublicationAuthorsPropertyStep) editStep)
                .setSelectedAuthorEditor(null);

            init(fse);
        }
    }

    @Override
    public void validate(final FormSectionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();
        final FormData formData = event.getFormData();
        boolean editing = false; //Are we editing the association

        if ((((PublicationAuthorsPropertyStep) editStep)
             .getSelectedAuthor() == null)
                && (formData.get(AUTHOR_SEARCH) == null)) {
            formData.addError(
                new GlobalizedMessage(
                    "publications.ui.authors.selectAuthor.no_author_selected",
                    SciPublicationsConstants.BUNDLE
                )
            );
            return;
        }

        final PublicationItem<?> item
                                     = (PublicationItem<?>) getItemSelectionModel()
                .getSelectedObject(state);
        final Person author;
        if (formData.get(AUTHOR_SEARCH) == null) {
            author = ((PublicationAuthorsPropertyStep) editStep)
                .getSelectedAuthor();
            editing = true;
        } else {
            author = (Person) formData.get(AUTHOR_SEARCH);
        }

        if (!editing) {

            final SciPublicationsController controller = CdiUtil
                .createCdiUtil()
                .findBean(SciPublicationsController.class);

            final boolean hasAuthor = controller.hasAuthor(
                item.getPublication().getPublicationId(),
                author.getObjectId()
            );

            if (hasAuthor) {
                formData.addError(
                    new GlobalizedMessage(
                        "publications.ui.authors.selectAuthor.already_added",
                        SciPublicationsConstants.BUNDLE
                    )
                );
            }
        }
    }

}
