/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.hibernate.envers.Audited;
import org.librecms.assets.AssetType;
import org.librecms.contentsection.Asset;
import org.scientificcms.publications.Publisher;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.assets.ui.PublisherForm;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 * An asset for storing the informations about a publisher required to create
 * correct bibliographic references.
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "PUBLISHER_ASSETS", schema = DB_SCHEMA)
@Audited
@AssetType(
    assetForm = PublisherForm.class,
    labelBundle = SciPublicationsConstants.BUNDLE,
    labelKey = "publisher.label",
    descriptionBundle = SciPublicationsConstants.BUNDLE,
    descriptionKey = "publisher.description"
)
public class PublisherAsset extends Asset {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.DETACH, 
                         CascadeType.MERGE, 
                         CascadeType.PERSIST, 
                         CascadeType.REFRESH
    })
    @JoinColumn(name = "PUBLISHER_ID")
    private Publisher publisher;

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(final Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 97 * hash + Objects.hashCode(publisher);
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
        if (!(obj instanceof PublisherAsset)) {
            return false;
        }
        final PublisherAsset other = (PublisherAsset) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(this.publisher, other.getPublisher());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof PublisherAsset;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format(", publisher = %s%s",
                                            Objects.toString(publisher),
                                            data));
    }

}
