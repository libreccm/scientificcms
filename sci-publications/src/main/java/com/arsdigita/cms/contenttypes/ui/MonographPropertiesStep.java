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
import com.arsdigita.domain.DomainService;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.toolbox.ui.DomainObjectPropertySheet;

import org.scientificcms.publications.SciPublicationsConstants;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class MonographPropertiesStep
    extends PublicationWithPublisherPropertiesStep {

    private final StringParameter selectedLangParam;

    public MonographPropertiesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, parent, selectedLangParam);
        this.selectedLangParam = selectedLangParam;
    }

    public static Component getMonographPropertySheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        DomainObjectPropertySheet sheet
                                      = (DomainObjectPropertySheet) PublicationWithPublisherPropertiesStep
                .getPublicationWithPublisherPropertySheet(
                    itemModel,
                    selectedLangParam
                );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.monograph.reviewed",
                SciPublicationsConstants.BUNDLE
            ),
            MonographController.REVIEWED,
            new ReviewedFormatter()
        );

        return sheet;
    }

    @Override
    protected void addBasicProperties(
        final ItemSelectionModel itemModel, final AuthoringKitWizard parent
    ) {
        final SimpleEditStep basicProperties = new SimpleEditStep(
            itemModel, parent, selectedLangParam, EDIT_SHEET_NAME
        );

        final BasicPageForm editBasicSheet
                                = new MonographPropertyForm(
                itemModel, this, selectedLangParam
            );

        basicProperties.add(
            EDIT_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.monograph.edit_basic_sheet",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(editBasicSheet, itemModel),
            editBasicSheet.getSaveCancelSection().getCancelButton()
        );

        basicProperties.setDisplayComponent(
            getMonographPropertySheet(itemModel, selectedLangParam)
        );

        getSegmentedPanel().addSegment(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.publication.basic_properties",
                    SciPublicationsConstants.BUNDLE
                )
            ),
            basicProperties);
    }

    private static class ReviewedFormatter
        extends DomainService
        implements DomainObjectPropertySheet.AttributeFormatter {

        public ReviewedFormatter() {
            super();
        }

        @Override
        public String format(
            final Object obj, final String attribute, final PageState state
        ) {
            if ((get(obj, attribute) != null)
                    && (get(obj, attribute) instanceof Boolean)
                    && ((Boolean) get(obj, attribute) == true)) {
                return (String) new GlobalizedMessage(
                    "publications.ui.monograph.reviewed.yes",
                    SciPublicationsConstants.BUNDLE).localize();
            } else {
                return (String) new GlobalizedMessage(
                    "publications.ui.monograph.reviewed.no",
                    SciPublicationsConstants.BUNDLE).localize();
            }
        }

    }

}
