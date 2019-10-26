/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.libreccm.l10n.GlobalizationHelper;
import org.scientificcms.publications.Journal;
import org.scientificcms.publications.JournalRepository;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class JournalSearchWidgetController {

    protected static final String JOURNAL_ID = "journalId";

    protected static final String TITLE = "title";

    protected static final String SYMBOL = "symbol";

    protected static final String ISSN = "issn";

    @Inject
    private JournalRepository journalRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public Map<String, String> getData(final long journalId) {

        final Journal journal = journalRepository
            .findById(journalId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Journal with ID %d found.", journalId)
                )
            );

        final Map<String, String> data = new HashMap<>();

        data.put(JOURNAL_ID, Long.toString(journal.getJournalId()));
        data.put(TITLE, journal.getTitle());
        data.put(SYMBOL, journal.getSymbol());
        data.put(ISSN, journal.getIssn());

        return data;
    }

}
