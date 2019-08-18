package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.Text;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.event.FormSubmissionListener;
import com.arsdigita.bebop.event.PrintEvent;
import com.arsdigita.bebop.event.PrintListener;
import com.arsdigita.bebop.form.Option;
import com.arsdigita.bebop.form.SingleSelect;
import com.arsdigita.bebop.parameters.NotNullValidationListener;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.assets.AssetSearchWidget;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.util.UncheckedWrapperException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.librecms.CmsConstants;
import org.librecms.assets.Person;
import org.scientificcms.contenttypes.sciproject.MembershipStatus;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

import java.util.ResourceBundle;
import java.util.TooManyListenersException;

/**
 *
 * @author Jens Pelzetter
 * @version $Id: SciProjectMemberAddForm.java 1170 2011-10-17 19:23:23Z jensp $
 */
public class SciProjectMemberAddForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener, FormSubmissionListener {

    private static final Logger LOGGER = LogManager.getLogger(
        SciProjectMemberAddForm.class);

    private final SciProjectMembersStep membersStep;

    private AssetSearchWidget personSearchWidget;

    private Text selectedPersonLabel;

    public SciProjectMemberAddForm(
        final ItemSelectionModel itemModel,
        final SciProjectMembersStep membersStep,
        final StringParameter selectedLanguageParam) {

        super("SciProjectMemberAddForm", itemModel, selectedLanguageParam);

        this.membersStep = membersStep;
        addSubmissionListener(this);
    }

    @Override
    public void addWidgets() {

        personSearchWidget = new AssetSearchWidget(
            SciProjectUiConstants.MEMBER_SEARCH,
            Person.class
        );
        personSearchWidget.setLabel(new GlobalizedMessage(
            "sciproject.ui.select_member",
            SciProjectConstants.SCI_PROJECT_BUNDLE
        ));
        add(personSearchWidget);

        selectedPersonLabel = new Text();
        add(selectedPersonLabel);

        final ParameterModel roleParam = new StringParameter(
            SciProjectUiConstants.MEMBER_ROLE
        );
        final SingleSelect roleSelect = new SingleSelect(roleParam);
        roleSelect.setLabel(new GlobalizedMessage(
            "sciproject.ui.member.role",
            SciProjectConstants.SCI_PROJECT_BUNDLE
        ));
        roleSelect.addValidationListener(new NotNullValidationListener());

        try {

            roleSelect.addPrintListener(new PrintListener() {

                @Override
                public void prepare(final PrintEvent event) {
                    final SingleSelect target = (SingleSelect) event.getTarget();
                    target.clearOptions();

                    target.addOption(new Option("",
                                                new Label(new GlobalizedMessage(
                                                    "cms.ui.select_one",
                                                    CmsConstants.CMS_BUNDLE))));

                    final String rolesBundleName = getController()
                        .getMemberRolesBundleName();
                    final ResourceBundle rolesBundle = ResourceBundle
                        .getBundle(rolesBundleName);
                    for (final String key : rolesBundle.keySet()) {

                        final Option option = new Option(
                            key,
                            new Label(new GlobalizedMessage(key,
                                                            rolesBundleName))
                        );
                        target.addOption(option);
                    }
                }

            });

        } catch (TooManyListenersException ex) {
            throw new UncheckedWrapperException(ex);
        }
        add(roleSelect);

        final ParameterModel statusModel = new StringParameter(
            SciProjectUiConstants.MEMBER_STATUS
        );
        final SingleSelect statusSelect = new SingleSelect(statusModel);
        statusSelect.setLabel(new GlobalizedMessage(
            "sciproject.ui.member.status",
            SciProjectConstants.SCI_PROJECT_BUNDLE
        ));
        statusSelect.addValidationListener(new NotNullValidationListener());
        try {
            statusSelect.addPrintListener(new PrintListener() {

                @Override
                public void prepare(final PrintEvent event) {

                    final SingleSelect target = (SingleSelect) event.getTarget();
                    target.clearOptions();

                    target.addOption(new Option("",
                                                new Label(new GlobalizedMessage(
                                                    "cms.ui.select_one",
                                                    CmsConstants.CMS_BUNDLE))));

                    for (MembershipStatus status : MembershipStatus.values()) {

                        final Option option = new Option(
                            status.toString(),
                            new Label(new GlobalizedMessage(
                                String.format("sciproject.membership.status.%s",
                                              status.toString()),
                                SciProjectConstants.SCI_PROJECT_BUNDLE
                            ))
                        );
                        target.addOption(option);
                    }
                }

            });
        } catch (TooManyListenersException ex) {
            throw new UncheckedWrapperException(
                "Something has gone terribly wrong", ex);
        }
        add(statusSelect);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();

        final Person member = membersStep.getSelectedMember(state);
        final String role = membersStep.getSelectedMemberRole(state);
        final String status = membersStep.getSelectedMemberStatus(state);

        if (member == null) {
            personSearchWidget.setVisible(state, true);
            selectedPersonLabel.setVisible(state, false);
        } else {
            formData.put(SciProjectUiConstants.MEMBER_SEARCH, member);
            formData.put(SciProjectUiConstants.MEMBER_ROLE, role);
            formData.put(SciProjectUiConstants.MEMBER_STATUS, status);

            personSearchWidget.setVisible(state, false);
            selectedPersonLabel.setVisible(state, true);
            selectedPersonLabel.setText(
                String.format(
                    "%s, %s",
                    member.getPersonName().getSurname(),
                    member.getPersonName().getGivenName()
                )
            );
        }
        setVisible(state, true);
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();

        final SciProject project = (SciProject) getItemSelectionModel()
            .getSelectedItem(state);

        if (getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Person selected = membersStep.getSelectedMember(state);

            if (selected == null) {

                final Person person = (Person) formData
                    .get(SciProjectUiConstants.MEMBER_SEARCH);

                final String role = (String) formData
                    .get(SciProjectUiConstants.MEMBER_ROLE);
                final String status = (String) formData
                    .get(SciProjectUiConstants.MEMBER_STATUS);

                getController().addMember(project.getObjectId(),
                                          person.getObjectId(),
                                          role,
                                          status);

            } else {

                final String role = (String) formData
                    .get(SciProjectUiConstants.MEMBER_ROLE);
                final String status = (String) formData
                    .get(SciProjectUiConstants.MEMBER_STATUS);

                getController().updateMembership(project.getObjectId(),
                                                 selected.getObjectId(),
                                                 role,
                                                 status);
            }
        }

        init(event);
    }

