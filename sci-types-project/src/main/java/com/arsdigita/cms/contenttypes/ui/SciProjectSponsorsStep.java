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

import org.librecms.assets.Organization;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectSponsorsStep extends SimpleEditStep {

    private Organization selectedSponsor;

    private String selectedSponsorFundingCode;

    public SciProjectSponsorsStep(final ItemSelectionModel itemModel,
                                 final AuthoringKitWizard parent,
                                 final StringParameter selectedLanguageParam) {
        this(itemModel, parent, selectedLanguageParam, null);
    }

    public SciProjectSponsorsStep(final ItemSelectionModel itemModel,
                                 final AuthoringKitWizard parent,
                                 final StringParameter selectedLanguageParam,
                                 final String prefix) {

        super(itemModel, parent, selectedLanguageParam, prefix);

        final BasicItemForm sponsorForm = new SciProjectSponsorForm(
            itemModel, this, selectedLanguageParam);
        add(SciProjectUiConstants.SPONSOR_STEP,
            new GlobalizedMessage("sciproject.ui.sponsor.add",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE),
            new WorkflowLockedComponentAccess(sponsorForm, itemModel),
            sponsorForm.getSaveCancelSection().getCancelButton());

        final SciProjectSponsorSheet sheet = new SciProjectSponsorSheet(
            itemModel, this);
        setDisplayComponent(sheet);
    }

    protected Organization getSelectedSponsor() {
        return selectedSponsor;
    }

    protected void setSelectedSponsor(
        final Organization selectedSponsor) {
        this.selectedSponsor = selectedSponsor;
    }

    protected String getSelectedSponsorFundingCode() {
        return selectedSponsorFundingCode;
    }

    protected void setSelectedSponsorFundingCode(
        final String selectedSponsorFundingCode) {
        this.selectedSponsorFundingCode = selectedSponsorFundingCode;
    }

}
