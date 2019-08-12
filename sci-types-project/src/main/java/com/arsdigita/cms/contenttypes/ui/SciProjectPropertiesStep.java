/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.Component;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.SegmentedPanel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.BasicPageForm;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.cms.ui.workflow.WorkflowLockedComponentAccess;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.toolbox.ui.DomainObjectPropertySheet;

import org.librecms.CmsConstants;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.SciProjectConstants;

import java.text.DateFormat;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectPropertiesStep extends SimpleEditStep {

    private SegmentedPanel segmentedPanel;

    public SciProjectPropertiesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParam) {

        super(itemModel, parent, selectedLanguageParam);

        segmentedPanel = new SegmentedPanel();
        setDefaultEditKey(SciProjectUiConstants.EDIT_CONTACT_SHEET_NAME);

        addBasicProperties(itemModel, parent, selectedLanguageParam);
        addSteps(itemModel, parent);

        setDisplayComponent(segmentedPanel);
    }

    public static Component getSciProjectPropertySheet(
        final ItemSelectionModel itemModel) {
        final DomainObjectPropertySheet sheet = new DomainObjectPropertySheet(
            itemModel);;

        sheet.add(new GlobalizedMessage(
            "org.scientificcms.contenttypes.sciproject.name",
            SciProjectConstants.SCI_PROJECT_BUNDLE),
                  SciProjectUiConstants.NAME);
        sheet.add(new GlobalizedMessage(
            "cms.contenttypes.ui.genericorgaunit.title",
            SciProjectConstants.SCI_PROJECT_BUNDLE),
                  SciProjectUiConstants.TITLE);

        sheet.add(new GlobalizedMessage("sciproject.ui.begin",
                                        SciProjectConstants.SCI_PROJECT_BUNDLE
        ),
                  SciProjectUiConstants.BEGIN,
                  new DomainObjectPropertySheet.AttributeFormatter() {

                  @Override
                  public String format(final Object obj,
                                       final String attribute,
                                       final PageState state) {
                      final SciProject project = (SciProject) obj;
                      if (project.getBegin() == null) {
                          return (String) new GlobalizedMessage(
                              "cms.ui.unknown",
                              CmsConstants.CMS_BUNDLE)
                              .localize();
                      } else {
                          return DateFormat.getDateInstance(DateFormat.LONG)
                              .format(
                                  project.getBegin());
                      }
                  }

              });
        sheet.add(new GlobalizedMessage("sciproject.ui.end",
                                        SciProjectConstants.SCI_PROJECT_BUNDLE),
                  SciProjectUiConstants.END,
                  new DomainObjectPropertySheet.AttributeFormatter() {

                  public String format(final Object obj,
                                       final String attribute,
                                       final PageState state) {
                      final SciProject project = (SciProject) obj;
                      if (project.getEnd() == null) {
                          return (String) new GlobalizedMessage(
                              "cms.ui.unknown",
                              CmsConstants.CMS_BUNDLE)
                              .localize();
                      } else {
                          return DateFormat.getDateInstance(DateFormat.LONG)
                              .format(project.
                                  getEnd());
                      }
                  }

              });
        sheet.add(new GlobalizedMessage("sciproject.ui.shortdesc",
                                        SciProjectConstants.SCI_PROJECT_BUNDLE),
                  SciProjectUiConstants.PROJECT_SHORT_DESCRIPTION
        );

        return sheet;
    }

    protected void addBasicProperties(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParameter) {
        final SimpleEditStep basicProperties = new SimpleEditStep(
            itemModel,
            parent,
            selectedLanguageParameter,
            SciProjectUiConstants.EDIT_CONTACT_SHEET_NAME
        );

        final BasicPageForm editBasicSheet = new SciProjectPropertyForm(
            itemModel,
            this,
            selectedLanguageParameter);

        basicProperties.add(SciProjectUiConstants.EDIT_CONTACT_SHEET_NAME,
            new GlobalizedMessage("sciproject.ui.edit_basic_sheet",
                                  SciProjectConstants.SCI_PROJECT_BUNDLE
            ),
            new WorkflowLockedComponentAccess(editBasicSheet, itemModel),
            editBasicSheet.getSaveCancelSection().getCancelButton());

        basicProperties.setDisplayComponent(
            getSciProjectPropertySheet(itemModel));

        segmentedPanel.addSegment(
            new Label(new GlobalizedMessage(
                "sciproject.ui.edit_basic_properties",
                SciProjectConstants.SCI_PROJECT_BUNDLE)
            ),
            basicProperties);
    }

    protected void addSteps(final ItemSelectionModel itemModel,
                            final AuthoringKitWizard parent) {

        addStep(new SciProjectContactsStep(itemModel, parent),
                new GlobalizedMessage("sciproject.ui.contacts",
                                      SciProjectConstants.SCI_PROJECT_BUNDLE));
////        addStep(new GenericOrganizationalUnitContactPropertiesStep(itemModel,
////                                                                   parent),
////                SciProjectGlobalizationUtil.globalize("sciproject.ui.contacts"));
//
    }

    /**
     * Helper method for editing a step.
     *
     * @param step
     * @param labelKey
     */
    protected void addStep(final SimpleEditStep step, final String labelKey) {
        segmentedPanel.addSegment(
            new Label(new GlobalizedMessage(labelKey,
                                            CmsConstants.CMS_BUNDLE)),
            step);
    }

    protected void addStep(final SimpleEditStep step,
                           final GlobalizedMessage label) {
        segmentedPanel.addSegment(new Label(label), step);
    }

    protected SegmentedPanel getSegmentedPanel() {
        return segmentedPanel;
    }

}
