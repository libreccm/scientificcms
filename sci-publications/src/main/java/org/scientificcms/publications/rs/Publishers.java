/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.rs;

import org.scientificcms.publications.Journal;
import org.scientificcms.publications.Publisher;
import org.scientificcms.publications.PublisherRepository;

import java.io.StringWriter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
@Path("/publishers")
public class Publishers {

    @Inject
    private PublisherRepository publisherRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public String findJournals(@QueryParam("query") final String query) {

        final List<Publisher> publisher;
        if ((query == null) || query.trim().isEmpty()) {
            publisher = publisherRepository.findAll();
        } else {
            publisher = publisherRepository.findByName(query);
        }

        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        publisher
            .stream()
            .map(this::publisherToJson)
            .forEach(arrayBuilder::add);

        final StringWriter writer = new StringWriter();
        final JsonWriter jsonWriter = Json.createWriter(writer);
        jsonWriter.writeArray(arrayBuilder.build());

        return writer.toString();
    }

    private JsonObject publisherToJson(final Publisher publisher) {

        return Json
            .createObjectBuilder()
            .add("publisherId", publisher.getPublisherId())
            .add("name", publisher.getName())
            .add("place", publisher.getPlace())
            .build();

    }

}
