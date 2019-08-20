/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.librecms.contentsection.Asset;
import org.scientificcms.publications.BasicPublicationProperties;
import org.scientificcms.publications.PublicationWithPublisherProperties;

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
public class CollectedVolume extends Asset {

    private static final long serialVersionUID = 1L;

    private BasicPublicationProperties basicProperties;

    private PublicationWithPublisherProperties withPublisherProperties;

    public CollectedVolume() {

        super();

        basicProperties = new BasicPublicationProperties();
        withPublisherProperties = new PublicationWithPublisherProperties();
    }

    public BasicPublicationProperties getBasicProperties() {
        return basicProperties;
    }

    protected void setBasicProperties(
        final BasicPublicationProperties basicProperties) {
        this.basicProperties = basicProperties;
    }

    public PublicationWithPublisherProperties getWithPublisherProperties() {
        return withPublisherProperties;
    }

    protected void setWithPublisherProperties(
        final PublicationWithPublisherProperties withPublisherProperties) {
        this.withPublisherProperties = withPublisherProperties;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 19 * hash + Objects.hashCode(basicProperties);
        hash = 19 * hash + Objects.hashCode(withPublisherProperties);
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
        if (!(obj instanceof CollectedVolume)) {
            return false;
        }
        final CollectedVolume other = (CollectedVolume) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(basicProperties, other.getBasicProperties())) {
            return false;
        }
        return Objects.equals(withPublisherProperties,
                              other.getWithPublisherProperties());
    }

    @Override
    public boolean canEqual(final Object obj) {

        return obj instanceof CollectedVolume;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format(
            "basicProperties = %s, "
                + "withPublisherProperties = %s%s",
            Objects.toString(basicProperties),
            Objects.toString(
                withPublisherProperties),
            data));
    }

}
