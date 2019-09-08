/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.hibernate.envers.Audited;
import org.librecms.contentsection.Asset;
import org.scientificcms.publications.Proceedings;

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
@Table(name = "PROCEEDINGS", schema = DB_SCHEMA)
@Audited
public class ProceedingsAsset extends Asset {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.DETACH, 
                         CascadeType.MERGE, 
                         CascadeType.PERSIST, 
                         CascadeType.REFRESH
    })
    @JoinColumn(name = "PROCEEDINGS_ID")
    private Proceedings proceedings;

    public Proceedings getProceedings() {
        return proceedings;
    }

    public void setProceedings(Proceedings proceedings) {
        this.proceedings = proceedings;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 59 * hash + Objects.hashCode(proceedings);
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
        if (!(obj instanceof ProceedingsAsset)) {
            return false;
        }
        final ProceedingsAsset other = (ProceedingsAsset) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(this.proceedings, other.getProceedings());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof ProceedingsAsset;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format(", proceeeings = %s%s",
                                            Objects.toString(proceedings),
                                            data));
    }

}
