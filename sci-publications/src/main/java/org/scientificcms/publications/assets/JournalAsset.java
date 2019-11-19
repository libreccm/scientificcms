/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.scientificcms.publications.assets.ui.JournalForm;

import org.hibernate.envers.Audited;
import org.librecms.assets.AssetType;
import org.librecms.contentsection.Asset;
import org.scientificcms.publications.Journal;
import org.scientificcms.publications.SciPublicationsConstants;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 * Asset for storing informations about a journal.
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "JOURNAL_ASSETS", schema = DB_SCHEMA)
@Audited
@AssetType(
    assetForm = JournalForm.class,
    labelBundle = SciPublicationsConstants.BUNDLE,
    labelKey = "journal.label",
    descriptionBundle = SciPublicationsConstants.BUNDLE,
    descriptionKey = "journal.description"
)
public class JournalAsset extends Asset {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.DETACH, 
                         CascadeType.MERGE, 
                         CascadeType.PERSIST, 
                         CascadeType.REFRESH
    })
    @JoinColumn(name = "JOURNAL_ID")
    private Journal journal;

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(final Journal journal) {
        this.journal = journal;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 59 * hash + Objects.hashCode(journal);
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
        if (!(obj instanceof JournalAsset)) {
            return false;
        }
        final JournalAsset other = (JournalAsset) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(journal, other.getJournal());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof JournalAsset;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format(", journal = %s%s",
                                            Objects.toString(journal),
                                            data));
    }

}
