/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.event.FormSubmissionListener;
import com.arsdigita.bebop.form.CheckboxGroup;
import com.arsdigita.bebop.form.Option;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.globalization.GlobalizedMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.CollectedVolumeItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class CollectedVolumePropertyForm
    extends PublicationWithPublisherPropertyForm
    implements FormProcessListener,
               FormInitListener,
               FormSubmissionListener {

    private static final Logger LOGGER = LogManager.getLogger(
        CollectedVolumePropertyForm.class
    );

    private static final String ID = "CollectedVolumeEdit";

    private final StringParameter selectedLangParam;
    
    private final CollectedVolumePropertiesStep step;

    private CheckboxGroup reviewed;

    public CollectedVolumePropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public CollectedVolumePropertyForm(
        final ItemSelectionModel itemModel,
        CollectedVolumePropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        this.step = step;
        this.selectedLangParam = selectedLangParam;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        reviewed = new CheckboxGroup("reviewedGroup");
        reviewed.addOption(
            new Option(
                CollectedVolumeController.PEER_REVIEWED,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.collectedVolume.reviewed",
                        SciPublicationsConstants.BUNDLE
                    )
                )
            )
        );
        reviewed.setLabel(
            new GlobalizedMessage(
                "publications.ui.collectedVolume.reviewed",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(reviewed);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        super.init(event);

        final FormData data = event.getFormData();
        final CollectedVolumeItem collectedVolumeItem
                                      = (CollectedVolumeItem) super
                .initBasicWidgets(event);

        final CollectedVolume collectedVolume = collectedVolumeItem
            .getPublication();

        if ((collectedVolume.getPeerReviewed() != null)
                && (collectedVolume.getPeerReviewed())) {
            reviewed.setValue(
                event.getPageState(),
                new String[]{CollectedVolumeController.PEER_REVIEWED}
            );
        } else {
            reviewed.setValue(event.getPageState(), null);
        }
    }

    @Override
    public void process(final FormSectionEvent event) throws
        FormProcessException {
        super.process(event);

        final PageState state = event.getPageState();
        final CollectedVolumeItem collectedVolumeItem
                                  = (CollectedVolumeItem) super
                .processBasicWidgets(event);

        if ((collectedVolumeItem != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {
            
            final CollectedVolumeController controller = CdiUtil
                .createCdiUtil()
                .findBean(CollectedVolumeController.class);
            final Map<String, Object> data = new HashMap<>();
            if (reviewed.getValue(state.getPageState()) == null) {
                data.put(CollectedVolumeController.PEER_REVIEWED, false);
            } else {
                data.put(CollectedVolumeController.PEER_REVIEWED, true);
            }

            controller.save(
                collectedVolumeItem.getPublication().getPublicationId(), 
                SelectedLanguageUtil.selectedLocale(
                    state, selectedLangParam
                ),
                data);
        }
    }

}
