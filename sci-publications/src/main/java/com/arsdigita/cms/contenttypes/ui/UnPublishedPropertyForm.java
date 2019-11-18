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
import com.arsdigita.cms.ui.assets.AssetSearchWidget;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.UnPublished;
import org.scientificcms.publications.contenttypes.PublicationItem;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class UnPublishedPropertyForm
    extends PublicationPropertyForm
    implements FormInitListener, FormProcessListener, FormSubmissionListener {

    public static final String ID = "UnPublishedEdit";

    private static final String ORGA_SEARCH = "organization";

    private UnPublishedPropertiesStep step;

    private AssetSearchWidget orgaSearch;

    public UnPublishedPropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public UnPublishedPropertyForm(
        final ItemSelectionModel itemModel,
        final UnPublishedPropertiesStep step,
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
            UnPublishedController.PLACE);
        final TextField place = new TextField(placeParam);
        place.setLabel(
            new GlobalizedMessage(
                "publications.ui.unpublished.place",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(place);

        ParameterModel numberParam = new StringParameter(
            UnPublishedController.NUMBER
        );
        final TextField number = new TextField(numberParam);
        number.setLabel(
            new GlobalizedMessage(
                "publications.ui.unpublished.number",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(number);

        final ParameterModel numberOfPagesParam = new IntegerParameter(
            UnPublishedController.NUMBER_OF_PAGES
        );
        final TextField numberOfPages = new TextField(numberOfPagesParam);
        numberOfPages.setLabel(
            new GlobalizedMessage(
                "publications.ui.unpublished.number_of_pages",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(numberOfPages);

    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        super.init(event);

        final FormData data = event.getFormData();
        final PublicationItem<?> publicationItem
                                     = (PublicationItem) initBasicWidgets(event);
        final UnPublished unPublished = (UnPublished) publicationItem
            .getPublication();

        data.put(
            UnPublishedController.PLACE, unPublished.getPlace()
        );
        data.put(
            UnPublishedController.NUMBER, unPublished.getNumber()
        );
        data.put(
            UnPublishedController.NUMBER_OF_PAGES,
            unPublished.getNumberOfPages()
        );
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {
        super.process(event);

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        final PublicationItem<?> publicationItem
                                     = (PublicationItem) initBasicWidgets(event);

        if ((publicationItem != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {
            final UnPublished unpublished = (UnPublished) publicationItem
                .getPublication();

            final Map<String, Object> data = new HashMap<>();
            data.put(
                UnPublishedController.PLACE,
                formData.get(UnPublishedController.PLACE)
            );
            data.put(
                UnPublishedController.NUMBER,
                formData.get(UnPublishedController.NUMBER)
            );
            data.put(
                UnPublishedController.NUMBER_OF_PAGES,
                formData.get(UnPublishedController.NUMBER_OF_PAGES)
            );

            final UnPublishedController controller = CdiUtil
                .createCdiUtil()
                .findBean(UnPublishedController.class);
            controller.saveUnPublished(unpublished.getPublicationId(), data);
        }
    }

}
