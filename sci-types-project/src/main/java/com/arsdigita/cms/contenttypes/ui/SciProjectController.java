/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.libreccm.configuration.ConfigurationManager;
import org.libreccm.l10n.LocalizedString;
import org.librecms.assets.ContactableEntity;
import org.librecms.assets.ContactableEntityRepository;
import org.librecms.assets.Organization;
import org.librecms.contentsection.AssetRepository;
import org.scientificcms.contenttypes.sciproject.Contact;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConfig;
import org.scientificcms.contenttypes.sciproject.SciProjectMananger;
import org.scientificcms.contenttypes.sciproject.SciProjectRepository;
import org.scientificcms.contenttypes.sciproject.Sponsoring;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
class SciProjectController {

    public static final String CONTACT_NAME = "name";

    public static final String CONTACT_TYPE = "contactType";

    public static final String CONTACT_ID = "contactId";
    
    public static final String SPONSOR_ID = "sponsorId";
    
    public static final String SPONSOR_NAME = "name";
    
    public static final String SPONSOR_FUNDING_CODE = "fundingCode";

    @Inject
    private AssetRepository assetRepository;

    @Inject
    private ConfigurationManager confManager;

    @Inject
    private ContactableEntityRepository contactableRepository;

    @Inject
    private SciProjectMananger projectMananger;

    @Inject
    private SciProjectRepository projectRepository;

    public String getContactTypesBundleName() {

        final SciProjectConfig conf = confManager
            .findConfiguration(SciProjectConfig.class);

        return conf.getContactTypesBundleName();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<Map<String, Object>> getContacts(final long forProjectId) {

        final SciProject project = projectRepository
            .findById(forProjectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  forProjectId))
            );

