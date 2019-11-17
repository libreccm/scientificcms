/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.security.AuthorizationRequired;
import org.libreccm.security.RequiresPrivilege;
import org.librecms.contentsection.privileges.ItemPrivileges;

import java.io.Serializable;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class CollectedVolumeManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PublicationRepository publicationRepository;

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void addArticleToCollectedVolume(
        final ArticleInCollectedVolume article,
        final CollectedVolume collectedVolume
    ) {
        Objects.requireNonNull(article);
        Objects.requireNonNull(collectedVolume);

        article.setCollectedVolume(collectedVolume);
        collectedVolume.addArticle(article);

        publicationRepository.save(article);
        publicationRepository.save(collectedVolume);
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void removeArticleFromCollectedVolume(
        final ArticleInCollectedVolume article,
        final CollectedVolume collectedVolume
    ) {
        Objects.requireNonNull(article);
        Objects.requireNonNull(collectedVolume);

        if (!collectedVolume.equals(article.getCollectedVolume())
                || !collectedVolume.getArticles().contains(article)) {
            throw new IllegalArgumentException(
                String.format(
                    "The provided article %s is not an article of the "
                        + "provided collected volume %s.",
                    article.getUuid(),
                    collectedVolume.getUuid()
                )
            );
        }

        article.setCollectedVolume(null);
        collectedVolume.removeArticle(article);

        publicationRepository.save(article);
        publicationRepository.save(collectedVolume);
    }
   
}
