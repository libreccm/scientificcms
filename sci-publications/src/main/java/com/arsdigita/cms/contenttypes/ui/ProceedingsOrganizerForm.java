/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

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
import org.scientificcms.publications.contenttypes.ProceedingsItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ProceedingsOrganizerForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener {

    private static final String ORGA_SEARCH = "conferenceOrganization";

    private AssetSearchWidget orgaSearch;

    private final ItemSelectionModel itemModel;

    public ProceedingsOrganizerForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        super("ProceeingsOrganizerForm", itemModel, selectedLangParam);
        this.itemModel = itemModel;
    }

    @Override
    protected void addWidgets() {
        orgaSearch = new AssetSearchWidget(
            ORGA_SEARCH, Organization.class
        );
        orgaSearch.setLabel(
            new GlobalizedMessage(
                "publications.ui.proceedings.organizer",
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
        final PageState state = event.getPageState();
        final ProceedingsItem proceedingsItem = (ProceedingsItem) itemModel.
            getSelectedItem(state);

        if (getSaveCancelSection().getSaveButton().isSelected(state)) {
            final Organization organization = (Organization) orgaSearch
                .getValue(state);
            final ProceedingsController controller = CdiUtil
                .createCdiUtil()
                .findBean(ProceedingsController.class);
            controller.setOrganizier(
                proceedingsItem.getPublication().getPublicationId(),
                organization.getObjectId()
            );
        }

        init(event);
    }

}
