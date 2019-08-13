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
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.CMSDHTMLEditor;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

import java.util.Locale;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectDescriptionTextEditForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener {

    private final StringParameter selectedLanguageParam;

    public SciProjectDescriptionTextEditForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLanguageParam) {

        super("SciProjectDescriptionTextEditForm",
              itemModel,
              selectedLanguageParam);

        this.selectedLanguageParam = selectedLanguageParam;
    }

    @Override
    public void addWidgets() {

        final ParameterModel descParam = new StringParameter(
            SciProjectUiConstants.PROJECT_DESCRIPTION);
        final CMSDHTMLEditor editor = new CMSDHTMLEditor(descParam);
        editor.setLabel(new GlobalizedMessage(
            "sciproject.ui.description",
            SciProjectConstants.SCI_PROJECT_BUNDLE));
        editor.setCols(80);
        editor.setRows(25);
        add(editor);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        final PageState state = event.getPageState();
        final FormData data = event.getFormData();
        final SciProject project = (SciProject) getItemSelectionModel()
            .getSelectedItem(state);

        data.put(SciProjectUiConstants.PROJECT_DESCRIPTION,
                 project.getProjectDescription());

        setVisible(state, true);
    }

    @Override
    public void process(final FormSectionEvent event) throws
        FormProcessException {

        final PageState state = event.getPageState();
        final FormData data = event.getFormData();
        final SciProject project = (SciProject) getItemSelectionModel()
            .getSelectedItem(state);

        if (project != null
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);
            final Locale selectedLang = SelectedLanguageUtil
                .selectedLocale(state, selectedLanguageParam);

            controller.updateDescription(
                project.getObjectId(),
                (String) data.get(SciProjectUiConstants.PROJECT_DESCRIPTION),
                selectedLang
            );
        }
    }

}
