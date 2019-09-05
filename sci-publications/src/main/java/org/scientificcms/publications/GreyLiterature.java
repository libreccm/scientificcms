/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "GREY_LITERATURE", schema = DB_SCHEMA)
@Audited
public class GreyLiterature extends UnPublished {

    private static final long serialVersionUID = 1L;

    @Column(name = "START_PAGE")
    private int startPage;

    @Column(name = "END_PAGE")
    private int endPage;

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
        hash = 97 * hash + startPage;
        hash = 97 * hash + endPage;
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
        if (!(obj instanceof GreyLiterature)) {
            return false;
        }
        final GreyLiterature other = (GreyLiterature) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (startPage != other.getStartPage()) {
            return false;
        }
        return endPage == other.getEndPage();
    }

    @Override
    public boolean canEqual(final Object obj) {

        return obj instanceof GreyLiterature;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format("startPage = %d,"
                                                + "endPage = %d%s",
                                            startPage,
                                            endPage,
                                            data));
    }

}
