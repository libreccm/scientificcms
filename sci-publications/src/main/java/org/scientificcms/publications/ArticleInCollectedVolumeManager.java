/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.security.AuthorizationRequired;
import org.libreccm.security.RequiresPrivilege;
import org.librecms.contentsection.privileges.ItemPrivileges;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class ArticleInCollectedVolumeManager {

    @Inject
    private PublicationRepository publicationRepository;

    /**
     * Set the {@link ArticleInCollectedVolume#collectedVolume} to the provided
     * {@link CollectedVolume} and update the list of articles of the provided
     * {@link CollectedVolume}.
     *
     * @param ofArticle
     * @param toCollectedVolume
     */
    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void setCollectedVolume(
        final ArticleInCollectedVolume ofArticle,
        final CollectedVolume toCollectedVolume
    ) {
        Objects.requireNonNull(ofArticle);
        Objects.requireNonNull(toCollectedVolume);

        ofArticle.setCollectedVolume(toCollectedVolume);
        toCollectedVolume.addArticle(ofArticle);
        publicationRepository.save(ofArticle);
        publicationRepository.save(toCollectedVolume);
    }

    /**
     * Unset the {@link ArticleInCollectedVolume#collectedVolume} and remove the
     * article from the {@link CollectedVolume}.
     *
     * @param ofArticle
     */
    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void unsetCollectedVolume(
        final ArticleInCollectedVolume ofArticle
    ) {
        Objects.requireNonNull(ofArticle);

        if (ofArticle.getCollectedVolume() != null) {
            final CollectedVolume collectedVolume = ofArticle
                .getCollectedVolume();
            ofArticle.setCollectedVolume(null);
            collectedVolume.removeArticle(ofArticle);
            
            publicationRepository.save(ofArticle);
            publicationRepository.save(collectedVolume);
        }
    }

}
