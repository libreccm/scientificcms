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
public class JournalManager {

    @Inject
    private JournalRepository journalRepository;

    @Inject
    private PublicationRepository publicationRepository;

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void addArticleToJournal(final ArticleInJournal article,
                                    final Journal journal) {
        Objects.requireNonNull(article);
        Objects.requireNonNull(journal);

        article.setJournal(journal);
        journal.addArticle(article);

        publicationRepository.save(article);
        journalRepository.save(journal);
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void removeArticleFromJournal(final ArticleInJournal article,
                                         final Journal journal) {

        Objects.requireNonNull(article);
        Objects.requireNonNull(journal);

        if (!journal.equals(article.getJournal())
                || !journal.getArticles().contains(article)) {
            throw new IllegalArgumentException(
                String.format(
                    "The provided article %s is not an article of the "
                        + "provided journal %s.",
                    article.getUuid(),
                    journal.getUuid()
                )
            );
        }
        
        article.setJournal(null);
        journal.removeArticle(article);
        
        publicationRepository.save(article);
        journalRepository.save(journal);
    }

}
