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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Jens Pelzetter <jens@jp-digital.de>
 * jensp $
 */
public class SciProjectFundingUploadForm extends AbstractTextUploadForm {

    private final StringParameter selectedLanguageParam;

    public SciProjectFundingUploadForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLanguageParam) {

        super(itemModel);
        this.selectedLanguageParam = selectedLanguageParam;
    }

    @Override
    public GlobalizedMessage getLabelText() {
        return new GlobalizedMessage("sciproject.ui.funding.upload",
                                     SciProjectConstants.SCI_PROJECT_BUNDLE);
    }

    @Override
    public GlobalizedMessage getMimeTypeLabel() {
        return new GlobalizedMessage("sciproject.ui.funding.upload.mimetype",
                                     SciProjectConstants.SCI_PROJECT_BUNDLE);
    }

    @Override
    public void setText(final ItemSelectionModel itemModel,
                        final PageState state,
                        final String text) {

        final SciProject project = (SciProject) itemModel.getSelectedObject(
            state);
        final Locale locale = SelectedLanguageUtil
            .selectedLocale(state,
                            selectedLanguageParam);

        final Map<String, Object> data = new HashMap<>();
        data.put(SciProjectUiConstants.FUNDING, text);
        CdiUtil
            .createCdiUtil()
            .findBean(SciProjectController.class)
            .updateFundingData(project.getObjectId(), locale, data);
    }

}
