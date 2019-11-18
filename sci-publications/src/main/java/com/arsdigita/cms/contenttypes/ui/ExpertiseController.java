/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.librecms.assets.Organization;
import org.librecms.contentsection.AssetRepository;
import org.scientificcms.publications.Expertise;
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
public class ExpertiseController {

    public static final String PLACE = "place";
    public static final String NUMBER_OF_PAGES = "numberOfPages";

    @Inject
    private AssetRepository assetRepository;

    @Inject
    private PublicationRepository publicationRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public void save(final long expertiseId, final Map<String, Object> data) {
        final Expertise expertise = publicationRepository
            .findByIdAndType(expertiseId, Expertise.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Expertise with ID %d found.", expertiseId)
                )
            );

        if (data.containsKey(PLACE)) {
            expertise.setPlace((String) data.get(PLACE));
        }

        if (data.containsKey(NUMBER_OF_PAGES)) {
            expertise.setNumberOfPages((Integer) data.get(NUMBER_OF_PAGES));
        }

        publicationRepository.save(expertise);
    }

    @Transactional
    public void setOrderer(final long expertiseId, final long ordererId) {
        final Expertise expertise = publicationRepository
            .findByIdAndType(expertiseId, Expertise.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Expertise with ID %d found.", expertiseId)
                )
            );

        final Organization orderer = assetRepository
            .findById(ordererId, Organization.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Organization with ID %d found.", ordererId
                    )
                )
            );

        expertise.setOrderer(orderer);
        publicationRepository.save(expertise);
    }

    @Transactional
    public void unsetOrderer(final long expertiseId) {
        final Expertise expertise = publicationRepository
            .findByIdAndType(expertiseId, Expertise.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Expertise with ID %d found.", expertiseId)
                )
            );
        
        expertise.setOrderer(null);
        publicationRepository.save(expertise);
    }

    @Transactional
    public void setOrganization(
        final long expertiseId, final long organizationId
    ) {
        final Expertise expertise = publicationRepository
            .findByIdAndType(expertiseId, Expertise.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Expertise with ID %d found.", expertiseId)
                )
            );

        final Organization organization = assetRepository
            .findById(organizationId, Organization.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No Organization with ID %d found.", organizationId
                    )
                )
            );
        
        expertise.setOrganization(organization);
        publicationRepository.save(expertise);
    }

    @Transactional
    public void unsetOrganization(final long expertiseId) {
        final Expertise expertise = publicationRepository
            .findByIdAndType(expertiseId, Expertise.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No Expertise with ID %d found.", expertiseId)
                )
            );
        expertise.setOrganization(null);
        publicationRepository.save(expertise);
    }

}
