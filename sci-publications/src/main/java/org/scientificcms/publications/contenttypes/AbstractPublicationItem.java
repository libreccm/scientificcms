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
    public boolean canEqual(final Object obj) {

        return obj instanceof AbstractPublicationItem;
    }

}
