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

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.ArticleInCollectedVolume;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ArticleInCollectedVolumeItem;

/**
 * Form for adding an association between an ArticleInCollectedVolume and a
 * CollectedVolume.
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ArticleInCollectedVolumeCollectedVolumeForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener {

    private final static String COLLECTED_VOLUME_SEARCH
                                    = "collectedVolumeSearch";

    private PublicationSearchWidget collectedVolumeSearch;

    public ArticleInCollectedVolumeCollectedVolumeForm(
        final ItemSelectionModel itemSelectionModel,
        final StringParameter selectedLanguageParam) {

        super(
            "ArticleInCollectedVolumeCollectedVolumeForm",
            itemSelectionModel,
            selectedLanguageParam
        );
    }

    @Override
    protected void addWidgets() {

        collectedVolumeSearch = new PublicationSearchWidget(
            COLLECTED_VOLUME_SEARCH, CollectedVolume.class
        );

        collectedVolumeSearch.setLabel(
            new GlobalizedMessage(
                "publications.ui.articleInCollectedVolume.selectCollectedVolume",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(collectedVolumeSearch);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        final PageState state = event.getPageState();

        setVisible(state, true);
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {

        final FormData data = event.getFormData();
        final PageState state = event.getPageState();

        final ArticleInCollectedVolumeItem articleItem
                                               = (ArticleInCollectedVolumeItem) getItemSelectionModel()
                .getSelectedObject(state);
        final ArticleInCollectedVolume article = articleItem.getPublication();

        if (this.getSaveCancelSection().getSaveButton().isSelected(state)) {
            final CollectedVolume collectedVolume = (CollectedVolume) data.get(
                COLLECTED_VOLUME_SEARCH
            );

            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final ArticleInCollectedVolumeController controller = cdiUtil
                .findBean(ArticleInCollectedVolumeController.class);
            controller.setCollectedVolume(
                article.getPublicationId(), collectedVolume.getPublicationId()
            );
        }

        init(event);
    }

}
