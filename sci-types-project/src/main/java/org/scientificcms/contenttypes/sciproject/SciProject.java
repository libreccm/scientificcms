/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.contenttypes.sciproject;

import com.arsdigita.cms.contenttypes.ui.SciProjectPropertiesStep;
import com.arsdigita.cms.ui.authoring.PageCreateForm;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;
import org.libreccm.l10n.LocalizedString;
import org.librecms.contentsection.ContentItem;
import org.librecms.contenttypes.AuthoringKit;
import org.librecms.contenttypes.AuthoringStep;
import org.librecms.contenttypes.ContentTypeDescription;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static org.scientificcms.contenttypes.sciproject.SciProjectConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Audited
@Table(name = "PROJECTS", schema = DB_SCHEMA)
@ContentTypeDescription(
    labelBundle = "org.scientificcms.contenttypes.SciProject",
    descriptionBundle = "org.scientificcms.contenttypes.SciProject"
)
@AuthoringKit(
    createComponent = PageCreateForm.class,
    steps = {
        @AuthoringStep(
            component = SciProjectPropertiesStep.class,
            labelBundle = SciProjectConstants.SCI_PROJECT_BUNDLE,
            labelKey = "cms.contenttypes.shared.basic_properties.title",
            descriptionBundle = SciProjectConstants.SCI_PROJECT_BUNDLE,
            descriptionKey = "cms.contenttypes.shared.basic_properties"
                                 + ".description",
            order = 1)
    })
@NamedQueries({
    @NamedQuery(
        name = "SciProject.findWhereBeginIsBefore",
        query = "SELECT p FROM SciProject p WHERE p.begin < :date"
    ),
    @NamedQuery(
        name = "SciProject.findWhereBeginIs",
        query = "SELECT p FROM SciProject p WHERE p.begin = :date"
    ),
    @NamedQuery(
        name = "SciProject.findWhereBeginIsAfter",
        query = "SELECT p FROM SciProject p WHERE p.begin > :date"
    ),
    @NamedQuery(
        name = "SciProject.findWhereEndIsBefore",
        query = "SELECT p FROM SciProject p WHERE p.end < :date"
    ),
    @NamedQuery(
        name = "SciProject.findWhereEndIs",
        query = "SELECT p FROM SciProject p WHERE p.end = :date"
    ),
    @NamedQuery(
        name = "SciProject.findWhereEndIsAfter",
        query = "SELECT p FROM SciProject p WHERE p.end > :date"
    ),
    @NamedQuery(
        name = "SciProject.findByMember",
        query = "SELECT p FROM SciProject p JOIN Membership m "
                    + "WHERE m.member = :member"
    )
})
public class SciProject extends ContentItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "PROJECT_BEGIN")
    private LocalDate begin;

    @Column(name = "PROJECT_END")
    private LocalDate end;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PROJECT_SHORT_DESCS",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "OBJECT_ID")
                               })
    )
    private LocalizedString shortDescription;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PROJECT_DESCS",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "OBJECT_ID")
                               })
    )
    private LocalizedString projectDescription;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PROJECT_FUNDING",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "OBJECT_ID")
                               })
    )
    private LocalizedString funding;

    @Embedded
    @AssociationOverride(
        name = "values",
        joinTable = @JoinTable(name = "PROJECT_FUNDING_VOLUME",
                               schema = DB_SCHEMA,
                               joinColumns = {
                                   @JoinColumn(name = "OBJECT_ID")
                               })
    )
    private LocalizedString fundingVolume;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Contact> contacts;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Membership> members;

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(final LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(final LocalDate end) {
        this.end = end;
    }

    public LocalizedString getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(final LocalizedString shortDescription) {
        this.shortDescription = shortDescription;
    }

    public LocalizedString getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(final LocalizedString projectDescription) {
        this.projectDescription = projectDescription;
    }

    public LocalizedString getFunding() {
        return funding;
    }

    public void setFunding(final LocalizedString funding) {
        this.funding = funding;
    }

    public LocalizedString getFundingVolume() {
        return fundingVolume;
    }

    public void setFundingVolume(final LocalizedString fundingVolume) {
        this.fundingVolume = fundingVolume;
    }

    public List<Contact> getContacts() {
        return Collections.unmodifiableList(contacts);
    }

    protected void addContact(final Contact contact) {
        contacts.add(contact);
    }

    protected void removeContact(final Contact contact) {
        contacts.remove(contact);
    }

    protected void setContacts(final List<Contact> contacts) {
        this.contacts = new ArrayList<>(contacts);
    }

    public List<Membership> getMembers() {
        return Collections.unmodifiableList(members);
    }

    protected void addMember(final Membership membership) {
        members.add(membership);
    }

    protected void removeMembership(final Membership membership) {
        members.remove(membership);
    }

    protected void setMembers(final List<Membership> members) {
        this.members = new ArrayList<>(members);
    }

}
