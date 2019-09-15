/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "VOLUMES_IN_SERIES", schema = DB_SCHEMA)
public class VolumeInSeries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "VOLUME_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long volumeId;

    @Column(name = "UUID", unique = true, nullable = false)
    private String uuid;

    @Column(name = "VOLUME_OF_SERIES")
    private String volumeOfSeries;

    @ManyToOne
    @JoinColumn(name = "PUBLICATION_ID")
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "SERIES_ID")
    private Series series;

    public long getVolumeId() {
        return volumeId;
    }

    protected void setVolumeId(long volumeId) {
        this.volumeId = volumeId;
    }

    public String getUuid() {
        return uuid;
    }

    protected void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getVolumeOfSeries() {
        return volumeOfSeries;
    }

    public void setVolumeOfSeries(final String volumeOfSeries) {
        this.volumeOfSeries = volumeOfSeries;
    }

    public Publication getPublication() {
        return publication;
    }

    protected void setPublication(final Publication publication) {
        this.publication = publication;
    }

    public Series getSeries() {
        return series;
    }

    protected void setSeries(final Series series) {
        this.series = series;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (volumeId ^ (volumeId >>> 32));
        hash = 29 * hash + Objects.hashCode(uuid);
        hash = 29 * hash + Objects.hashCode(volumeOfSeries);
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
        if (!(obj instanceof VolumeInSeries)) {
            return false;
        }
        final VolumeInSeries other = (VolumeInSeries) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (volumeId != other.getVolumeId()) {
            return false;
        }
        if (!Objects.equals(uuid, other.getUuid())) {
            return false;
        }
        return Objects.equals(volumeOfSeries, other.getVolumeOfSeries());
    }

    public boolean canEqual(final Object obj) {
        return obj instanceof VolumeInSeries;
    }

    @Override
    public final String toString() {
        return toString("");
    }

    public String toString(final String data) {
        return String.format(
            "%s{ "
                + "volumeId = %d, "
                + "uuid = %s, "
                + "volumeInSeries = \"%s\"%s"
                + " }",
            super.toString(),
            volumeId,
            uuid,
            volumeOfSeries,
            data
        );
    }

}
