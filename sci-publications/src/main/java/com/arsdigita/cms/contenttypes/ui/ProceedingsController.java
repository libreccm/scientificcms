/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.librecms.assets.Organization;
import org.librecms.contentsection.AssetRepository;
import org.scientificcms.publications.InProceedings;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.ProceedingsManager;
import org.scientificcms.publications.PublicationRepository;

import java.time.LocalDate;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class ProceedingsController {

    public static final String NAME_OF_CONFERENCE = "nameOfConference";

    public static final String PLACE_OF_CONFERENCE = "placeOfConference";

    public static final String START_DATE = "startDate";

    public static final String END_DATE = "endDate";

    @Inject
    private AssetRepository assetRepository;

    @Inject
    private ProceedingsManager proceedingsManager;

    @Inject
    private PublicationRepository publicationRepository;

    public void saveProceedings(
        final long proceedingsId, final Map<String, Object> data
    ) {
        final Proceedings proceedings = publicationRepository
            .findByIdAndType(proceedingsId, Proceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Procceedings with ID %d found.", proceedingsId
                    )
                )
            );

        if (data.containsKey(NAME_OF_CONFERENCE)) {
            proceedings.setNameOfConference(
                (String) data.get(NAME_OF_CONFERENCE)
            );

        }

        if (data.containsKey(PLACE_OF_CONFERENCE)) {
            proceedings.setPlaceOfConference((String) data.get(
                PLACE_OF_CONFERENCE)
            );
        }

        if (data.containsKey(START_DATE)) {
            proceedings.setStartDate((LocalDate) data.get(START_DATE));
        }

        if (data.containsKey(END_DATE)) {
            proceedings.setStartDate((LocalDate) data.get(END_DATE));
        }

        publicationRepository.save(proceedings);
    }

    public InProceedings findPaper(final long paperId) {
        final InProceedings paper = publicationRepository
            .findByIdAndType(paperId, InProceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No InProcceedings with ID %d found.", paperId
                    )
                )
            );

        return paper;
    }

    public void addPaper(final long proceedingsId, final long paperId) {
        final Proceedings proceedings = publicationRepository
            .findByIdAndType(proceedingsId, Proceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Procceedings with ID %d found.", proceedingsId
                    )
                )
            );

        final InProceedings paper = publicationRepository
            .findByIdAndType(paperId, InProceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No InProcceedings with ID %d found.", paperId
                    )
                )
            );

        proceedingsManager.addPaperToCollectedVolume(paper, proceedings);
    }

    public void removePaper(final long proceedingsId, final long paperId) {
        final Proceedings proceedings = publicationRepository
            .findByIdAndType(proceedingsId, Proceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Procceedings with ID %d found.", proceedingsId
                    )
                )
            );

        final InProceedings paper = publicationRepository
            .findByIdAndType(paperId, InProceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No InProcceedings with ID %d found.", paperId
                    )
                )
            );

        proceedingsManager.removeArticleFromCollectedVolume(paper, proceedings);
    }

    public void setOrganizier(final long proceedingsId, final long organizerId) {
        final Proceedings proceedings = publicationRepository
            .findByIdAndType(proceedingsId, Proceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Procceedings with ID %d found.", proceedingsId
                    )
                )
            );

        final Organization organizer = assetRepository
            .findById(organizerId, Organization.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Organization with ID %d found.", organizerId
                    )
                )
            );

        proceedings.setOrganizer(organizer);
        publicationRepository.save(proceedings);
    }

    public void unsetOrganizier(final long proceedingsId) {
        final Proceedings proceedings = publicationRepository
            .findByIdAndType(proceedingsId, Proceedings.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Procceedings with ID %d found.", proceedingsId
                    )
                )
            );

        proceedings.setOrganizer(null);
        publicationRepository.save(proceedings);
    }

}
