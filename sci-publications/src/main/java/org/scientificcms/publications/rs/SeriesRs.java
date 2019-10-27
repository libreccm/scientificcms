/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.rs;

import org.libreccm.l10n.GlobalizationHelper;
import org.scientificcms.publications.Journal;
import org.scientificcms.publications.Series;
import org.scientificcms.publications.SeriesRepository;

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
@Path("/series")
public class SeriesRs {

    @Inject
    private SeriesRepository seriesRepository;

    @Inject
    private GlobalizationHelper globalizationHelper;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public String findJournals(@QueryParam("query") final String query) {

        final List<Series> series;
        if ((query == null) || query.trim().isEmpty()) {
            series = seriesRepository.findAll();
        } else {
            series = seriesRepository.findByTitle(query);
        }

        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        series
            .stream()
            .map(this::seriesToJson)
            .forEach(arrayBuilder::add);

        final StringWriter writer = new StringWriter();
        final JsonWriter jsonWriter = Json.createWriter(writer);
        jsonWriter.writeArray(arrayBuilder.build());

        return writer.toString();
    }

    private JsonObject seriesToJson(final Series series) {

        return Json
            .createObjectBuilder()
            .add("seriesId", series.getSeriesId())
            .add(
                "title",
                globalizationHelper.getValueFromLocalizedString(
                    series.getTitle()
                )
            )
            .build();

    }

}
