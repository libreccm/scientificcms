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

import org.scientificcms.publications.SciPublicationsConstants;

import java.util.concurrent.Flow.Publisher;


/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationWithPublisherSetPublisherForm
    extends BasicItemForm
    implements FormInitListener, FormProcessListener {

    private PublisherSearchWidget publisherSearch;
    private final String PUBLISHER_SEARCH = "setPublisher";

    public PublicationWithPublisherSetPublisherForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        super(
            "PublicationWithPublisherSetPublisher",
            itemModel,
            selectedLangParam
        );
    }
    
     @Override
    public void addWidgets() {

        publisherSearch = new PublisherSearchWidget(PUBLISHER_SEARCH); 
        publisherSearch.setLabel(
            new GlobalizedMessage(
                            "publications.ui.with_publisher.publisher",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(publisherSearch);
    }

        @Override
    public void init(final FormSectionEvent fse) throws FormProcessException {
        final PageState state = fse.getPageState();

        setVisible(state, true);
    }

    @Override
    public void process(final FormSectionEvent fse) 
        throws FormProcessException {
        final FormData data = fse.getFormData();
        final PageState state = fse.getPageState();
        final PublicationWithPublisherItem publication =
                                 (PublicationWithPublisherItem) getItemSelectionModel().
                getSelectedObject(state);

        if (this.getSaveCancelSection().getSaveButton().isSelected(state)) {
            final Publisher publisher = (Publisher) data.get(PUBLISHER_SEARCH);
            
            publication.setPublisher(publisher);
            itemSearch.publishCreatedItem(data, publisher);
        }

        init(fse);
    }

    

}
