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
import org.librecms.assets.Person;
import org.librecms.contentsection.ContentItem;
import org.librecms.contenttypes.AuthoringKit;
import org.librecms.contenttypes.AuthoringStep;
import org.librecms.contenttypes.ContentTypeDescription;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
    private LocalizedString description;

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
    
    @OneToMany(mappedBy = "project")
    private List<Contact> contacts;
    
    @OneToMany(mappedBy = "project")
    private List<Membership> members;

}
