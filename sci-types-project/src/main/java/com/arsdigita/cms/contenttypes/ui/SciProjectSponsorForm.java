/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.Text;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.event.FormSubmissionListener;
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.assets.AssetSearchWidget;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.librecms.assets.Organization;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectSponsorForm
    extends BasicItemForm
    implements FormInitListener, FormProcessListener, FormSubmissionListener {

    private final SimpleEditStep editStep;

    private AssetSearchWidget itemSearch;

    private TextField fundingCode;

    private Text selectedSponsorLabel;

    private StringParameter selectedLanguageParam;

    public SciProjectSponsorForm(final ItemSelectionModel itemModel,
                                 final SimpleEditStep editStep,
                                 final StringParameter selectedLanguageParam) {
        super("SciProjectSetSponsor", itemModel, selectedLanguageParam);
        this.editStep = editStep;
        addSubmissionListener(this);
    }

    @Override
    public void addWidgets() {

        itemSearch = new AssetSearchWidget(
            SciProjectUiConstants.SPONSOR_SEARCH,
            Organization.class);
        itemSearch.setLabel(new GlobalizedMessage(
            "sciproject.ui.sponsor", SciProjectConstants.SCI_PROJECT_BUNDLE));
        add(itemSearch);

        selectedSponsorLabel = new Text();
        add(selectedSponsorLabel);

        fundingCode = new TextField(SciProjectUiConstants.FUNDING_CODE);
        fundingCode.setLabel(new GlobalizedMessage(
            "sciproject.ui.sponsor_fundingcode",
            SciProjectConstants.SCI_PROJECT_BUNDLE));
        add(fundingCode);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        final FormData data = event.getFormData();
        final PageState state = event.getPageState();

        final Organization sponsor = ((SciProjectSponsorsStep) editStep).
            getSelectedSponsor();
        final String sponsorFundingCode = ((SciProjectSponsorsStep) editStep).
            getSelectedSponsorFundingCode();

        if (sponsor == null) {
            itemSearch.setVisible(state, true);
            selectedSponsorLabel.setVisible(state, false);
        } else {
            data.put(SciProjectUiConstants.SPONSOR_SEARCH, sponsor);
            if ((sponsorFundingCode == null) || sponsorFundingCode.isEmpty()) {
                fundingCode.setValue(state, null);
            } else {
                fundingCode.setValue(state, sponsorFundingCode);
            }

            itemSearch.setVisible(state, false);
            selectedSponsorLabel
                .setText(
                    sponsor.getTitle().getValue(
                        SelectedLanguageUtil.selectedLocale(
                            state, selectedLanguageParam)
                    )
                );
            selectedSponsorLabel.setVisible(state, true);
        }

        setVisible(state, true);
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {

        final FormData data = event.getFormData();
        final PageState state = event.getPageState();
        final SciProject project = (SciProject) getItemSelectionModel()
            .getSelectedObject(state);

        if (getSaveCancelSection().getSaveButton().isSelected(state)) {
            final Organization sponsor = ((SciProjectSponsorsStep) editStep).
                getSelectedSponsor();

            String sponsorFundingCode;
            if (fundingCode.getValue(state) == null) {
                sponsorFundingCode = null;
            } else {
                sponsorFundingCode = (String) fundingCode.getValue(state);
            }

            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);
            if (sponsor == null) {
                final Organization sponsorToAdd = (Organization) data
                    .get(SciProjectUiConstants.SPONSOR_SEARCH);

                if ((sponsorFundingCode == null) || sponsorFundingCode.isEmpty()) {
                    controller.addSponsor(project.getObjectId(),
                                          sponsorToAdd.getObjectId(),
                                          "");
                } else {
                    controller.addSponsor(project.getObjectId(),
                                          sponsorToAdd.getObjectId(),
                                          sponsorFundingCode);
                }
            } else {

                controller.updateFundingCode(project.getObjectId(),
                                             sponsor.getObjectId(),
                                             sponsorFundingCode);
            }
        }

        init(event);
    }

    @Override
    public void submitted(final FormSectionEvent event)
        throws FormProcessException {

        if (getSaveCancelSection()
            .getCancelButton()
            .isSelected(event.getPageState())) {

            ((SciProjectSponsorsStep) editStep).setSelectedSponsor(null);
            ((SciProjectSponsorsStep) editStep)
                .setSelectedSponsorFundingCode(null);

            init(event);
        }
    }

    @Override
    public void validate(final FormSectionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();
        final FormData data = event.getFormData();
        boolean editing = false; //Are we editing the association?

        if ((((SciProjectSponsorsStep) editStep).getSelectedSponsor() == null)
                && (data.get(SciProjectUiConstants.SPONSOR_SEARCH) == null)) {
            data.addError(new GlobalizedMessage(
                "sciproject.ui.sponsor_no_sponsor_selected",
                SciProjectConstants.SCI_PROJECT_BUNDLE));
            return;
        }

        final SciProject project = (SciProject) getItemSelectionModel()
            .getSelectedObject(state);
        Organization sponsor = (Organization) data
            .get(SciProjectUiConstants.SPONSOR_SEARCH);
        if (sponsor == null) {
            sponsor = ((SciProjectSponsorsStep) editStep).getSelectedSponsor();
            editing = true;
        }

        if (!editing) {
            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final SciProjectController controller = cdiUtil
                .findBean(SciProjectController.class);

            if (controller.hasSponsor(project.getObjectId(),
                                      sponsor.getObjectId())) {
                data.addError(new GlobalizedMessage(
                    "sciproject.ui.sponsor.already_added",
                    SciProjectConstants.SCI_PROJECT_BUNDLE));
            }
        }
    }

}
