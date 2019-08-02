/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.contenttypes.sciproject;

import org.librecms.assets.ContactableEntity;
import org.librecms.assets.Person;

import java.io.Serializable;
import java.util.Objects;

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
    public void addcontact(final ContactableEntity contactable, 
                           final SciProject toProject,
                           final String withType) {
        
        Objects.requireNonNull(contactable, 
                               "Can't null as Contact to a SciProject.");
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
    public void addMember(final Person person,
                          final SciProject toProject,
                          final String withRole,
                          final MembershipStatus withStatus) {
        
        throw new UnsupportedOperationException("ToDo");
    }
    
}
