/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.publications.ArticleInJournal;
import org.scientificcms.publications.Journal;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.contenttypes.ArticleInJournalItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = ArticleInJournalItem.class)
public class ArticleInJournalRenderer extends AbstractPublicationRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void renderPublication(
        final Publication publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final ArticleInJournal article;
        if (publication instanceof ArticleInJournal) {
            article = (ArticleInJournal) publication;
        } else {
            return;
        }
        
        publicationData.put("endPage", article.getEndPage());
        publicationData.put("issue", article.getIssue());
        publicationData.put(
            "journal", renderJournal(article.getJournal(), language)
        );
        publicationData.put("publicationDate", article.getPublicationDate());
        publicationData.put("startPage", article.getStartPage());
        publicationData.put("volume", article.getVolume());
    }

    private Map<String, Object> renderJournal(
        final Journal journal, final Locale language
    ) {
        final Map<String, Object> data = new HashMap<>();

        data.put("description", journal.getDescription().getValue(language));
        data.put("firstYear", journal.getFirstYear());
        data.put("issn", journal.getIssn());
        data.put("journalId", journal.getJournalId());
        data.put("lastYear", journal.getLastYear());
        data.put("symbol", journal.getSymbol());
        data.put("title", journal.getTitle());
        data.put("uuid", journal.getUuid());

        return data;
    }

}