    @Override
    public void submitted(final FormSectionEvent event) throws
        FormProcessException {

        final PageState state = event.getPageState();
        if (getSaveCancelSection().getCancelButton().isSelected(state)) {

            membersStep.setSelectedMember(state, null);
            membersStep.setSelectedMemberRole(state, null);
            membersStep.setSelectedMemberStatus(state, null);

            init(event);
        }
    }

    @Override
    public void validate(final FormSectionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();
        final FormData data = event.getFormData();

        if (membersStep.getSelectedMember(state) == null
                && data.get(SciProjectUiConstants.MEMBER_SEARCH) == null) {

            data.addError(
                new GlobalizedMessage(
                    "sciproject.ui.member.no_person_selected",
                    SciProjectConstants.SCI_PROJECT_BUNDLE
                )
            );
            return;
        }
        
        if (membersStep.getSelectedMember(state) == null) {
            
            final SciProject project = (SciProject) getItemSelectionModel()
            .getSelectedItem(state);
            
            final Person person = (Person) data
                .get(SciProjectUiConstants.MEMBER_SEARCH);
            
            if (getController().hasMember(project.getObjectId(), 
                                          person.getObjectId())) {
                
                 data.addError(new GlobalizedMessage(
                    "cms.contenttypes.ui.sciproject.select_member.already_added",
                    SciProjectConstants.SCI_PROJECT_BUNDLE
                ));
            }
        }
    }

    private SciProjectController getController() {
        final CdiUtil cdiUtil = CdiUtil.createCdiUtil();

        return cdiUtil.findBean(SciProjectController.class);
    }

}
