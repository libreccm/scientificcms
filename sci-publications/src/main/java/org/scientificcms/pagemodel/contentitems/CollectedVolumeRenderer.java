/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.publications.ArticleInCollectedVolume;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.PublicationWithPublisher;
import org.scientificcms.publications.contenttypes.CollectedVolumeItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = CollectedVolumeItem.class)
public class CollectedVolumeRenderer
    extends AbstractPublicationWithPublisherRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void renderPublicationWithPublisher(
        final PublicationWithPublisher publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final CollectedVolume collectedVolume;
        if (publication instanceof CollectedVolume) {
            collectedVolume = (CollectedVolume) publication;
        } else {
            return;
        }

        publicationData.put(
            "articles",
            collectedVolume
                .getArticles()
                .stream()
                .map(article -> renderArticle(article, language))
                .collect(Collectors.toList())
        );
    }

    private Map<String, Object> renderArticle(
        final ArticleInCollectedVolume article, final Locale language
    ) {
        final Map<String, Object> data = new HashMap<>();

        data.put(
            "authors",
            article
                .getAuthorships()
                .stream()
                .map(this::renderAuthorship)
                .collect(Collectors.toList())
        );
        data.put("chapter", article.getChapter());
        data.put("endPage", article.getEndPage());
        data.put(
            "languageOfPublication",
            article.getLanguageOfPublication().toString()
        );
        data.put("misc", article.getMisc().getValue(language));
        data
            .put("abstract", article.getPublicationAbstract().getValue(language));
        data.put("publicationId", article.getPublicationId());
        data.put(
            "shortDescription",
            article.getShortDescription().getValue(language)
        );
        data.put("startPage", article.getStartPage());
        data.put("title", article.getTitle().getValue(language));
        data.put("uuid", article.getUuid());
        data.put("yearFirstPublished", article.getYearFirstPublished());
        data.put("yearOfPublication", article.getYearOfPublication());

        return data;
    }

}
