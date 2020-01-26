/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;
import org.librecms.assets.Organization;

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
@Table(name = "UNPUBLISHED", schema = DB_SCHEMA)
@Audited
public class UnPublished extends Publication {

    private static final long serialVersionUID = 1L;

    @Column(name = "PLACE", length = 2048)
    private String place;

    @Column(name = "NUMBER", length = 512)
    private String number;

    @Column(name = "NUMBER_OF_PAGES")
    private int numberOfPages;

    @OneToOne
    @JoinColumn(name = "ORGANIZATION")
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

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(final int numberOfPages) {
        this.numberOfPages = numberOfPages;
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
        hash = 37 * hash + Objects.hashCode(place);
        hash = 37 * hash + Objects.hashCode(number);
        hash = 37 * hash + numberOfPages;
        hash = 37 * hash + Objects.hashCode(organization);
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
        if (!(obj instanceof UnPublished)) {
            return false;
        }
        final UnPublished other = (UnPublished) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (numberOfPages != other.getNumberOfPages()) {
            return false;
        }
        if (!Objects.equals(place, other.getPlace())) {
            return false;
        }
        if (!Objects.equals(number, other.getNumber())) {
            return false;
        }
        return Objects.equals(organization, other.getOrganization());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof UnPublished;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format("place = \"%s\", "
                                                + "number = \"%s\", "
                                                + "numberOfPages = %d, "
                                                + "organization = %s%s",
                                            place,
                                            number,
                                            numberOfPages,
                                            Objects.toString(organization),
                                            data));
    }

}
