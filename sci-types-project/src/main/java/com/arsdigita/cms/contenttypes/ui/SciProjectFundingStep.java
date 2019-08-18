package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.Component;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;

import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.cms.ui.workflow.WorkflowLockedComponentAccess;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.toolbox.ui.DomainObjectPropertySheet;

import org.scientificcms.contenttypes.sciproject.SciProjectConfig;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

/**
 *
 * @author Jens Pelzetter <jens@jp-digital.de>
 * @version $Id: SciProjectFundingStep.java 2334 2013-10-03 07:51:31Z jensp $
 */
public class SciProjectFundingStep extends SimpleEditStep {

    private static final String EDIT_PROJECT_FUNDING_SHEET_NAME
                                = "editProjectFunding";

    private static final String UPLOAD_PROJECT_FUNDING_SHEET_NAME
                                = "uploadProjectFunding";

    public SciProjectFundingStep(final ItemSelectionModel itemModel,
                                 final AuthoringKitWizard parent,
                                 final StringParameter selectedLanguageParam) {
        this(itemModel, parent, selectedLanguageParam, null);
    }

    public SciProjectFundingStep(final ItemSelectionModel itemModel,
                                 final AuthoringKitWizard parent,
                                 final StringParameter selectedLanguageParam,
                                 final String prefix) {
        super(itemModel, parent, selectedLanguageParam, prefix);

        final BasicItemForm editFundingForm = new SciProjectFundingEditForm(
            itemModel, selectedLanguageParam);
        add(EDIT_PROJECT_FUNDING_SHEET_NAME,
            new GlobalizedMessage("sciproject.ui.funding.edit",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE),
            new WorkflowLockedComponentAccess(editFundingForm, itemModel),
            editFundingForm.getSaveCancelSection().getCancelButton());

        final SciProjectFundingUploadForm uploadFundingForm
                                              = new SciProjectFundingUploadForm(
                itemModel, selectedLanguageParam);
        add(UPLOAD_PROJECT_FUNDING_SHEET_NAME,
            new GlobalizedMessage("sciproject.ui.funding.upload",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE),
            new WorkflowLockedComponentAccess(uploadFundingForm, itemModel),
            uploadFundingForm.getSaveCancelSection().getCancelButton());

        setDisplayComponent(getSciProjectEditFundingSheet(itemModel));
    }

    public static Component getSciProjectEditFundingSheet(
        final ItemSelectionModel itemModel) {
        final DomainObjectPropertySheet sheet = new DomainObjectPropertySheet(
            itemModel);

        if (SciProjectConfig.getConfig().isEnableFunding()) {
            sheet.add(
                new GlobalizedMessage("sciproject.ui.funding",
                                      SciProjectConstants.SCI_PROJECT_BUNDLE),
                SciProjectUiConstants.FUNDING);
        }
        if (SciProjectConfig.getConfig().isEnableFundingVolume()) {
            sheet.add(
                new GlobalizedMessage("sciproject.ui.funding.volume",
                                      SciProjectConstants.SCI_PROJECT_BUNDLE),
                SciProjectUiConstants.FUNDING_VOLUME);
        }

        return sheet;
    }

}
