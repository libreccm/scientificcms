/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.hibernate.envers.Audited;
import org.librecms.assets.AssetType;
import org.librecms.contentsection.Asset;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.Series;
import org.scientificcms.publications.assets.ui.SeriesForm;

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
@Table(name = "SERIES_ASSETS", schema = DB_SCHEMA)
@Audited
@AssetType(
    assetForm = SeriesForm.class,
    labelBundle = SciPublicationsConstants.BUNDLE,
    labelKey = "journal.label",
    descriptionBundle = SciPublicationsConstants.BUNDLE,
    descriptionKey = "journal.description"
)
public class SeriesAsset extends Asset {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.DETACH,
                         CascadeType.MERGE,
                         CascadeType.PERSIST,
                         CascadeType.REFRESH
    })
    @JoinColumn(name = "SERIES_ID")
    private Series series;

    public Series getSeries() {
        return series;
    }

    public void setSeries(final Series series) {
        this.series = series;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(series);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof SeriesAsset)) {
            return false;
        }
        final SeriesAsset other = (SeriesAsset) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(series, other.getSeries());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof SeriesAsset;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format(", series = %s%s",
                                            Objects.toString(series),
                                            data));
    }

}
