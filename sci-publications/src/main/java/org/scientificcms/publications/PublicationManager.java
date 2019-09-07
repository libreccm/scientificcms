/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.security.AuthorizationRequired;
import org.libreccm.security.RequiresPrivilege;
import org.librecms.assets.Person;
import org.librecms.contentsection.privileges.ItemPrivileges;

import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class PublicationManager {

    @Inject
    private PublicationRepository publicationRepository;
    
    @Inject
    private EntityManager entityManager;

    public void addAuthor(final Person person,
                          final Publication toPublication) {

        Objects.requireNonNull(person);
        Objects.requireNonNull(toPublication);

        addAuthor(person,
                  toPublication,
                  false,
                  toPublication.getAuthorships().size());
    }

    public void addAuthor(final Person person,
                          final Publication toPublication,
                          final boolean asEditor) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(toPublication);

        addAuthor(person,
                  toPublication,
                  asEditor,
                  toPublication.getAuthorships().size());
    }

    /**
     * Adds an person as author to a publication.
     *
     * @param person
     * @param toPublication
     * @param asEditor
     * @param atPosition
     */
    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void addAuthor(final Person person,
                          final Publication toPublication,
                          final boolean asEditor,
                          final long atPosition) {

        Objects.requireNonNull(person);
        Objects.requireNonNull(toPublication);
        if (atPosition < 0) {
            throw new IllegalArgumentException("position can't less than 0");
        }

        // First check if person is already an author.
        final boolean alreadyAdded = toPublication
            .getAuthorships()
            .stream()
            .map(Authorship::getAuthor)
            .anyMatch(author -> author.equals(person));
        if (alreadyAdded) {
            throw new IllegalArgumentException(
                String.format(
                    "Person %s is already an author of "
                        + "publication %s.",
                    Objects.toString(person),
                    Objects.toString(toPublication)
                )
            );
        }

        // Create authorship relation
        final long numberOfAuthors = toPublication.getAuthorships().size();
        final Authorship authorship = new Authorship();
        authorship.setAuthor(person);
        authorship.setPublication(toPublication);
        authorship.setEditor(asEditor);
        if (atPosition >= numberOfAuthors) {
            authorship.setAuthorOrder(numberOfAuthors);
        } else {
            authorship.setAuthorOrder(atPosition);
            toPublication
                .getAuthorships()
                .stream()
                .filter(obj -> obj.getAuthorOrder() >= atPosition)
                .forEach(obj -> obj.setAuthorOrder(obj.getAuthorOrder() + 1));

        }
        toPublication.addAuthorship(authorship);
        publicationRepository.save(toPublication);
    }

    public void removeAuthor(final Person author, 
                             final Publication fromPublication) {
        
        final Optional<Authorship> result = fromPublication
        .getAuthorships()
        .stream()
        .filter(authorship -> authorship.getAuthor().equals(author))
        .findAny();
        
        if (!result.isPresent()) {
            return ;
        }
        
        final Authorship remove = result.get();
        fromPublication.removeAuthorship(remove);
        
        for(int i = 0; i < fromPublication.getAuthorships().size(); i++) {
            fromPublication.getAuthorships().get(i).setAuthorOrder(i);
        }
        
        entityManager.remove(remove);
        publicationRepository.save(fromPublication);
    }
}
