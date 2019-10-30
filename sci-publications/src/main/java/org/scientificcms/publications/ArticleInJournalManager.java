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
public class ArticleInJournalManager {

    @Inject
    private JournalRepository journalRepository;

    @Inject
    private PublicationRepository publicationRepository;

    /**
     * Set the {@link ArticleInJournal#journal} to the provided {@link Journal}
     * and update the list of articles of the provided {@link Journal}.
     *
     * @param ofArticle
     * @param toJournal
     */
    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void setJournal(
        final ArticleInJournal ofArticle,
        final Journal toJournal
    ) {
        Objects.requireNonNull(ofArticle);
        Objects.requireNonNull(toJournal);

        ofArticle.setJournal(toJournal);
        toJournal.addArticle(ofArticle);
        publicationRepository.save(ofArticle);
        journalRepository.save(toJournal);
    }

    /**
     * Unset the {@link ArticleInJournal#journal} and remove the article from
     * the {@link CollectedVolume}.
     *
     * @param ofArticle
     */
    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void unsetJournal(
        final ArticleInJournal ofArticle
    ) {
        Objects.requireNonNull(ofArticle);

        if (ofArticle.getJournal() != null) {
            final Journal journal = ofArticle.getJournal();
            ofArticle.setJournal(null);
            journal.removeArticle(ofArticle);

            publicationRepository.save(ofArticle);
            journalRepository.save(journal);
        }
    }

}
