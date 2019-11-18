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
import org.scientificcms.publications.GreyLiterature;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.GreyLiteratureItem;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class GreyLiteraturePropertyForm extends UnPublishedPropertyForm
    implements FormInitListener,
               FormProcessListener, FormSubmissionListener {

    public static final String ID = "GreyLiteratureEdit";

    private GreyLiteraturePropertiesStep step;

    public GreyLiteraturePropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public GreyLiteraturePropertyForm(
        final ItemSelectionModel itemModel,
        final GreyLiteraturePropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        this.step = step;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        final ParameterModel fromParam = new IntegerParameter(
            GreyLiteratureController.START_PAGE
        );
        final TextField pagesFrom = new TextField(fromParam);
        pagesFrom.setLabel(
            new GlobalizedMessage(
                "publications.ui.greyliterature.pages_from",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(pagesFrom);

        final ParameterModel toParam = new IntegerParameter(
            GreyLiteratureController.END_PAGE);
        final TextField pagesTo = new TextField(toParam);
        pagesTo.setLabel(
            new GlobalizedMessage(
                "publications.ui.greyliterature.pages_to",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(pagesTo);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        super.init(event);

        final FormData data = event.getFormData();
        final GreyLiteratureItem greyItem
                                     = (GreyLiteratureItem) initBasicWidgets(
                event);
        final GreyLiterature grey = greyItem.getPublication();

        data.put(
            GreyLiteratureController.START_PAGE, grey.getStartPage()
        );
        data.put(
            GreyLiteratureController.END_PAGE, grey.getEndPage()
        );
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {
        super.process(event);

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        final GreyLiteratureItem greyItem
                                     = (GreyLiteratureItem) processBasicWidgets(
                event);

        if ((greyItem != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Map<String, Object> data = new HashMap<>();
            data.put(
                GreyLiteratureController.START_PAGE,
                formData.get(GreyLiteratureController.START_PAGE)
            );
            data.put(
                GreyLiteratureController.END_PAGE,
                formData.get(GreyLiteratureController.END_PAGE)
            );

            final GreyLiteratureController controller = CdiUtil
                .createCdiUtil()
                .findBean(GreyLiteratureController.class);
            controller.saveGreyLiterature(
                greyItem.getPublication().getPublicationId(),
                data
            );
        }
    }

}
