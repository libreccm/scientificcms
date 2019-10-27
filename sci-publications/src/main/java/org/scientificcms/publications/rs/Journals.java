/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.rs;

import org.scientificcms.publications.Journal;
import org.scientificcms.publications.JournalRepository;

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
@Path("/journals")
public class Journals {

    @Inject
    private JournalRepository journalRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public String findJournals(@QueryParam("query") final String query) {
        
        
        final List<Journal> journals;
        if ((query == null) || query.trim().isEmpty()) {
            journals = journalRepository.findAll();
        } else {
            journals = journalRepository.findByTitle(query);
        }
        
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        journals
            .stream()
            .map(this::journalToJson)
            .forEach(arrayBuilder::add);
        
        final StringWriter writer = new StringWriter();
        final JsonWriter jsonWriter = Json.createWriter(writer);
        jsonWriter.writeArray(arrayBuilder.build());
        
        return writer.toString();
    }
    
    private JsonObject journalToJson(final Journal journal) {
        
        return Json
            .createObjectBuilder()
            .add("journalId", journal.getJournalId())
            .add("title", journal.getTitle())
            .add("symbol", journal.getSymbol())
            .add("issn", journal.getIssn())
            .build();
        
    }

}
