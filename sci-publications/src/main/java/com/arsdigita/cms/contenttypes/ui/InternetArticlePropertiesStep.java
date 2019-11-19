/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.Component;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.BasicPageForm;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.cms.ui.workflow.WorkflowLockedComponentAccess;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.toolbox.ui.DomainObjectPropertySheet;

import org.scientificcms.publications.SciPublicationsConstants;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class InternetArticlePropertiesStep extends PublicationPropertiesStep {

    private final StringParameter selectedLangParam;

    public InternetArticlePropertiesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, parent, selectedLangParam);
        this.selectedLangParam = selectedLangParam;
    }

    public static Component getInternetArticlePropertySheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        final DomainObjectPropertySheet sheet
                                            = (DomainObjectPropertySheet) PublicationPropertiesStep
                .getPublicationPropertySheet(itemModel, selectedLangParam);

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.internetarticle.place",
                SciPublicationsConstants.BUNDLE
            ),
            InternetArticleController.PLACE
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.internetarticle.number",
                SciPublicationsConstants.BUNDLE
            ),
            InternetArticleController.NUMBER
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.internetarticle.number_of_pages",
                SciPublicationsConstants.BUNDLE
            ),
            InternetArticleController.NUMBER_OF_PAGES
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.internetarticle.edition",
                SciPublicationsConstants.BUNDLE
            ),
            InternetArticleController.EDITION
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.internetarticle.issn",
                SciPublicationsConstants.BUNDLE
            ),
            InternetArticleController.ISSN
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.internetarticle.lastAccessed",
                SciPublicationsConstants.BUNDLE
            ),
            InternetArticleController.LAST_ACCESSED
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.internetarticle.url",
                SciPublicationsConstants.BUNDLE
            ),
            InternetArticleController.URL
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.internetarticle.urn",
                SciPublicationsConstants.BUNDLE
            ),
            InternetArticleController.URN
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.internetarticle.doi",
                SciPublicationsConstants.BUNDLE
            ),
            InternetArticleController.DOI
        );

        return sheet;
    }

    @Override
    protected void addBasicProperties(
        final ItemSelectionModel itemModel, final AuthoringKitWizard parent
    ) {
        final SimpleEditStep basicProperties = new SimpleEditStep(
            itemModel,
            parent,
            selectedLangParam,
            EDIT_SHEET_NAME
        );

        final BasicPageForm editBasicSheet
                                = new InternetArticlePropertyForm(
                itemModel,
                this,
                selectedLangParam
            );

        basicProperties.add(
            EDIT_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.internetarticle.edit_basic_sheet",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(editBasicSheet, itemModel),
            editBasicSheet.getSaveCancelSection().getCancelButton()
        );

        basicProperties.setDisplayComponent(
            getInternetArticlePropertySheet(itemModel, selectedLangParam));

        getSegmentedPanel().addSegment(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.publication.basic_properties",
                    SciPublicationsConstants.BUNDLE
                )
            ),
            basicProperties
        );
    }

    @Override
    protected void addSteps(
        final ItemSelectionModel itemModel, final AuthoringKitWizard parent
    ) {
        super.addSteps(itemModel, parent);

        addStep(
            new InternetArticleOrganizationStep(
                itemModel, parent, selectedLangParam
            ),
            new GlobalizedMessage(
                "publications.ui.internetarticle.setOrganization",
                SciPublicationsConstants.BUNDLE
            )
        );
    }

}
