/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;
import org.libreccm.l10n.LocalizedString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "SERIES", schema = DB_SCHEMA)
@Audited
@NamedQueries({
    @NamedQuery(
        name = "Series.findByUuid",
        query = "SELECT s FROM Series s WHERE s.uuid = :uuid"
    ),
    @NamedQuery(
        name = "Series.findByTitle",
        query = "SELECT DISTINCT s FROM Series s JOIN s.title.values t WHERE lower(t) LIKE CONCAT ('%', :title, '%')"
    )
})
public class Series implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SERIES_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long seriesId;

    @Column(name = "UUID", unique = true, nullable = false)
    private String uuid;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(
            name = "SERIES_TITLES",
            schema = DB_SCHEMA,
            joinColumns = {
                @JoinColumn(name = "SERIES_ID")
            }
        )
    )
    private LocalizedString title;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(
            name = "SERIES_DESCRIPTIONS",
            schema = DB_SCHEMA,
            joinColumns = {
                @JoinColumn(name = "SERIES_ID")
            }
        )
    )
    private LocalizedString description;

    @OneToMany(mappedBy = "series")
    private List<VolumeInSeries> volumes;

    public Series() {
        title = new LocalizedString();
        description = new LocalizedString();
        volumes = new ArrayList<>();
    }

    public long getSeriesId() {
        return seriesId;
    }

    protected void setSeriesId(final long seriesId) {
        this.seriesId = seriesId;
    }

    public String getUuid() {
        return uuid;
    }

    protected void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public LocalizedString getTitle() {
        return title;
    }

    public void setTitle(final LocalizedString title) {
        this.title = title;
    }

    public LocalizedString getDescription() {
        return description;
    }

    public void setDescription(final LocalizedString description) {
        this.description = description;
    }

    public List<VolumeInSeries> getVolumes() {
        if (volumes == null) {
            return null;
        } else {
            return Collections.unmodifiableList(volumes);
        }
    }

    protected void addVolume(final VolumeInSeries volume) {
        volumes.add(volume);
    }

    protected void removeVolume(final VolumeInSeries volume) {
        volumes.remove(volume);
    }

    protected void setVolumes(final List<VolumeInSeries> volumes) {
        this.volumes = new ArrayList<>(volumes);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (seriesId ^ (seriesId >>> 32));
        hash = 71 * hash + Objects.hashCode(uuid);
        hash = 71 * hash + Objects.hashCode(title);
        hash = 71 * hash + Objects.hashCode(description);
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
        if (!(obj instanceof Series)) {
            return false;
        }
        final Series other = (Series) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (seriesId != other.getSeriesId()) {
            return false;
        }
        if (!Objects.equals(uuid, other.getUuid())) {
            return false;
        }
        if (!Objects.equals(title, other.getTitle())) {
            return false;
        }
        return Objects.equals(description, other.getDescription());
    }

    public boolean canEqual(final Object obj) {
        return obj instanceof Series;
    }

    @Override
    public final String toString() {
        return toString("");
    }

    public String toString(final String data) {

        return String.format(
            "%s{ "
                + "seriesId = %d, "
                + "uuid = %s, "
                + "title = %s, "
                + "description = %s, "
                + "volumes = %s%s"
                + " }",
            super.toString(),
            seriesId,
            uuid,
            Objects.toString(title),
            Objects.toString(description),
            Objects.toString(volumes),
            data
        );
    }

}
