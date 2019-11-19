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
import org.scientificcms.publications.InProceedings;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ProceedingsItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ProceedingsPapersAddForm extends BasicItemForm
    implements FormProcessListener,
               FormInitListener {

    private static final String PAPER_SEARCH = "papers";

    private PublicationSearchWidget paperSearch;

    public ProceedingsPapersAddForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        super("PapersAddForm", itemModel, selectedLangParam);
    }

    @Override
    protected void addWidgets() {
        paperSearch = new PublicationSearchWidget(
            PAPER_SEARCH, InProceedings.class
        );
        paperSearch.setLabel(
            new GlobalizedMessage(
                "publications.ui.proceedings.select_paper",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(paperSearch);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        PageState state = event.getPageState();
        setVisible(state, true);
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {
        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        final ProceedingsItem proceedingsItem
                                  = (ProceedingsItem) getItemSelectionModel().
                getSelectedObject(state);
        final Proceedings proceedings = proceedingsItem.getPublication();

        if (getSaveCancelSection().getSaveButton().isSelected(state)) {
            final InProceedings paper = (InProceedings) formData.get(
                PAPER_SEARCH
            );

            final ProceedingsController controller = CdiUtil
                .createCdiUtil()
                .findBean(ProceedingsController.class);
            controller.addPaper(
                proceedings.getPublicationId(), paper.getPublicationId()
            );

        }

        init(event);
    }

}
