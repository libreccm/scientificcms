/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.Component;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.BasicPageForm;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.cms.ui.workflow.WorkflowLockedComponentAccess;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.toolbox.ui.DomainObjectPropertySheet;

import org.librecms.CmsConstants;
import org.scientificcms.publications.ArticleInJournal;
import org.scientificcms.publications.SciPublicationsConstants;

import java.text.DateFormat;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ArticleInJournalPropertiesStep extends PublicationPropertiesStep {

    private final StringParameter selectedLanguageParam;

    public ArticleInJournalPropertiesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParam
    ) {
        super(itemModel, parent, selectedLanguageParam);

        this.selectedLanguageParam = selectedLanguageParam;
    }

    public static Component getArticleInJournalPropertySheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLanguageParameter
    ) {
        final DomainObjectPropertySheet sheet
                                            = (DomainObjectPropertySheet) PublicationPropertiesStep
                .getPublicationPropertySheet(
                    itemModel,
                    selectedLanguageParameter
                );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.articleinjournal.volume",
                SciPublicationsConstants.BUNDLE
            ),
            ArticleInJournalController.VOLUME
        );

        sheet.add(new GlobalizedMessage(
            "publications.ui.articleinjournal.issue",
            SciPublicationsConstants.BUNDLE
        ),
                  ArticleInJournalController.ISSUE);

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.articleinjournal.pages_from",
                SciPublicationsConstants.BUNDLE
            ),
            ArticleInJournalController.START_PAGE
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.articleinjournal.pages_to",
                SciPublicationsConstants.BUNDLE
            ),
            ArticleInJournalController.END_PAGE
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.articleinjournal.publication_date",
                SciPublicationsConstants.BUNDLE
            ),
            ArticleInJournalController.PUBLICATION_DATE,
            ArticleInJournalPropertiesStep::formatPublicationDate
        );

        sheet.add(new GlobalizedMessage(
                "publications.ui.articleinjournal.reviewed"
            ),
            ArticleInJournalController.PEER_REVIEWED,
            ArticleInJournalPropertiesStep::formatReviewed
        );

        return sheet;
    }

    @Override
    protected void addBasicProperties(final ItemSelectionModel itemModel,
                                      final AuthoringKitWizard parent
    ) {
        final SimpleEditStep basicProperties = new SimpleEditStep(
            itemModel,
            parent,
            selectedLanguageParam,
            EDIT_SHEET_NAME
        );

        final BasicPageForm editBasicSheet = new ArticleInJournalPropertyForm(
            itemModel, this, selectedLanguageParam
        );

        basicProperties.add(
            EDIT_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.articleinjournal.edit_basic_sheet",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(editBasicSheet,
                                              itemModel
            ),
            editBasicSheet.getSaveCancelSection().getCancelButton()
        );

        basicProperties.setDisplayComponent(
            getArticleInJournalPropertySheet(
                itemModel,
                selectedLanguageParam
            )
        );

        getSegmentedPanel().addSegment(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.publication.basic_properties",
                    SciPublicationsConstants.BUNDLE)
            ),
            basicProperties
        );
    }

    @Override
    protected void addSteps(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent
    ) {
        super.addSteps(itemModel, parent);

        addStep(
            new ArticleInJournalJournalStep(
                itemModel, parent, selectedLanguageParam
            ),
            new GlobalizedMessage(
                "publication.ui.articleInJournal.journal",
                SciPublicationsConstants.BUNDLE
            )
        );

    }

    private static String formatPublicationDate(
        final Object obj, final String attribute, final PageState state
    ) {
        final ArticleInJournal article = (ArticleInJournal) obj;

        if (article.getPublicationDate() != null) {
            return DateFormat.getDateInstance(DateFormat.LONG)
                .format(
                    article.getPublicationDate());
        } else {
            return (String) new GlobalizedMessage(
                "cms.ui.unknown",
                CmsConstants.CMS_BUNDLE
            ).localize();
        }
    }

    private static String formatReviewed(
        final Object obj, final String attribute, final PageState state
    ) {
        final ArticleInJournal article = (ArticleInJournal) obj;

        if (article.getPeerReviewed()) {
            return (String) new GlobalizedMessage(
                "publications.ui.articleinjournal.reviewed.yes",
                SciPublicationsConstants.BUNDLE
            ).localize();
        } else {
            return (String) new GlobalizedMessage(
                "publications.ui.articleinjournal.reviewed.no",
                SciPublicationsConstants.BUNDLE
            ).localize();
        }
    }

}
