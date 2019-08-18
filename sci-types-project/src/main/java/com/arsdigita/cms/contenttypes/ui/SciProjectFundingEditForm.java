/* ;
 * Copyright (c) 2013 Jens Pelzetter
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.form.TextArea;
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringInRangeValidationListener;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;

import com.arsdigita.cms.ui.CMSDHTMLEditor;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConfig;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Jens Pelzetter <jens@jp-digital.de>
 */
public class SciProjectFundingEditForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener {

    private final static SciProjectConfig CONFIG = SciProjectConfig.getConfig();

    private final StringParameter selectedLanguageParam;

    public SciProjectFundingEditForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLanguageParam) {

        super("SciProjectFundingForm", itemModel, selectedLanguageParam);
        this.selectedLanguageParam = selectedLanguageParam;
    }

    @Override
    public void addWidgets() {

        if (CONFIG.isEnableFunding()) {
            final ParameterModel fundingParam = new StringParameter(
                SciProjectUiConstants.FUNDING);
            final TextArea funding;
            if (CONFIG.isEnableFundingDhtml()) {
                funding = new CMSDHTMLEditor(fundingParam);
            } else {
                funding = new TextArea(fundingParam);
            }
            funding.setLabel(new GlobalizedMessage(
                "sciproject.ui.funding",
                SciProjectConstants.SCI_PROJECT_BUNDLE));
            funding.setCols(75);
            funding.setRows(8);
            add(funding);
        }

        if (CONFIG.isEnableFundingVolume()) {
            final ParameterModel fundingVolumeParam = new StringParameter(
                SciProjectUiConstants.FUNDING_VOLUME);
            final TextField fundingVolume = new TextField(fundingVolumeParam);
            fundingVolume.addValidationListener(
                new StringInRangeValidationListener(
                    0,
                    CONFIG.getFundingVolumeLength()));
            fundingVolume.setLabel(new GlobalizedMessage(
                "sciproject.ui.funding.volume",
                SciProjectConstants.SCI_PROJECT_BUNDLE));
            add(fundingVolume);
        }
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        final PageState state = event.getPageState();
        final FormData data = event.getFormData();
        final Locale locale = SelectedLanguageUtil
            .selectedLocale(state, selectedLanguageParam);
        final SciProject project = (SciProject) getItemSelectionModel()
            .getSelectedObject(state);

        if (CONFIG.isEnableFunding()) {
            data.put(SciProjectUiConstants.FUNDING,
                     project.getFunding().getValue(locale));
        }

        if (CONFIG.isEnableFundingVolume()) {
            data.put(SciProjectUiConstants.FUNDING_VOLUME,
                     project.getFundingVolume().getValue(locale));
        }

        setVisible(state, true);

    }

    @Override
    public void process(final FormSectionEvent event) throws
        FormProcessException {
        final PageState state = event.getPageState();
        final FormData formData = event.getFormData();
        final Locale locale = SelectedLanguageUtil
            .selectedLocale(state, selectedLanguageParam);
        final SciProject project = (SciProject) getItemSelectionModel()
            .getSelectedObject(state);

        if ((project != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Map<String, Object> data = new HashMap<>();
            
            if (CONFIG.isEnableFunding()) {
                data.put(SciProjectUiConstants.FUNDING,
                         formData.get(SciProjectUiConstants.FUNDING));
            }
            if (CONFIG.isEnableFundingVolume()) {
                data.put(SciProjectUiConstants.FUNDING_VOLUME,
                         formData.get(SciProjectUiConstants.FUNDING_VOLUME));
            }

            final SciProjectController controller = CdiUtil
                .createCdiUtil()
                .findBean(SciProjectController.class);
            controller.updateFundingData(project.getObjectId(), locale, data);
        }

        init(event);
    }

}
