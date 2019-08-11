/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.event.FormSubmissionListener;
import com.arsdigita.bebop.form.Date;
import com.arsdigita.bebop.parameters.DateParameter;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.BasicPageForm;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.globalization.GlobalizedMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectPropertyForm
    extends BasicPageForm
    implements FormProcessListener, FormInitListener, FormSubmissionListener {

    private static final Logger LOGGER = LogManager
        .getLogger(SciProjectPropertyForm.class);

    public static final String ID = "SciProject_edit";

    private SciProjectPropertiesStep step;

    private final StringParameter selectedLanguageParameter;

    public SciProjectPropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLanguageParameter) {

        this(itemModel, null, selectedLanguageParameter);
    }

    public SciProjectPropertyForm(
        final ItemSelectionModel itemModel,
        final SciProjectPropertiesStep step,
        final StringParameter selectedLanguageParameter) {
        super(ID, itemModel, selectedLanguageParameter);
        this.step = step;
        this.selectedLanguageParameter = selectedLanguageParameter;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        final DateParameter beginParam = new DateParameter(
            SciProjectUiConstants.BEGIN);
        final Date begin = new Date(beginParam);
        begin.setLabel(new GlobalizedMessage(
            "sciproject.ui.begin", SciProjectConstants.SCI_PROJECT_BUNDLE));

        final DateParameter endParam = new DateParameter(
            SciProjectUiConstants.END);
        final Date end = new Date(endParam);

        throw new UnsupportedOperationException("ToDo");
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        final FormData data = event.getFormData();
        final SciProject project = (SciProject) super.initBasicWidgets(event);

        throw new UnsupportedOperationException("ToDo");

    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();
        final FormData formData = event.getFormData();

        final SciProject project = (SciProject) super
            .processBasicWidgets(event);

        if (project != null
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);

            final Map<String, Object> data = new HashMap<>();
            data.put(SciProjectUiConstants.BEGIN,
                     formData.get(SciProjectUiConstants.BEGIN));
            data.put(SciProjectUiConstants.END,
                     formData.get(SciProjectUiConstants.END));
            data.put(
                SciProjectUiConstants.PROJECT_SHORT_DESCRIPTION,
                formData.get(SciProjectUiConstants.PROJECT_SHORT_DESCRIPTION));

            final Locale selectedLangauge = SelectedLanguageUtil
                .selectedLocale(state, selectedLanguageParameter);

            controller.save(project.getObjectId(), selectedLangauge, data);
        }
    }

    @Override
    public void submitted(final FormSectionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();

        if (step != null
                && getSaveCancelSection().getCancelButton().isSelected(state)) {

            step.cancelStreamlinedCreation(state);
        }
    }

}
