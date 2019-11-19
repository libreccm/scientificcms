/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.assets;

import org.librecms.contentsection.Asset;
import org.librecms.pagemodel.assets.AbstractAssetRenderer;
import org.librecms.pagemodel.assets.AssetRenderer;
import org.scientificcms.publications.Publisher;
import org.scientificcms.publications.assets.PublisherAsset;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
@AssetRenderer(renders = PublisherAsset.class)
public class PublisherAssetRenderer extends AbstractAssetRenderer {

    @Override
    protected void renderAsset(
        final Asset asset, 
        final Locale language,
        final Map<String, Object> result
    ) {
        final PublisherAsset publisherAsset;
        if (asset instanceof PublisherAsset) {
            publisherAsset = (PublisherAsset) asset;
        } else {
            return;
        }
        
        final Publisher publisher = publisherAsset.getPublisher();
        final Map<String, Object> publisherData = new HashMap<>();
        publisherData.put("publisherId", publisher.getPublisherId());
        publisherData.put("uuid", publisher.getUuid());
        publisherData.put("name", publisher.getName());
        publisherData.put("place", publisher.getPlace());
        
        result.put("publisher", publisherData);
    }

}
