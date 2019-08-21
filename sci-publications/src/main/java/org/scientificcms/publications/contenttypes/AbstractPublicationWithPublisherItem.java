/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.scientificcms.publications.PublicationWithPublisher;


/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 * @param <T>
 */
public abstract class AbstractPublicationWithPublisherItem<T extends PublicationWithPublisher>
    extends AbstractPublicationItem<T> {
//extends PublicationItem {

    private static final long serialVersionUID = 1L;
//
//    @Embedded
//    private AbstractPublicationWithPublisherItem withPublisherProperties;
//
//    public AbstractPublicationWithPublisherItem() {
//
//        this.withPublisherProperties = new AbstractPublicationWithPublisherItem();
//    }
//
//    public AbstractPublicationWithPublisherItem getWithPublisherProperties() {
//        return withPublisherProperties;
//    }
//
//    protected void setWithPublisherProperties(
//        final AbstractPublicationWithPublisherItem withPublisherProperties) {
//        this.withPublisherProperties = withPublisherProperties;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = super.hashCode();
//        hash = 79 * hash + Objects.hashCode(withPublisherProperties);
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
//        if (!super.equals(this)) {
//            return false;
//        }
//        if (!(obj instanceof AbstractPublicationWithPublisherItem)) {
//            return false;
//        }
//        final AbstractPublicationWithPublisherItem other = (AbstractPublicationWithPublisherItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(withPublisherProperties,
//                              other.getWithPublisherProperties());
//    }
//
    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof AbstractPublicationWithPublisherItem;
    }
//
//    @Override
//    public String toString(final String data) {
//
//        return super.toString(String.format("withPublisherProperties = %s%s",
//                                            Objects.toString(
//                                                withPublisherProperties),
//                                            data));
//    }

}
