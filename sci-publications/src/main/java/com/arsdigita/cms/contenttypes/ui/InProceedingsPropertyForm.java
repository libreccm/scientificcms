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
import org.scientificcms.publications.InProceedings;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.InProceedingsItem;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class InProceedingsPropertyForm extends PublicationPropertyForm
    implements FormProcessListener, FormInitListener, FormSubmissionListener {

    public static final String ID = "InProceedingsEdit";

    private InProceedingsPropertiesStep step;

    public InProceedingsPropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public InProceedingsPropertyForm(
        final ItemSelectionModel itemModel,
        final InProceedingsPropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        this.step = step;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        final ParameterModel pagesFromParam = new IntegerParameter(
            InProceedingsController.START_PAGE);
        final TextField pagesFrom = new TextField(pagesFromParam);
        pagesFrom.setLabel(
            new GlobalizedMessage(
                "publications.ui.inproceedings.pages_from",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(pagesFrom);

        ParameterModel pagesToParam = new IntegerParameter(
            InProceedingsController.END_PAGE
        );
        final TextField pagesTo = new TextField(pagesToParam);
        pagesTo.setLabel(
            new GlobalizedMessage(
                "publications.ui.inproceedings.pages_to",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(pagesTo);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        super.init(event);

        final FormData data = event.getFormData();
        final InProceedingsItem inProceedingsItem = (InProceedingsItem) super
            .initBasicWidgets(event);
        final InProceedings inProceedings = inProceedingsItem.getPublication();

        data.put(
            InProceedingsController.START_PAGE, inProceedings.getStartPage()
        );
        data.put(
            InProceedingsController.END_PAGE, inProceedings.getEndPage()
        );
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {
        super.process(event);

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        final InProceedingsItem inProceedingsItem = (InProceedingsItem) super
            .processBasicWidgets(event);

        if ((inProceedingsItem != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Map<String, Object> data = new HashMap<>();
            data.put(
                InProceedingsController.START_PAGE,
                formData.get(InProceedingsController.START_PAGE)
            );
            data.put(
                InProceedingsController.END_PAGE,
                formData.get(InProceedingsController.END_PAGE)
            );

            final InProceedingsController controller = CdiUtil
                .createCdiUtil()
                .findBean(InProceedingsController.class);

            controller.saveInProceedings(
                inProceedingsItem.getPublication().getPublicationId(), data
            );
        }
    }

}
