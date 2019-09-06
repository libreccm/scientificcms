/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.hibernate.envers.Audited;
import org.librecms.contentsection.ContentItem;
import org.scientificcms.publications.Publication;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 * @param <T>
 */
@Entity
@Table(name = "PUBLICATION_ITEMS", schema = DB_SCHEMA)
@Audited
public abstract class AbstractPublicationItem<T extends Publication>
    extends ContentItem {

    private static final long serialVersionUID = 1L;

    public abstract T getPublication();

    protected abstract void setPublication(T publication);

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 29 * hash;
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
        if (!(obj instanceof AbstractPublicationItem)) {
            return false;
        }
        final AbstractPublicationItem<?> other
                                             = (AbstractPublicationItem<?>) obj;
        return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {

        return obj instanceof AbstractPublicationItem;
    }

}
