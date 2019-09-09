/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.hibernate.envers.Audited;
import org.scientificcms.publications.ArticleInCollectedVolume;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "ARTICLE_IN_COLLECTED_VOLUME_ITEMS", schema = DB_SCHEMA)
@Audited
public class ArticleInCollectedVolumeItem 
    extends PublicationItem<ArticleInCollectedVolume> {

    private static final long serialVersionUID = 1L;

//    @OneToOne(cascade = {CascadeType.DETACH,
//                         CascadeType.MERGE,
//                         CascadeType.PERSIST,
//                         CascadeType.REFRESH
//    })
//    @JoinColumn(name = "ARTICLE_ID")
//    private ArticleInCollectedVolume article;
//
//    @Override
//    public ArticleInCollectedVolume getPublication() {
//        return article;
//    }
//
//    @Override
//    protected void setPublication(final ArticleInCollectedVolume article) {
//        this.article = article;
//    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
//        hash = 67 * hash + Objects.hashCode(article);
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
        if (!(obj instanceof ArticleInCollectedVolumeItem)) {
            return false;
        }
        final ArticleInCollectedVolumeItem other
                                               = (ArticleInCollectedVolumeItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(article, other.getPublication());
return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof ArticleInCollectedVolumeItem;
    }

//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format(", article = %s%s",
//                                            Objects.toString(article),
//                                            data));
//    }

}
