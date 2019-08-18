/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.Page;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;

import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;

import com.arsdigita.cms.ui.workflow.WorkflowLockedComponentAccess;
import com.arsdigita.globalization.GlobalizedMessage;

import org.librecms.assets.Person;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

/**
 *
 * @author Jens Pelzetter
 */
public class SciProjectMembersStep extends SimpleEditStep {

    private static final String ADD_PROJECT_MEMBER_SHEET_NAME
                                    = "SciProjectAddMember";

    public static final String SELECTED_PERSON = "selected-person";

//    private final AssetSelectionModel selectedPerson;
    private Person selectedMember;

    private String selectedMembershipRole;

    private String selectedMembershipStatus;

    public SciProjectMembersStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParameter) {

        this(itemModel, parent, selectedLanguageParameter, null);
    }

    public SciProjectMembersStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParameter,
        final String prefix) {
        super(itemModel, parent, selectedLanguageParameter, prefix);

        final BasicItemForm addMemberSheet = new SciProjectMemberAddForm(
            itemModel, this, selectedLanguageParameter
        );
        add(ADD_PROJECT_MEMBER_SHEET_NAME,
            new GlobalizedMessage("sciproject.ui.members.add",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE),
            new WorkflowLockedComponentAccess(addMemberSheet, itemModel),
            addMemberSheet.getSaveCancelSection().getCancelButton());

        final SciProjectMembersTable memberTable = new SciProjectMembersTable(
            itemModel, this);
        setDisplayComponent(memberTable);

//        selectedPerson = new ItemSelectionModel(SELECTED_PERSON);
    }

    @Override
    public void register(final Page page) {
        super.register(page);

//        page.addGlobalStateParam(selectedPerson.getStateParameter());
    }

    public Person getSelectedMember(final PageState state) {
        return selectedMember;
    }

    public void setSelectedMember(final PageState state,
                                      final Person selectedMember) {
        this.selectedMember = selectedMember;
    }

    public String getSelectedMemberRole(final PageState state) {
        return selectedMembershipRole;
    }

    public void setSelectedMemberRole(final PageState state,
                                      final String selectedPersonRole) {
        this.selectedMembershipRole = selectedPersonRole;
    }

    public String getSelectedMemberStatus(final PageState state) {
        return selectedMembershipStatus;
    }

    public void setSelectedMemberStatus(final PageState state,
                                        final String selectedPersonStatus) {
        this.selectedMembershipStatus = selectedPersonStatus;
    }

    public void showEditComponent(final PageState state) {
        showComponent(state, ADD_PROJECT_MEMBER_SHEET_NAME);
    }

}
