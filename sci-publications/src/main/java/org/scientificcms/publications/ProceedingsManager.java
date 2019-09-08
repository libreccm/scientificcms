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
public class ProceedingsManager {

    @Inject
    private PublicationRepository publicationRepository;

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void addPaperToCollectedVolume(final InProceedings paper,
                                          final Proceedings proceedings) {
        Objects.requireNonNull(paper);
        Objects.requireNonNull(proceedings);

        paper.setProceedings(proceedings);
        proceedings.addPaper(paper);

        publicationRepository.save(paper);
        publicationRepository.save(proceedings);
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void removeArticleFromCollectedVolume(
        final InProceedings paper,
        final Proceedings collectedVolume
    ) {
        Objects.requireNonNull(paper);
        Objects.requireNonNull(collectedVolume);

        if (!collectedVolume.equals(paper.getProceedings())
                || !collectedVolume.getPapers().contains(paper)) {
            throw new IllegalArgumentException(
                String.format(
                    "The provided paper %s is not a paper of the "
                        + "provided proceedings %s.",
                    paper.getUuid(),
                    collectedVolume.getUuid()
                )
            );
        }

        paper.setProceedings(null);
        collectedVolume.removePaper(paper);

        publicationRepository.save(paper);
        publicationRepository.save(collectedVolume);
    }

}
