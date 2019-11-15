/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.scientificcms.publications.PublicationWithPublisher;
import org.scientificcms.publications.PublicationWithPublisherRepository;
import org.scientificcms.publications.Publisher;
import org.scientificcms.publications.PublisherManager;
import org.scientificcms.publications.PublisherRepository;

import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class SciPublicationsWithPublisherController {

    private static final long serialVersionUID = 1L;

    public static final String ISBN10 = "isbn10";

    public static final String ISBN13 = "isbn13";

    public static final String VOLUME = "volume";

    public static final String NUMBER_OF_VOLUMES = "numberOfVolumes";

    public static final String NUMBER_OF_PAGES = "numberOfPages";

    public static final String EDITION = "edition";

    @Inject
    private SciPublicationsController publicationsController;

    @Inject
    private PublicationWithPublisherRepository publicationRepository;

    @Inject
    private PublisherManager publisherManager;

    @Inject
    private PublisherRepository publisherRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public void savePublication(final long publicationId,
                                final Locale selectedLocale,
                                final Map<String, Object> data) {
        publicationsController.savePublication(publicationId, selectedLocale,
                                               data);

        final PublicationWithPublisher publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No PublicationWithPublisher with ID %d found.",
                        publicationId
                    )
                )
            );

        final String isbn10 = (String) data.get(ISBN10);
        final String isbn13 = (String) data.get(ISBN13);
        final Integer volume = (Integer) data.get(VOLUME);
        final Integer numberOfVolumes = (Integer) data.get(NUMBER_OF_VOLUMES);
        final Integer numberOfPages = (Integer) data.get(NUMBER_OF_PAGES);
        final String edition = (String) data.get(EDITION);

        publication.setIsbn10(isbn10);
        publication.setIsbn13(isbn13);
        publication.setVolume(volume);
        publication.setNumberOfVolumes(numberOfVolumes);
        publication.setNumberOfPages(numberOfPages);
        publication.getEdition().addValue(selectedLocale, edition);

        publicationRepository.save(publication);
    }

    public void setPublisher(final long publicationId, final long publisherId) {

        final PublicationWithPublisher publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No PublicationWithPublisher with ID %d found.",
                        publicationId
                    )
                )
            );

        final Publisher publisher = publisherRepository
            .findById(publisherId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publisher with ID %d found.", publisherId)
                )
            );

        publisherManager.addPublicationToPublisher(publication, publisher);

    }

    public void unsetPublisher(
        final long publicationId, final long publisherId
    ) {
        final PublicationWithPublisher publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No PublicationWithPublisher with ID %d found.",
                        publicationId
                    )
                )
            );

        final Publisher publisher = publisherRepository
            .findById(publisherId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Publisher with ID %d found.", publisherId)
                )
            );

        publisherManager.removePublicationFromPublisher(publication, publisher);
    }

}
