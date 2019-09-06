/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;
import org.libreccm.l10n.LocalizedString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "JOURNALS", schema = DB_SCHEMA)
@Audited
public class Journal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "JOURNAL_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long journalId;

    @Column(name = "UUID")
    private String uuid;

    /**
     * Year in which the journal was published first.
     */
    @Column(name = "FIRST_YEAR", nullable = true)
    private Integer firstYear;

    /**
     * Year in which the journal was published last
     */
    @Column(name = "LAST_YEAR", nullable = true)
    private Integer lastYear;

    /**
     * The ISSN of the journal.
     */
    @Column(name = "ISSN", length = 9)
    private String issn;

    @Column(name = "title", length = 1024, nullable = false)
    private String title;

    /**
     * A short description of the journal.
     */
    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "JOURNAL_DESCRIPTIONS",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "OBJECT_ID")
                               })
    )
    private LocalizedString description;

    /**
     * The usual symbol/abbrevation used to refer to the journal.
     */
    @Column(name = "SYMBOL")
    private String symbol;

    @OneToMany(mappedBy = "journal")
    private List<ArticleInJournal> articles;

    public Journal() {
        super();

        articles = new ArrayList<>();
    }

    public long getJournalId() {
        return journalId;
    }

    public void setJournalId(final long journalId) {
        this.journalId = journalId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public Integer getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(final Integer firstYear) {
        this.firstYear = firstYear;
    }

    public Integer getLastYear() {
        return lastYear;
    }

    public void setLastYear(final Integer lastYear) {
        this.lastYear = lastYear;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(final String issn) {
        this.issn = issn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public LocalizedString getDescription() {
        return description;
    }

    public void setDescription(final LocalizedString description) {
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<ArticleInJournal> getArticles() {
        return Collections.unmodifiableList(articles);
    }

    protected void addArticle(final ArticleInJournal article) {
        articles.add(article);
    }

    protected void removeArticle(final ArticleInJournal article) {
        articles.remove(article);
    }

    protected void setArticles(final List<ArticleInJournal> articles) {
        this.articles = new ArrayList<>(articles);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(firstYear);
        hash = 73 * hash + Objects.hashCode(lastYear);
        hash = 73 * hash + Objects.hashCode(issn);
        hash = 73 * hash + Objects.hashCode(title);
        hash = 73 * hash + Objects.hashCode(description);
        hash = 73 * hash + Objects.hashCode(symbol);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Journal)) {
            return false;
        }
        final Journal other = (Journal) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(firstYear, other.getFirstYear())) {
            return false;
        }
        if (!Objects.equals(lastYear, other.getLastYear())) {
            return false;
        }
        if (!Objects.equals(issn, other.getIssn())) {
            return false;
        }
        if (!Objects.equals(title, other.getTitle())) {
            return false;
        }
        if (!Objects.equals(symbol, other.getSymbol())) {
            return false;
        }
        return Objects.equals(description, other.getDescription());
    }

    public boolean canEqual(final Object obj) {
        return obj instanceof Journal;
    }

    @Override
    public final String toString() {
        return toString("");
    }

    public String toString(final String data) {

        return String.format("%s{ "
                                 + "journalId = %d, "
                                 + "uuid = \"%s\", "
                                 + "firstYear = %s, "
                                 + "lastYear = %s, "
                                 + "issn = \"%s\", "
                                 + "symbol = \"%s\", "
                                 + "title = \"%s\", "
                                 + "description = %s%s"
                                 + "}",
                             super.toString(),
                             journalId,
                             uuid,
                             Objects.toString(firstYear),
                             Objects.toString(lastYear),
                             issn,
                             symbol,
                             title,
                             description,
                             data);
    }

}
