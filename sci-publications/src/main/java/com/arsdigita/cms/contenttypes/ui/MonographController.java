/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.scientificcms.publications.Monograph;
import org.scientificcms.publications.PublicationRepository;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class MonographController {
    
    public static final String REVIEWED = "reviewed";
    
    @Inject
    private PublicationRepository publicationRepository;
    
    public void saveMonograph(
        final long monographId, final Map<String, Object> data
    ) {
        final Monograph monograph = publicationRepository
        .findByIdAndType(monographId, Monograph.class)
        .orElseThrow(
            () -> new IllegalArgumentException(
                String.format(
                    "No Monograph with ID %d found.", monographId
                )
            )
        );
        
        if (data.containsKey(REVIEWED)) {
            monograph.setReviewed((Boolean) data.get(REVIEWED));
        }
        
        publicationRepository.save(monograph);
    }
    
}
