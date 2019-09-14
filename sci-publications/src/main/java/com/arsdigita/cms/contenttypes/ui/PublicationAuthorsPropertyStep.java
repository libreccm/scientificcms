/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.cms.ui.workflow.WorkflowLockedComponentAccess;
import com.arsdigita.globalization.GlobalizedMessage;

import org.librecms.assets.Person;
import org.scientificcms.publications.SciPublicationsConstants;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationAuthorsPropertyStep extends SimpleEditStep {

    public static final String ADD_AUTHOR_SHEET_NAME = "addAuthor";

    private Person selectedAuthor;

    private Boolean selectedAuthorEditor;

    public PublicationAuthorsPropertyStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParam
    ) {
        this(itemModel, parent, selectedLanguageParam, null);
    }

    public PublicationAuthorsPropertyStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard authoringKitWizard,
        final StringParameter selectedLanguageParameter,
        final String prefix
    ) {
        super(itemModel, authoringKitWizard, selectedLanguageParameter, prefix);

        final BasicItemForm addAuthorSheet = new PublicationAuthorAddForm(
            itemModel, this, selectedLanguageParameter
        );
        add(ADD_AUTHOR_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.authors.add_author",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(addAuthorSheet, itemModel),
            addAuthorSheet.getSaveCancelSection().getCancelButton());

        final PublicationAuthorsTable authorsTable = new PublicationAuthorsTable(
            itemModel, this);
        setDisplayComponent(authorsTable);
    }
       
    protected Person getSelectedAuthor() {
        return selectedAuthor;
    }

    protected Boolean isSelectedAuthorEditor() {
        return selectedAuthorEditor;
    }

    protected void setSelectedAuthor(final Person selectedAuthor) {
        this.selectedAuthor = selectedAuthor;
    }

    protected void setSelectedAuthorEditor(final Boolean selectedAuthorEditor) {
        this.selectedAuthorEditor = selectedAuthorEditor;
    }

    

}
