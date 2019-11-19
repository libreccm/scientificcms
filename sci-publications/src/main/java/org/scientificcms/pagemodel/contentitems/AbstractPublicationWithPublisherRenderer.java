/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.scientificcms.publications.Publication;
import org.scientificcms.publications.PublicationWithPublisher;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public abstract class AbstractPublicationWithPublisherRenderer
    extends AbstractPublicationRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected final void renderPublication(
        final Publication publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final PublicationWithPublisher publicationWithPublisher;
        if (publication instanceof PublicationWithPublisher) {
            publicationWithPublisher = (PublicationWithPublisher) publication;
        } else {
            return;
        }

        publicationData.put(
            "edition", publicationWithPublisher.getEdition().getValue(language)
        );
        publicationData.put("isbn10", publicationWithPublisher.getIsbn10());
        publicationData.put("isbn13", publicationWithPublisher.getIsbn13());
        publicationData.put(
            "numberOfPages", publicationWithPublisher.getNumberOfPages()
        );
        publicationData.put(
            "numberOfVolumes", publicationWithPublisher.getNumberOfVolumes()
        );
        publicationData.put(
            "publisher", 
            renderPublisher(publicationWithPublisher.getPublisher())
        );

        renderPublicationWithPublisher(
            publicationWithPublisher, language, publicationData
        );
    }

    protected abstract void renderPublicationWithPublisher(
        final PublicationWithPublisher publication,
        final Locale language,
        final Map<String, Object> publicationData
    );
    
    

}
