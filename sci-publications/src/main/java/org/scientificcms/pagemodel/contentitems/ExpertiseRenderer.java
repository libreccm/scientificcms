/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.publications.Expertise;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.contenttypes.ExpertiseItem;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = ExpertiseItem.class)
public class ExpertiseRenderer extends AbstractPublicationRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void renderPublication(
        final Publication publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final Expertise expertise;
        if (publication instanceof Expertise) {
            expertise = (Expertise) publication;
        } else {
            return;
        }

        publicationData.put("numberOfPages", expertise.getNumberOfPages());
        publicationData.put("place", expertise.getPlace());
        publicationData.put(
            "orderer", renderOrganization(expertise.getOrderer())
        );
        publicationData.put(
            "organization", renderOrganization(expertise.getOrganization())
        );
    }

}
