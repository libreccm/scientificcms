/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

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
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.WorkingPaper;
import org.scientificcms.publications.contenttypes.WorkingPaperItem;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class WorkingPaperPropertyForm
    extends UnPublishedPropertyForm
    implements FormInitListener,
               FormProcessListener,
               FormSubmissionListener {

    public static final String ID = "WorkingPaperEdit";

    private final WorkingPaperPropertiesStep step;

    private CheckboxGroup reviewed;

    public WorkingPaperPropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public WorkingPaperPropertyForm(
        ItemSelectionModel itemModel,
        WorkingPaperPropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        this.step = step;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        reviewed = new CheckboxGroup("reviewedGroup");
        reviewed.addOption(
            new Option(
                WorkingPaperController.PEER_REVIEWED,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.workingpaper.reviewed",
                        SciPublicationsConstants.BUNDLE
                    )
                )
            )
        );
        reviewed.setLabel(
            new GlobalizedMessage(
                "publications.ui.workingpaper.reviewed",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(reviewed);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        super.init(event);

        final WorkingPaperItem paperItem = (WorkingPaperItem) super
            .initBasicWidgets(event);
        final WorkingPaper paper = paperItem.getPublication();

        if ((paper.getPeerReviewed() != null) && (paper.getPeerReviewed())) {
            reviewed.setValue(
                event.getPageState(),
                new String[]{WorkingPaperController.PEER_REVIEWED}
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

        final WorkingPaperItem paperItem = (WorkingPaperItem) super
            .processBasicWidgets(event);

        if ((paperItem != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Map<String, Object> data = new HashMap<>();
            if (reviewed.getValue(event.getPageState()) == null) {
                data.put(WorkingPaperController.PEER_REVIEWED, false);
            } else {
                data.put(WorkingPaperController.PEER_REVIEWED, true);
            }

            final WorkingPaperController controller = CdiUtil
                .createCdiUtil()
                .findBean(WorkingPaperController.class);

            controller.saveWorkingPaper(
                paperItem.getPublication().getPublicationId(), data
            );
        }
    }

}
