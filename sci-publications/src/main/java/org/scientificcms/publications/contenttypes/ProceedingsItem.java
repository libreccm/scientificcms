/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.scientificcms.publications.assets.ProceedingsAsset;

import java.util.Objects;

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
@Table(name = "PROCEEDINGS_ITEMS", schema = DB_SCHEMA)
public class ProceedingsItem {
//    extends Publication {

//    private static final long serialVersionUID = 1L;
//
//    @OneToOne
//    @JoinColumn(name = "DATA_ID")
//    private ProceedingsAsset publicationData;
//
//    public ProceedingsAsset getPublicationData() {
//        return publicationData;
//    }
//
//    protected void setPublicationData(final ProceedingsAsset publicationData) {
//        this.publicationData = publicationData;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = super.hashCode();
//        hash = 43 * hash + Objects.hashCode(publicationData);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(final Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (!super.equals(obj)) {
//            return false;
//        }
//        if (!(obj instanceof ProceedingsItem)) {
//            return false;
//        }
//        final ProceedingsItem other = (ProceedingsItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(publicationData, other.getPublicationData());
//    }
//
//    @Override
//    public boolean canEqual(final Object obj) {
//
//        return obj instanceof ProceedingsItem;
//    }
//
//    @Override
//    public String toString(final String data) {
//
//        return super.toString(String.format("data = %s%s",
//                                            Objects.toString(publicationData),
//                                            data));
//    }

}
