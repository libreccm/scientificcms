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
public class UnPublishedOrganizationStep extends SimpleEditStep {

    private static final String SET_UNPUBLISHED_ORGANIZATION_STEP
                                    = "setUnPublishedOrganizationStep";

    public UnPublishedOrganizationStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, parent, selectedLangParam, null);
    }

    public UnPublishedOrganizationStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam,
        final String prefix
    ) {
        super(itemModel, parent, selectedLangParam, prefix);

        final BasicItemForm setOrgaForm = new UnPublishedOrganizationForm(
            itemModel, selectedLangParam
        );
        add(
            SET_UNPUBLISHED_ORGANIZATION_STEP,
            new GlobalizedMessage(
                "publications.ui.unpublished.setOrganization",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(setOrgaForm, itemModel),
            setOrgaForm.getSaveCancelSection().getCancelButton());

        final UnPublishedOrganizationSheet sheet
                                           = new UnPublishedOrganizationSheet(
                itemModel
            );
        setDisplayComponent(sheet);
    }

}
