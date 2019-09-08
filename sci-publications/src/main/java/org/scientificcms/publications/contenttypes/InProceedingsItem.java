/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.hibernate.envers.Audited;
import org.scientificcms.publications.InProceedings;

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
@Table(name = "INPROCEEDINGS_ITEMS", schema = DB_SCHEMA)
@Audited
public class InProceedingsItem 
    extends AbstractPublicationItem<InProceedings> {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.DETACH, 
                         CascadeType.MERGE, 
                         CascadeType.PERSIST, 
                         CascadeType.REFRESH
    })
    @JoinColumn(name = "INPROCEEDINGS_ID")
    private InProceedings inProcedings;

    @Override
    public InProceedings getPublication() {
        return inProcedings;
    }

    @Override
    protected void setPublication(final InProceedings inProceedings) {
        this.inProcedings = inProceedings;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 67 * hash + Objects.hashCode(inProcedings);
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
        if (!(obj instanceof InProceedingsItem)) {
            return false;
        }
        final InProceedingsItem other
                                   = (InProceedingsItem) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(this.inProcedings, other.getPublication());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof InProceedingsItem;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format(", inProceedings = %s%s",
                                            Objects.toString(inProcedings),
                                            data));
    }

}
