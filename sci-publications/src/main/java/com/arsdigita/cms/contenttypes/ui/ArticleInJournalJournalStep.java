package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.cms.ui.workflow.WorkflowLockedComponentAccess;
import com.arsdigita.globalization.GlobalizedMessage;

import org.scientificcms.publications.SciPublicationsConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ArticleInJournalJournalStep extends SimpleEditStep {

    private String ADD_JOURNAL_STEP = "addJournal";

    public ArticleInJournalJournalStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParam
    ) {
        this(itemModel, parent, selectedLanguageParam, null);
    }

    public ArticleInJournalJournalStep(
        final ItemSelectionModel itemSelectionModel,
        final AuthoringKitWizard authoringKitWizard,
        final StringParameter selectedLanguageParam,
        final String parameterSuffix
    ) {
        super(
            itemSelectionModel,
            authoringKitWizard,
            selectedLanguageParam,
            parameterSuffix
        );

        final BasicItemForm addJournalForm = new ArticleInJournalJournalForm(
            itemSelectionModel, selectedLanguageParam
        );
        add(
            ADD_JOURNAL_STEP,
            new GlobalizedMessage(
                "publications.ui.articleInJournal.addJournal",
                SciPublicationsConstants.BUNDLE),
            new WorkflowLockedComponentAccess(
                addJournalForm, itemSelectionModel
            ),
            addJournalForm.getSaveCancelSection().getCancelButton());

        final ArticleInJournalJournalSheet sheet
                                           = new ArticleInJournalJournalSheet(
                itemSelectionModel,
                selectedLanguageParam
            );
        setDisplayComponent(sheet);
    }

}
