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
    private EntityManager entityManager;

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private SeriesRepository seriesRepository;

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void addAuthor(final Person person,
                          final Publication toPublication) {

        Objects.requireNonNull(person);
        Objects.requireNonNull(toPublication);

        addAuthor(person,
                  toPublication,
                  false,
                  toPublication.getAuthorships().size());
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
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
                          final int atPosition) {

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

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void removeAuthor(final Person author,
                             final Publication fromPublication) {

        final Optional<Authorship> result = fromPublication
            .getAuthorships()
            .stream()
            .filter(authorship -> authorship.getAuthor().equals(author))
            .findAny();

        if (!result.isPresent()) {
            return;
        }

        final Authorship remove = result.get();
        fromPublication.removeAuthorship(remove);

        for (int i = 0; i < fromPublication.getAuthorships().size(); i++) {
            fromPublication.getAuthorships().get(i).setAuthorOrder(i);
        }

        entityManager.remove(remove);
        publicationRepository.save(fromPublication);
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void moveAuthorToPosition(
        final Publication ofPublication, 
        final Person author,
        final int toPosition
    ) {
        Objects.requireNonNull(ofPublication);
        Objects.requireNonNull(author);
        if (toPosition < 0) {
            throw new IllegalArgumentException(
                "Can't move author to a negative position."
            );
        }

        final Optional<Authorship> result = ofPublication
            .getAuthorships()
            .stream()
            .filter(authorship -> authorship.getAuthor().equals(author))
            .findAny();

        if (!result.isPresent()) {
            throw new IllegalArgumentException(
                String.format(
                    "Person %s is not an author of the publication %s.",
                    Objects.toString(author),
                    Objects.toString(ofPublication)
                )
            );
        }

        moveAuthorshipToPosition(ofPublication, result.get(), toPosition);
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void moveAuthorshipToPosition(final Publication ofPublication,
                                         final Authorship moving,
                                         final int toPosition) {

        Objects.requireNonNull(ofPublication);
        Objects.requireNonNull(moving);
        if (toPosition < 0) {
            throw new IllegalArgumentException(
                "Can't move author to a negative position."
            );
        }

        if (toPosition < moving.getAuthorOrder()) {
            ofPublication
                .getAuthorships()
                .stream()
                .filter(
                    authorship -> authorship.getAuthorOrder() >= toPosition
                                      && authorship.getAuthorOrder()
                                             < moving.getAuthorOrder()
                )
                .forEach(
                    authorship -> authorship.setAuthorOrder(
                        authorship.getAuthorOrder() + 1
                    )
                );
            moving.setAuthorOrder(toPosition);

        } else if (toPosition > moving.getAuthorOrder()) {
            ofPublication
                .getAuthorships()
                .stream()
                .filter(
                    authorship -> authorship.getAuthorOrder() > moving
                    .getAuthorOrder()
                                      && authorship.getAuthorOrder()
                                             <= toPosition
                )
                .forEach(
                    authorship -> authorship.setAuthorOrder(
                        authorship.getAuthorOrder() - 1
                    )
                );
        } else {
            // Nothing to do
            return;
        }

        publicationRepository.save(ofPublication);
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void swapWithPrevious(final Publication publication,
                                 final Authorship authorship) {

        Objects.requireNonNull(publication);
        Objects.requireNonNull(authorship);

        if (!publication.getAuthorships().contains(authorship)) {
            throw new IllegalArgumentException(
                String.format(
                    "Provided Authorship entity %s is not part of "
                        + "the provided Publication entity %s.",
                    Objects.toString(authorship),
                    Objects.toString(publication)
                )
            );
        }

        if (authorship.getAuthorOrder() == 0) {
            return;
        }

        final Authorship previous = publication
            .getAuthorships()
            .get((int) authorship.getAuthorOrder() - 1);
        final long previousOrder = previous.getAuthorOrder();
        final long movingOrder = authorship.getAuthorOrder();

        previous.setAuthorOrder(movingOrder);
        authorship.setAuthorOrder(previousOrder);

        publicationRepository.save(publication);
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void swapWithNext(final Publication publication,
                             final Authorship authorship) {

        Objects.requireNonNull(publication);
        Objects.requireNonNull(authorship);

        if (!publication.getAuthorships().contains(authorship)) {
            throw new IllegalArgumentException(
                String.format(
                    "Provided Authorship entity %s is not part of "
                        + "the provided Publication entity %s.",
                    Objects.toString(authorship),
                    Objects.toString(publication)
                )
            );
        }

        if (authorship.getAuthorOrder() > publication
            .getAuthorships().size() - 1) {
            return;
        }

        final Authorship next = publication
            .getAuthorships()
            .get((int) authorship.getAuthorOrder() + 1);
        final long nextOrder = next.getAuthorOrder();
        final long movingOrder = authorship.getAuthorOrder();

        next.setAuthorOrder(movingOrder);
        authorship.setAuthorOrder(nextOrder);

        publicationRepository.save(publication);
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void addSeries(
        final Series series,
        final Publication publication,
        final String volumeInSeries
    ) {
        Objects.requireNonNull(series);
        Objects.requireNonNull(publication);

        final boolean alreadyAdded = publication
            .getSeries()
            .stream()
            .map(VolumeInSeries::getSeries)
            .anyMatch(obj -> obj.equals(series));

        if (alreadyAdded) {
            throw new IllegalArgumentException(
                String.format(
                    "Publication %s is already a volume of series %s.",
                    Objects.toString(publication),
                    Objects.toString(series)
                )
            );
        }

        final VolumeInSeries volume = new VolumeInSeries();
        volume.setPublication(publication);
        volume.setSeries(series);
        volume.setVolumeOfSeries(volumeInSeries);
        publication.addSeries(volume);
        series.addVolume(volume);
        seriesRepository.save(series);
        publicationRepository.save(publication);
    }

    @AuthorizationRequired
    @RequiresPrivilege(ItemPrivileges.EDIT)
    @Transactional(Transactional.TxType.REQUIRED)
    public void removeSeries(
        final Series series, final Publication fromPublication
    ) {
        Objects.requireNonNull(series);
        Objects.requireNonNull(fromPublication);

        final Optional<VolumeInSeries> result = fromPublication
            .getSeries()
            .stream()
            .filter(volume -> volume.getSeries().equals(series))
            .findAny();

        if (!result.isPresent()) {
            return;
        }

        final VolumeInSeries remove = result.get();
        fromPublication.removeSeries(remove);

        entityManager.remove(remove);
        publicationRepository.save(fromPublication);
    }

}
