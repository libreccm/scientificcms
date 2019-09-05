/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "IN_PROCEEDINGS", schema = DB_SCHEMA)
@Audited
public class InProceedings extends Publication {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROCEEDINGS_ID")
    private Proceedings proceedings;

    private int startPage;

    private int endPage;

    public Proceedings getProceedings() {
        return proceedings;
    }

    protected void setProceedings(final Proceedings proceedings) {
        this.proceedings = proceedings;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(final int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(final int endPage) {
        this.endPage = endPage;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 17 * hash + Objects.hashCode(proceedings);
        hash = 17 * hash + startPage;
        hash = 17 * hash + endPage;
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
        if (!(obj instanceof InProceedings)) {
            return false;
        }
        final InProceedings other = (InProceedings) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (startPage != other.getStartPage()) {
            return false;
        }
        if (endPage != other.getEndPage()) {
            return false;
        }
        return Objects.equals(proceedings, other.getProceedings());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof InProceedings;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format("proceedings = %s, "
                                                + "startPage = %d, "
                                                + "endPage = %d%s",
                                            Objects.toString(proceedings),
                                            startPage,
                                            endPage,
                                            data));
    }

}
