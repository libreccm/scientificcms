/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;

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
@Table(name = "MONOGRAPHS", schema = DB_SCHEMA)
@Audited
public class Monograph extends PublicationWithPublisher {

    private static final long serialVersionUID = 1L;

    @Column(name = "REVIEWED")
    private Boolean reviewed;

    public Boolean getReviewed() {
        return reviewed;
    }

    public void setReviewed(Boolean reviewed) {
        this.reviewed = reviewed;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 23 * hash + Objects.hashCode(reviewed);
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
        if (!(obj instanceof Monograph)) {
            return false;
        }
        final Monograph other = (Monograph) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(reviewed, other.getReviewed());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof Monograph;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format(", reviewed = %b%s",
                                            reviewed,
                                            data));
    }

}
