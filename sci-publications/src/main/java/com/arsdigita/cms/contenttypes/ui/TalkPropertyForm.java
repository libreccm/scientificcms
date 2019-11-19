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
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.Talk;
import org.scientificcms.publications.contenttypes.TalkItem;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class TalkPropertyForm extends PublicationPropertyForm
    implements FormInitListener, FormProcessListener, FormSubmissionListener {

    private static final String ID = "TaskEdit";

    public TalkPropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParameter
    ) {
        this(itemModel, null, selectedLangParameter);
    }

    public TalkPropertyForm(
        final ItemSelectionModel itemModel,
        final TalkPropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        addSubmissionListener(this);
    }

    @Override
    public void addWidgets() {

        super.addWidgets();

        final ParameterModel placeParameter = new StringParameter(
            TalkController.PLACE
        );
        final TextField placeField = new TextField(placeParameter);
        placeField
            .setLabel(
                new GlobalizedMessage(
                    "publications.ui.talk.place",
                    SciPublicationsConstants.BUNDLE
                )
            );
        add(placeField);

        final ParameterModel dateParameter = new DateParameter(
            TalkController.DATE_OF_TALK
        );
        final Date dateField = new Date(dateParameter);
        final Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.get(Calendar.YEAR);
        dateField.setYearRange(currentYear - 10, currentYear + 1);
        dateField
            .setLabel(
                new GlobalizedMessage(
                    "publications.ui.talk.date",
                    SciPublicationsConstants.BUNDLE
                )
            );
        add(dateField);

        final ParameterModel eventParameter = new StringParameter(
            TalkController.EVENT
        );
        final TextField eventField = new TextField(eventParameter);
        eventField
            .setLabel(
                new GlobalizedMessage(
                    "publications.ui.talk.event",
                    SciPublicationsConstants.BUNDLE
                )
            );
        add(eventField);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        super.init(event);

        final FormData data = event.getFormData();
        final TalkItem talkItem = (TalkItem) initBasicWidgets(event);
        final Talk talk = talkItem.getPublication();

        data.put(
            TalkController.EVENT,
            talk.getEvent()
        );
        final LocalDate localDateOfTalk = talk.getDateOfTalk();
        final java.util.Date dateOfTalk = java.util.Date.from(
            localDateOfTalk.atStartOfDay().atZone(
                ZoneId.systemDefault()
            ).toInstant()
        );
        data.put(TalkController.DATE_OF_TALK, dateOfTalk);
        data.put(TalkController.PLACE, talk.getPlace());
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {

        super.process(event);

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        final TalkItem talkItem = (TalkItem) processBasicWidgets(event);

        if (talkItem != null
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Map<String, Object> data = new HashMap<>();

            final java.util.Date dateOfTalk = (java.util.Date) data.get(
                TalkController.DATE_OF_TALK
            );
            final LocalDate localDateOfTalk = dateOfTalk
                .toInstant().atZone(
                    ZoneId.systemDefault()
                ).toLocalDate();
            data.put(TalkController.DATE_OF_TALK, localDateOfTalk);

            data.put(TalkController.EVENT, data.get(TalkController.EVENT));
            data.put(TalkController.PLACE, data.get(TalkController.PLACE));

            final TalkController controller = CdiUtil
                .createCdiUtil()
                .findBean(TalkController.class);

            controller.saveTalk(
                talkItem.getPublication().getPublicationId(),
                data
            );
        }
    }

}
