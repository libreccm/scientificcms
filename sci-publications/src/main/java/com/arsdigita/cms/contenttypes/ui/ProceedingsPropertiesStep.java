/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.Component;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.BasicPageForm;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;
import com.arsdigita.cms.ui.workflow.WorkflowLockedComponentAccess;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.toolbox.ui.DomainObjectPropertySheet;

import org.librecms.CmsConstants;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ProceedingsItem;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ProceedingsPropertiesStep
    extends PublicationWithPublisherPropertiesStep {

    private StringParameter selectedLangParam;

    public ProceedingsPropertiesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, parent, selectedLangParam);
        this.selectedLangParam = selectedLangParam;
    }

    public static Component getProceedingsPropertySheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        final DomainObjectPropertySheet sheet
                                            = (DomainObjectPropertySheet) getPublicationWithPublisherPropertySheet(
                itemModel, selectedLangParam
            );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.proceedings.name_of_conference",
                SciPublicationsConstants.BUNDLE
            ),
            ProceedingsController.NAME_OF_CONFERENCE);

        sheet.add(new GlobalizedMessage(
            "publications.ui.proceedings.place_of_conference",
            SciPublicationsConstants.BUNDLE
        ),
                  ProceedingsController.PLACE_OF_CONFERENCE);

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.proceedings.date_from_of_conference",
                SciPublicationsConstants.BUNDLE
            ),
            ProceedingsController.START_DATE,
            new DomainObjectPropertySheet.AttributeFormatter() {

            public String format(
                final Object item,
                final String attribute,
                final PageState state
            ) {
                final ProceedingsItem proceedingsItem = (ProceedingsItem) item;
                final Proceedings proceedings = proceedingsItem.getPublication();
                if (proceedings.getStartDate() != null) {
                    final LocalDate startLocalDate = proceedings.getStartDate();
                    final Date startDate = Date.from(
                        startLocalDate
                            .atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                    );
                    return DateFormat.getDateInstance(DateFormat.LONG)
                        .format(startDate);
                } else {
                    return (String) new GlobalizedMessage(
                        "cms.ui.unknown",
                        CmsConstants.CMS_BUNDLE
                    ).localize();
                }
            }

        });

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.proceedings.date_to_of_conference",
                SciPublicationsConstants.BUNDLE
            ),
            ProceedingsController.END_DATE,
            new DomainObjectPropertySheet.AttributeFormatter() {

            public String format(
                final Object item,
                final String attribute,
                final PageState state
            ) {
                final ProceedingsItem proceedingsItem = (ProceedingsItem) item;
                final Proceedings proceedings = proceedingsItem.getPublication();
                if (proceedings.getEndDate() != null) {
                    final LocalDate endLocalDate = proceedings.getEndDate();
                    final Date endDate = Date.from(
                        endLocalDate
                            .atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                    );

                    return DateFormat.getDateInstance(DateFormat.LONG)
                        .format(endDate);
                } else {
                    return (String) new GlobalizedMessage(
                        "cms.ui.unknown",
                        CmsConstants.CMS_BUNDLE
                    ).localize();
                }
            }

        });

        return sheet;
    }

    @Override
    protected void addBasicProperties(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent) {
        final SimpleEditStep basicProperties = new SimpleEditStep(
            itemModel, parent, selectedLangParam, EDIT_SHEET_NAME);

        BasicPageForm editBasicSheet = new ProceedingsPropertyForm(
            itemModel, this, selectedLangParam
        );

        basicProperties.add(
            EDIT_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.proceedings.edit_basic_sheet",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(editBasicSheet, itemModel),
            editBasicSheet.getSaveCancelSection().getCancelButton());

        basicProperties.setDisplayComponent(
            getProceedingsPropertySheet(itemModel, selectedLangParam)
        );

        getSegmentedPanel().addSegment(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.proceedings.basic_properties",
                    SciPublicationsConstants.BUNDLE
                )
            ),
            basicProperties);
    }

    @Override
    protected void addSteps(
        final ItemSelectionModel itemModel, final AuthoringKitWizard parent) {
        super.addSteps(itemModel, parent);

        addStep(
            new ProceedingsOrganizerStep(itemModel, parent, selectedLangParam),
            new GlobalizedMessage(
                "publications.ui.proceedings.organizer",
                SciPublicationsConstants.BUNDLE
            )
        );

        addStep(
            new ProceedingsPapersStep(itemModel, parent, selectedLangParam),
            new GlobalizedMessage(
                "publications.ui.proceedings.papers",
                SciPublicationsConstants.BUNDLE
            )
        );
    }

}
