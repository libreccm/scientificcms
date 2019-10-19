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
public class ArticleInCollectedVolumePropertiesStep
    extends PublicationPropertiesStep {

    private final StringParameter selectedLangParameter;

    public ArticleInCollectedVolumePropertiesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParameter
    ) {
        super(itemModel, parent, selectedLangParameter);
        this.selectedLangParameter = selectedLangParameter;
    }

    public static Component getArticleInCollectedVolumePropertySheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLanguageParam
    ) {

        final DomainObjectPropertySheet sheet
                                            = (DomainObjectPropertySheet) PublicationPropertiesStep
                .getPublicationPropertySheet(itemModel, selectedLanguageParam);

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.article_in_collected_volume.pages_from",
                SciPublicationsConstants.BUNDLE
            ),
            ArticleInCollectedVolumeController.START_PAGE
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.article_in_collected_volume.pages_to",
                SciPublicationsConstants.BUNDLE
            ),
            ArticleInCollectedVolumeController.END_PAGE
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.article_in_collected_volume.chapter",
                SciPublicationsConstants.BUNDLE
            ),
            ArticleInCollectedVolumeController.CHAPTER
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.articleInCollectedVolume.reviewed",
                SciPublicationsConstants.BUNDLE
            ),
            ArticleInCollectedVolumeController.PEER_REVIEWED,
            new ReviewedFormatter()
        );

        return sheet;
    }

    @Override
    protected void addBasicProperties(
        final ItemSelectionModel itemModel, final AuthoringKitWizard parent
    ) {
        final SimpleEditStep basicProperties = new SimpleEditStep(
            itemModel, parent, selectedLangParameter, EDIT_SHEET_NAME
        );

        final BasicPageForm editBasicSheet
                                = new ArticleInCollectedVolumePropertyForm(
                itemModel, this, selectedLangParameter
            );

        basicProperties.add(
            EDIT_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.article_in_collected_volume.edit_basic_sheet",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(editBasicSheet, itemModel),
            editBasicSheet.getSaveCancelSection().getCancelButton()
        );

        basicProperties.setDisplayComponent(
            getArticleInCollectedVolumePropertySheet(
                itemModel, selectedLangParameter
            )
        );

        getSegmentedPanel().addSegment(
            new Label(
                new GlobalizedMessage(
                    "publications.ui.publication.basic_properties",
                    SciPublicationsConstants.BUNDLE
                )
            ),
            basicProperties
        );
    }

    @Override
    protected void addSteps(
        final ItemSelectionModel itemModel, final AuthoringKitWizard parent
    ) {
        super.addSteps(itemModel, parent);

        addStep(
            new ArticleInCollectedVolumeCollectedVolumeStep(
                itemModel, parent, selectedLangParameter
            ),
            new GlobalizedMessage(
                "publications.ui.articleInCollectedVolume.collectedVolume",
                SciPublicationsConstants.BUNDLE
            )
        );
    }

    private static class ReviewedFormatter
        extends DomainService
        implements DomainObjectPropertySheet.AttributeFormatter {

        @Override
        public String format(
            final Object obj, final String attribute, final PageState state
        ) {
            final GlobalizedMessage msg;
            if ((get(obj, attribute) instanceof Boolean)
                    && ((Boolean) get(obj, attribute) == true)) {
                msg = new GlobalizedMessage(
                    "publications.ui.articleInCollectedVolume.reviewed.yes",
                    SciPublicationsConstants.BUNDLE
                );
            } else {
                msg = new GlobalizedMessage(
                    "publications.ui.articleInCollectedVolume.reviewed.no",
                    SciPublicationsConstants.BUNDLE
                );
            }
            return (String) msg.localize();
        }

    }

    @Override
    protected boolean isSeriesStepEnabled() {
        return false;
    }

}
