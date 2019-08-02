/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.contenttypes.sciproject;

import org.hibernate.envers.Audited;
import org.libreccm.core.CcmObjects;
import org.librecms.assets.ContactableEntity;

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
@Table(name = "PROJECT_CONTACTS", schema = DB_SCHEMA)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CONTACT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long contactId;

    @Column(name = "CONTACT_TYPE", length = 255, nullable = true)
    private String contactType;

    @Column(name = "CONTACT_ORDER")
    private long order;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private SciProject project;

    @ManyToOne
    @JoinColumn(name = "CONTACTABLE_ID")
    private ContactableEntity contactable;

    public long getContactId() {
        return contactId;
    }

    public void setContactId(final long contactId) {
        this.contactId = contactId;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(final String contactType) {
        this.contactType = contactType;
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

    public ContactableEntity getContactable() {
        return contactable;
    }

    protected void setContactable(final ContactableEntity contactable) {
        this.contactable = contactable;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (int) (contactId ^ (contactId >>> 32));
        hash = 41 * hash + Objects.hashCode(contactType);
        hash = 41 * hash + (int) (order ^ (order >>> 32));
        hash = 41 * hash + CcmObjects.hashCodeUsingUuid(project);
        hash = 41 * hash + CcmObjects.hashCodeUsingUuid(contactable);
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
        if (!(obj instanceof Contact)) {
            return false;
        }
        final Contact other = (Contact) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (contactId != other.getContactId()) {
            return false;
        }
        if (!Objects.equals(contactType, other.getContactType())) {
            return false;
        }
        if (!CcmObjects.equalsUsingUuid(project, other.getProject())) {
            return false;
        }
        if (!CcmObjects.equalsUsingUuid(contactable, other.getContactable())) {
            return false;
        }
        return order == other.getOrder();
    }

    public boolean canEqual(final Object obj) {

        return obj instanceof Contact;
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

        final String contactableStr;
        if (contactable == null) {
            contactableStr = "";
        } else {
            contactableStr = String.format("objectId = %d,"
                                               + "uuid = \"%s\", "
                                               + "name = \"%s\"",
                                           contactable.getObjectId(),
                                           contactable.getUuid(),
                                           contactable.getDisplayName());
        }

        return String.format("%s{ "
                                 + "contactId = %d, "
                                 + "contactType = \"%s\", "
                                 + "order = %d, "
                                 + "project = { %s } "
                                 + "contactableUuid = %s%s"
                                 + " }",
                             super.toString(),
                             contactId,
                             contactType,
                             order,
                             projectStr,
                             contactableStr,
                             data
        );
    }

}
