/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets.ui;

import com.arsdigita.cms.ui.assets.AbstractAssetFormController;
import com.arsdigita.cms.ui.assets.IsControllerForAssetType;

import org.scientificcms.publications.Publisher;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

import org.scientificcms.publications.assets.PublisherAsset;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
@IsControllerForAssetType(PublisherAsset.class)
public class PublisherFormController
    extends AbstractAssetFormController<PublisherAsset> {

    protected static final String NAME = "name";
    
    protected static final String PLACE = "place";
    
    @Override
    protected Map<String, Object> getAssetData(
        final PublisherAsset asset,
        final Locale selectedLocale
    ) {
        final Map<String, Object> data = new HashMap<>();
        
        final Publisher publisher = asset.getPublisher();
        
        data.put(NAME, publisher.getName());
        data.put(PLACE, publisher.getPlace());
        
        return data;
    }

    
    @Override
    public void updateAssetProperties(
        final PublisherAsset asset,
        final Locale selectedLocale,
        final Map<String, Object> data
    ) {
        final Publisher publisher = asset.getPublisher();
        
        publisher.setName((String) data.get(NAME));
        publisher.setPlace((String) data.get(PLACE));
    }
    
}
