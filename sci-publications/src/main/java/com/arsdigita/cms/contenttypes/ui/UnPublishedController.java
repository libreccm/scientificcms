/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.librecms.assets.Organization;
import org.librecms.contentsection.AssetRepository;
import org.scientificcms.publications.PublicationRepository;
import org.scientificcms.publications.UnPublished;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class UnPublishedController extends PublicationPropertiesStep {

    public static final String PLACE = "place";

    public static final String NUMBER = "number";

    public static final String NUMBER_OF_PAGES = "numberOfPages";

    @Inject
    private AssetRepository assetRepository;

    @Inject
    private PublicationRepository publicationRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveUnPublished(
        final long unpublishedId, final Map<String, Object> data
    ) {
        final UnPublished unPublished = publicationRepository
            .findByIdAndType(unpublishedId, UnPublished.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No UnPublished publication with ID %d found.",
                        unpublishedId
                    )
                )
            );

        if (data.containsKey(PLACE)) {
            unPublished.setPlace((String) data.get(PLACE));
        }

        if (data.containsKey(NUMBER)) {
            unPublished.setNumber((String) data.get(NUMBER));
        }

        if (data.containsKey(NUMBER_OF_PAGES)) {
            unPublished.setNumberOfPages((Integer) data.get(NUMBER_OF_PAGES));
        }

        publicationRepository.save(unPublished);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void setOrganzation(
        final long unpublishedId, final long organizationId
    ) {
        final UnPublished unPublished = publicationRepository
            .findByIdAndType(unpublishedId, UnPublished.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No UnPublished publication with ID %d found.",
                        unpublishedId
                    )
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

        unPublished.setOrganization(organization);
        publicationRepository.save(unPublished);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void unsetOrganization(final long unpublishedId) {
        final UnPublished unPublished = publicationRepository
            .findByIdAndType(unpublishedId, UnPublished.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No UnPublished publication with ID %d found.",
                        unpublishedId
                    )
                )
            );

        unPublished.setOrganization(null);
        publicationRepository.save(unPublished);
    }

}
