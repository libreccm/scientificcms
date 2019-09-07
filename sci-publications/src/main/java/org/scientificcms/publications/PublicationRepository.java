/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.core.AbstractEntityRepository;

import javax.enterprise.context.RequestScoped;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class PublicationRepository 
    extends AbstractEntityRepository<Long, Publication> {

    private static final long serialVersionUID = 1L;

    @Override
    public String getIdAttributeName() {
        return "publicationId";
    }

    @Override
    public Long getIdOfEntity(final Publication entity) {
        return entity.getPublicationId();
    }

    @Override
    public boolean isNew(final Publication entity) {
        return entity.getPublicationId() == 0;
    }

    @Override
    public Class<Publication> getEntityClass() {
        return Publication.class;
    }

}
