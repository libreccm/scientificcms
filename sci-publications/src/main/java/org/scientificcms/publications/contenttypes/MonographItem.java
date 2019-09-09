/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.scientificcms.publications.Monograph;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "MONOGRAPH_ITEMS", schema = DB_SCHEMA)
public class MonographItem extends PublicationWithPublisherItem<Monograph> {

    private static final long serialVersionUID = 1L;

//    @OneToOne(cascade = {CascadeType.DETACH, 
//                         CascadeType.MERGE, 
//                         CascadeType.PERSIST, 
//                         CascadeType.REFRESH
//    })
//    @JoinColumn(name = "MONOGRAPH_ID")
//    private Monograph monograph;
//
//    @Override
//    public Monograph getPublication() {
//        return monograph;
//    }
//
//    @Override
//    protected void setPublication(final Monograph monograph) {
//        this.monograph = monograph;
//    }
    @Override
    public int hashCode() {
        int hash = super.hashCode();
//        hash = 29 * hash + Objects.hashCode(monograph);
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
        if (!(obj instanceof MonographItem)) {
            return false;
        }
        final MonographItem other = (MonographItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(monograph, other.getPublication());
        return other.canEqual(this);
    }

    @Override
    public boolean canEqual(Object obj) {
        return obj instanceof MonographItem;
    }

//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format(", publication = %s%s",
//                                            Objects.toString(monograph),
//                                            data));
//    }

}
