/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.publications.ArticleInCollectedVolume;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.contenttypes.ArticleInCollectedVolumeItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = ArticleInCollectedVolumeItem.class)
public class ArticleInCollectedVolumeRenderer
    extends AbstractPublicationRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void renderPublication(
        final Publication publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final ArticleInCollectedVolume article;
        if (publication instanceof ArticleInCollectedVolume) {
            article = (ArticleInCollectedVolume) publication;
        } else {
            return;
        }
        publicationData.put("chapter", article.getChapter());
        publicationData.put(
            "collectedVolume", 
            renderCollectedVolume(article.getCollectedVolume(), language)
        );
        publicationData.put("endPage", article.getEndPage());
        publicationData.put("startPage", article.getStartPage());
    }

    private Map<String, Object> renderCollectedVolume(
        final CollectedVolume collectedVolume,
        final Locale language
    ) {
        final Map<String, Object> data = new HashMap<>();

        data.put(
            "authors",
            collectedVolume
                .getAuthorships()
                .stream()
                .map(this::renderAuthorship)
                .collect(Collectors.toList())
        );
        data.put("edition", collectedVolume.getEdition().getValue(language));
        data.put("isbn10", collectedVolume.getIsbn10());
        data.put("isbn13", collectedVolume.getIsbn13());
        data.put(
            "languageOfPublication",
            collectedVolume.getLanguageOfPublication().toString()
        );
        data.put("misc", collectedVolume.getMisc().getValue(language));
        data.put("numberOfPages", collectedVolume.getNumberOfPages());
        data.put("numberOfVolumes", collectedVolume.getNumberOfVolumes());
        data.put(
            "abstract",
            collectedVolume.getPublicationAbstract().getValue(language)
        );
        data.put("publicationId", collectedVolume.getPublicationId());
        data.put("publisher", renderPublisher(collectedVolume.getPublisher()));
        data.put(
            "shortDescription",
            collectedVolume.getShortDescription().getValue(language)
        );
        data.put("title", collectedVolume.getTitle().getValue(language));
        data.put("uuid", collectedVolume.getUuid());
        data.put("volume", collectedVolume.getVolume());
        data.put(
            "yearFirstPublished", 
            collectedVolume.getYearFirstPublished()
        );
        data.put("yearOfPublication", collectedVolume.getYearOfPublication());

        return data;
    }

}
