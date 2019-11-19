/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets.ui;

import com.arsdigita.cms.ui.assets.AbstractAssetFormController;
import com.arsdigita.cms.ui.assets.IsControllerForAssetType;

import org.scientificcms.publications.Series;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

import org.scientificcms.publications.assets.SeriesAsset;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
@IsControllerForAssetType(SeriesAsset.class)
public class SeriesFormController
    extends AbstractAssetFormController<SeriesAsset> {

    protected static final String TITLE = "title";

    protected static final String DESCRIPTION = "description";

    @Override
    protected Map<String, Object> getAssetData(
        final SeriesAsset asset,
        final Locale selectedLocale
    ) {
        final Series series = asset.getSeries();

        final Map<String, Object> data = new HashMap<>();

        data.put(TITLE, series.getTitle().getValue(selectedLocale));
        data.put(DESCRIPTION, series.getDescription().getValue(selectedLocale));

        return data;
    }

    @Override
    public void updateAssetProperties(
        final SeriesAsset asset,
        final Locale selectedLocale,
        final Map<String, Object> data
    ) {
        final Series series = asset.getSeries();
        
        series.getTitle().addValue(selectedLocale, (String) data.get(TITLE));
        series.getDescription().addValue(
            selectedLocale, (String) data.get(DESCRIPTION)
        );
    }

}
