/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import org.libreccm.configuration.ConfigurationManager;
import org.librecms.assets.ContactableEntity;
import org.librecms.assets.ContactableEntityRepository;
import org.scientificcms.contenttypes.sciproject.Contact;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConfig;
import org.scientificcms.contenttypes.sciproject.SciProjectMananger;
import org.scientificcms.contenttypes.sciproject.SciProjectRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
class SciProjectController {

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
