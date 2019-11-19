/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.scientificcms.publications.PublicationRepository;
import org.scientificcms.publications.Talk;

import java.time.LocalDate;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class TalkController {
    
    public static final String PLACE = "place";
    
    public static final String DATE_OF_TALK = "dateOfTalk";
    
    public static final String EVENT = "event";
    
    @Inject
    private PublicationRepository publicationRepository;
    
    public void saveTalk(final long talkId, final Map<String, Object> data) {
        
        final Talk talk = publicationRepository
        .findByIdAndType(talkId, Talk.class)
        .orElseThrow(
            () -> new IllegalArgumentException(
                String.format(
                    "No Talk with ID %d found.", talkId
                )
            )
        );
        
        if (data.containsKey(PLACE)) {
            talk.setPlace((String) data.get(PLACE));
        }
        
        if (data.containsKey(DATE_OF_TALK)) {
            talk.setDateOfTalk((LocalDate) data.get(DATE_OF_TALK));
        }
        
        if (data.containsKey(EVENT)) {
            talk.setEvent((String) data.get(EVENT));
        }
        
        publicationRepository.save(talk);
    }
}
