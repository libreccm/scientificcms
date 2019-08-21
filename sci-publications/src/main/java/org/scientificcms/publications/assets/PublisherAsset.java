/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.hibernate.envers.Audited;
import org.librecms.contentsection.Asset;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 * An asset for storing the informations about a publisher required to create
 * correct bibliographic references.
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "PUBLISHERS", schema = DB_SCHEMA)
@Audited
public class PublisherAsset extends Asset {

//    private static final long serialVersionUID = 1L;
//
//    /**
//     * Name of the publisher. The title of the asset is only for internal use,
//     * this property should be used for name of the publisher which is displayed
//     * on public pages.
//     */
//    @Column(name = "NAME", length = 2048, nullable = false)
//    private String name;
//
//    /**
//     * The place of the publisher.
//     */
//    @Column(name = "PLACE", length = 2048)
//    private String place;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(final String name) {
//        this.name = name;
//    }
//
//    public String getPlace() {
//        return place;
//    }
//
//    public void setPlace(final String place) {
//        this.place = place;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = super.hashCode();
//        hash = 29 * hash + Objects.hashCode(name);
//        hash = 29 * hash + Objects.hashCode(place);
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
//        if (!(obj instanceof PublisherAsset)) {
//            return false;
//        }
//        final PublisherAsset other = (PublisherAsset) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        if (!Objects.equals(name, other.getName())) {
//            return false;
//        }
//        return Objects.equals(place, other.getPlace());
//    }
//
//    @Override
//    public boolean canEqual(final Object obj) {
//        return obj instanceof PublisherAsset;
//    }
//
//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format(
//            ", name = \"%s\", "
//                + "place = \"%s\"%s",
//            name,
//            place,
//            data));
//    }

}
