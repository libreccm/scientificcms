/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.librecms.assets.Person;
import org.librecms.assets.PersonRepository;
import org.scientificcms.publications.Authorship;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.PublicationManager;
import org.scientificcms.publications.PublicationRepository;
import org.scientificcms.publications.VolumeInSeries;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
class SciPublicationsController implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "name";

    public static final String TITLE = "title";

    public static final String YEAR_OF_PUBLICATION = "yearOfPublication";

    public static final String SHORT_DESCRIPTION = "shortDescription";

    public static final String ABSTRACT = "abstract";

    public static final String MISC = "misc";

    public static final String YEAR_FIRST_PUBLISHED = "yearFirstPublished";

    public static final String LANGUAGE_OF_PUBLICATION = "languageOfPublication";

    public static final String LAUNCH_DATE = "launchDate";

    public static final String AUTHORSHIP_ID = "authorshipId";

    public static final String AUTHOR_NAME = "authorName";

    public static final String AUTHORSHIP_IS_EDITOR = "isEditor";

    public static final String AUTHORSHIP_ORDER = "order";

    public static final String VOLUME_IN_SERIES = "volumeInSeries";

    public static final String VOLUME_IN_SERIES_ID = "volumeInSeriesId";

    public static final String VOLUME_IN_SERIES_TITLE = "volumeInSeriesTitle";
    
    public static final String VOLUME_IN_SERIES_VOLUME = "volumeInSeriesVolume";

    @Inject
    private PersonRepository personRepository;

    @Inject
    private PublicationManager publicationManager;

    @Inject
    private PublicationRepository publicationRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public void savePublication(final long publicationId,
                                final Locale selectedLocale,
                                final Map<String, Object> data) {

        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        final Integer yearOfPublication = (Integer) data.get(
            YEAR_OF_PUBLICATION
        );
        final Integer yearFirstPublished = (Integer) data.get(
            YEAR_FIRST_PUBLISHED
        );
        final Locale languageOfPublication = (Locale) data.get(
            LANGUAGE_OF_PUBLICATION
        );
        final String publicationAbstract = (String) data.get(ABSTRACT);
        final String shortDescription = (String) data.get(SHORT_DESCRIPTION);
        final String misc = (String) data.get(MISC);

        publication.setYearOfPublication(yearOfPublication);
        publication.setYearFirstPublished(yearFirstPublished);
        publication.setLanguageOfPublication(languageOfPublication);
        publication.getShortDescription().addValue(selectedLocale,
                                                   shortDescription);
        publication.getPublicationAbstract().addValue(selectedLocale,
                                                      publicationAbstract);
        publication.getMisc().addValue(selectedLocale, misc);

        publicationRepository.save(publication);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<Map<String, Object>> getAuthors(final long publicationId) {

        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        return publication
            .getAuthorships()
            .stream()
            .map(this::buildAuthorshipEntry)
            .collect(Collectors.toList());
    }

    private Map<String, Object> buildAuthorshipEntry(
        final Authorship authorship
    ) {
        Objects.requireNonNull(authorship);

        final Map<String, Object> result = new HashMap<>();
        result.put(AUTHORSHIP_ID, authorship.getAuthorshipId());
        result.put(
            AUTHOR_NAME,
            String.format(
                "%s, %s",
                authorship.getAuthor().getPersonName().getSurname(),
                authorship.getAuthor().getPersonName().getGivenName()
            )
        );
        result.put(AUTHORSHIP_IS_EDITOR, authorship.isEditor());
        result.put(AUTHORSHIP_ORDER, authorship.getAuthorOrder());

        return result;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Optional<Authorship> findAuthorship(
        final long publicationId, final Object key
    ) {
        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        final long authorshipId = (long) key;

        return publication
            .getAuthorships()
            .stream()
            .filter(authorship -> authorship.getAuthorshipId() == authorshipId)
            .findAny();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void addAuthor(
        final long publicationId, final long authorId, final boolean isEditor
    ) {

        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        final Person author = personRepository
            .findById(authorId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Person with ID %d found.",
                                  authorId)
                )
            );

        publicationManager.addAuthor(author, publication, isEditor);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void updateAuthorship(
        final long publicationId, final long authorId, final boolean isEditor
    ) {
        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        final Person author = personRepository
            .findById(authorId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Person with ID %d found.",
                                  authorId)
                )
            );

        final Optional<Authorship> authorship = publication
            .getAuthorships()
            .stream()
            .filter(current -> filterAuthorship(current, publication, author))
            .findAny();

        if (authorship.isPresent()) {

            authorship.get().setEditor(isEditor);
            publicationRepository.save(publication);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void removeAuthor(
        final long publicationId, final long authorshipId
    ) {
        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        final Optional<Authorship> authorship = findAuthorship(publicationId,
                                                               authorshipId);

        if (authorship.isPresent()) {
            publicationManager.removeAuthor(
                authorship.get().getAuthor(),
                publication
            );
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean hasAuthor(final long publicationId, final long authorId) {

        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        final Person author = personRepository
            .findById(authorId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Person with ID %d found.",
                                  authorId)
                )
            );

        return publication
            .getAuthorships()
            .stream()
            .anyMatch(
                current -> filterAuthorship(current, publication, author)
            );
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void swapWithPrevAuthorship(
        final long publicationId, final long authorshipId
    ) {
        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        final List<Authorship> authorships = publication.getAuthorships();
        Authorship authorship = null;
        int index = -1;
        for (int i = 0; i < authorships.size(); i++) {

            if (authorships.get(i).getAuthorshipId() == authorshipId) {
                authorship = authorships.get(i);
                index = i;
                break;
            }
        }

        if (index > 0 && authorship != null) {
            final long order = authorship.getAuthorOrder();
            final Authorship prevAuthorship = authorships.get(index - 1);
            final long prevOrder = prevAuthorship.getAuthorOrder();

            authorship.setAuthorOrder(prevOrder);
            prevAuthorship.setAuthorOrder(order);

            publicationRepository.save(publication);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void swapWithNextAuthorship(
        final long publicationId, final long authorshipId
    ) {
        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        final List<Authorship> authorships = publication.getAuthorships();
        Authorship authorship = null;
        int index = -1;
        for (int i = 0; i < authorships.size(); i++) {

            if (authorships.get(i).getAuthorshipId() == authorshipId) {
                authorship = authorships.get(i);
                index = i;
                break;
            }
        }

        if (index < authorships.size() && authorship != null) {
            final long order = authorship.getAuthorOrder();
            final Authorship nextAuthorship = authorships.get(index + 1);
            final long nextOrder = nextAuthorship.getAuthorOrder();

            authorship.setAuthorOrder(nextOrder);
            nextAuthorship.setAuthorOrder(order);

            publicationRepository.save(publication);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<Map<String, Object>> getVolumesInSeries(
        final long publicationId
    ) {
        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        return publication
            .getSeries()
            .stream()
            .map(this::buildVolumeInSeriesEntry)
            .collect(Collectors.toList());
    }

    private Map<String, Object> buildVolumeInSeriesEntry(
        final VolumeInSeries volumeInSeries
    ) {
        Objects.requireNonNull(volumeInSeries);

        final Map<String, Object> result = new HashMap<>();
        result.put(VOLUME_IN_SERIES_ID, volumeInSeries.getVolumeId());
        result.put(
            VOLUME_IN_SERIES_TITLE, volumeInSeries.getSeries().getTitle()
        );
        result.put(VOLUME_IN_SERIES_VOLUME, volumeInSeries.getVolumeOfSeries());

        return result;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Optional<VolumeInSeries> findVolumeInSeries(
        final long publicationId, final Object key
    ) {
        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        final long volumeId = (long) key;

        return publication
            .getSeries()
            .stream()
            .filter(series -> series.getVolumeId() == volumeId)
            .findAny();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void removeSeries(final long publicationId,
                             final long volumeInSeriesId) {

        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publication with ID %d found.", publicationId
                    )
                )
            );

        final Optional<VolumeInSeries> volumeInSeries = findVolumeInSeries(
            publicationId, volumeInSeriesId);

        if (volumeInSeries.isPresent()) {
            publicationManager.removeSeries(
                volumeInSeries.get().getSeries(),
                publication
            );
        }
    }

    private boolean filterAuthorship(final Authorship authorship,
                                     final Publication publication,
                                     final Person author) {

        return authorship.getPublication().equals(publication)
                   && authorship.getAuthor().equals(author);
    }

}
