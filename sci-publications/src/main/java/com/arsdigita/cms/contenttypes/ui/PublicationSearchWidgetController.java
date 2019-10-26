/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.libreccm.l10n.GlobalizationHelper;
import org.librecms.assets.Person;
import org.scientificcms.publications.Authorship;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.PublicationRepository;
import org.scientificcms.publications.PublicationWithPublisher;
import org.scientificcms.publications.Publisher;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class PublicationSearchWidgetController {

    protected static final String PUBLICATION_ID = "publicationId";

    protected static final String TITLE = "title";

    protected static final String YEAR = "year";

    protected static final String AUTHORS = "authors";
    
    protected static final String PUBLISHER = "publisher";
    
    protected static final String PLACE = "place";

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private GlobalizationHelper globalizationHelper;

    @Transactional(Transactional.TxType.REQUIRED)
    public Map<String, String> getData(final long publicationId) {

        final Publication publication = publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Publication with ID %d found.",
                                  publicationId)
                )
            );

        final Map<String, String> data = new HashMap<>();

        data.put(PUBLICATION_ID, Long.toString(publication.getPublicationId()));
        data.put(
            TITLE,
            globalizationHelper.getValueFromLocalizedString(
                publication.getTitle()
            )
        );
        data.put(YEAR, Integer.toString(publication.getYearOfPublication()));
        data.put(
            AUTHORS,
            publication
                .getAuthorships()
                .stream()
                .sorted()
                .map(Authorship::getAuthor)
                .map(Person::getPersonName)
                .map(name -> String.format("%s, %s",
                                           name.getSurname(),
                                           name.getGivenName()))
                .collect(Collectors.joining("; "))
        );

        if (publication instanceof PublicationWithPublisher) {
            final Publisher publisher = ((PublicationWithPublisher) publication)
                .getPublisher();
            data.put(PUBLISHER, publisher.getName());
            data.put(PLACE, publisher.getPlace());
        }

        return data;
    }

}
