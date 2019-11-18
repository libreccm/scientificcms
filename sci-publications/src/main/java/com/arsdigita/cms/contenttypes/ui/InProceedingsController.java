/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.scientificcms.publications.InProceedings;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.ProceedingsManager;
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
public class InProceedingsController {

    public static final String START_PAGE = "startPage";
    public static final String END_PAGE = "endPage";

    @Inject
    private ProceedingsManager proceedingsManager;

    @Inject
    private PublicationRepository publicationRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveInProceedings(
        final long inProceedingsId,
        final Map<String, Object> data
    ) {
        final InProceedings inProceedings = publicationRepository
            .findByIdAndType(inProceedingsId, InProceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No InProceedings publication with ID %d found.",
                        inProceedingsId
                    )
                )
            );

        if (data.containsKey(START_PAGE)) {
            inProceedings.setStartPage((Integer) data.get(START_PAGE));
        }

        if (data.containsKey(END_PAGE)) {
            inProceedings.setEndPage((Integer) data.get(END_PAGE));
        }

        publicationRepository.save(inProceedings);
    }

    public void setProceedings(
        final long inProceedingsId, final long proceedingsId
    ) {
        final InProceedings inProceedings = publicationRepository
            .findByIdAndType(inProceedingsId, InProceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No InProceedings publication with ID %d found.",
                        inProceedingsId
                    )
                )
            );

        final Proceedings proccedings = publicationRepository
            .findByIdAndType(proceedingsId, Proceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Proceedings with ID %d found", proceedingsId
                    )
                )
            );

        proceedingsManager.addPaperToCollectedVolume(inProceedings, proccedings);
    }

    public void unsetProcceedings(
        final long inProceedingsId, final long proceedingsId
    ) {
        final InProceedings inProceedings = publicationRepository
            .findByIdAndType(inProceedingsId, InProceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No InProceedings publication with ID %d found.",
                        inProceedingsId
                    )
                )
            );

        final Proceedings proccedings = publicationRepository
            .findByIdAndType(proceedingsId, Proceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Proceedings with ID %d found", proceedingsId
                    )
                )
            );

        proceedingsManager.removeArticleFromCollectedVolume(
            inProceedings, proccedings);
    }

}
