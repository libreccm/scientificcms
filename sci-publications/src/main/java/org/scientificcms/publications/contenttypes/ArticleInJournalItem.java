/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.hibernate.envers.Audited;
import org.scientificcms.publications.ArticleInJournal;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "ARTICLE_IN_COLLECTED_VOLUME_ITEMS", schema = DB_SCHEMA)
@Audited
public class ArticleInJournalItem extends PublicationItem<ArticleInJournal> {

    private static final long serialVersionUID = 1L;

//    @OneToOne(cascade = {CascadeType.DETACH, 
//                         CascadeType.MERGE, 
//                         CascadeType.PERSIST, 
//                         CascadeType.REFRESH
//    })
//    @JoinColumn(name = "ARTICLE_ID")
//    private ArticleInJournal article;
//    @Override
//    public ArticleInJournal getPublication() {
//        return article;
//    }
//
//    @Override
//    protected void setPublication(final ArticleInJournal article) {
//        this.article = article;
//    }
    @Override
    public int hashCode() {
        int hash = super.hashCode();
//        hash = 67 * hash + Objects.hashCode(this.article);
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
        if (!(obj instanceof ArticleInJournalItem)) {
            return false;
        }
        final ArticleInJournalItem other
                                       = (ArticleInJournalItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(this.article, other.getPublication());
        return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof ArticleInJournalItem;
    }

//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format(", article = %s%s",
//                                            Objects.toString(article),
//                                            data));
//    }

}
