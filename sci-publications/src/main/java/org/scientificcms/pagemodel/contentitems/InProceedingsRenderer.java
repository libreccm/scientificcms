/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.publications.InProceedings;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.contenttypes.InProceedingsItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = InProceedingsItem.class)
public class InProceedingsRenderer extends AbstractPublicationRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void renderPublication(
        final Publication publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final InProceedings paper;
        if (publication instanceof InProceedings) {
            paper = (InProceedings) publication;
        } else {
            return;
        }

        publicationData.put("", paper.getEndPage());
        publicationData.put(
            "proceedings", renderProceedings(paper.getProceedings(), language)
        );
        publicationData.put("", paper.getStartPage());
    }

    private Map<String, Object> renderProceedings(
        final Proceedings proceedings, final Locale language
    ) {
        final Map<String, Object> data = new HashMap<>();

        data.put(
            "authors",
            proceedings
                .getAuthorships()
                .stream()
                .map(this::renderAuthorship)
                .collect(Collectors.toList())
        );

        data.put("edition", proceedings.getEdition());
        data.put("endDate", proceedings.getEndDate());
        data.put("isbn10", proceedings.getIsbn10());
        data.put("isbn13", proceedings.getIsbn13());
        data.put(
            "languageOfPublication",
            proceedings.getLanguageOfPublication().toString()
        );
        data.put("misc", proceedings.getMisc().getValue(language));
        data.put("nameOfConference", proceedings.getNameOfConference());
        data.put("numberOfPages", proceedings.getNumberOfPages());
        data.put("numberOfVolumes", proceedings.getNumberOfVolumes());
        data.put("organizer", renderOrganization(proceedings.getOrganizer()));
        data.put("peerReviewed", proceedings.getPeerReviewed());
        data.put("placeOfConference", proceedings.getPlaceOfConference());
        data.put(
            "abstract", proceedings.getPublicationAbstract().getValue(language)
        );
        data.put("publicationId", proceedings.getPublicationId());
        data.put("publisher", renderPublisher(proceedings.getPublisher()));
        data.put(
            "shortDescription",
            proceedings.getShortDescription().getValue(language)
        );
        data.put("startDate", proceedings.getStartDate());
        data.put("title", proceedings.getTitle().getValue(language));
        data.put("uuid", proceedings.getUuid());
        data.put("volume", proceedings.getVolume());
        data.put("yearFirstPublished", proceedings.getYearFirstPublished());
        data.put("yearOfPublication", proceedings.getYearOfPublication());

        return data;
    }

}
