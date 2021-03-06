/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.hibernate.envers.Audited;
import org.scientificcms.publications.PublicationWithPublisher;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 * @param <T>
 */
@Entity
@Table(name = "PUBLICATION_WITH_PUBLISHER_ITEMS", schema = DB_SCHEMA)
@Audited
public class PublicationWithPublisherItem<T extends PublicationWithPublisher>
    extends PublicationItem<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 23 * hash;
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
        if (!(obj instanceof PublicationWithPublisherItem)) {
            return false;
        }
        final PublicationWithPublisherItem<?> other
                                                          = (PublicationWithPublisherItem<?>) obj;
        return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof PublicationWithPublisherItem;
    }

}
