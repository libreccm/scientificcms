/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.contenttypes.sciproject;

import org.librecms.assets.ContactableEntity;
import org.librecms.assets.Organization;
import org.librecms.assets.Person;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class SciProjectMananger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SciProjectRepository sciProjectRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public void addContact(final ContactableEntity contactable,
                           final SciProject toProject,
                           final String withType) {

        Objects.requireNonNull(contactable,
                               "Can't add null as Contact to a SciProject.");
        Objects.requireNonNull(toProject,
                               "Can't add a Contact to a project null.");

        final Contact contact = new Contact();
        contact.setContactable(contactable);
        contact.setProject(toProject);
        contact.setContactType(withType);
        contact.setOrder(toProject.getContacts().size());

        toProject.addContact(contact);

        sciProjectRepository.save(toProject);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void removeContact(final ContactableEntity contactable,
                              final SciProject fromProject) {

        Objects.requireNonNull(
            contactable,
            "Can't remove null as Contact from a SciProject."
        );
        Objects.requireNonNull(fromProject,
                               "Can't remove a Contact from project null.");

        final Optional<Contact> result = fromProject
            .getContacts()
            .stream()
            .filter(contact -> filterContact(contact, contactable, fromProject))
            .findFirst();

        if (result.isPresent()) {
            final Contact remove = result.get();
            fromProject.removeContact(remove);
            sciProjectRepository.save(fromProject);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void addMember(final Person person,
                          final SciProject toProject,
                          final String withRole,
                          final MembershipStatus withStatus) {

        Objects.requireNonNull(
            person,
            "Can't add null as a member null to a SciProject."
        );
        Objects.requireNonNull(
            toProject,
            "Can't a member to a SciProject null."
        );

        final Membership membership = new Membership();
        membership.setProject(toProject);
        membership.setMember(person);
        membership.setRole(withRole);
        membership.setStatus(withStatus);

        toProject.addMember(membership);

        sciProjectRepository.save(toProject);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void removeMember(final Person person,
                             final SciProject fromProject) {

        Objects.requireNonNull(
            person,
            "Can't remove null as a member null from a SciProject."
        );
        Objects.requireNonNull(
            fromProject,
            "Can't a member from a SciProject null."
        );

        final Optional<Membership> result = fromProject
            .getMembers()
            .stream()
            .filter(membership -> filterMembership(membership,
                                                   person,
                                                   fromProject))
            .findFirst();

        if (result.isPresent()) {
            final Membership remove = result.get();
            fromProject.removeMembership(remove);
            sciProjectRepository.save(fromProject);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void addSponsor(final Organization sponsor,
                           final SciProject toProject,
                           final String withFundingCode) {

        Objects.requireNonNull(sponsor, "Can't add null as sponsor.");
        Objects.requireNonNull(toProject,
                               "Can't add a sponsor to project null.");

        final Sponsoring sponsoring = new Sponsoring();
        sponsoring.setFundingCode(withFundingCode);
        sponsoring.setOrder(toProject.getSponsoring().size());
        sponsoring.setProject(toProject);
        sponsoring.setSponsor(sponsor);

        toProject.addSponsor(sponsoring);

        sciProjectRepository.save(toProject);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void removeSponsor(final Organization sponsor,
                              final SciProject fromProject) {

        Objects.requireNonNull(sponsor, "Can't remove null as sponsor.");
        Objects.requireNonNull(fromProject,
                               "Can't remove a sponsor from project null.");

        final Optional<Sponsoring> result = fromProject
            .getSponsoring()
            .stream()
            .filter(sponsoring -> sponsoring.getSponsor().equals(sponsor))
            .findFirst();

        if (result.isPresent()) {
            final Sponsoring sponsoring = result.get();
            fromProject.removeSponsor(sponsoring);
            sciProjectRepository.save(fromProject);
        }
    }

    private boolean filterContact(final Contact contact,
                                  final ContactableEntity byContactable,
                                  final SciProject byProject) {

        return contact.getContactable().equals(byContactable)
                   && contact.getProject().equals(byProject);
    }

    private boolean filterMembership(final Membership membership,
                                     final Person byPerson,
                                     final SciProject byProject) {

        return membership.getMember().equals(byPerson)
                   && membership.getProject().equals(byProject);

    }

}
