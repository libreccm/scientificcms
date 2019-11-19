/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.scientificcms.publications.PublicationRepository;
import org.scientificcms.publications.WorkingPaper;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class WorkingPaperController {

    public static final String PEER_REVIEWED = "reviewed";
    
    @Inject
    private PublicationRepository publicationRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveWorkingPaper(
        final long workingPaperId, final Map<String, Object> data
    ) {
        final WorkingPaper workingPaper = publicationRepository
        .findByIdAndType(workingPaperId, WorkingPaper.class)
        .orElseThrow(
            () -> new IllegalArgumentException(
                String.format(
                    "No WorkingPaper with Id %d found.", workingPaperId
                )
            )
        );
        
        if (data.containsKey(PEER_REVIEWED)) {
            workingPaper.setPeerReviewed((Boolean) data.get(PEER_REVIEWED));
        }
        
        publicationRepository.save(workingPaper);
    }

}
