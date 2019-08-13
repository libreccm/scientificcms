/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.contenttypes.sciproject;

import org.hibernate.envers.Audited;
import org.libreccm.core.CcmObjects;
import org.librecms.assets.Organization;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.scientificcms.contenttypes.sciproject.SciProjectConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Audited
@Table(name = "SPONSORING", schema = DB_SCHEMA)
public class Sponsoring implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SPONSORING_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sponsoringId;

    @Column(name = "FUNDING_CODE", length = 512, nullable = true)
    private String fundingCode;

    @Column(name = "SPONSOR_ORDER")
    private long order;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private SciProject project;

    @ManyToOne
    @JoinColumn(name = "SPONSOR_ID")
    private Organization sponsor;

    public long getSponsoringId() {
        return sponsoringId;
    }

    protected void setSponsoringId(final long sponsoringId) {
        this.sponsoringId = sponsoringId;
    }

    public String getFundingCode() {
        return fundingCode;
    }

    public void setFundingCode(final String fundingCode) {
        this.fundingCode = fundingCode;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(final long order) {
        this.order = order;
    }

    public SciProject getProject() {
        return project;
    }

    protected void setProject(final SciProject project) {
        this.project = project;
    }

    public Organization getSponsor() {
        return sponsor;
    }

    protected void setSponsor(final Organization sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public int hashCode() {

        int hash = 3;
        hash = 53 * hash
                   + (int) (sponsoringId ^ (sponsoringId >>> 32));
        hash = 53 * hash + Objects.hashCode(fundingCode);
        hash = 53 * hash + (int) (order ^ (order >>> 32));
        hash = 53 * hash + CcmObjects.hashCodeUsingUuid(project);
        hash = 53 * hash + CcmObjects.hashCodeUsingUuid(sponsor);
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
        if (!(obj instanceof Sponsoring)) {
            return false;
        }
        final Sponsoring other = (Sponsoring) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (sponsoringId != other.getSponsoringId()) {
            return false;
        }
        if (!Objects.equals(fundingCode, other.getFundingCode())) {
            return false;
        }
        if (!CcmObjects.equalsUsingUuid(project, other.getProject())) {
            return false;
        }
        if (!CcmObjects.equalsUsingUuid(sponsor, other.getSponsor())) {
            return false;
        }
        return order == other.getOrder();
    }
    

    public boolean canEqual(final Object obj) {

        return obj instanceof Sponsoring;
    }
    
    @Override
    public final String toString() {
        
        return toString("");
    }

    public String toString(final String data) {

        final String projectStr;
        if (project == null) {
            projectStr = "";
        } else {
            projectStr = String.format("objectId = %d,"
                                           + "uuid = \"%s\", "
                                           + "name = \"%s\"",
                                       project.getObjectId(),
                                       project.getUuid(),
                                       project.getDisplayName());
        }

        final String sponsorStr;
        if (sponsor == null) {
            sponsorStr = "";
        } else {
            sponsorStr = String.format("objectId = %d, "
                                           + "uuid = \"%s\", "
                                           + "name = \"%s\"",
                                       sponsor.getObjectId(),
                                       sponsor.getUuid(),
                                       sponsor.getDisplayName());
        }

        return String.format("%s{ "
                                 + "sponsoringId = %d, "
                                 + "fundingCode = \"%s\", "
                                 + "order = %d, "
                                 + "project = { %s }, "
                                 + "sponsor = { %s }%s"
                                 + " }",
                             super.toString(),
                             sponsoringId,
                             fundingCode,
                             order,
                             projectStr,
                             sponsorStr,
                             data);
    }

}
