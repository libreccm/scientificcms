/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.librecms.contentsection.ContentItemRepository;
import org.scientificcms.publications.Publication;

import java.util.Optional;

import javax.persistence.NoResultException;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationItemRepository extends ContentItemRepository {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public <T extends Publication> Optional<PublicationItem<T>> findForPublication(
        final T publication, final Class<T> publicationType
    ) {
        try {
            return Optional.of(
                (PublicationItem<T>) getEntityManager()
                    .createNamedQuery("PublicationItem.findForPublication",
                                      PublicationItem.class)
                    .setParameter("publication", publication)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}
