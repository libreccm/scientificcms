/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.core.AbstractEntityRepository;
import org.librecms.assets.Person;

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

    @Override
    public void initNewEntity(final Publication entity) {
        final String uuid = UUID.randomUUID().toString();
        entity.setUuid(uuid);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public <T extends Publication> Optional<T> findByIdAndType(
        final long publicationId, final Class<T> type
    ) {
        try {
            return Optional.of(
                getEntityManager()
                    .createNamedQuery("Publication.findByIdAndType", type)
                    .setParameter("publicationId", publicationId)
                    .setParameter("type", type)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Optional<Publication> findByUuid(final String uuid) {

        try {
            return Optional.of(
                getEntityManager()
                    .createNamedQuery("Publication.findByUuid",
                                      Publication.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public <T extends Publication> Optional<T> findByUuidAndType(
        final String uuid, final Class<T> type
    ) {
        try {
            return Optional.of(
                getEntityManager()
                    .createNamedQuery("Publication.findByUuidAndType", type)
                    .setParameter("uuid", uuid)
                    .setParameter("type", type)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Publication> findByTitle(final String title) {

        return getEntityManager()
            .createNamedQuery("Publication.findByTitle", Publication.class)
            .setParameter("title", title)
            .getResultList();
    }
    
    public <T extends Publication> List<T> findByType(final Class<T> type) {
        return getEntityManager()
            .createNamedQuery("Publication.findByType", type)
            .setParameter("type", type)
            .getResultList();
    }

    public <T extends Publication> List<T> findByTitleAndType(
        final String title, final Class<T> type
    ) {
        return getEntityManager()
            .createNamedQuery("Publication.findByTitleAndType", type)
            .setParameter("title", title)
            .setParameter("type", type)
            .getResultList();
    }

    public List<Publication> findByAuthor(final Person author) {
        return getEntityManager()
            .createNamedQuery("Publication.findByAuthor", Publication.class)
            .setParameter("author", author)
            .getResultList();
    }

    public <T extends Publication> List<T> findByAuthorAndType(
        final Person author, final Class<T> type
    ) {
        return getEntityManager()
            .createNamedQuery("Publication.findByAuthorAndType", type)
            .setParameter("author", author)
            .setParameter("type", type)
            .getResultList();
    }

}
