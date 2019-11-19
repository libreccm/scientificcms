/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.assets;

import org.librecms.contentsection.Asset;
import org.librecms.pagemodel.assets.AbstractAssetRenderer;
import org.librecms.pagemodel.assets.AssetRenderer;
import org.scientificcms.publications.Series;
import org.scientificcms.publications.assets.SeriesAsset;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
@AssetRenderer(renders = SeriesAsset.class)
public class SeriesAssetRenderer extends AbstractAssetRenderer {

    @Override
    protected void renderAsset(
        final Asset asset,
        final Locale language,
        final Map<String, Object> result
    ) {
        final SeriesAsset seriesAsset;
        if (asset instanceof SeriesAsset) {
            seriesAsset = (SeriesAsset) asset;
        } else {
            return;
        }

        final Series series = seriesAsset.getSeries();

        final Map<String, Object> seriesData = new HashMap<>();

        seriesData.put(
            "description", series.getDescription().getValue(language)
        );

        seriesData.put("seriesId", series.getSeriesId());
        seriesData.put("title", series.getTitle().getValue(language));
        seriesData.put("uuid", series.getUuid());

        result.put("series", seriesData);
    }

}
