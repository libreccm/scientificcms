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
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.globalization.GlobalizedMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.ArticleInCollectedVolume;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.CollectedVolumeItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class CollectedVolumeArticlesAddForm
    extends BasicItemForm
    implements FormProcessListener,
               FormInitListener {

    private static final Logger LOGGER = LogManager.getLogger(
        CollectedVolumeArticlesAddForm.class
    );

    private final String ARTICLE_SEARCH = "articles";

    private final ItemSelectionModel itemModel;

    private PublicationSearchWidget articleSearch;

    public CollectedVolumeArticlesAddForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        super("ArticlesAddForm", itemModel, selectedLangParam);
        this.itemModel = itemModel;
    }

    @Override
    protected void addWidgets() {

        articleSearch = new PublicationSearchWidget(
            ARTICLE_SEARCH, ArticleInCollectedVolume.class
        );
        articleSearch.setLabel(
            new GlobalizedMessage(
                "publications.ui.collected_volume.articles.select_article",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(articleSearch);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        final PageState state = event.getPageState();

        setVisible(state, true);
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {
        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        final CollectedVolumeItem collectedVolumeItem
                                      = (CollectedVolumeItem) getItemSelectionModel()
                .getSelectedItem(state);

        if (this.getSaveCancelSection().getSaveButton().isSelected(state)) {
            final ArticleInCollectedVolume article
                                               = (ArticleInCollectedVolume) formData
                    .get(ARTICLE_SEARCH);

            final CollectedVolumeController controller = CdiUtil
                .createCdiUtil()
                .findBean(CollectedVolumeController.class);

            controller.addArticle(
                collectedVolumeItem.getPublication().getPublicationId(),
                article.getPublicationId()
            );
        }

        init(event);
    }

}
