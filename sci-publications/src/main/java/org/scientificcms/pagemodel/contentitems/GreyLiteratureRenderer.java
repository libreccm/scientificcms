/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.publications.GreyLiterature;
import org.scientificcms.publications.UnPublished;
import org.scientificcms.publications.contenttypes.GreyLiteratureItem;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = GreyLiteratureItem.class)
public class GreyLiteratureRenderer extends AbstractUnPublishedRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void renderUnPublished(
        final UnPublished unPublished,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final GreyLiterature greyLiterature;
        if (unPublished instanceof GreyLiterature) {
            greyLiterature = (GreyLiterature) unPublished;
        } else {
            return;
        }

        publicationData.put("startPage", greyLiterature.getStartPage());
        publicationData.put("endPage", greyLiterature.getEndPage());
    }

}
