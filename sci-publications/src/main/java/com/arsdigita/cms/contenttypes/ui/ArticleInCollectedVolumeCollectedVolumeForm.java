/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.assets.ItemSearchWidget;
import com.arsdigita.cms.ui.authoring.BasicItemForm;

import org.scientificcms.publications.contenttypes.CollectedVolumeItem;

/**
 * Form for adding an association between an ArticleInCollectedVolume and a
 * CollectedVolume.
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ArticleInCollectedVolumeCollectedVolumeForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener {

    private ItemSearchWidget itemSearch;

    private final String ITEM_SEARCH = "collectedVolume";

    public ArticleInCollectedVolumeCollectedVolumeForm(
        final String formName,
        final ItemSelectionModel itemSelectionModel,
        final StringParameter selectedLanguageParam) {

        super(formName, itemSelectionModel, selectedLanguageParam);
    }

    @Override
    protected void addWidgets() {

        itemSearch = new ItemSearchWidget(
            ITEM_SEARCH,
            CollectedVolumeItem.class
        );
        itemSearch.
        
        itemSearch.setDefaultCreationFolder(config
            .getDefaultCollectedVolumesFolder());
        itemSearch.setLabel(PublicationGlobalizationUtil.globalize(
            "publications.ui.articleInCollectedVolume.selectCollectedVolume"));
        add(itemSearch);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void process(final FormSectionEvent event) throws
        FormProcessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
