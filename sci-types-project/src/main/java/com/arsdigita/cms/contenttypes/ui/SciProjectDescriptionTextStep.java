/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectDescriptionTextStep extends SimpleEditStep {

    public SciProjectDescriptionTextStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParam) {

        this(itemModel, parent, selectedLanguageParam, null);
    }

    public SciProjectDescriptionTextStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParam,
        final String suffix) {

        super(itemModel, parent, selectedLanguageParam, suffix);

        final BasicItemForm descTextEditSheet
                                = new SciProjectDescriptionTextEditForm(
                itemModel, selectedLanguageParam);
        add(SciProjectUiConstants.EDIT_DESC_TEXT_SHEET_NAME,
            new GlobalizedMessage("sciproject.ui.desc.text.edit",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE),
            new WorkflowLockedComponentAccess(descTextEditSheet, itemModel),
            descTextEditSheet.getSaveCancelSection().getCancelButton());

        final SciProjectDescriptionUploadForm uploadDescForm
                                                  = new SciProjectDescriptionUploadForm(
                itemModel,
                selectedLanguageParam);
        add(SciProjectUiConstants.UPLOAD_DESC_TEXT_SHEET_NAME,
            new GlobalizedMessage("sciproject.ui.desc.upload",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE),
            new WorkflowLockedComponentAccess(uploadDescForm, itemModel),
            uploadDescForm.getSaveCancelSection().getCancelButton());

        setDisplayComponent(
            getSciProjectEditDescTextSheet(itemModel,
                                           selectedLanguageParam)
        );
    }

    public static Component getSciProjectEditDescTextSheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLanguageParam) {

        final DomainObjectPropertySheet sheet = new DomainObjectPropertySheet(
            itemModel);

        sheet.add(new GlobalizedMessage(
            "sciproject.ui.desc", SciProjectConstants.SCI_PROJECT_BUNDLE),
                  SciProjectUiConstants.PROJECT_DESCRIPTION);

        return sheet;
    }

}
