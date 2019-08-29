/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;
import org.librecms.assets.Organization;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "INTERNET_ARTICLES", schema = DB_SCHEMA)
@Audited
public class InternetArticle extends Publication {

    private static final long serialVersionUID = 1L;

    @Column(name = "PLACE", length = 512)
    private String place;

    @Column(name = "NUMBER", length = 256)
    private String number;

    @Column(name = "NUMBER_OF_PAGES")
    private Integer numberOfPages;

    @Column(name = "EDITION", length = 512)
    private String edition;

    @Column(name = "ISSN", length = 9)
    private String issn;

    @Column(name = "LAST_ACCESSED")
    private LocalDate lastAccessed;

    @Column(name = "PUBLICATION_DATE")
    private LocalDate publicationDate;

    @Column(name = "URL", length = 2048)
    private String url;

    @Column(name = "URN", length = 2048)
    private String urn;

    @Column(name = "DOI", length = 2048)
    private String doi;

    @OneToOne
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organization organization;

    public String getPlace() {
        return place;
    }

    public void setPlace(final String place) {
        this.place = place;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(final Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(final String edition) {
        this.edition = edition;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(final String issn) {
        this.issn = issn;
    }

    public LocalDate getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(final LocalDate lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(final LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(final String urn) {
        this.urn = urn;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(final String doi) {
        this.doi = doi;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(final Organization organization) {
        this.organization = organization;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 97 * hash + Objects.hashCode(place);
        hash = 97 * hash + Objects.hashCode(number);
        hash = 97 * hash + Objects.hashCode(numberOfPages);
        hash = 97 * hash + Objects.hashCode(edition);
        hash = 97 * hash + Objects.hashCode(issn);
        hash = 97 * hash + Objects.hashCode(lastAccessed);
        hash = 97 * hash + Objects.hashCode(publicationDate);
        hash = 97 * hash + Objects.hashCode(url);
        hash = 97 * hash + Objects.hashCode(urn);
        hash = 97 * hash + Objects.hashCode(doi);
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
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof InternetArticle)) {
            return false;
        }
        final InternetArticle other = (InternetArticle) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(place, other.getPlace())) {
            return false;
        }
        if (!Objects.equals(number, other.getNumber())) {
            return false;
        }
        if (!Objects.equals(edition, other.getEdition())) {
            return false;
        }
        if (!Objects.equals(issn, other.getIssn())) {
            return false;
        }
        if (!Objects.equals(url, other.getUrl())) {
            return false;
        }
        if (!Objects.equals(urn, other.getUrn())) {
            return false;
        }
        if (!Objects.equals(doi, other.getDoi())) {
            return false;
        }
        if (!Objects.equals(numberOfPages, other.getNumberOfPages())) {
            return false;
        }
        if (!Objects.equals(lastAccessed, other.getLastAccessed())) {
            return false;
        }
        return Objects.equals(publicationDate, other.getPublicationDate());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof InternetArticle;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format(", place = \"%s\", "
                                                + "number = \"%s\", "
                                                + "edition = \"%s\", "
                                                + "issn = \"%s\", "
                                                + "url = \"%s\", "
                                                + "urn = \"%s\", "
                                                + "doi = \"%s\", "
                                                + "numberOfPages = %d, "
                                                + "lastAccessed = %s, "
                                                + "publicationDate = %s, "
                                                + "organization = %s%s",
                                            place,
                                            number,
                                            edition,
                                            issn,
                                            url,
                                            urn,
                                            doi,
                                            numberOfPages,
                                            Objects.toString(lastAccessed),
                                            Objects.toString(publicationDate),
                                            Objects.toString(organization),
                                            data));
    }

}
