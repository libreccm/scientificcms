/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.l10n.LocalizedString;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 * Basic properties of publication.
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Embeddable
public class BasicPublicationProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "YEAR_OF_PUBLICATION")
    private Integer yearOfPublication;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PUBLICATION_TITLES",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "OBJECT_ID")
                               })
    )
    private LocalizedString title;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PUBLICATION_SHORT_DESCS",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "OBJECT_ID")
                               })
    )
    private LocalizedString shortDescription;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PUBLICATION_ABSTRACTS",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "OBJECT_ID")
                               })
    )
    private LocalizedString publicationAbstract;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PUBLICATION_MISC",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "OBJECT_ID")
                               })
    )
    private LocalizedString misc;

    @Column(name = "PEER_REVIEWED")
    private Boolean peerReviewed;

    @Column(name = "YEAR_FIRST_PUBLISHED")
    private Integer yearFirstPublished;

    @Column(name = "LANGUAGE_OF_PUBLICATION")
    private Locale languageOfPublication;

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(final Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(yearOfPublication);
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
        if (!(obj instanceof BasicPublicationProperties)) {
            return false;
        }
        final BasicPublicationProperties other
                                             = (BasicPublicationProperties) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(yearOfPublication, other.getYearOfPublication())) {
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

        return obj instanceof BasicPublicationProperties;
    }

    public String toString(final String data) {

        return String.format("%s{ "
                                 + "yearOfPublication = %d, "
                                 + "title = %s, "
                                 + "shortDescription = %s, "
                                 + "publicationAbstract = %s, "
                                 + "misc = %s, "
                                 + "peerReviewed = %b, "
                                 + "yearFirstPublished = %d, "
                                 + "languageOfPublication = \"%s\"%d"
                                 + " }",
                             super.toString(),
                             yearOfPublication,
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
