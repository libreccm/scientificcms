/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.hibernate.envers.Audited;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.PublicationWithPublisher;

import java.util.Objects;

import javax.persistence.CascadeType;
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
@Table(name = "COLLECTED_VOLUME_ITEMS", schema = DB_SCHEMA)
@Audited
public class CollectedVolumeItem
    extends AbstractPublicationWithPublisherItem<CollectedVolume> {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.DETACH, 
                         CascadeType.MERGE, 
                         CascadeType.PERSIST, 
                         CascadeType.REFRESH
    })
    @JoinColumn(name = "COLLECTED_VOLUME_ID")
    private CollectedVolume collectedVolume;

    @Override
    public CollectedVolume getPublication() {
        return collectedVolume;
    }

    @Override
    protected void setPublication(final CollectedVolume collectedVolume) {
        this.collectedVolume = collectedVolume;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 59 * hash + Objects.hashCode(collectedVolume);
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
        if (!(obj instanceof CollectedVolumeItem)) {
            return false;
        }
        final CollectedVolumeItem other = (CollectedVolumeItem) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(collectedVolume, other.getPublication());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof CollectedVolumeItem;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format(", collectedVolume = %s%s",
                                            Objects.toString(collectedVolume),
                                            data));
    }

}
