/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.scientificcms.publications.PublicationWithPublisher;
import org.scientificcms.publications.assets.CollectedVolumeAsset;

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
@Table(name = "COLLECTED_VOLUME_ITEMS", schema = DB_SCHEMA)
public class CollectedVolumeItem extends AbstractPublicationWithPublisherItem<PublicationWithPublisher>{
//    extends Publication {

    private static final long serialVersionUID = 1L;
//
//    @OneToOne
//    @JoinColumn(name = "DATA_ID")
//    private CollectedVolumeAsset publicationData;
//
//    public CollectedVolumeAsset getPublicationData() {
//        return publicationData;
//    }
//
//    protected void setPublicationData(final CollectedVolumeAsset publicationData) {
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
//        if (!(obj instanceof CollectedVolumeItem)) {
//            return false;
//        }
//        final CollectedVolumeItem other = (CollectedVolumeItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(publicationData, other.getPublicationData());
//    }
//
//    @Override
//    public boolean canEqual(final Object obj) {
//
//        return obj instanceof CollectedVolumeItem;
//    }
//
//    @Override
//    public String toString(final String data) {
//
//        return super.toString(String.format("data = %s%s",
//                                            Objects.toString(publicationData),
//                                            data));
//    }

    @Override
    public PublicationWithPublisher getPublication() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setPublication(PublicationWithPublisher publication) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
