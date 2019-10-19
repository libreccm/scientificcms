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
import com.arsdigita.bebop.parameters.IntegerParameter;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.ArticleInCollectedVolume;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ArticleInCollectedVolumeItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ArticleInCollectedVolumePropertyForm
    extends PublicationPropertyForm
    implements FormProcessListener, FormInitListener, FormSubmissionListener {

    public static final String ID = "ArticleInCollectedVolumeEdit";

    private static final String REVIEWED = "reviewed";

    private final ArticleInCollectedVolumePropertiesStep propertiesStep;

    private final StringParameter selectedLangParam;

    private CheckboxGroup reviewed;

    public ArticleInCollectedVolumePropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public ArticleInCollectedVolumePropertyForm(
        final ItemSelectionModel itemModel,
        final ArticleInCollectedVolumePropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        propertiesStep = step;
        this.selectedLangParam = selectedLangParam;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        final ParameterModel startPageParam = new IntegerParameter(
            ArticleInCollectedVolumeController.START_PAGE);
        final TextField startPage = new TextField(startPageParam);
        startPage.setLabel(
            new GlobalizedMessage(
                "publications.ui.article_in_collected_volume.pages_from",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(startPage);

        ParameterModel endPageParam = new IntegerParameter(
            ArticleInCollectedVolumeController.END_PAGE
        );
        final TextField endPage = new TextField(endPageParam);
        endPage.setLabel(
            new GlobalizedMessage(
                "publications.ui.article_in_collected_volume.pages_to",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(endPage);

        ParameterModel chapterParam = new StringParameter(
            ArticleInCollectedVolumeController.CHAPTER
        );
        final TextField chapter = new TextField(chapterParam);
        chapter.setLabel(
            new GlobalizedMessage(
                "publications.ui.article_in_collected_volume.chapter",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(chapter);

        reviewed = new CheckboxGroup("reviewedGroup");
        reviewed.addOption(
            new Option(
                REVIEWED,
                new Label(
                    new GlobalizedMessage(
                        "publications.ui.articleInCollectedVolume.reviewed",
                        SciPublicationsConstants.BUNDLE
                    )
                )
            )
        );
        reviewed.setLabel(
            new GlobalizedMessage(
                "publications.ui.articleInCollectedVolume.reviewed",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(reviewed);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        super.init(event);

        final FormData data = event.getFormData();
        final ArticleInCollectedVolumeItem articleItem
                                               = (ArticleInCollectedVolumeItem) initBasicWidgets(
                event);

        final ArticleInCollectedVolume article = articleItem.getPublication();

        data.put(
            ArticleInCollectedVolumeController.START_PAGE,
            article.getStartPage()
        );
        data.put(
            ArticleInCollectedVolumeController.END_PAGE,
            article.getEndPage()
        );
        data.put(
            ArticleInCollectedVolumeController.CHAPTER, article.getChapter()
        );

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
        final ArticleInCollectedVolumeItem articleItem
                                               = (ArticleInCollectedVolumeItem) processBasicWidgets(
                event);

        if (articleItem != null
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Map<String, Object> data = new HashMap<>();

            data.put(
                ArticleInCollectedVolumeController.START_PAGE,
                data.get(ArticleInCollectedVolumeController.START_PAGE)
            );
            data.put(
                ArticleInCollectedVolumeController.END_PAGE,
                data.get(ArticleInCollectedVolumeController.END_PAGE)
            );
            data.put(
                ArticleInCollectedVolumeController.CHAPTER,
                data.get(ArticleInCollectedVolumeController.CHAPTER)
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
                state, selectedLangParam
            );
            
            final ArticleInCollectedVolumeController controller  = CdiUtil
            .createCdiUtil()
            .findBean(ArticleInCollectedVolumeController.class);
            controller.saveArticle(
                articleItem.getPublication().getPublicationId(),
                selectedLocale,
                data
            );
        }

    }

}
