/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.cms.ui.workflow.WorkflowLockedComponentAccess;
import com.arsdigita.globalization.GlobalizedMessage;

import org.librecms.assets.ContactableEntity;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectContactsStep extends SimpleEditStep {
    
    private ContactableEntity selectedContact;
    private String selectedContactType;

    public SciProjectContactsStep(final ItemSelectionModel itemModel,
                                  final AuthoringKitWizard parent) {
        this(itemModel, parent, null);
    }

    public SciProjectContactsStep(final ItemSelectionModel itemModel,
                                  final AuthoringKitWizard parent,
                                  final StringParameter selectedLanguageParam) {
        super(itemModel, parent, selectedLanguageParam);
    }

    public SciProjectContactsStep(final ItemSelectionModel itemModel,
                                  final AuthoringKitWizard parent,
                                  final String parameterSuffix,
                                  final StringParameter selectedLanguageParam) {
        super(itemModel, parent, selectedLanguageParam, parameterSuffix);

        final BasicItemForm addContactSheet = new SciProjectContactAddForm(
            itemModel, this, selectedLanguageParam);
        
        add(SciProjectUiConstants.ADD_CONTACT_SHEET_NAME,
            new GlobalizedMessage(
                "cms.contenttypes.ui.genericorgaunit.add_contact",
                SciProjectConstants.SCI_PROJECT_BUNDLE),
            new WorkflowLockedComponentAccess(addContactSheet, itemModel),
            addContactSheet.getSaveCancelSection().getCancelButton());
        
        final SciProjectContactsTable contactsTable 
            = new SciProjectContactsTable(itemModel, this);
        setDisplayComponent(contactsTable);
    }
    
    public ContactableEntity getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(final ContactableEntity selectedContact) {
        this.selectedContact = selectedContact;
    }

    public String getSelectedContactType() {
        return selectedContactType;
    }

    public void setSelectedContactType(final String selectedContactType) {
        this.selectedContactType = selectedContactType;
    }
}

}
