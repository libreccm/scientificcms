/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;
import org.libreccm.l10n.LocalizedString;

import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "PUBLICATIONS_WITH_PUBLISHER")
@Audited
public class PublicationWithPublisher extends Publication {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Publisher publisher;

    @Column(name = "ISBN10", length = 13)
    private String isbn10;

    @Column(name = "ISBN13", length = 17)
    private String isbn13;

    @Column(name = "VOLUME")
    private Integer volume;

    @Column(name = "NUMBER_OF_VOLUMES")
    private Integer numberOfVolumes;

    @Column(name = "NUMBER_OF_PAGES")
    private Integer numberOfPages;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PUBLICATION_EDITION",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "OBJECT_ID")
                               })
    )
    private LocalizedString edition;

    public Publisher getPublisher() {
        return publisher;
    }

    protected void setPublisher(final Publisher publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(final String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(final String isbn13) {
        this.isbn13 = isbn13;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(final Integer volume) {
        this.volume = volume;
    }

    public Integer getNumberOfVolumes() {
        return numberOfVolumes;
    }

    public void setNumberOfVolumes(final Integer numberOfVolumes) {
        this.numberOfVolumes = numberOfVolumes;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(final Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public LocalizedString getEdition() {
        return edition;
    }

    public void setEdition(final LocalizedString edition) {
        this.edition = edition;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 47 * hash + Objects.hashCode(publisher);
        hash = 47 * hash + Objects.hashCode(isbn10);
        hash = 47 * hash + Objects.hashCode(isbn13);
        hash = 47 * hash + Objects.hashCode(volume);
        hash = 47 * hash + Objects.hashCode(numberOfVolumes);
        hash = 47 * hash + Objects.hashCode(numberOfPages);
        hash = 47 * hash + Objects.hashCode(edition);
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
        if (!(obj instanceof PublicationWithPublisher)) {
            return false;
        }
        final PublicationWithPublisher other
                                           = (PublicationWithPublisher) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(isbn10, other.getIsbn10())) {
            return false;
        }
        if (!Objects.equals(isbn13, other.getIsbn13())) {
            return false;
        }
        if (!Objects.equals(publisher, other.getPublisher())) {
            return false;
        }
        if (!Objects.equals(volume, other.getVolume())) {
            return false;
        }
        if (!Objects.equals(numberOfVolumes, other.getNumberOfVolumes())) {
            return false;
        }
        if (!Objects.equals(numberOfPages, other.getNumberOfPages())) {
            return false;
        }
        return Objects.equals(edition, other.getEdition());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof PublicationWithPublisher;
    }

    @Override
    public String toString(final String data) {

        return (String.format("%s{ "
                              + "publisher = %s, "
                              + "isbn10 = \"%s\", "
                              + "isbn13 = \"%s\", "
                              + "volume = %d, "
                              + "numberOfVolumes = %d, "
                              + "numberOfPages = %d, "
                              + "edition = %s%s"
                              + "}",
                              super.toString(),
                              Objects.toString(publisher),
                              isbn10,
                              isbn13,
                              volume,
                              numberOfVolumes,
                              numberOfPages,
                              Objects.toString(edition),
                              data));
    }

}
