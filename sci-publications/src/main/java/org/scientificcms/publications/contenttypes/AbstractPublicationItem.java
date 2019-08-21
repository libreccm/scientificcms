/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.librecms.contentsection.ContentItem;
import org.scientificcms.publications.Publication;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 * @param <T>
 */
@Entity
@Table(name = "PUBLICATION_ITEMS", schema = DB_SCHEMA)
public abstract class AbstractPublicationItem<T extends Publication> 
    extends ContentItem { 
//extends Publication {

    private static final long serialVersionUID = 1L;
    
    public abstract T getPublication();
    
    protected abstract void setPublication(T publication);
//
//    @Embedded
//    private Publication basicProperties;
//
//    public AbstractPublicationItem() {
//        
//        super();
//        
//        basicProperties = new Publication();
//    }
//    
//    public Publication getBasicProperties() {
//        return basicProperties;
//    }
//
//    protected void setBasicProperties(
//        final Publication basicProperties) {
//
//        this.basicProperties = basicProperties;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = super.hashCode();
//        hash = 59 * hash + Objects.hashCode(basicProperties);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (!super.equals(obj)) {
//            return false;
//        }
//        if (!(obj instanceof AbstractPublicationItem)) {
//            return false;
//        }
//        final AbstractPublicationItem other = (AbstractPublicationItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(basicProperties, other.getBasicProperties());
//    }
//
    @Override
    public boolean canEqual(final Object obj) {

        return obj instanceof AbstractPublicationItem;
    }
//
//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format("basicProperties = %s%s",
//                                            Objects.toString(basicProperties),
//                                            data));
//    }

}
