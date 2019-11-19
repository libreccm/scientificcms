/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.publications.InternetArticle;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.contenttypes.InternetArticleItem;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = InternetArticleItem.class)
public class InternetArticleRenderer extends AbstractPublicationRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void renderPublication(
        final Publication publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final InternetArticle article;
        if (publication instanceof InternetArticle) {
            article = (InternetArticle) publication;
        } else {
            return;
        }
        
        
        publicationData.put("doi", article.getDoi());
        publicationData.put("edition", article.getEdition());
        publicationData.put("issn", article.getIssn());
        publicationData.put("lastAccessed", article.getLastAccessed());
        publicationData.put("number", article.getNumber());
        publicationData.put("numberOfPages", article.getNumberOfPages());
        publicationData.put(
            "organization", renderOrganization(article.getOrganization())
        );
        publicationData.put("place", article.getPlace());
        publicationData.put("publicationDate", article.getPublicationDate());
        publicationData.put("url", article.getUrl());
        publicationData.put("urn", article.getUrn());
    }

}
