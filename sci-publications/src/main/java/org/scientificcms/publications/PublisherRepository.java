/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.core.AbstractEntityRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublisherRepository
    extends AbstractEntityRepository<Long, Publisher> {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<Publisher> getEntityClass() {
        return Publisher.class;
    }

    @Override
    public String getIdAttributeName() {
        return "publisherId";
    }

    @Override
    public Long getIdOfEntity(final Publisher entity) {
        return entity.getPublisherId();
    }

    @Override
    public boolean isNew(final Publisher entity) {
        return entity.getPublisherId() == 0;
    }

    @Override
    protected void initNewEntity(final Publisher entity) {
        entity.setUuid(UUID.randomUUID().toString());
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Optional<Publisher> findByUuid(final String uuid) {
        try {
            return Optional.of(
                getEntityManager()
                    .createNamedQuery("Publisher.findByUuid", Publisher.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public List<Publisher> findByName(final String name) {
        return getEntityManager()
            .createNamedQuery("Publisher.findByName", Publisher.class)
            .setParameter("name", name)
            .getResultList();
    }

}
