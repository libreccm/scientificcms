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
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.InProceedingsItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class InProceedingsProceedingsForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener {

    private static final String PROCEEDINGS_SEARCH = "proceedings";

    private PublicationSearchWidget proceedingsSearch;

    public InProceedingsProceedingsForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        super("InProceedingsProceedings", itemModel, selectedLangParam);
    }

    @Override
    protected void addWidgets() {
        proceedingsSearch = new PublicationSearchWidget(
            PROCEEDINGS_SEARCH, Proceedings.class
        );
        proceedingsSearch.setLabel(
            new GlobalizedMessage(
                "publications.ui.inProceedings.selectProceedings",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(proceedingsSearch);
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

        final InProceedingsItem inProceedingsItem
                                    = (InProceedingsItem) getItemSelectionModel()
                .getSelectedItem(state);

        if (this.getSaveCancelSection().getSaveButton().isSelected(state)) {
            final Proceedings proceedings = (Proceedings) data
                .get(PROCEEDINGS_SEARCH);
            
            final InProceedingsController controller = CdiUtil
            .createCdiUtil()
            .findBean(InProceedingsController.class);
            
            controller.setProceedings(
               inProceedingsItem.getPublication().getPublicationId(), 
               proceedings.getPublicationId()
            );
        }

        init(event);
    }

}
