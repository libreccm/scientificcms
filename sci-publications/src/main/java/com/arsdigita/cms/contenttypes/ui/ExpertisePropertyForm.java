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
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.IntegerParameter;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.Expertise;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ExpertiseItem;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ExpertisePropertyForm
    extends PublicationPropertyForm
    implements FormInitListener, FormProcessListener, FormSubmissionListener {

    public static final String ID = "ExpertiseEdit";

    private final ExpertisePropertiesStep step;

    public ExpertisePropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public ExpertisePropertyForm(
        final ItemSelectionModel itemModel,
        final ExpertisePropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        this.step = step;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        final ParameterModel placeParam = new StringParameter(
            ExpertiseController.PLACE
        );
        final TextField place = new TextField(placeParam);
        place.setLabel(
            new GlobalizedMessage(
                "publications.ui.expertise.place",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(place);

        final ParameterModel numberOfPagesParam = new IntegerParameter(
            ExpertiseController.NUMBER_OF_PAGES
        );
        final TextField numberOfPages = new TextField(numberOfPagesParam);
        numberOfPages.setLabel(
            new GlobalizedMessage(
                "publications.ui.expertise.number_of_pages",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(numberOfPages);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        super.init(event);

        final FormData data = event.getFormData();
        final ExpertiseItem expertiseItem = (ExpertiseItem) initBasicWidgets(
            event);

        final Expertise expertise = expertiseItem.getPublication();

        data.put(ExpertiseController.PLACE, expertise.getPlace());
        data.put(
            ExpertiseController.NUMBER_OF_PAGES,
            expertise.getNumberOfPages()
        );
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {
        super.process(event);

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        final ExpertiseItem expertiseItem = (ExpertiseItem) processBasicWidgets(
            event
        );

        if ((expertiseItem != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Map<String, Object> data = new HashMap<>();
            data.put(
                ExpertiseController.PLACE,
                formData.get(ExpertiseController.PLACE)
            );
            data.put(
                ExpertiseController.NUMBER_OF_PAGES,
                formData.get(ExpertiseController.NUMBER_OF_PAGES)
            );

            final ExpertiseController controller = CdiUtil
                .createCdiUtil()
                .findBean(ExpertiseController.class);

            controller.save(
                expertiseItem.getPublication().getPublicationId(),
                data
            );
        }
    }

}
