/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.rs;

import com.arsdigita.cms.contenttypes.ui.PublicationSeriesTable;

import org.libreccm.l10n.GlobalizationHelper;
import org.librecms.assets.Person;
import org.scientificcms.publications.Authorship;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.PublicationRepository;
import org.scientificcms.publications.PublicationWithPublisher;
import org.scientificcms.publications.Publisher;

import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
@Path("/publications")
public class Publications {

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private GlobalizationHelper globalizationHelper;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public String findPublications(
        @QueryParam("query") final String query,
        @QueryParam("type") final String type
    ) {

        final List<Publication> publications;
        if ((query == null || query.trim().isEmpty())
                && (type == null || type.trim().isEmpty())) {
            publications = publicationRepository.findAll();
        } else if ((query != null && !query.trim().isEmpty())
                       && (type == null || type.trim().isEmpty())) {
            publications = publicationRepository.findByTitle(query);
        } else if ((query == null || query.trim().isEmpty())
                       && (type != null && !type.trim().isEmpty())) {
            publications = publicationRepository.findByType(
                toPublicationClass(type)
            );
        } else {
            publications = publicationRepository.findByTitleAndType(
                query, toPublicationClass(type)
            );
        }
        
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        publications
            .stream()
            .map(this::publicationToJson)
            .forEach(arrayBuilder::add);
        
        final StringWriter writer = new StringWriter();
        final JsonWriter jsonWriter = Json.createWriter(writer);
        jsonWriter.writeArray(arrayBuilder.build());
        
        return writer.toString();
    }

    private Class<Publication> toPublicationClass(final String type) {

        final Class<?> clazz;
        try {
            clazz = Class.forName(type);
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(String.format(
                "Type '%s' is not a valid class.",
                type));
        }

        if (Publication.class.isAssignableFrom(clazz)) {
            @SuppressWarnings("unchecked")
            final Class<Publication> typeClass
                                               = (Class<Publication>) clazz;
            return typeClass;
        } else {
            throw new IllegalArgumentException(String.format(
                "Type '%s is not a subclass of '%s'.",
                type,
                Publication.class.getName()));
        }
    }

    private JsonObject publicationToJson(final Publication publication) {

        final JsonObjectBuilder objectBuilder = Json
            .createObjectBuilder()
            .add("publicationId", publication.getPublicationId())
            .add(
                "authors",
                publication
                    .getAuthorships()
                    .stream()
                    .sorted()
                    .map(Authorship::getAuthor)
                    .map(Person::getPersonName)
                    .map(
                        name -> String.format(
                            "%s, %s", name.getSurname(), name.getGivenName()
                        )
                    )
                    .collect(Collectors.joining("; "))
            )
            .add(
                "title",
                globalizationHelper.getValueFromLocalizedString(
                    publication.getTitle()
                )
            )
            .add("year", publication.getYearOfPublication());

        if (publication instanceof PublicationWithPublisher) {

            final Publisher publisher = ((PublicationWithPublisher) publication)
                .getPublisher();
            if (publisher != null) {
                objectBuilder.add(
                    "publisher",
                    String.format(
                        "%s: %s", publisher.getPlace(), publisher.getName()
                    )
                );
            }

        }

        return objectBuilder.build();

    }

}
