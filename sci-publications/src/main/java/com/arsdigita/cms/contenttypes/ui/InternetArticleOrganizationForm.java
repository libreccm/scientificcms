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
import com.arsdigita.cms.ui.assets.AssetSearchWidget;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.librecms.assets.Organization;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.InternetArticleItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class InternetArticleOrganizationForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener {

    private static final String ORGA_SEARCH = "internetArticleOrga";

    private AssetSearchWidget orgaSearch;

    public InternetArticleOrganizationForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        super("InternetArticleOrganizationForm", itemModel, selectedLangParam);
    }

    @Override
    public void addWidgets() {
        orgaSearch = new AssetSearchWidget(ORGA_SEARCH, Organization.class);
        orgaSearch.setLabel(
            new GlobalizedMessage(
                "publications.ui.internetarticle.select_organization",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(orgaSearch);
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

        final InternetArticleItem articleItem
                                  = (InternetArticleItem) getItemSelectionModel()
                .getSelectedItem(state);

        if (getSaveCancelSection().getSaveButton().isSelected(state)) {
            final Organization orga
                                      = (Organization) formData
                    .get(ORGA_SEARCH);
            final InternetArticleController controller = CdiUtil
            .createCdiUtil()
            .findBean(InternetArticleController.class);
            
            controller.setOrganization(
                articleItem.getPublication().getPublicationId(), 
                orga.getObjectId()
            );
        }

        init(event);
    }

}
