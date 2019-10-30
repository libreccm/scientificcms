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
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.DateParameter;
import com.arsdigita.bebop.parameters.IntegerParameter;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.ArticleInJournal;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ArticleInJournalItem;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ArticleInJournalPropertyForm
    extends PublicationPropertyForm
    implements FormInitListener,
               FormProcessListener,
               FormSubmissionListener {

    public static final String ID = "ArticleInJournalEdit";

    private static final String REVIEWED = "reviewed";

    private final ArticleInJournalPropertiesStep propertiesStep;

    private final StringParameter selectedLanguageParam;

    private CheckboxGroup reviewed;

    public ArticleInJournalPropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLanguageParam
    ) {
        this(itemModel, null, selectedLanguageParam);
    }

    public ArticleInJournalPropertyForm(
        final ItemSelectionModel itemModel,
        final ArticleInJournalPropertiesStep propertiesStep,
        final StringParameter selectedLanguageParam
    ) {

        super(itemModel, propertiesStep, selectedLanguageParam);
        this.propertiesStep = propertiesStep;
        this.selectedLanguageParam = selectedLanguageParam;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        final ParameterModel volumeParam = new IntegerParameter(
            ArticleInJournalController.VOLUME
        );
        final TextField volume = new TextField(volumeParam);
        volume.setLabel(
            new GlobalizedMessage(
                "publications.ui.articleinjournal.volume",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(volume);

        final ParameterModel issueParam = new StringParameter(
            ArticleInJournalController.ISSUE
        );
        final TextField issue = new TextField(issueParam);
        issue.setLabel(
            new GlobalizedMessage(
                "publications.ui.articleinjournal.issue",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(issue);

        final ParameterModel pagesFromParam = new IntegerParameter(
            ArticleInJournalController.START_PAGE
        );
        final TextField startPage = new TextField(pagesFromParam);
        startPage.setLabel(
            new GlobalizedMessage(
                "publications.ui.articleinjournal.pages_from",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(startPage);

        final ParameterModel pagesToParam = new IntegerParameter(
            ArticleInJournalController.END_PAGE
        );
        final TextField endPage = new TextField(pagesToParam);
        endPage.setLabel(
            new GlobalizedMessage(
                "publications.ui.articleinjournal.pages_to",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(endPage);

        final Calendar today = new GregorianCalendar();
        final ParameterModel pubDateParam = new DateParameter(
            ArticleInJournalController.PUBLICATION_DATE
        );
        final com.arsdigita.bebop.form.Date pubDate
                                                = new com.arsdigita.bebop.form.Date(
                pubDateParam
            );
        pubDate.setYearAsc(false);
        pubDate.setYearRange(1900, today.get(Calendar.YEAR) + 2);
        pubDate.setLabel(
            new GlobalizedMessage(
                "publications.ui.articleinjournal.publicationDate",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(pubDate);

        reviewed = new CheckboxGroup("reviewedGroup");
        reviewed.addOption(new Option(
            REVIEWED,
            new Label(
                new GlobalizedMessage(
                    "publications.ui.articleinjournal.reviewed",
                    SciPublicationsConstants.BUNDLE)
            )
        )
        );
        reviewed.setLabel(
            new GlobalizedMessage(
                "publications.ui.articleinjournal.reviewed"
            )
        );
        add(reviewed);
    }

    protected final CheckboxGroup getReviewed() {
        return reviewed;
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        super.init(event);

        final FormData data = event.getFormData();
        final ArticleInJournalItem articleItem
                                       = (ArticleInJournalItem) initBasicWidgets(
                event);
        final ArticleInJournal article = articleItem.getPublication();

        data.put(ArticleInJournalController.VOLUME, article.getVolume());
        data.put(ArticleInJournalController.ISSUE, article.getIssue());
        data.put(ArticleInJournalController.START_PAGE, article.getStartPage());
        data.put(ArticleInJournalController.END_PAGE, article.getEndPage());
        data.put(ArticleInJournalController.PUBLICATION_DATE,
                 article.getPublicationDate());

        if ((article.getPeerReviewed() != null)
                && (article.getPeerReviewed())) {
            reviewed.setValue(event.getPageState(), new String[]{REVIEWED});
        } else {
            reviewed.setValue(event.getPageState(), null);
        }
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {

        super.process(event);

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        final ArticleInJournalItem articleItem
                                       = (ArticleInJournalItem) processBasicWidgets(
                event
            );

        if ((articleItem != null)
                && getSaveCancelSection().getSaveButton().isSelected(event
                .getPageState()
            )) {

            final Map<String, Object> data = new HashMap<>();

            data.put(
                ArticleInJournalController.VOLUME,
                formData.get(ArticleInJournalController.VOLUME
                )
            );

            data.put(
                ArticleInJournalController.ISSUE,
                formData.get(ArticleInJournalController.ISSUE
                )
            );

            data.put(
                ArticleInJournalController.START_PAGE,
                formData.get(ArticleInJournalController.START_PAGE
                )
            );

            data.put(
                ArticleInJournalController.END_PAGE,
                formData.get(ArticleInJournalController.END_PAGE
                )
            );

            data.put(
                ArticleInJournalController.PUBLICATION_DATE,
                formData.get(ArticleInJournalController.PUBLICATION_DATE
                )
            );

            if (reviewed.getValue(event.getPageState()) == null) {
                data.put(
                    ArticleInCollectedVolumeController.PEER_REVIEWED,
                    Boolean.FALSE
                );
            } else {
                data.put(
                    ArticleInCollectedVolumeController.PEER_REVIEWED,
                    Boolean.TRUE
                );
            }

            final Locale selectedLocale = SelectedLanguageUtil.selectedLocale(
                state, selectedLanguageParam
            );

            final ArticleInJournalController controller = CdiUtil
                .createCdiUtil()
                .findBean(ArticleInJournalController.class);
            controller.saveArticle(
                articleItem.getPublication().getPublicationId(),
                selectedLocale,
                data
            );
        }
    }

}
