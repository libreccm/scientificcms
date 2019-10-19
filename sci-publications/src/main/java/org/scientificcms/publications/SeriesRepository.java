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

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class SeriesRepository
    extends AbstractEntityRepository<Long, Series> {

    @Override
    public Class<Series> getEntityClass() {
        return Series.class;
    }

    @Override
    public String getIdAttributeName() {
        return "seriesId";
    }

    @Override
    public Long getIdOfEntity(final Series entity) {
        return entity.getSeriesId();
    }

    @Override
    public boolean isNew(final Series entity) {
        return entity.getSeriesId() == 0;
    }

    @Override
    public void initNewEntity(final Series entity) {
        entity.setUuid(UUID.randomUUID().toString());
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Optional<Series> findByUuid(final String uuid) {
        try {
            return Optional.of(
                getEntityManager()
                    .createNamedQuery("Series.findByUuid", Series.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<Series> findByTitle(final String title) {
        return getEntityManager()
            .createNamedQuery("Series.findByTitle", Series.class)
            .setParameter("title", title)
            .getResultList();
    }

}
