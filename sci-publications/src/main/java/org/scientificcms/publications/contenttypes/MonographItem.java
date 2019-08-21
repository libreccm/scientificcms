/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.scientificcms.publications.Monograph;

import java.util.Objects;

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
@Table(name = "MONOGRAPH_ITEMS", schema = DB_SCHEMA)
public class MonographItem extends AbstractPublicationWithPublisherItem<Monograph> {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "PUBLICATION_ID")
    private Monograph publication;

    @Override
    public Monograph getPublication() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 

  
    @Override
    protected void setPublication(final Monograph publication) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 29 * hash + Objects.hashCode(publication);
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
        if (!(obj instanceof MonographItem)) {
            return false;
        }
        final MonographItem other = (MonographItem) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(publication, other.getPublication());
    }

       @Override
    public boolean canEqual(Object obj) {
     return obj instanceof MonographItem;
    }
    
    
    @Override
    public String toString(final String data) {
        return super.toString(String.format(", publication = %s%s", 
                                            Objects.toString(publication),
                                            data));
    }


}
