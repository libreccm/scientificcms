/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.Text;
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
import org.librecms.assets.ContactableEntity;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

import java.util.ResourceBundle;
import java.util.TooManyListenersException;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectContactAddForm
    extends BasicItemForm
    implements FormSubmissionListener {

    private final static Logger LOGGER = LogManager
        .getLogger(SciProjectContactAddForm.class);

    private static final String CONTACT_TYPE = "SciProjectContactType";

    private AssetSearchWidget searchWidget;

    private final String SEARCH = "project_contact";

    private ItemSelectionModel itemModel;

    private SciProjectContactsStep editStep;

    private Text selectedContactLabel;

    public SciProjectContactAddForm(final ItemSelectionModel itemModel,
                                    final SciProjectContactsStep editStep,
                                    final StringParameter selectedLanguage) {

        super("ContactEntryAddForm", itemModel, selectedLanguage);
        this.itemModel = itemModel;
        this.editStep = editStep;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        searchWidget = new AssetSearchWidget(SEARCH, ContactableEntity.class);
        searchWidget.setLabel(new GlobalizedMessage(
            "cms.contenttypes.ui.sciproject.contact.type",
            SciProjectConstants.SCI_PROJECT_BUNDLE));
        add(searchWidget);

        selectedContactLabel = new Text();
        add(selectedContactLabel);

        final ParameterModel contactTypeParam
                                 = new StringParameter(CONTACT_TYPE);
        final SingleSelect contactType = new SingleSelect(contactTypeParam);
        contactType.setLabel(new GlobalizedMessage(
            "cms.contenttypes.ui.sciproject.contact.type",
            SciProjectConstants.SCI_PROJECT_BUNDLE));
        contactType.addValidationListener(new NotNullValidationListener());

        try {

            contactType.addPrintListener(new PrintListener() {

                @Override
                public void prepare(final PrintEvent event) {

                    final SingleSelect target = (SingleSelect) event.getTarget();
                    target.clearOptions();

                    target.addOption(new Option("",
                                                new Label(new GlobalizedMessage(
                                                    "cms.ui.select_one",
                                                    CmsConstants.CMS_BUNDLE))));

                    final String contactTypeBundleName = getController()
                        .getContactTypesBundleName();
                    final ResourceBundle contactTypesBundle = ResourceBundle
                        .getBundle(contactTypeBundleName);
                    for (final String key : contactTypesBundle.keySet()) {

                        final Option option = new Option(
                            key,
                            new Label(
                                new GlobalizedMessage(key,
                                                      contactTypeBundleName)));
                        target.addOption(option);
                    }
                }

            });

        } catch (TooManyListenersException ex) {
            throw new UncheckedWrapperException(
                "Something has gone terribly wrong", ex);
        }
        add(contactType);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        final FormData data = event.getFormData();
        final PageState state = event.getPageState();

        final ContactableEntity selectedContact = editStep.getSelectedContact();
        final String selectedType = editStep.getSelectedContactType();

        if (selectedContact == null) {
            selectedContactLabel.setVisible(state, false);
        } else {
            data.put(SEARCH, selectedContact);
            data.put(CONTACT_TYPE, selectedType);

            searchWidget.setVisible(state, false);
            selectedContactLabel.setText(selectedContact.getDisplayName());
            selectedContactLabel.setVisible(state, true);
        }

        setVisible(state, true);
    }

    @Override
    public void process(final FormSectionEvent event) throws
        FormProcessException {

        final FormData data = event.getFormData();
        final PageState state = event.getPageState();
        final SciProject project = (SciProject) getItemSelectionModel()
            .getSelectedItem(state);

        if (getSaveCancelSection().getSaveButton().isSelected(state)) {

            final ContactableEntity selected = editStep.getSelectedContact();

            if (selected == null) {
                final ContactableEntity contact = (ContactableEntity) data
                    .get(SEARCH);

                final String type = (String) data.get(CONTACT_TYPE);

                getController().addContact(project.getObjectId(),
                                           contact.getObjectId(),
                                           type);
            } else {

                final String type = (String) data.get(CONTACT_TYPE);

                getController().updateContactType(project.getObjectId(),
                                                  selected.getObjectId(),
                                                  type);
            }
        }

        init(event);
    }

    @Override
    public void submitted(final FormSectionEvent event) throws
        FormProcessException {

        final PageState state = event.getPageState();
        if (getSaveCancelSection().getCancelButton().isSelected(state)) {

            editStep.setSelectedContact(null);
            editStep.setSelectedContactType(null);

            init(event);
        }
    }

    @Override
    public void validate(final FormSectionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();
        final FormData data = event.getFormData();

        if (editStep.getSelectedContact() == null
                && (data.get(SEARCH) == null)) {

            data.addError(new GlobalizedMessage(
                "cms.contenttypes.ui.sciproject.select_contact.no_contact_selected",
                SciProjectConstants.SCI_PROJECT_BUNDLE));

            return;
        }

        if (editStep.getSelectedContact() == null) {

            final SciProject project = (SciProject) getItemSelectionModel()
                .getSelectedItem(state);

            final ContactableEntity selected = (ContactableEntity) data
                .get(SEARCH);

            if (getController().hasContact(project.getObjectId(),
                                           selected.getObjectId())) {

                data.addError(new GlobalizedMessage(
                    "cms.contenttypes.ui.sciproject.select_contact.already_added",
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
