/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.security.AuthorizationRequired;
import org.libreccm.security.RequiresPrivilege;
import org.librecms.contentsection.privileges.ItemPrivileges;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class PublisherManager {

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private PublisherRepository publisherRepository;

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void addPublicationToPublisher(
        final PublicationWithPublisher publication,
        final Publisher publisher
    ) {
        Objects.requireNonNull(publication);
        Objects.requireNonNull(publisher);

        publication.setPublisher(publisher);
        publisher.addPublication(publication);

        publicationRepository.save(publication);
        publisherRepository.save(publisher);
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void removePublicationFromPublisher(
        final PublicationWithPublisher publication,
        final Publisher publisher
    ) {
        Objects.requireNonNull(publication);
        Objects.requireNonNull(publisher);

        if (!publisher.equals(publication.getPublisher())
                || !publisher.getPublications().contains(publication)) {
            throw new IllegalArgumentException(
                String.format(
                    "The provided publication %s is not an publication of the "
                        + "provided publisher %s.",
                    publication.getUuid(),
                    publisher.getUuid()
                )
            );
        }

        publication.setPublisher(null);
        publisher.removePublication(publication);

        publicationRepository.save(publication);
        publisherRepository.save(publisher);
    }

}
