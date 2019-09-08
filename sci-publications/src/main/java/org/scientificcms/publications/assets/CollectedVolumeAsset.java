/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.hibernate.envers.Audited;
import org.librecms.contentsection.Asset;
import org.scientificcms.publications.CollectedVolume;

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
@Table(name = "COLLECTED_VOLUMES", schema = DB_SCHEMA)
@Audited
public class CollectedVolumeAsset extends Asset {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.DETACH, 
                         CascadeType.MERGE, 
                         CascadeType.PERSIST, 
                         CascadeType.REFRESH
    })
    @JoinColumn(name = "COLLECTED_VOLUME_ID")
    private CollectedVolume collectedVolume;

    public CollectedVolume getCollectedVolume() {
        return collectedVolume;
    }

    protected void setCollectedVolume(final CollectedVolume collectedVolume) {
        this.collectedVolume = collectedVolume;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 29 * hash + Objects.hashCode(collectedVolume);
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
        if (!(obj instanceof CollectedVolumeAsset)) {
            return false;
        }
        final CollectedVolumeAsset other = (CollectedVolumeAsset) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(this.collectedVolume, other.getCollectedVolume());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof CollectedVolumeAsset;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format("collectedVolume = %s%s",
                                            Objects.toString(collectedVolume),
                                            data));
    }

}
