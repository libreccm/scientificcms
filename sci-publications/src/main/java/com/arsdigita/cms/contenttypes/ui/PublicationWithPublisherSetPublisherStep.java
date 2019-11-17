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
import org.scientificcms.publications.SciPublicationsConstants;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationWithPublisherSetPublisherStep extends SimpleEditStep {

    private String SET_PUBLICATION_PUBLISHER_STEP
                       = "setPublicationPublisherStep";

    public PublicationWithPublisherSetPublisherStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, parent, selectedLangParam, null);
    }

    public PublicationWithPublisherSetPublisherStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParameter,
        final String prefix) {
        super(itemModel, parent, selectedLangParameter, prefix);

        final BasicItemForm setPublisherForm
                                = new PublicationWithPublisherSetPublisherForm(
                itemModel, selectedLangParameter);
        add(SET_PUBLICATION_PUBLISHER_STEP,
            new GlobalizedMessage(
                "publications.ui.with_publisher.setPublisher",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(setPublisherForm, itemModel),
            setPublisherForm.getSaveCancelSection().getCancelButton());

        final PublicationWithPublisherSetPublisherSheet sheet
                                                            = new PublicationWithPublisherSetPublisherSheet(
                itemModel, selectedLangParameter
            );
        setDisplayComponent(sheet);
    }

}
