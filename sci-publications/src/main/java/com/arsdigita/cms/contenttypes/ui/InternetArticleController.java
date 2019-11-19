/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.librecms.assets.Organization;
import org.librecms.contentsection.AssetRepository;
import org.scientificcms.publications.InternetArticle;
import org.scientificcms.publications.PublicationRepository;

import java.time.LocalDate;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class InternetArticleController {

    public static final String PLACE = "place";

    public static final String NUMBER = "number";

    public static final String NUMBER_OF_PAGES = "numberOfPages";

    public static final String EDITION = "edition";

    public static final String ISSN = "issn";

    public static final String LAST_ACCESSED = "lastAccessed";

    public static final String URL = "url";

    public static final String URN = "urn";

    public static final String DOI = "doi";

    @Inject
    private AssetRepository assetRepository;

    @Inject
    private PublicationRepository publicationRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveInternetArticle(
        final long internetArticleId, final Map<String, Object> data
    ) {
        final InternetArticle internetArticle = publicationRepository
            .findByIdAndType(internetArticleId, InternetArticle.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No InternetArticle with ID %d found.",
                        internetArticleId
                    )
                )
            );

        if (data.containsKey(PLACE)) {
            internetArticle.setPlace((String) data.get(PLACE));
        }

        if (data.containsKey(NUMBER)) {
            internetArticle.setNumber((String) data.get(NUMBER));
        }

        if (data.containsKey(NUMBER_OF_PAGES)) {
            internetArticle.setNumberOfPages(
                (Integer) data.get(NUMBER_OF_PAGES)
            );
        }

        if (data.containsKey(EDITION)) {
            internetArticle.setEdition((String) data.get(EDITION));
        }

        if (data.containsKey(ISSN)) {
            internetArticle.setIssn((String) data.get(ISSN));
        }

        if (data.containsKey(LAST_ACCESSED)) {
            internetArticle.setLastAccessed((LocalDate) data.get(LAST_ACCESSED));
        }

        if (data.containsKey(URL)) {
            internetArticle.setUrl((String) data.get(URL));
        }

        if (data.containsKey(URN)) {
            internetArticle.setUrn((String) data.get(URN));
        }

        if (data.containsKey(DOI)) {
            internetArticle.setDoi((String) data.get(DOI));
        }

        publicationRepository.save(internetArticle);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void setOrganization(
        final long internetArticleId,
        final long organizationId
    ) {
        final InternetArticle internetArticle = publicationRepository
            .findByIdAndType(internetArticleId, InternetArticle.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No InternetArticle with ID %d found.",
                        internetArticleId
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

        internetArticle.setOrganization(organization);
        publicationRepository.save(internetArticle);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void unsetOrganization(final long internetArticleId) {
        final InternetArticle internetArticle = publicationRepository
            .findByIdAndType(internetArticleId, InternetArticle.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format(
                        "No InternetArticle with ID %d found.",
                        internetArticleId
                    )
                )
            );

        internetArticle.setOrganization(null);
    }

}
