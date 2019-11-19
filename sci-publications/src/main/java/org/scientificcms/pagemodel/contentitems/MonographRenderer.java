/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.publications.Monograph;
import org.scientificcms.publications.PublicationWithPublisher;
import org.scientificcms.publications.contenttypes.MonographItem;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = MonographItem.class)
public class MonographRenderer extends AbstractPublicationWithPublisherRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void renderPublicationWithPublisher(
        final PublicationWithPublisher publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final Monograph monograph;
        if (publication instanceof Monograph) {
            monograph = (Monograph) publication;
        }else {
            return;
        }
        publicationData.put("reviewed", monograph.getReviewed());
    }

}
