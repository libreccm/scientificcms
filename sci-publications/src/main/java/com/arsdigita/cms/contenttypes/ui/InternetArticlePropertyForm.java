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
import com.arsdigita.bebop.event.ParameterEvent;
import com.arsdigita.bebop.event.ParameterListener;
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.DateParameter;
import com.arsdigita.bebop.parameters.IntegerParameter;
import com.arsdigita.bebop.parameters.ParameterData;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.InternetArticle;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.InternetArticleItem;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class InternetArticlePropertyForm
    extends PublicationPropertyForm
    implements FormInitListener, FormProcessListener, FormSubmissionListener {

    public static final String ID = "InternetArticleEdit";

    private final InternetArticlePropertiesStep step;

    private final StringParameter selectedLangParam;

    public InternetArticlePropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public InternetArticlePropertyForm(
        final ItemSelectionModel itemModel,
        final InternetArticlePropertiesStep step,
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

        final ParameterModel placeParam = new StringParameter(
            InternetArticleController.PLACE
        );
        final TextField place = new TextField(placeParam);
        place.setLabel(
            new GlobalizedMessage(
                "publications.ui.internetarticle.place",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(place);

        final ParameterModel numberParam = new StringParameter(
            InternetArticleController.NUMBER
        );
        final TextField number = new TextField(numberParam);
        number.setLabel(
            new GlobalizedMessage(
                "publications.ui.internetarticle.number",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(number);

        final ParameterModel numberOfPagesParam = new IntegerParameter(
            InternetArticleController.NUMBER_OF_PAGES
        );
        final TextField numberOfPages = new TextField(numberOfPagesParam);
        numberOfPages.setLabel(
            new GlobalizedMessage(
                "publications.ui.internetarticle.number_of_pages",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(numberOfPages);

        final ParameterModel editionParam = new StringParameter(
            InternetArticleController.EDITION
        );
        final TextField edition = new TextField(editionParam);
        edition.setLabel(
            new GlobalizedMessage(
                "publications.ui.internetarticle.edition",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(edition);

        final ParameterModel issnParam = new StringParameter(
            InternetArticleController.ISSN
        );
        final TextField issn = new TextField(issnParam);
        issn.setLabel(
            new GlobalizedMessage(
                "publications.ui.internetarticle.issn",
                SciPublicationsConstants.BUNDLE
            )
        );
        issn.setMaxLength(9);
        issn.addValidationListener(new ParameterListener() {

            @Override
            public void validate(final ParameterEvent event)
                throws FormProcessException {
                final ParameterData data = event.getParameterData();
                String value = (String) data.getValue();

                if (value.isEmpty()) {
                    return;
                }

                value = value.replace("-", "");

                if (value.length() != 8) {
                    data.invalidate();
                    data.addError(new GlobalizedMessage(
                        "publications.ui.invalid_issn"));
                }

                try {
                    Long num = Long.parseLong(value);
                } catch (NumberFormatException ex) {
                    data.invalidate();
                    data.addError(new GlobalizedMessage(
                        "publications.ui.invalid_issn"));
                }
            }

        });
        add(issn);

        final Calendar today = new GregorianCalendar();
        final ParameterModel pubDateParam = new DateParameter(
            InternetArticleController.LAST_ACCESSED
        );
        final com.arsdigita.bebop.form.Date pubDate
                                                = new com.arsdigita.bebop.form.Date(
                pubDateParam
            );
        pubDate.setYearRange(1900, today.get(Calendar.YEAR) + 2);
        pubDate.setLabel(
            new GlobalizedMessage(
                "publications.ui.internetarticle.lastAccessed",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(pubDate);

        ParameterModel urlModel = new StringParameter(
            InternetArticleController.URL
        );
        TextField url = new TextField(urlModel);
        url.setLabel(new GlobalizedMessage(
            "publications.ui.internetarticle.url"));
        add(url);

        final ParameterModel urnModel = new StringParameter(
            InternetArticleController.URN
        );
        final TextField urn = new TextField(urnModel);
        urn.setLabel(
            new GlobalizedMessage(
                "publications.ui.internetarticle.urn",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(urn);

        final ParameterModel doiModel = new StringParameter(
            InternetArticleController.DOI
        );
        final TextField doi = new TextField(doiModel);
        doi.setLabel(
            new GlobalizedMessage(
                "publications.ui.internetarticle.doi",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(doi);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        super.init(event);

        final FormData data = event.getFormData();
        final InternetArticleItem articleItem
                                      = (InternetArticleItem) initBasicWidgets(
                event);
        final InternetArticle article = articleItem.getPublication();

        data.put(InternetArticleController.PLACE, article.getPlace());
        data.put(InternetArticleController.NUMBER, article.getNumber());
        data.put(InternetArticleController.NUMBER_OF_PAGES, article
                 .getNumberOfPages());
        data.put(InternetArticleController.EDITION, article.getEdition());
        data.put(InternetArticleController.ISSN, article.getIssn());
        data.put(
            InternetArticleController.LAST_ACCESSED,
            java.util.Date.from(
                article.getLastAccessed().atStartOfDay().atZone(
                    ZoneId.systemDefault()
                ).toInstant()
            )
        );
        data.put(InternetArticleController.URL, article.getUrl());
        data.put(InternetArticleController.URN, article.getUrn());
        data.put(InternetArticleController.DOI, article.getDoi());
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {
        super.process(event);

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();

        final InternetArticleItem articleItem
                                  = (InternetArticleItem) processBasicWidgets(
                event);

        if ((articleItem != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {
            final Map<String, Object> data = new HashMap<>();

            data.put(
                InternetArticleController.PLACE,
                formData.get(InternetArticleController.PLACE
                )
            );
            data.put(
                InternetArticleController.NUMBER,
                formData.get(InternetArticleController.NUMBER
                )
            );
            data.put(
                InternetArticleController.NUMBER_OF_PAGES,
                formData.get(InternetArticleController.NUMBER_OF_PAGES
                )
            );
            data.put(
                InternetArticleController.EDITION,
                formData.get(InternetArticleController.EDITION
                )
            );
            data.put(
                InternetArticleController.ISSN,
                formData.get(InternetArticleController.ISSN
                )
            );
            final java.util.Date lastAccessed = (java.util.Date) formData.get(
                InternetArticleController.LAST_ACCESSED
            );
            data.put(
                InternetArticleController.LAST_ACCESSED,
                lastAccessed.toInstant().atZone(
                    ZoneId.systemDefault()
                ).toLocalDate()
            );
            data.put(
                InternetArticleController.URL,
                formData.get(InternetArticleController.URL
                )
            );
            data.put(
                InternetArticleController.URN,
                formData.get(InternetArticleController.URN
                )
            );
            data.put(
                InternetArticleController.DOI,
                formData.get(InternetArticleController.DOI
                )
            );
            
            final InternetArticleController controller = CdiUtil
            .createCdiUtil()
            .findBean(InternetArticleController.class);
            controller.saveInternetArticle(
                articleItem.getPublication().getPublicationId(), data
            );
        }
    }

}
