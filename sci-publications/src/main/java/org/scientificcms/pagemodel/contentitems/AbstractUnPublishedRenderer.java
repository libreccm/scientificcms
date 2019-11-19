/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.scientificcms.publications.Publication;
import org.scientificcms.publications.UnPublished;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public abstract class AbstractUnPublishedRenderer extends AbstractPublicationRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected final void renderPublication(
        final Publication publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final UnPublished unPublished;
        if (publication instanceof UnPublished) {
            unPublished = (UnPublished) publication;
        } else {
            return;
        }
        
        publicationData.put("number", unPublished.getNumber());
        publicationData.put("numberOfPages", unPublished.getNumberOfPages());
        publicationData.put(
            "organization", renderOrganization(unPublished.getOrganization())
        );
        publicationData.put("place", unPublished.getPlace());
        
        renderUnPublished(unPublished, language, publicationData);
    }
    
    protected abstract void renderUnPublished(
        final UnPublished unPublished,
        final Locale language,
        final Map<String, Object> publicationData
    );
    
    

}
