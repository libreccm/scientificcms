/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.librecms.contentsection.Asset;
import org.scientificcms.publications.BasicPublicationProperties;
import org.scientificcms.publications.PublicationWithPublisherProperties;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "PROCEEDINGS", schema = DB_SCHEMA)
public class Proceedings extends Asset {

    private static final long serialVersionUID = 1L;

    private BasicPublicationProperties basicProperties;

    private PublicationWithPublisherProperties withPublisherProperties;

    @Column(name = "NAME_OF_CONFERENCE", length = 1024)
    private String nameOfConference;

    @Column(name = "PLACE_OF_CONFERENCE", length = 1024)
    private String placeOfConference;

    @Column(name = "START_DATE_OF_CONFERENCE")
    private LocalDate startDateOfConference;

    @Column(name = "END_DATE_OF_CONFERENCE")
    private LocalDate endDateOfConference;

    public Proceedings() {

        super();

        basicProperties = new BasicPublicationProperties();
        withPublisherProperties = new PublicationWithPublisherProperties();
    }

    public BasicPublicationProperties getBasicProperties() {
        return basicProperties;
    }

    protected void setBasicProperties(
        final BasicPublicationProperties basicProperties) {
        this.basicProperties = basicProperties;
    }

    public PublicationWithPublisherProperties getWithPublisherProperties() {
        return withPublisherProperties;
    }

    protected void setWithPublisherProperties(
        final PublicationWithPublisherProperties withPublisherProperties) {
        this.withPublisherProperties = withPublisherProperties;
    }

    public String getNameOfConference() {
        return nameOfConference;
    }

    public void setNameOfConference(final String nameOfConference) {
        this.nameOfConference = nameOfConference;
    }

    public String getPlaceOfConference() {
        return placeOfConference;
    }

    public void setPlaceOfConference(final String placeOfConference) {
        this.placeOfConference = placeOfConference;
    }

    public LocalDate getStartDateOfConference() {
        return startDateOfConference;
    }

    public void setStartDateOfConference(final LocalDate startDateOfConference) {
        this.startDateOfConference = startDateOfConference;
    }

    public LocalDate getEndDateOfConference() {
        return endDateOfConference;
    }

    public void setEndDateOfConference(final LocalDate endDateOfConference) {
        this.endDateOfConference = endDateOfConference;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 19 * hash + Objects.hashCode(basicProperties);
        hash = 19 * hash + Objects.hashCode(withPublisherProperties);
        hash = 19 * hash + Objects.hashCode(nameOfConference);
        hash = 19 * hash + Objects.hashCode(placeOfConference);
        hash = 19 * hash + Objects.hashCode(startDateOfConference);
        hash = 19 * hash + Objects.hashCode(endDateOfConference);
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
        if (!(obj instanceof Proceedings)) {
            return false;
        }
        final Proceedings other = (Proceedings) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(basicProperties, other.getBasicProperties())) {
            return false;
        }
        if (!Objects.equals(withPublisherProperties,
                            other.getWithPublisherProperties())) {
            return false;
        }
        if (!Objects.equals(nameOfConference, other.getNameOfConference())) {
            return false;
        }
        if (!Objects.equals(placeOfConference, other.getPlaceOfConference())) {
            return false;
        }
        if (!Objects.equals(startDateOfConference,
                            other.getStartDateOfConference())) {
            return false;
        }
        return Objects.equals(endDateOfConference,
                              other.getEndDateOfConference());
    }

    @Override
    public boolean canEqual(final Object obj) {

        return obj instanceof Proceedings;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format(
            "basicProperties = %s, "
                + "withPublisherProperties = %s, "
                + "nameOfConference = \"%s\", "
                + "placeOfConference = \"%s\", "
                + "startDateOfConference = \"%s\", "
                + "endDateOfConference = \"%s\"%s",
            Objects.toString(basicProperties),
            Objects.toString(withPublisherProperties),
            nameOfConference,
            placeOfConference,
            Objects.toString(startDateOfConference),
            Objects.toString(endDateOfConference),
            data));
    }

}
