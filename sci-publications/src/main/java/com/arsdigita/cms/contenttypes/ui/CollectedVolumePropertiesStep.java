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

import static com.arsdigita.cms.contenttypes.ui.PublicationPropertiesStep.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class CollectedVolumePropertiesStep
    extends PublicationWithPublisherPropertiesStep {

    private final StringParameter selectedLangParam;

    public CollectedVolumePropertiesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, parent, selectedLangParam);
        this.selectedLangParam = selectedLangParam;
    }

    public static Component getCollectedVolumePropertySheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        final DomainObjectPropertySheet sheet
                                            = (DomainObjectPropertySheet) PublicationWithPublisherPropertiesStep
                .getPublicationWithPublisherPropertySheet(
                    itemModel, selectedLangParam
                );

        sheet.add(new GlobalizedMessage(
            "publications.ui.collectedVolume.reviewed",
            SciPublicationsConstants.BUNDLE
        ),
                  CollectedVolumeController.PEER_REVIEWED,
                  new ReviewedFormatter());

        return sheet;
    }

    @Override
    protected void addBasicProperties(
        ItemSelectionModel itemModel,
        AuthoringKitWizard parent) {
        SimpleEditStep basicProperties = new SimpleEditStep(
            itemModel, parent, selectedLangParam, EDIT_SHEET_NAME);

        final BasicPageForm editBasicSheet
                                = new CollectedVolumePropertyForm(
                itemModel,
                this,
                selectedLangParam
            );

        basicProperties.add(
            EDIT_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.collected_volume.edit_basic_sheet",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(editBasicSheet, itemModel),
            editBasicSheet.getSaveCancelSection().getCancelButton());

        basicProperties.setDisplayComponent(
            getCollectedVolumePropertySheet(itemModel, selectedLangParam));

        getSegmentedPanel().addSegment(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.publication.basic_properties",
                    SciPublicationsConstants.BUNDLE
                )
            ),
            basicProperties);
    }

    @Override
    protected void addSteps(
        final ItemSelectionModel itemModel, final AuthoringKitWizard parent
    ) {
        super.addSteps(itemModel, parent);

        addStep(
            new CollectedVolumeArticlesStep(
                itemModel, parent, selectedLangParam
            ),
            new GlobalizedMessage(
                "publications.ui.collected_volume_articles",
                SciPublicationsConstants.BUNDLE
            ));
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
                    "publications.ui.collectedVolume.reviewed.yes",
                    SciPublicationsConstants.BUNDLE
                ).localize();
            } else {
                return (String) new GlobalizedMessage(
                    "publications.ui.collectedVolume.reviewed.no",
                    SciPublicationsConstants.BUNDLE
                ).localize();
            }
        }

    }

}
