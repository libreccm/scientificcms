/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.scientificcms.publications.ArticleInCollectedVolume;
import org.scientificcms.publications.ArticleInCollectedVolumeManager;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.PublicationRepository;

import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class ArticleInCollectedVolumeController {

    public static final String START_PAGE = "startPage";

    public static final String END_PAGE = "endPage";

    public static final String CHAPTER = "chapter";

    public static final String PEER_REVIEWED = "peerReviewed";

    @Inject
    private ArticleInCollectedVolumeManager articleManager;

    @Inject
    private PublicationRepository publicationRepository;

    /**
     * Save a changed {@link ArticleInCollectedVolume}.
     *
     * @param publicationId  The ID of the article.
     * @param selectedLocale The locale selected in the UI.
     * @param data           The data to set on the article.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public void saveArticle(
        final long publicationId,
        final Locale selectedLocale,
        final Map<String, Object> data
    ) {
        final ArticleInCollectedVolume article = publicationRepository
            .findByIdAndType(publicationId, ArticleInCollectedVolume.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No ArticleInCollectedVolume with ID %d found.",
                        publicationId
                    )
                )
            );

        if (data.get(START_PAGE) != null) {
            final Integer startPage = (Integer) data.get(START_PAGE);
            article.setStartPage(startPage);
        }
        if (data.get(END_PAGE) != null) {
            final Integer endPage = (Integer) data.get(END_PAGE);
            article.setEndPage(endPage);
        }
        if (data.get(CHAPTER) != null) {
            final String chapter = (String) data.get(CHAPTER);
            article.setChapter(chapter);
        }
        if (data.get(PEER_REVIEWED) != null) {
            final Boolean peerReviewed = (Boolean) data.get(PEER_REVIEWED);
            article.setPeerReviewed(peerReviewed);
        }

        publicationRepository.save(article);
    }

    /**
     * Set the value of {@link ArticleInCollectedVolume#collectedVolume}
     * property to a {@link CollectedVolume}.
     *
     * @param articleId         The ID of the article to use.
     * @param collectedVolumeId The ID of the collected volume to use.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public void setCollectedVolume(
        final long articleId, final long collectedVolumeId
    ) {
        final ArticleInCollectedVolume article = publicationRepository
            .findByIdAndType(articleId, ArticleInCollectedVolume.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No ArticleInCollectedVolume with ID %d found",
                        articleId
                    )
                )
            );

        final CollectedVolume collectedVolume = publicationRepository
            .findByIdAndType(collectedVolumeId, CollectedVolume.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No CollectedVolume with ID %d found.",
                        collectedVolumeId
                    )
                )
            );

        articleManager.setCollectedVolume(article, collectedVolume);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void unsetCollectedVolume(
        final long articleId
    ) {
        final ArticleInCollectedVolume article = publicationRepository
            .findByIdAndType(articleId, ArticleInCollectedVolume.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No ArticleInCollectedVolume with ID %d found",
                        articleId
                    )
                )
            );

        articleManager.unsetCollectedVolume(article);
    }

}
