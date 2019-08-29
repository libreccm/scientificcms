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
@Table(name = "EXPERTISES", schema = DB_SCHEMA)
@Audited
public class Expertise extends Publication {

    private static final long serialVersionUID = 1L;

    @Column(name = "PLACE", length = 512)
    private String place;

    @Column(name = "NUMBER_OF_PAGES")
    private Integer numberOfPages;

    @OneToOne
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organization organization;

    @OneToOne
    @JoinColumn(name = "ORDERER_ID")
    private Organization orderer;

    public String getPlace() {
        return place;
    }

    public void setPlace(final String place) {
        this.place = place;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(final Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(final Organization organization) {
        this.organization = organization;
    }

    public Organization getOrderer() {
        return orderer;
    }

    public void setOrderer(final Organization orderer) {
        this.orderer = orderer;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 17 * hash + Objects.hashCode(place);
        hash = 17 * hash + Objects.hashCode(numberOfPages);
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
        if (!(obj instanceof Expertise)) {
            return false;
        }
        final Expertise other = (Expertise) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(numberOfPages, other.getNumberOfPages())) {
            return false;
        }
        return Objects.equals(place, other.getPlace());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof Expertise;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format(", place = \"%s\", "
                                                + "numberOfPages = %d, "
                                                + "organization = %s, "
                                                + "orderer = %s%s",
                                            place,
                                            Objects.toString(numberOfPages),
                                            Objects.toString(organization),
                                            Objects.toString(orderer),
                                            data));
    }

}
