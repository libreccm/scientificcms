/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.librecms.contentsection.ContentItem;
import org.scientificcms.publications.BasicPublicationProperties;

import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "PUBLICATION_ITEMS", schema = DB_SCHEMA)
public class PublicationItem extends Publication {

    private static final long serialVersionUID = 1L;

    @Embedded
    private BasicPublicationProperties basicProperties;

    public PublicationItem() {
        
        super();
        
        basicProperties = new BasicPublicationProperties();
    }
    
    public BasicPublicationProperties getBasicProperties() {
        return basicProperties;
    }

    protected void setBasicProperties(
        final BasicPublicationProperties basicProperties) {

        this.basicProperties = basicProperties;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 59 * hash + Objects.hashCode(basicProperties);
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
        if (!(obj instanceof PublicationItem)) {
            return false;
        }
        final PublicationItem other = (PublicationItem) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(basicProperties, other.getBasicProperties());
    }

    @Override
    public boolean canEqual(final Object obj) {

        return obj instanceof PublicationItem;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format("basicProperties = %s%s",
                                            Objects.toString(basicProperties),
                                            data));
    }

}
