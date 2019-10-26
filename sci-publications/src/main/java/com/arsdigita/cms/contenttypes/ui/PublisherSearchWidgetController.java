/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.scientificcms.publications.Publisher;
import org.scientificcms.publications.PublisherRepository;

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
public class PublisherSearchWidgetController {
    
    protected static final String PUBLISHER_ID = "publisherId";
    protected static final String NAME = "name";
    protected static final String PLACE = "place";
    
    @Inject
    private PublisherRepository publisherRepository;
    
    @Transactional(Transactional.TxType.REQUIRED)
    public Map<String, String> getData(final long publisherId) {
        
        final Publisher publisher = publisherRepository
        .findById(publisherId)
        .orElseThrow(
            () -> new IllegalArgumentException(
                String.format("No Publisher with ID %d found.", publisherId)
            )
        );
        
        final Map<String, String> data = new HashMap<>();
        
        data.put(PUBLISHER_ID, Long.toString(publisher.getPublisherId()));
        data.put(NAME, publisher.getName());
        data.put(PLACE, publisher.getPlace());
        
        return data;
    }
}
