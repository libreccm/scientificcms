/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.scientificcms.publications.Proceedings;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "PROCEEDINGS_ITEMS", schema = DB_SCHEMA)
public class ProceedingsItem extends PublicationWithPublisherItem<Proceedings> {

    private static final long serialVersionUID = 1L;

//    @OneToOne(cascade = {CascadeType.DETACH, 
//                         CascadeType.MERGE, 
//                         CascadeType.PERSIST, 
//                         CascadeType.REFRESH
//    })
//    @JoinColumn(name = "PROCEEDINGS_ID")
//    private Proceedings article;
//
//    @Override
//    public Proceedings getPublication() {
//        return article;
//    }
//
//    @Override
//    protected void setPublication(final Proceedings article) {
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
        if (!(obj instanceof ProceedingsItem)) {
            return false;
        }
        final ProceedingsItem other
                                  = (ProceedingsItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(this.article, other.getPublication());
        return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof ProceedingsItem;
    }

//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format(", proceedings = %s%s",
//                                            Objects.toString(article),
//                                            data));
//    }

}
