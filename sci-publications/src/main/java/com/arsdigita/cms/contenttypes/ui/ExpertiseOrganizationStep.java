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
public class ExpertiseOrganizationStep extends SimpleEditStep {

    private static final String SET_EXPERTISE_ORGANIZATION_STEP
                                    = "setExpertiseOrganizationStep";

    public ExpertiseOrganizationStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, parent, selectedLangParam, null);
    }

    public ExpertiseOrganizationStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam,
        final String prefix
    ) {
        super(itemModel, parent, selectedLangParam, prefix);

        final BasicItemForm setOrgaForm = new ExpertiseOrganizationForm(
            itemModel, selectedLangParam
        );
        add(
            SET_EXPERTISE_ORGANIZATION_STEP,
            new GlobalizedMessage(
                "publications.ui.expertise.setOrganization",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(
                setOrgaForm, itemModel),
            setOrgaForm.getSaveCancelSection().getCancelButton()
        );

        final ExpertiseOrganizationSheet sheet = new ExpertiseOrganizationSheet(
            itemModel
        );
        setDisplayComponent(sheet);
    }

}
