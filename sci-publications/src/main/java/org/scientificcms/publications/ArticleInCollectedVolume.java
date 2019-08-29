/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;

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
@Table(name = "ARTICLES_IN_COLLECTED_VOLUME", schema = DB_SCHEMA)
@Audited
public class ArticleInCollectedVolume extends Publication {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "COLLECTED_VOLUME_ID")
    private CollectedVolume collectedVolume;

    @Column(name = "PAGES_FROM")
    private Integer pagesFrom;

    @Column(name = "PAGES_TO")
    private Integer pagesTo;

    @Column(name = "CHAPTER", length = 1024)
    private String chapter;

    public CollectedVolume getCollectedVolume() {
        return collectedVolume;
    }

    protected void setCollectedVolume(final CollectedVolume collectedVolume) {
        this.collectedVolume = collectedVolume;
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

    public String getChapter() {
        return chapter;
    }

    public void setChapter(final String chapter) {
        this.chapter = chapter;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 47 * hash + Objects.hashCode(collectedVolume);
        hash = 47 * hash + Objects.hashCode(pagesFrom);
        hash = 47 * hash + Objects.hashCode(pagesTo);
        hash = 47 * hash + Objects.hashCode(chapter);
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
        if (!(obj instanceof ArticleInCollectedVolume)) {
            return false;
        }
        final ArticleInCollectedVolume other = (ArticleInCollectedVolume) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(chapter, other.getChapter())) {
            return false;
        }
        if (!Objects.equals(collectedVolume, other.getCollectedVolume())) {
            return false;
        }
        if (!Objects.equals(pagesFrom, other.getPagesFrom())) {
            return false;
        }
        return Objects.equals(pagesTo, other.getPagesTo());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof ArticleInCollectedVolume;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format(", collectedVolume = %s, "
                                                + "chapter = \"%s\", "
                                                + "pagesFrom = %d, "
                                                + "pagesTo = %d%s",
                                            Objects.toString(collectedVolume),
                                            chapter,
                                            pagesFrom,
                                            pagesTo,
                                            data));
    }

}
