/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.scientificcms.publications.PublicationWithPublisherProperties;

import java.util.Objects;

import javax.persistence.Embedded;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationWithPublisher extends PublicationItem {

    private static final long serialVersionUID = 1L;

    @Embedded
    private PublicationWithPublisherProperties withPublisherProperties;

    public PublicationWithPublisher() {

        this.withPublisherProperties = new PublicationWithPublisherProperties();
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
        hash = 79 * hash + Objects.hashCode(withPublisherProperties);
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
        if (!super.equals(this)) {
            return false;
        }
        if (!(obj instanceof PublicationWithPublisher)) {
            return false;
        }
        final PublicationWithPublisher other = (PublicationWithPublisher) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(withPublisherProperties,
                              other.getWithPublisherProperties());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof PublicationWithPublisher;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format("withPublisherProperties = %s%s",
                                            Objects.toString(
                                                withPublisherProperties),
                                            data));
    }

}
