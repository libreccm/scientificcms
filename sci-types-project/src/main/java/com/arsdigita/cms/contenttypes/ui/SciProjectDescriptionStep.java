/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.SegmentedPanel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.globalization.GlobalizedMessage;

import org.scientificcms.contenttypes.sciproject.SciProjectConfig;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectDescriptionStep extends SimpleEditStep {

    private final SegmentedPanel segmentedPanel;
    private final StringParameter selectedLanguageParam;

    public SciProjectDescriptionStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParam) {

        super(itemModel, parent, selectedLanguageParam);

        segmentedPanel = new SegmentedPanel();
        setDefaultEditKey(SciProjectUiConstants.EDIT_DESC_SHEET_NAME);

        addSteps(itemModel, parent);

        setDisplayComponent(segmentedPanel);
        this.selectedLanguageParam = selectedLanguageParam;
    }

    protected SegmentedPanel getSegmentedPanel() {
        return segmentedPanel;
    }

    protected void addSteps(final ItemSelectionModel itemModel,
                            final AuthoringKitWizard parent) {

        addStep(new SciProjectDescriptionTextStep(itemModel,
                                                  parent,
                                                  selectedLanguageParam),
                "sciproject.ui.steps.description.title");

        final SciProjectConfig config = SciProjectConfig.getConfig();

        if (config.isEnableSponsor()) {
            addStep(new SciProjectSponsorStep(itemModel, parent),
                    "sciproject.ui.steps.sponsor.title");
        }

        if (config.isEnableFunding()) {
            addStep(new SciProjectFundingStep(itemModel, parent),
                    "sciproject.ui.steps.funding.title");
        }
    }

    protected void addStep(final SimpleEditStep step, final String labelKey) {

        segmentedPanel.addSegment(
            new Label(new GlobalizedMessage(
                labelKey,
                SciProjectConstants.SCI_PROJECT_BUNDLE)),
            step
        );
    }

}
