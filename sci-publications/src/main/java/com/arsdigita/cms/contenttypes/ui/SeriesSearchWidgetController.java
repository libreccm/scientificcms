/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.libreccm.l10n.GlobalizationHelper;
import org.scientificcms.publications.Series;
import org.scientificcms.publications.SeriesRepository;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class SeriesSearchWidgetController {

    protected static final String SERIES_ID = "seriesId";

    protected static final String TITLE = "title";

    @Inject
    private SeriesRepository seriesRepository;

    @Inject
    private GlobalizationHelper globalizationHelper;

    @Transactional(Transactional.TxType.REQUIRED)
    public Map<String, String> getData(final long seriesId) {

        final Series series = seriesRepository
            .findById(seriesId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Series with ID %d found.", seriesId)
                )
            );

        final Map<String, String> data = new HashMap<>();

        data.put(SERIES_ID, Long.toString(series.getSeriesId()));
        data.put(
            TITLE, 
            globalizationHelper.getValueFromLocalizedString(series.getTitle())
        );

        return data;
    }

}