        return project
            .getContacts()
            .stream()
            .map(this::buildContactEntry)
            .collect(Collectors.toList());
    }

    private Map<String, Object> buildContactEntry(final Contact fromContact) {

        Objects.requireNonNull(fromContact);

        final Map<String, Object> result = new HashMap<>();
        result.put(CONTACT_ID, fromContact.getContactId());
        result.put(CONTACT_TYPE, fromContact.getContactType());
        result.put(CONTACT_NAME, fromContact.getContactable().getDisplayName());

        return result;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Optional<Contact> findContact(final long projectId,
                                         final Object key) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final long contactId = (long) key;

        return project
            .getContacts()
            .stream()
            .filter(contact -> contact.getContactId() == contactId)
            .findAny();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void addContact(final long projectId,
                           final long contactableId,
                           final String contactType) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final ContactableEntity contact = contactableRepository
            .findById(contactableId)
            .orElseThrow(() -> new IllegalArgumentException(
            String.format("No ContactableEntity with ID %d found.",
                          contactableId)));

        projectMananger.addContact(contact, project, contactType);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void updateContactType(final long projectId,
                                  final long contactableId,
                                  final String contactType) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final ContactableEntity contactable = contactableRepository
            .findById(contactableId)
            .orElseThrow(() -> new IllegalArgumentException(
            String.format("No ContactableEntity with ID %d found.",
                          contactableId)));

        final Optional<Contact> contact = project
            .getContacts()
            .stream()
            .filter(current -> filterContact(current, project, contactable))
            .findAny();

        if (contact.isPresent()) {

            contact.get().setContactType(contactType);

            projectRepository.save(project);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void removeContact(final long projectId, final long contactId) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final Optional<Contact> contact = project
            .getContacts()
            .stream()
            .filter(current -> current.getContactId() == contactId)
            .findAny();

        if (contact.isPresent()) {
            projectMananger.removeContact(contact.get().getContactable(),
                                          project);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void swapWithPreviousContact(final long projectId, 
                                        final long contactId) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final List<Contact> contacts = project.getContacts();
        Contact contact = null;
        int index = -1;
        for (int i = 0; i < contacts.size(); i++) {

            if (contacts.get(i).getContactId() == contactId) {
                contact = contacts.get(i);
                index = i;
                break;
            }
        }

        if (index > 0 && contact != null) {
            final long order = contact.getOrder();
            final Contact prevContact = contacts.get(index - 1);
            final long prevOrder = prevContact.getOrder();

            contact.setOrder(prevOrder);
            prevContact.setOrder(order);

            projectRepository.save(project);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void swapWithNextContact(final long projectId, 
                                    final long contactId) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final List<Contact> contacts = project.getContacts();
        Contact contact = null;
        int index = -1;
        for (int i = 0; i < contacts.size(); i++) {

            if (contacts.get(i).getContactId() == contactId) {
                contact = contacts.get(i);
                index = i;
                break;
            }
        }

        if (index < contacts.size() && contact != null) {
            final long order = contact.getOrder();
            final Contact nextContact = contacts.get(index + 1);
            final long nextOrder = nextContact.getOrder();

            contact.setOrder(nextOrder);
            nextContact.setOrder(order);

            projectRepository.save(project);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean hasContact(final long projectId,
                              final long contactableId) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final ContactableEntity contactable = contactableRepository
            .findById(contactableId)
            .orElseThrow(() -> new IllegalArgumentException(
            String.format("No ContactableEntity with ID %d found.",
                          contactableId)));

        return project
            .getContacts()
            .stream()
            .anyMatch(current -> filterContact(current, project, contactable));
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void updateDescription(final long projectId,
                                  final String descriptionText,
                                  final Locale language) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final LocalizedString desc = project.getDescription();
        desc.addValue(Objects.requireNonNull(language),
                      descriptionText);

        projectRepository.save(project);
    }
    
    @Transactional(Transactional.TxType.REQUIRED) 
    public Optional<Sponsoring> findSponsoring(final long projectId, 
                                               final Object key) {
        
         final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final long sponsoringId = (long) key;
        
        return project
            .getSponsoring()
            .stream()
            .filter(sponsoring -> sponsoring.getSponsoringId() == sponsoringId)
            .findAny();
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public List<Map<String, Object>> getSponsors(final long forProjectId) {
        
          final SciProject project = projectRepository
            .findById(forProjectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  forProjectId))
            );

        return project
            .getSponsoring()
            .stream()
            .map(this::buildSponsorEntry)
            .collect(Collectors.toList());
    }
    
    private Map<String, Object> buildSponsorEntry(final Sponsoring sponsoring) {
        
        Objects.requireNonNull(sponsoring);
        
        final Map<String, Object> result = new HashMap<>();
        result.put(SPONSOR_ID, sponsoring.getSponsoringId());
        result.put(SPONSOR_NAME, sponsoring.getSponsor().getName());
        result.put(SPONSOR_FUNDING_CODE, sponsoring.getFundingCode());
        
        return result;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void addSponsor(final long projectId,
                           final long sponsorId,
                           final String withFundingCode) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final Organization sponsor = assetRepository
            .findById(sponsorId, Organization.class)
            .orElseThrow(() -> new IllegalArgumentException(
            String.format("No Organization with ID %d found.", sponsorId)));

        projectMananger.addSponsor(sponsor, project, withFundingCode);
    }

    public void removeSponsor(final long projectId, final long sponsoringId) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final Optional<Sponsoring> sponsoring = project
            .getSponsoring()
            .stream()
            .filter(current -> current.getSponsoringId() == sponsoringId)
            .findAny();

        if (sponsoring.isPresent()) {
            projectMananger.removeSponsor(sponsoring.get().getSponsor(),
                                          project);
        }
    }

    public boolean hasSponsor(final long projectId, final long sponsorId) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final Organization sponsor = assetRepository
            .findById(sponsorId, Organization.class)
            .orElseThrow(() -> new IllegalArgumentException(
            String.format("No Organization with ID %d found.", sponsorId)));

        return project
            .getSponsoring()
            .stream()
            .anyMatch(sponsoring -> sponsoring.getSponsor().equals(sponsor));
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void updateFundingCode(final long projectId,
                                  final long sponsorId,
                                  final String fundingCode) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final Optional<Sponsoring> result = project
            .getSponsoring()
            .stream()
            .filter(
                sponsoring -> sponsoring.getSponsor().getObjectId() == sponsorId
            )
            .findAny();

        if (result.isPresent()) {

            final Sponsoring sponsoring = result.get();
            sponsoring.setFundingCode(fundingCode);
            projectRepository.save(project);
        }
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public void swapWithPrevSponsoring(final long projectId,
                                       final long sponsoringId) {
        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );
        
        final List<Sponsoring> sponsoringList = project.getSponsoring();
        Sponsoring sponsoring = null;
        int index = -1;
        for (int i = 0; i < sponsoringList.size(); i++) {
            
            if (sponsoringList.get(i).getSponsoringId() == sponsoringId) {
                sponsoring = sponsoringList.get(i);
                index = i;
                break;
            }
        }
        
        if (index > 0 && sponsoring != null) {
            final long order = sponsoring.getOrder();
            final Sponsoring prevSponsoring = sponsoringList.get(index - 1);
            final long prevOrder = prevSponsoring.getOrder();
            
            sponsoring.setOrder(prevOrder);
            prevSponsoring.setOrder(order);
            
            projectRepository.save(project);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void swapWithNextSponsoring(final long projectId,
                                       final long sponsoringId) {
        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );
        
          final List<Sponsoring> sponsoringList = project.getSponsoring();
        Sponsoring sponsoring = null;
        int index = -1;
        for (int i = 0; i < sponsoringList.size(); i++) {
            
            if (sponsoringList.get(i).getSponsoringId() == sponsoringId) {
                sponsoring = sponsoringList.get(i);
                index = i;
                break;
            }
        }
        
        if (index > 0 && sponsoring != null) {
            final long order = sponsoring.getOrder();
            final Sponsoring nextSponsoring = sponsoringList.get(index + 1);
            final long nextOrder = nextSponsoring.getOrder();
            
            sponsoring.setOrder(nextOrder);
            nextSponsoring.setOrder(order);
            
            projectRepository.save(project);
        }
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public void save(final long projectId,
                     final Locale selectedLocale,
                     final Map<String, Object> data) {

        final SciProject project = projectRepository
            .findById(projectId, SciProject.class)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("No SciProject with ID %d found.",
                                  projectId))
            );

        final Date begin = (Date) data.get(SciProjectUiConstants.BEGIN);
        final Date end = (Date) data.get(SciProjectUiConstants.END);
        final String shortDesc = (String) data
            .get(SciProjectUiConstants.PROJECT_SHORT_DESCRIPTION);

        final LocalDate beginDate = LocalDate.from(begin.toInstant());
        final LocalDate endDate = LocalDate.from(end.toInstant());

        project.setBegin(beginDate);
        project.setEnd(endDate);
        project.getShortDescription().addValue(selectedLocale, shortDesc);

        projectRepository.save(project);
    }

    private boolean filterContact(final Contact contact,
                                  final SciProject project,
                                  final ContactableEntity contactable) {

        return contact.getProject().equals(project)
                   && contact.getContactable().equals(contactable);
    }

}
