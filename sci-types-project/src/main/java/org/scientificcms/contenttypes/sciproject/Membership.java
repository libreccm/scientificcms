/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.contenttypes.sciproject;

import org.hibernate.envers.Audited;
import org.libreccm.core.CcmObjects;
import org.librecms.assets.Person;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "PROJECT_MEMBERSHIPS", schema = DB_SCHEMA)
public class Membership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MEMBERSHIP_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long membershipId;

    @Column(name = "MEMBER_ROLE", length = 255, nullable = true)
    private String role;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private MembershipStatus status;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private SciProject project;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Person member;

    public long getMembershipId() {
        return membershipId;
    }

    protected void setMembershipId(final long membershipId) {
        this.membershipId = membershipId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public MembershipStatus getStatus() {
        return status;
    }

    public void setStatus(final MembershipStatus status) {
        this.status = status;
    }

    public SciProject getProject() {
        return project;
    }

    protected void setProject(final SciProject project) {
        this.project = project;
    }

    public Person getMember() {
        return member;
    }

    protected void setMember(final Person member) {
        this.member = member;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash
                   + (int) (membershipId ^ (membershipId >>> 32));
        hash = 37 * hash + Objects.hashCode(role);
        hash = 37 * hash + Objects.hashCode(status);
        hash = 37 * hash + CcmObjects.hashCodeUsingUuid(project);
        hash = 37 * hash + CcmObjects.hashCodeUsingUuid(member);
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
        if (!(obj instanceof Membership)) {
            return false;
        }
        final Membership other = (Membership) obj;
        if (!other.canEqual(this)) {
            return false;
        }

        if (membershipId != other.getMembershipId()) {
            return false;
        }
        if (!Objects.equals(role, other.getRole())) {
            return false;
        }

        if (!CcmObjects.equalsUsingUuid(project, other.getProject())) {
            return false;
        }
        if (!CcmObjects.equalsUsingUuid(member, other.getMember())) {
            return false;
        }
        return status == other.getStatus();
    }

    public boolean canEqual(final Object obj) {

        return obj instanceof Membership;
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

        final String memberStr;
        if (member == null) {
            memberStr = "";
        } else {
            memberStr = String.format("objectId = %d, "
                                          + "uuid = \"%s\", "
                                          + "name = \"%s\", "
                                          + "surname = \"%s\", "
                                          + "givenName = \"%s\", "
                                          + "prefix = \"%s\", "
                                          + "suffix = \"%s\"",
                                      member.getObjectId(),
                                      member.getUuid(),
                                      member.getDisplayName(),
                                      member.getPersonName().getSurname(),
                                      member.getPersonName().getGivenName(),
                                      member.getPersonName().getPrefix(),
                                      member.getPersonName().getSuffix());
        }

        return String.format("%s{ "
                                 + "membershipId = %d, "
                                 + "role = \"%s\", "
                                 + "status = \"%s\","
                                 + "project = { %s }, "
                                 + "member = { %s }%s"
                                 + " }",
                             super.toString(),
                             membershipId,
                             role,
                             Objects.toString(status),
                             projectStr,
                             memberStr,
                             data
        );
    }

}
