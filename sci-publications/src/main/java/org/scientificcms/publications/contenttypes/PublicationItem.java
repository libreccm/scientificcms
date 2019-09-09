/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.hibernate.envers.Audited;
import org.librecms.contentsection.ContentItem;
import org.scientificcms.publications.Publication;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 * Base Item for Publications.
 * 
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 * @param <T>
 */
@Entity
@Table(name = "PUBLICATION_ITEMS", schema = DB_SCHEMA)
@Audited
@NamedQueries({
    @NamedQuery(
        name = "PublicationItem.findForPublication",
        query = "SELECT i FROM PublicationItem i WHERE i.publication = :publication"
    )
})
public class PublicationItem<T extends Publication> extends ContentItem {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.DETACH,
                         CascadeType.MERGE,
                         CascadeType.PERSIST,
                         CascadeType.REFRESH},
              fetch = FetchType.LAZY,
              targetEntity = Publication.class
    )
    private T publication;

    public T getPublication() {
        return publication;
    }

    public void setPublication(final T publication) {
        this.publication = publication;
    }

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
        if (!(obj instanceof PublicationItem)) {
            return false;
        }
        final PublicationItem<?> other = (PublicationItem<?>) obj;
        return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {

        return obj instanceof PublicationItem;
    }

}
