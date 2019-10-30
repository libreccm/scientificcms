/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.scientificcms.publications.ArticleInJournal;
import org.scientificcms.publications.ArticleInJournalManager;
import org.scientificcms.publications.Journal;
import org.scientificcms.publications.JournalRepository;
import org.scientificcms.publications.PublicationRepository;

import java.time.LocalDate;
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
public class ArticleInJournalController {

    public static final String VOLUME = "volume";

    public static final String ISSUE = "issue";

    public static final String START_PAGE = "startPage";

    public static final String END_PAGE = "endPage";

    public static final String PUBLICATION_DATE = "publicationDate";

    public static final String PEER_REVIEWED = "reviewed";

    @Inject
    private ArticleInJournalManager articleManager;

    @Inject
    private JournalRepository journalRepository;

    @Inject
    private PublicationRepository publicationRepository;

    /**
     * Save a changed {@link ArticleInJournal}.
     *
     * @param articleId      The ID of the article.
     * @param selectedLocale The locale selected in the UI.
     * @param data           The data to set on the article.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public void saveArticle(
        final long articleId,
        final Locale selectedLocale,
        final Map<String, Object> data
    ) {
        final ArticleInJournal article = publicationRepository
            .findByIdAndType(articleId, ArticleInJournal.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No ArticleInJournal with ID %d found.",
                        articleId
                    )
                )
            );

        if (data.get(VOLUME) != null) {
            final Integer volume = (Integer) data.get(VOLUME);
            article.setVolume(volume);
        }
        if (data.get(ISSUE) != null) {
            final String issue = (String) data.get(ISSUE);
            article.setIssue(issue);
        }
        if (data.get(START_PAGE) != null) {
            final Integer startPage = (Integer) data.get(START_PAGE);
            article.setStartPage(startPage);
        }
        if (data.get(END_PAGE) != null) {
            final Integer endPage = (Integer) data.get(END_PAGE);
            article.setStartPage(endPage);
        }
        if (data.get(PUBLICATION_DATE) != null) {
            final LocalDate publicationDate = (LocalDate) data.get(
                PUBLICATION_DATE
            );
            article.setPublicationDate(publicationDate);
        }
        if (data.get(PEER_REVIEWED) != null) {
            final Boolean peerReviewed = (Boolean) data.get(PEER_REVIEWED);
            article.setPeerReviewed(peerReviewed);
        }

        publicationRepository.save(article);
    }

    /**
     * Set the value of the {@link ArticleInJournal#journal} property to a
     * {@link Journal}.
     *
     * @param articleId The ID of the article to use.
     * @param journalId The ID of the journal to use.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public void setJournal(
        final long articleId, final long journalId
    ) {
        final ArticleInJournal article = publicationRepository
            .findByIdAndType(articleId, ArticleInJournal.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No ArticleInJournal with ID %d found.",
                        articleId
                    )
                )
            );
        final Journal journal = journalRepository
            .findById(journalId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Journal with ID %d found",
                        journalId
                    )
                )
            );

        articleManager.setJournal(article, journal);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void unsetJournal(
        final long articleId, final long journalId
    ) {
        final ArticleInJournal article = publicationRepository
            .findByIdAndType(articleId, ArticleInJournal.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No ArticleInJournal with ID %d found.",
                        articleId
                    )
                )
            );

        articleManager.unsetJournal(article);
    }

}
