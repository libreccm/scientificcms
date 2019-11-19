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
import com.arsdigita.bebop.form.Date;
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.DateParameter;
import com.arsdigita.bebop.parameters.NotEmptyValidationListener;
import com.arsdigita.bebop.parameters.NotNullValidationListener;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ProceedingsItem;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ProceedingsPropertyForm
    extends PublicationWithPublisherPropertyForm
    implements FormProcessListener, FormInitListener, FormSubmissionListener {

    public static final String ID = "proceedingsEdit";

    public ProceedingsPropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public ProceedingsPropertyForm(
        final ItemSelectionModel itemModel,
        final ProceedingsPropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        //m_step = step;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        final ParameterModel nameOfConfParam = new StringParameter(
            ProceedingsController.NAME_OF_CONFERENCE
        );
        final TextField nameOfConf = new TextField(nameOfConfParam);
        nameOfConf.addValidationListener(new NotNullValidationListener());
        nameOfConf.addValidationListener(new NotEmptyValidationListener());
        nameOfConf.setLabel(
            new GlobalizedMessage(
                "publications.ui.proceedings.name_of_conference",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(nameOfConf);

        final ParameterModel placeOfConfParam = new StringParameter(
            ProceedingsController.PLACE_OF_CONFERENCE);
        final TextField placeOfConf = new TextField(placeOfConfParam);
        placeOfConf.setLabel(
            new GlobalizedMessage(
                "publications.ui.proceedings.place_of_conference",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(placeOfConf);

        final Calendar today = new GregorianCalendar();
        final ParameterModel dateFromParam = new DateParameter(
            ProceedingsController.START_DATE);
        final Date dateFrom = new Date(dateFromParam);
        dateFrom.setYearRange(1900, today.get(Calendar.YEAR) + 3);

        dateFrom.setLabel(
            new GlobalizedMessage(
                "publications.ui.proceedings.date_from_of_conference",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(dateFrom);

        final ParameterModel dateToParam = new DateParameter(
            ProceedingsController.END_DATE
        );
        final Date dateTo = new Date(dateToParam);
        dateTo.setYearRange(1900, today.get(Calendar.YEAR) + 3);

        dateTo.setLabel(
            new GlobalizedMessage(
                "publications.ui.proceedings.date_to_of_conference",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(dateTo);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        super.init(event);

        final FormData data = event.getFormData();
        final ProceedingsItem proceedingsItem = (ProceedingsItem) super
            .initBasicWidgets(event);
        final Proceedings proceedings = proceedingsItem.getPublication();

        data.put(
            ProceedingsController.NAME_OF_CONFERENCE,
            proceedings.getNameOfConference()
        );
        data.put(
            ProceedingsController.PLACE_OF_CONFERENCE,
            proceedings.getPlaceOfConference()
        );
        final LocalDate localStartDate = proceedings.getStartDate();
        final java.util.Date startDate = java.util.Date.from(
            localStartDate.atStartOfDay().atZone(
                ZoneId.systemDefault()
            ).toInstant());
        data.put(ProceedingsController.START_DATE, startDate);
        final LocalDate localEndDate = proceedings.getEndDate();
        final java.util.Date endDate = java.util.Date.from(
            localEndDate.atStartOfDay().atZone(
                ZoneId.systemDefault()
            ).toInstant());
        data.put(ProceedingsController.END_DATE, endDate);
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {
        super.process(event);

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        final ProceedingsItem proceedingsItem = (ProceedingsItem) super
            .processBasicWidgets(event);

        if ((proceedingsItem != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Map<String, Object> data = new HashMap<>();
            data.put(
                ProceedingsController.NAME_OF_CONFERENCE,
                formData.get(ProceedingsController.NAME_OF_CONFERENCE)
            );
            data.put(
                ProceedingsController.PLACE_OF_CONFERENCE,
                formData.get(ProceedingsController.PLACE_OF_CONFERENCE)
            );

            final java.util.Date startDate = (java.util.Date) formData
                .get(ProceedingsController.START_DATE);
            data.put(
                ProceedingsController.START_DATE,
                startDate.toInstant().atZone(
                    ZoneId.systemDefault()
                ).toLocalDate()
            );

            final java.util.Date endDate = (java.util.Date) formData
                .get(ProceedingsController.END_DATE);
            data.put(
                ProceedingsController.END_DATE,
                endDate.toInstant().atZone(
                    ZoneId.systemDefault()
                ).toLocalDate()
            );

            final ProceedingsController controller = CdiUtil
                .createCdiUtil()
                .findBean(ProceedingsController.class);
            controller.saveProceedings(
                proceedingsItem.getPublication().getPublicationId(), data
            );
        }
    }

}
