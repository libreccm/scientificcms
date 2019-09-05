/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.annotations.CollectionId;
import org.hibernate.envers.Audited;

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
@Table(name = "TALKS", schema = DB_SCHEMA)
@Audited
public class Talk extends Publication {

    private static final long serialVersionUID = 1L;

    @Column(name = "EVENT", length = 2048)
    private String event;

    @Column(name = "DATE_OF_TALK")
    private LocalDate dateOfTalk;

    @Column(name = "PLACE", length = 2048)
    private String place;

    public String getEvent() {
        return event;
    }

    public void setEvent(final String event) {
        this.event = event;
    }

    public LocalDate getDateOfTalk() {
        return dateOfTalk;
    }

    public void setDateOfTalk(final LocalDate dateOfTalk) {
        this.dateOfTalk = dateOfTalk;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(final String place) {
        this.place = place;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + Objects.hashCode(event);
        hash = 83 * hash + Objects.hashCode(dateOfTalk);
        hash = 83 * hash + Objects.hashCode(place);
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
        if (!(obj instanceof Talk)) {
            return false;
        }
        final Talk other = (Talk) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(event, other.getEvent())) {
            return false;
        }
        if (!Objects.equals(place, other.getPlace())) {
            return false;
        }
        return Objects.equals(dateOfTalk, other.getDateOfTalk());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof Talk;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format("event = \"%s\", "
                                                + "place = \"%s\", "
                                                + "dateOfTalk = %s%s",
                                            event,
                                            place,
                                            Objects.toString(dateOfTalk),
                                            data));
    }

}
