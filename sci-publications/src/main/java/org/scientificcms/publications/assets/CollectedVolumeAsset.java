/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.librecms.contentsection.Asset;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.PublicationWithPublisher;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "COLLECTED_VOLUMES", schema = DB_SCHEMA)
public class CollectedVolumeAsset extends Asset {

//    private static final long serialVersionUID = 1L;
//
//    private Publication basicProperties;
//
//    private PublicationWithPublisher withPublisherProperties;
//
//    public CollectedVolumeAsset() {
//
//        super();
//
//        basicProperties = new Publication();
//        withPublisherProperties = new PublicationWithPublisher();
//    }
//
//    public Publication getBasicProperties() {
//        return basicProperties;
//    }
//
//    protected void setBasicProperties(
//        final Publication basicProperties) {
//        this.basicProperties = basicProperties;
//    }
//
//    public PublicationWithPublisher getWithPublisherProperties() {
//        return withPublisherProperties;
//    }
//
//    protected void setWithPublisherProperties(
//        final PublicationWithPublisher withPublisherProperties) {
//        this.withPublisherProperties = withPublisherProperties;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = super.hashCode();
//        hash = 19 * hash + Objects.hashCode(basicProperties);
//        hash = 19 * hash + Objects.hashCode(withPublisherProperties);
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
//        if (!(obj instanceof CollectedVolumeAsset)) {
//            return false;
//        }
//        final CollectedVolumeAsset other = (CollectedVolumeAsset) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        if (!Objects.equals(basicProperties, other.getBasicProperties())) {
//            return false;
//        }
//        return Objects.equals(withPublisherProperties,
//                              other.getWithPublisherProperties());
//    }
//
//    @Override
//    public boolean canEqual(final Object obj) {
//
//        return obj instanceof CollectedVolumeAsset;
//    }
//
//    @Override
//    public String toString(final String data) {
//
//        return super.toString(String.format(
//            "basicProperties = %s, "
//                + "withPublisherProperties = %s%s",
//            Objects.toString(basicProperties),
//            Objects.toString(
//                withPublisherProperties),
//            data));
//    }
//
}
