/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.cms.ui.contenttypes.AbstractTextUploadForm;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

import java.util.Locale;

import static com.arsdigita.cms.ui.authoring.SelectedLanguageUtil.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectDescriptionUploadForm extends AbstractTextUploadForm {

    private StringParameter selectedLanguageParam;

    public SciProjectDescriptionUploadForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLanguageParam) {

        super(itemModel);
    }

    @Override
    public GlobalizedMessage getLabelText() {
        return new GlobalizedMessage("sciproject.ui.description.upload",
                                     SciProjectConstants.SCI_PROJECT_BUNDLE);
    }

    @Override
    public GlobalizedMessage getMimeTypeLabel() {
        return new GlobalizedMessage(
            "sciproject.ui.description.upload.mimetype",
            SciProjectConstants.SCI_PROJECT_BUNDLE);
    }

    @Override
    public void setText(final ItemSelectionModel itemModel,
                        final PageState state,
                        final String text) {

        final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
        final SciProjectController controller = cdiUtil
            .findBean(SciProjectController.class);

        final SciProject project = (SciProject) itemModel
            .getSelectedItem(state);

        final Locale locale = SelectedLanguageUtil
            .selectedLocale(state, selectedLanguageParam);
        
        controller.updateDescription(project.getObjectId(), 
                                     text, 
                                     locale);
    }

}
