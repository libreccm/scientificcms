/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.publications.InProceedings;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.PublicationWithPublisher;
import org.scientificcms.publications.contenttypes.ProceedingsItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = ProceedingsItem.class)
public class ProceedingsRenderer
    extends AbstractPublicationWithPublisherRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void renderPublicationWithPublisher(
        final PublicationWithPublisher publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final Proceedings proceedings;
        if (publication instanceof Proceedings) {
            proceedings = (Proceedings) publication;
        } else {
            return;
        }
        
        publicationData.put("endDate", proceedings.getEndDate());
        publicationData.put(
            "nameOfConference", proceedings.getNameOfConference()
        );
        publicationData.put(
            "organizer", renderOrganization(proceedings.getOrganizer())
        );
        publicationData.put(
            "papers", 
            proceedings
                .getPapers()
            .stream()
            .map(paper -> renderPaper(paper, language))
            .collect(Collectors.toList())
        );
        publicationData.put(
            "placeOfConference", proceedings.getPlaceOfConference()
        );
        publicationData.put("startDate", proceedings.getStartDate());
    }

    private Map<String, Object> renderPaper(
        final InProceedings paper, final Locale language
    ) {
        final Map<String, Object> data = new HashMap<>();

        data.put(
            "authors",
            paper
                .getAuthorships()
                .stream()
                .map(this::renderAuthorship)
                .collect(Collectors.toList())
        );
        data.put("endPage", paper.getEndPage());
        data.put(
            "languageOfPublication", 
            paper.getLanguageOfPublication().toString()
        );
        data.put("misc", paper.getMisc().getValue(language));
        data.put("peerReviewed", paper.getPeerReviewed());
        data.put("abstract", paper.getPublicationAbstract().getValue(language));
        data.put("publicationId", paper.getPublicationId());
        data.put(
            "shortDescription", paper.getShortDescription().getValue(language)
        );
        data.put("startPage", paper.getStartPage());
        data.put("title", paper.getTitle().getValue(language));
        data.put("uuid", paper.getUuid());
        data.put("yearFirstPublished", paper.getYearFirstPublished());
        data.put("yearOfPublication", paper.getYearOfPublication());

        return data;
    }

}
