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
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.Monograph;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.MonographItem;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class MonographPropertyForm extends PublicationWithPublisherPropertyForm
    implements FormProcessListener, FormInitListener, FormSubmissionListener {

    public static final String ID = "MonographEdit";


    private CheckboxGroup reviewed;

    public MonographPropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public MonographPropertyForm(
        final ItemSelectionModel itemModel,
        final MonographPropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        reviewed = new CheckboxGroup("reviewedGroup");
        reviewed.addOption(
            new Option(
                MonographController.REVIEWED,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.monograph.reviewed",
                        SciPublicationsConstants.BUNDLE
                    )
                )
            )
        );
        reviewed.setLabel(
            new GlobalizedMessage(
                "publications.ui.monograph.reviewed",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(reviewed);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        super.init(event);

        final FormData data = event.getFormData();
        final MonographItem monographItem = (MonographItem) super
            .initBasicWidgets(event);
        final Monograph monograph = monographItem.getPublication();

        if ((monograph.getReviewed() != null) && (monograph.getReviewed())) {
            reviewed.setValue(
                event.getPageState(),
                new String[]{MonographController.REVIEWED}
            );
        } else {
            reviewed.setValue(event.getPageState(), null);
        }

    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {
        super.process(event);

        final PageState state = event.getPageState();
        final MonographItem monographItem = (MonographItem) super
            .processBasicWidgets(event);

        if ((monographItem != null) 
            && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Map<String, Object> data = new HashMap<>();
            if (reviewed.getValue(event.getPageState()) == null) {
                data.put(MonographController.REVIEWED, false);
            } else {
                data.put(MonographController.REVIEWED, true);
            }

            final MonographController controller = CdiUtil
            .createCdiUtil()
            .findBean(MonographController.class);
            controller.saveMonograph(
                monographItem.getPublication().getPublicationId(), 
                data
            );
        }
    }

}
