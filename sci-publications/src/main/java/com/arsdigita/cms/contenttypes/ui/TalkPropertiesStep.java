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
import org.scientificcms.publications.Talk;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class TalkPropertiesStep extends PublicationPropertiesStep {

    private final StringParameter selectedLangParam;

    public TalkPropertiesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, parent, selectedLangParam);
        this.selectedLangParam = selectedLangParam;
    }

    public static Component getTalkPropertiesSheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {

        final DomainObjectPropertySheet sheet
                                            = (DomainObjectPropertySheet) getPublicationPropertySheet(
                itemModel, selectedLangParam);

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.talk.place",
                SciPublicationsConstants.BUNDLE
            ),
            TalkController.PLACE
        );
        sheet.add(
            new GlobalizedMessage(
                "publications.ui.talk.date",
                SciPublicationsConstants.BUNDLE
            ),
            TalkController.DATE_OF_TALK
        );
        sheet.add(
            new GlobalizedMessage(
                "publications.ui.talk.event",
                SciPublicationsConstants.BUNDLE
            ),
            TalkController.EVENT
        );

        return sheet;
    }

    @Override
    protected void addBasicProperties(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent
    ) {

        final SimpleEditStep basicProperties = new SimpleEditStep(
            itemModel, parent, selectedLangParam, EDIT_SHEET_NAME);

        final BasicPageForm editBasicSheet = new TalkPropertyForm(
            itemModel, this, selectedLangParam
        );

        basicProperties.add(
            EDIT_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.talk.edit_basic_sheet",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(editBasicSheet, itemModel),
            editBasicSheet.getSaveCancelSection().getCancelButton());

        basicProperties.setDisplayComponent(
            getTalkPropertiesSheet(itemModel, selectedLangParam)
        );

        getSegmentedPanel().addSegment(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.talk.basic_properties",
                    SciPublicationsConstants.BUNDLE
                )
            ),
            basicProperties);
    }

}
