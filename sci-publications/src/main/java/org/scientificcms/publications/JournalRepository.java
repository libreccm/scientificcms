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
public class JournalRepository extends AbstractEntityRepository<Long, Journal> {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<Journal> getEntityClass() {
        return Journal.class;
    }

    @Override
    public String getIdAttributeName() {
        return "journalId";
    }

    @Override
    public Long getIdOfEntity(final Journal entity) {
        return entity.getJournalId();
    }

    @Override
    public boolean isNew(final Journal entity) {
        return entity.getJournalId() == 0;
    }

    @Override
    protected void initNewEntity(final Journal entity) {
        entity.setUuid(UUID.randomUUID().toString());
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Optional<Journal> findByUuid(final String uuid) {

        try {
            return Optional.of(
                getEntityManager()
                    .createNamedQuery("Journal.findByUuid", Journal.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<Journal> findByTitle(final String title) {
        return getEntityManager()
            .createNamedQuery("Journal.findByTitle", Journal.class)
            .setParameter("title", title)
            .getResultList();
    }

}
