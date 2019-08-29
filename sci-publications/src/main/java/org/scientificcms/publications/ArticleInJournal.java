/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "ARTICLES_IN_JOURNAL", schema = DB_SCHEMA)
@Audited
public class ArticleInJournal extends Publication {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "JOURNAL_ID")
    private Journal journal;

    @Column(name = "VOLUME")
    private Integer volume;

    @Column(name = "ISSUE")
    private String issue;

    @Column(name = "PAGES_FROM")
    private Integer pagesFrom;

    @Column(name = "PAGES_TO")
    private Integer pagesTo;

    @Column(name = "PUBLICATION_DATE")
    private LocalDate publicationDate;

    public Journal getJournal() {
        return journal;
    }

    protected void setJournal(final Journal journal) {
        this.journal = journal;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(final Integer volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(final String issue) {
        this.issue = issue;
    }

    public Integer getPagesFrom() {
        return pagesFrom;
    }

    public void setPagesFrom(final Integer pagesFrom) {
        this.pagesFrom = pagesFrom;
    }

    public Integer getPagesTo() {
        return pagesTo;
    }

    public void setPagesTo(final Integer pagesTo) {
        this.pagesTo = pagesTo;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(final LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 67 * hash + Objects.hashCode(journal);
        hash = 67 * hash + Objects.hashCode(volume);
        hash = 67 * hash + Objects.hashCode(issue);
        hash = 67 * hash + Objects.hashCode(pagesFrom);
        hash = 67 * hash + Objects.hashCode(pagesTo);
        hash = 67 * hash + Objects.hashCode(publicationDate);
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
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof ArticleInJournal)) {
            return false;
        }
        final ArticleInJournal other = (ArticleInJournal) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(journal, other.getJournal())) {
            return false;
        }
        if (!Objects.equals(volume, other.getVolume())) {
            return false;
        }
        if (!Objects.equals(issue, other.getIssue())) {
            return false;
        }
        if (!Objects.equals(pagesFrom, other.getPagesFrom())) {
            return false;
        }
        if (!Objects.equals(pagesTo, other.getPagesTo())) {
            return false;
        }
        return Objects.equals(publicationDate, other.getPublicationDate());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof ArticleInJournal;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format(", journal = %s, "
                                                + "volume = %d, "
                                                + "issue = \"%s\", "
                                                + "pagesFrom = %d, "
                                                + "pagesTo = %d, "
                                                + "publicationDate = %s%s",
                                            Objects.toString(journal),
                                            volume,
                                            issue,
                                            pagesFrom,
                                            pagesTo,
                                            Objects.toString(publicationDate),
                                            data));
    }

}
