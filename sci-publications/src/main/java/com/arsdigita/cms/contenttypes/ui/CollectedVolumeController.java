/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.scientificcms.publications.ArticleInCollectedVolume;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.CollectedVolumeManager;
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
public class CollectedVolumeController {

    public static final String PEER_REVIEWED = "peerReviewed";

    @Inject
    private CollectedVolumeManager collectedVolumeManager;

    @Inject
    private PublicationRepository publicationRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public void save(
        final long collectedVolumeId,
        final Locale selectedLocale,
        final Map<String, Object> data
    ) {
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

        if (data.get(PEER_REVIEWED) != null) {
            final Boolean reviewed = (Boolean) data.get(PEER_REVIEWED);
            collectedVolume.setPeerReviewed(reviewed);
        }

        publicationRepository.save(collectedVolume);
    }

    public ArticleInCollectedVolume findArticle(final long articleId) {
        return publicationRepository
            .findByIdAndType(articleId, ArticleInCollectedVolume.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No ArticleInCollectedVolume with ID %d found.",
                        articleId
                    )
                )
            );
    }

    public void addArticle(
        final long collectedVolumeId, final long articleId
    ) {
        final CollectedVolume collectedVolume = publicationRepository
            .findByIdAndType(collectedVolumeId, CollectedVolume.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No CollectedVolume with ID %d found",
                        collectedVolumeId
                    )
                )
            );

        final ArticleInCollectedVolume article = publicationRepository
            .findByIdAndType(articleId, ArticleInCollectedVolume.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No ArticleInCollectedVolume with ID %d found.",
                        articleId
                    )
                )
            );

        collectedVolumeManager.addArticleToCollectedVolume(
            article, collectedVolume
        );
    }

    public void removeArticle(
        final long collectedVolumeId, final long articleId
    ) {
        final CollectedVolume collectedVolume = publicationRepository
            .findByIdAndType(collectedVolumeId, CollectedVolume.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No CollectedVolume with ID %d found",
                        collectedVolumeId
                    )
                )
            );

        final ArticleInCollectedVolume article = publicationRepository
            .findByIdAndType(articleId, ArticleInCollectedVolume.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No ArticleInCollectedVolume with ID %d found.",
                        articleId
                    )
                )
            );

        collectedVolumeManager.removeArticleFromCollectedVolume(
            article, collectedVolume
        );
    }

}
