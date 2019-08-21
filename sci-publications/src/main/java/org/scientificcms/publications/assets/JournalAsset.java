/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.hibernate.envers.Audited;
import org.libreccm.l10n.LocalizedString;
import org.librecms.contentsection.Asset;

import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 * Asset for storing informations about a journal.
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "JOURNALS", schema = DB_SCHEMA)
@Audited
public class JournalAsset extends Asset {

//    private static final long serialVersionUID = 1L;
//
//    /**
//     * Year in which the journal was published first.
//     */
//    private Integer firstYear;
//
//    /**
//     * Year in which the journal was published last
//     */
//    private Integer lastYear;
//
//    /**
//     * The ISSN of the journal.
//     */
//    private String issn;
//
//    /**
//     * A short description of the journal.
//     */
//    @Embedded
//    @AssociationOverride(
//        name = "values",
//        joinTable = @JoinTable(name = "JOURNAL_DESCRIPTIONS",
//                               schema = DB_SCHEMA,
//                               joinColumns = {
//                                   @JoinColumn(name = "OBJECT_ID")
//                               })
//    )
//    private LocalizedString description;
//
//    /**
//     * The usual symbol/abbrevation used to refer to the journal.
//     */
//    private String symbol;
//
//    public Integer getFirstYear() {
//        return firstYear;
//    }
//
//    public void setFirstYear(final Integer firstYear) {
//        this.firstYear = firstYear;
//    }
//
//    public Integer getLastYear() {
//        return lastYear;
//    }
//
//    public void setLastYear(final Integer lastYear) {
//        this.lastYear = lastYear;
//    }
//
//    public String getIssn() {
//        return issn;
//    }
//
//    public void setIssn(final String issn) {
//        this.issn = issn;
//    }
//
//    public LocalizedString getDescription() {
//        return description;
//    }
//
//    public void setDescription(final LocalizedString description) {
//        this.description = description;
//    }
//
//    public String getSymbol() {
//        return symbol;
//    }
//
//    public void setSymbol(final String symbol) {
//        this.symbol = symbol;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = super.hashCode();
//        hash = 73 * hash + Objects.hashCode(firstYear);
//        hash = 73 * hash + Objects.hashCode(lastYear);
//        hash = 73 * hash + Objects.hashCode(issn);
//        hash = 73 * hash + Objects.hashCode(description);
//        hash = 73 * hash + Objects.hashCode(symbol);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(final Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (!super.equals(obj)) {
//            return false;
//        }
//        if (!(obj instanceof JournalAsset)) {
//            return false;
//        }
//        final JournalAsset other = (JournalAsset) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        if (!Objects.equals(firstYear, other.getFirstYear())) {
//            return false;
//        }
//        if (!Objects.equals(lastYear, other.getLastYear())) {
//            return false;
//        }
//        if (!Objects.equals(issn, other.getIssn())) {
//            return false;
//        }
//        if (!Objects.equals(symbol, other.getSymbol())) {
//            return false;
//        }
//        return Objects.equals(description, other.getDescription());
//    }
//
//    @Override
//    public boolean canEqual(final Object obj) {
//        return obj instanceof JournalAsset;
//    }
//
//    @Override
//    public String toString(final String data) {
//
//        return super.toString(String.format(
//            ", firstYear = %s, "
//                + "lastYear = %s, "
//                + "issn = \"%s\", "
//                + "symbol = \"%s\", "
//                + "description = %s%s",
//            Objects.toString(firstYear),
//            Objects.toString(lastYear),
//            issn,
//            symbol,
//            description,
//            data));
//    }

}
