/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.assets.Organization;
import org.librecms.assets.Person;
import org.librecms.assets.PersonName;
import org.librecms.contentsection.ContentItem;
import org.librecms.pagemodel.assets.AssetRenderers;
import org.librecms.pagemodel.contentitems.AbstractContentItemRenderer;
import org.scientificcms.publications.Authorship;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.Publisher;
import org.scientificcms.publications.Series;
import org.scientificcms.publications.VolumeInSeries;
import org.scientificcms.publications.contenttypes.PublicationItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public abstract class AbstractPublicationRenderer
    extends AbstractContentItemRenderer {

    private static final long serialVersionUID = 1L;

    @Inject
    private AssetRenderers assetRenderers;

    @Override
    protected void renderItem(
        final ContentItem item,
        final Locale language,
        final Map<String, Object> result
    ) {
        final PublicationItem<?> publicationItem;
        if (item instanceof PublicationItem) {
            publicationItem = (PublicationItem<?>) item;
        } else {
            return;
        }

        final Publication publication = publicationItem.getPublication();

        final Map<String, Object> publicationData = new HashMap<>();

        publicationData.put(
            "authorships",
            publication
                .getAuthorships()
                .stream()
                .map(this::renderAuthorship)
                .collect(Collectors.toList())
        );
        publicationData.put(
            "languageOfPublication",
            publication.getLanguageOfPublication().toString()
        );
        publicationData.put(
            "misc",
            publication.getMisc().getValue(language)
        );
        publicationData.put(
            "peerReviewed",
            publication.getPeerReviewed()
        );
        publicationData.put(
            "abstract",
            publication.getPublicationAbstract().getValue(language)
        );
        publicationData.put("publicationId", publication.getPublicationId());
        publicationData.put(
            "series",
            publication
                .getSeries()
                .stream()
                .map(volume -> renderVolume(volume, language))
                .collect(Collectors.toList())
        );
        publicationData.put(
            "shortDescription",
            publication.getShortDescription().getValue(language)
        );
        publicationData.put(
            "title",
            publication.getTitle().getValue(language)
        );
        publicationData.put("uuid", publication.getUuid());
        publicationData.put(
            "yearFirstPublished", publication.getYearFirstPublished()
        );
        publicationData.put(
            "yearOfPublication", publication.getYearOfPublication()
        );

        renderPublication(publication, language, publicationData);

        result.put("publication", publicationData);
    }

    protected abstract void renderPublication(
        final Publication publication,
        final Locale language,
        final Map<String, Object> publicationData
    );

    @Override
    public AssetRenderers getAssetRenderers() {
        return assetRenderers;
    }

    protected Map<String, Object> renderAuthorship(final Authorship authorship) {
        final Map<String, Object> data = new HashMap<>();

        data.put("author", renderPerson(authorship.getAuthor()));
        data.put("authorOrder", authorship.getAuthorOrder());
        data.put("authorshipId", authorship.getAuthorshipId());
        data.put("uuid", authorship.getUuid());
        data.put("isEditor", authorship.isEditor());

        return data;
    }

    protected Map<String, Object> renderPublisher(final Publisher publisher) {
        final Map<String, Object> data = new HashMap<>();

        data.put("name", publisher.getName());
        data.put("place", publisher.getPlace());
        data.put("publisherId", publisher.getPublisherId());
        data.put("uuid", publisher.getUuid());

        return data;
    }

    protected Map<String, Object> renderOrganization(
        final Organization organization
    ) {
        final Map<String, Object> data = new HashMap<>();

        data.put("", organization.getName());

        return data;
    }

    private Map<String, Object> renderPerson(final Person person) {
        final Map<String, Object> data = new HashMap<>();

        final PersonName personName = person.getPersonName();

        data.put("givenName", personName.getGivenName());
        data.put("surname", personName.getSurname());
        data.put("prefix", personName.getPrefix());
        data.put("suffix", personName.getSuffix());

        return data;
    }

    private Map<String, Object> renderVolume(
        final VolumeInSeries volume, final Locale language
    ) {
        final Map<String, Object> data = new HashMap<>();

        data.put("series", renderSeries(volume.getSeries(), language));
        data.put("uuid", volume.getUuid());
        data.put("volumeId", volume.getVolumeId());
        data.put("volumeOfSeries", volume.getVolumeOfSeries());

        return data;
    }

    private Map<String, Object> renderSeries(
        final Series series, final Locale language
    ) {
        final Map<String, Object> data = new HashMap<>();

        data.put("description", series.getDescription().getValue(language));
        data.put("seriesId", series.getSeriesId());
        data.put("title", series.getTitle().getValue(language));
        data.put("uuid", series.getUuid());

        return data;
    }

}
