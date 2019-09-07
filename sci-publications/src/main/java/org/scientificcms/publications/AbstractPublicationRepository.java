/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.core.AbstractEntityRepository;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 * @param <T>
 */
public abstract class AbstractPublicationRepository<T extends Publication>
    extends AbstractEntityRepository<Long, T> {

    private static final long serialVersionUID = 1L;

    @Override
    public String getIdAttributeName() {
        return "publicationId";
    }

    @Override
    public Long getIdOfEntity(final T entity) {
        return entity.getPublicationId();
    }

    @Override
    public boolean isNew(final T entity) {
        return entity.getPublicationId() == 0;
    }

}
