/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.core.AbstractEntityRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class PublicationWithPublisherRepository
    extends AbstractEntityRepository<Long, PublicationWithPublisher>
    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PublicationRepository publicationRepository;

    @Override
    public Class<PublicationWithPublisher> getEntityClass() {
        return PublicationWithPublisher.class;
    }

    @Override
    public String getIdAttributeName() {
        return publicationRepository.getIdAttributeName();
    }

    @Override
    public Long getIdOfEntity(final PublicationWithPublisher entity) {
        return publicationRepository.getIdOfEntity(entity);
    }

    @Override
    public boolean isNew(final PublicationWithPublisher entity) {
        return publicationRepository.isNew(entity);
    }

    @Override
    public void initNewEntity(final PublicationWithPublisher entity) {
        publicationRepository.initNewEntity(entity);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<PublicationWithPublisher> findByPublisher(
        final Publisher publisher
    ) {
        return getEntityManager()
            .createNamedQuery("PublicationWithPublisher.findByPublisher",
                              PublicationWithPublisher.class)
            .setParameter("publisher", publisher)
            .getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public <T extends PublicationWithPublisher> List<T> findByPublisherAndType(
        final Publisher publisher, final Class<T> type
    ) {
        return getEntityManager()
            .createNamedQuery("PublicationWithPublisher.findByPublisherAndType",
                              type)
            .setParameter("publisher", publisher)
            .setParameter("type", type)
            .getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Optional<PublicationWithPublisher> findByIsbn10(final String isbn) {
        try {
            return Optional.of(
                getEntityManager()
                    .createNamedQuery("PublicationWithPublisher.findByISBN10",
                                      PublicationWithPublisher.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Optional<PublicationWithPublisher> findByIsbn13(final String isbn) {
        try {
            return Optional.of(
                getEntityManager()
                    .createNamedQuery("PublicationWithPublisher.findByISBN13",
                                      PublicationWithPublisher.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}
