/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.scientificcms.publications.GreyLiterature;
import org.scientificcms.publications.PublicationRepository;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class GreyLiteratureController {
    
    public static final String START_PAGE = "startPage";
    public static final String END_PAGE = "endPage";
    
    @Inject
    private PublicationRepository publicationRepository;
    
    @Transactional(Transactional.TxType.REQUIRED)
    public void saveGreyLiterature(
        final long greyLiteratureId, final Map<String, Object> data
    ) {
        final GreyLiterature publication = publicationRepository
        .findByIdAndType(greyLiteratureId, GreyLiterature.class
        )
        .orElseThrow(
            () -> new IllegalArgumentException(
                String.format(
                    "No GreyLiterature publication with ID %d found",
                    greyLiteratureId
                )
            )
        );
        
        if (data.containsKey(START_PAGE)) {
            publication.setStartPage((Integer) data.get(START_PAGE));
        }
        
        if (data.containsKey(END_PAGE)) {
            publication.setEndPage((Integer) data.get(END_PAGE));
        }
        
        publicationRepository.save(publication);
    }
    
}
