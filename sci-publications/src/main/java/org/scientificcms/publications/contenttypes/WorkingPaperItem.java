/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.hibernate.envers.Audited;
import org.scientificcms.publications.WorkingPaper;

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
@Table(name = "WORKING_PAPER_ITEMS", schema = DB_SCHEMA)
@Audited
public class WorkingPaperItem 
    extends AbstractPublicationItem<WorkingPaper> {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.DETACH, 
                         CascadeType.MERGE, 
                         CascadeType.PERSIST, 
                         CascadeType.REFRESH
    })
    @JoinColumn(name = "EXPERTISE_ID")
    private WorkingPaper workingPaper;

    @Override
    public WorkingPaper getPublication() {
        return workingPaper;
    }

    @Override
    protected void setPublication(final WorkingPaper workingPaper) {
        this.workingPaper = workingPaper;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 67 * hash + Objects.hashCode(workingPaper);
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
        if (!(obj instanceof WorkingPaperItem)) {
            return false;
        }
        final WorkingPaperItem other
                                   = (WorkingPaperItem) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(this.workingPaper, other.getPublication());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof WorkingPaperItem;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format(", workingPaper = %s%s",
                                            Objects.toString(workingPaper),
                                            data));
    }

}
