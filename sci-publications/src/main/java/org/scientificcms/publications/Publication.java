/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.envers.Audited;
import org.libreccm.l10n.LocalizedString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 * Basic properties of publication.
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "PUBLICATIONS", schema = DB_SCHEMA)
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
@NamedQueries({
    @NamedQuery(
        name = "Publication.findByIdAndType",
        query = "SELECT p FROM Publication p "
                    + "WHERE p.publicationId = :publicationId "
                    + "AND TYPE(p) = :type"
    ),
    @NamedQuery(
        name = "Publication.findByUuid",
        query = "SELECT p FROM Publication p WHERE p.uuid = :uuid"
    ),
    @NamedQuery(
        name = "Publication.findByUuidAndType",
        query = "SELECT p FROM Publication p "
                    + "WHERE p.uuid = :uuid "
                    + "AND TYPE(p) = :type"
    ),
    @NamedQuery(
        name = "Publication.findByTitle",
        query = "SELECT DISTINCT p "
                    + "FROM Publication p JOIN p.title.values t "
                    + "WHERE LOWER(t) LIKE CONCAT('%', :title, '%')"
    ),
    @NamedQuery(
        name = "Publication.findByTitleAndType",
        query = "SELECT DISTINCT p "
                    + "FROM Publication p JOIN p.title.values t "
                    + "WHERE LOWER(t) LIKE CONCAT('%', :title, '%') "
                    + "AND TYPE(p) = :type"
    ),
    @NamedQuery(
        name = "Publication.findByType",
        query = "SELECT DISTINCT p FROM Publication p WHERE TYPE(p) = :type"
    ),
    @NamedQuery(
        name = "Publication.findByAuthor",
        query = "SELECT DISTINCT p "
                    + "FROM Publication p JOIN p.authorships a "
                    + "WHERE a.author = :author"
    ),
    @NamedQuery(
        name = "Publication.findByAuthorAndType",
        query = "SELECT DISTINCT p "
                    + "FROM Publication p JOIN p.authorships a "
                    + "WHERE a.author = :author "
                    + "AND TYPE(p) = :type"
    )
})
public class Publication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PUBLICATION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long publicationId;

    @Column(name = "UUID", unique = true, nullable = false)
    private String uuid;

    @Column(name = "YEAR_OF_PUBLICATION")
    private Integer yearOfPublication;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "author",
               orphanRemoval = true)
    @OrderBy("authorOrder")
    private List<Authorship> authorships;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PUBLICATION_TITLES",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "PUBLICATION_ID")
                               })
    )
    private LocalizedString title;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PUBLICATION_SHORT_DESCS",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "PUBLICATION_ID")
                               })
    )
    private LocalizedString shortDescription;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PUBLICATION_ABSTRACTS",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "PUBLICATION_ID")
                               })
    )
    private LocalizedString publicationAbstract;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PUBLICATION_MISC",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "PUBLICATION_ID")
                               })
    )
    private LocalizedString misc;

    @Column(name = "PEER_REVIEWED")
    private Boolean peerReviewed;

    @Column(name = "YEAR_FIRST_PUBLISHED")
    private Integer yearFirstPublished;

    @Column(name = "LANGUAGE_OF_PUBLICATION")
    private Locale languageOfPublication;

    @OneToMany(mappedBy = "publication")
    private List<VolumeInSeries> series;

    public Publication() {
        authorships = new ArrayList<>();
        title = new LocalizedString();
        shortDescription = new LocalizedString();
        publicationAbstract = new LocalizedString();
        misc = new LocalizedString();
        series = new ArrayList<>();
    }

    public long getPublicationId() {
        return publicationId;
    }

    protected void setPublicationId(final long publicationId) {
        this.publicationId = publicationId;
    }

    public String getUuid() {
        return uuid;
    }

    protected void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(final Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public List<Authorship> getAuthorships() {
        if (authorships == null) {
            return null;
        } else {
            return Collections.unmodifiableList(authorships);
        }
    }

    protected void addAuthorship(final Authorship authorship) {
        authorships.add(authorship);
    }

    protected void removeAuthorship(final Authorship authorship) {
        authorships.remove(authorship);
    }

    protected void setAuthorships(final List<Authorship> authorships) {
        if (authorships == null) {
            this.authorships = null;
        } else {
            this.authorships = new ArrayList<>(authorships);
        }
    }

    public LocalizedString getTitle() {
        return title;
    }

    public void setTitle(final LocalizedString title) {
        this.title = title;
    }

    public LocalizedString getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(final LocalizedString shortDescription) {
        this.shortDescription = shortDescription;
    }

    public LocalizedString getPublicationAbstract() {
        return publicationAbstract;
    }

    public void setPublicationAbstract(final LocalizedString publicationAbstract) {
        this.publicationAbstract = publicationAbstract;
    }

    public LocalizedString getMisc() {
        return misc;
    }

    public void setMisc(final LocalizedString misc) {
        this.misc = misc;
    }

    public Boolean getPeerReviewed() {
        return peerReviewed;
    }

    public void setPeerReviewed(final Boolean peerReviewed) {
        this.peerReviewed = peerReviewed;
    }

    public Integer getYearFirstPublished() {
        return yearFirstPublished;
    }

    public void setYearFirstPublished(final Integer yearFirstPublished) {
        this.yearFirstPublished = yearFirstPublished;
    }

    public Locale getLanguageOfPublication() {
        return languageOfPublication;
    }

    public void setLanguageOfPublication(final Locale languageOfPublication) {
        this.languageOfPublication = languageOfPublication;
    }

    public List<VolumeInSeries> getSeries() {
        if (series == null) {
            return null;
        } else {
            return Collections.unmodifiableList(series);
        }
    }
    
    protected void addSeries(final VolumeInSeries series) {
        this.series.add(series);
    }
    
    protected void removeSeries(final VolumeInSeries series) {
        this.series.remove(series);
    }
    
    protected void setSeries(final List<VolumeInSeries> series) {
        this.series = series;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (publicationId ^ (publicationId >>> 32));
        hash = 97 * hash + Objects.hashCode(uuid);
        hash = 97 * hash + Objects.hashCode(yearOfPublication);
        hash = 97 * hash + Objects.hashCode(authorships);
        hash = 97 * hash + Objects.hashCode(title);
        hash = 97 * hash + Objects.hashCode(shortDescription);
        hash = 97 * hash + Objects.hashCode(publicationAbstract);
        hash = 97 * hash + Objects.hashCode(misc);
        hash = 97 * hash + Objects.hashCode(peerReviewed);
        hash = 97 * hash + Objects.hashCode(yearFirstPublished);
        hash = 97 * hash + Objects.hashCode(languageOfPublication);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Publication)) {
            return false;
        }
        final Publication other
                              = (Publication) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (publicationId != other.getPublicationId()) {
            return false;
        }
        if (!Objects.equals(uuid, other.getUuid())) {
            return false;
        }
        if (!Objects.equals(yearOfPublication, other.getYearOfPublication())) {
            return false;
        }
        if (!Objects.equals(authorships, other.getAuthorships())) {
            return false;
        }
        if (!Objects.equals(title, other.getTitle())) {
            return false;
        }
        if (!Objects.equals(shortDescription, other.getShortDescription())) {
            return false;
        }
        if (!Objects.equals(publicationAbstract,
                            other.getPublicationAbstract())) {
            return false;
        }
        if (!Objects.equals(misc, other.getMisc())) {
            return false;
        }
        if (!Objects.equals(peerReviewed, other.getPeerReviewed())) {
            return false;
        }
        if (!Objects.equals(yearFirstPublished, other.getYearFirstPublished())) {
            return false;
        }
        return Objects.equals(languageOfPublication,
                              other.getLanguageOfPublication());
    }

    public boolean canEqual(final Object obj) {

        return obj instanceof Publication;
    }

    public String toString(final String data) {

        return String.format("%s{ "
                                 + "publicationId = %d, "
                                 + "uuid = \"%s\""
                                 + "yearOfPublication = %d, "
                                 + "authorships = %s, "
                                 + "title = %s, "
                                 + "shortDescription = %s, "
                                 + "publicationAbstract = %s, "
                                 + "misc = %s, "
                                 + "peerReviewed = %b, "
                                 + "yearFirstPublished = %d, "
                                 + "languageOfPublication = \"%s\"%d"
                                 + " }",
                             super.toString(),
                             publicationId,
                             uuid,
                             yearOfPublication,
                             authorships,
                             Objects.toString(title),
                             Objects.toString(shortDescription),
                             Objects.toString(publicationAbstract),
                             Objects.toString(misc),
                             peerReviewed,
                             yearFirstPublished,
                             Objects.toString(languageOfPublication),
                             data);
    }

    @Override
    public final String toString() {

        return toString("");
    }

}
