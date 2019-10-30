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
 * /**
 * Step for adding a association between a ArticleInCollectedVolume and a
 * CollectedVolume.
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ArticleInCollectedVolumeCollectedVolumeStep
    extends SimpleEditStep {

    private final static String ADD_COLLECTED_VOLUME_STEP = "addCollectedVolume";

    public ArticleInCollectedVolumeCollectedVolumeStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParam
    ) {
        this(itemModel, parent, selectedLanguageParam, null);
    }

    public ArticleInCollectedVolumeCollectedVolumeStep(
        final ItemSelectionModel itemSelectionModel,
        final AuthoringKitWizard authoringKitWizard,
        final StringParameter selectedLanguageParam,
        final String parameterSuffix
    ) {
        super(itemSelectionModel,
              authoringKitWizard,
              selectedLanguageParam,
              parameterSuffix);

        final BasicItemForm addCollectedVolumeForm
                                = new ArticleInCollectedVolumeCollectedVolumeForm(
                itemSelectionModel,
                selectedLanguageParam
            );

        add(ADD_COLLECTED_VOLUME_STEP,
            new GlobalizedMessage(
                "publications.ui.collectedVolume.addCollectedVolume",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(
                addCollectedVolumeForm,
                itemSelectionModel
            ),
            addCollectedVolumeForm.getSaveCancelSection().getCancelButton());

        final ArticleInCollectedVolumeCollectedVolumeSheet sheet
                                                     = new ArticleInCollectedVolumeCollectedVolumeSheet(
                itemSelectionModel, selectedLanguageParam);
        setDisplayComponent(sheet);

    }

}
