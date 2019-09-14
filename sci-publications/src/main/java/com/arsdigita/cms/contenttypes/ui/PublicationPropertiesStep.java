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
import com.arsdigita.domain.DomainService;
import com.arsdigita.globalization.GlobalizedMessage;
import com.arsdigita.toolbox.ui.DomainObjectPropertySheet;

import org.arsdigita.cms.CMSConfig;
import org.librecms.CmsConstants;
import org.librecms.contentsection.ContentItem;
import org.scientificcms.publications.SciPublicationsConfig;
import org.scientificcms.publications.SciPublicationsConstants;

import java.text.DateFormat;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationPropertiesStep extends SimpleEditStep {

    public static final String EDIT_SHEET_NAME = "edit";

    private final SegmentedPanel segmentedPanel;

    private final StringParameter selectedLangParameter;

    public PublicationPropertiesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParameter
    ) {
        super(itemModel, parent, selectedLangParameter);

        segmentedPanel = new SegmentedPanel();
        setDefaultEditKey(EDIT_SHEET_NAME);

        addBasicProperties(itemModel, parent);
        addSteps(itemModel, parent);

        this.selectedLangParameter = selectedLangParameter;

        setDisplayComponent(segmentedPanel);
    }

    public static Component getPublicationPropertySheet(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLanguageParam
    ) {
        final DomainObjectPropertySheet sheet = new DomainObjectPropertySheet(
            itemModel,
            false,
            selectedLanguageParam
        );

        sheet.add(
            new GlobalizedMessage(
                "publications.ui.publication.name",
                SciPublicationsConstants.BUNDLE
            ),
            SciPublicationsController.NAME
        );
        sheet.add(
            new GlobalizedMessage(
                "publications.ui.publication.title",
                SciPublicationsConstants.BUNDLE),
            SciPublicationsController.TITLE
        );
        sheet.add(
            new GlobalizedMessage(
                "publications.ui.publication.year_of_publication",
                SciPublicationsConstants.BUNDLE
            ),
            SciPublicationsController.YEAR_OF_PUBLICATION
        );
        sheet.add(
            new GlobalizedMessage(
                "publications.ui.publication.short_desc",
                SciPublicationsConstants.BUNDLE
            ),
            SciPublicationsController.SHORT_DESCRIPTION
        );
        sheet.add(
            new GlobalizedMessage(
                "publications.ui.publication.abstract",
                SciPublicationsConstants.BUNDLE
            ),
            SciPublicationsController.ABSTRACT
        );
        //new PreFormattedTextFormatter());
        sheet.add(
            new GlobalizedMessage(
                "publications.ui.publication.misc",
                SciPublicationsConstants.BUNDLE
            ),
            SciPublicationsController.MISC
        );

        final SciPublicationsConfig config = SciPublicationsConfig.getConfig();
        if (config.isFirstPublishedPropertyEnabled()) {
            sheet.add(
                new GlobalizedMessage(
                    "publications.ui.publication.first_published",
                    SciPublicationsConstants.BUNDLE),
                SciPublicationsController.YEAR_FIRST_PUBLISHED
            );
        }

        if (config.isLanguagePropertyEnabled()) {
            sheet.add(
                new GlobalizedMessage(
                    "publications.ui.publication.language_of_publication",
                    SciPublicationsConstants.BUNDLE
                ),
                SciPublicationsController.LANGUAGE_OF_PUBLICATION
            );
        }

        if (!CMSConfig.getConfig().isHideLaunchDate()) {
            sheet.add(new GlobalizedMessage("cms.contenttypes.ui.launch_date",
                                            CmsConstants.CMS_BUNDLE),
                      SciPublicationsController.LAUNCH_DATE,
                      new DomainObjectPropertySheet.AttributeFormatter() {

                      @Override
                      public String format(final Object item,
                                           final String attribute,
                                           final PageState state) {
                          final ContentItem page = (ContentItem) item;
                          if (page.getLaunchDate() != null) {
                              return DateFormat
                                  .getDateInstance(DateFormat.LONG)
                                  .format(page.getLaunchDate());
                          } else {
                              return (String) new GlobalizedMessage(
                                  "cms.ui.unknown",
                                  CmsConstants.CMS_BUNDLE)
                                  .localize();
                          }
                      }

                  });
        }

        return sheet;
    }

    protected SegmentedPanel getSegmentedPanel() {
        return segmentedPanel;
    }

    protected void addBasicProperties(final ItemSelectionModel itemModel,
                                      final AuthoringKitWizard parent) {

        SimpleEditStep basicProperties = new SimpleEditStep(
            itemModel,
            parent,
            selectedLangParameter,
            EDIT_SHEET_NAME
        );

        final BasicPageForm editBasicSheet = new PublicationPropertyForm(
            itemModel,
            this,
            selectedLangParameter
        );
        basicProperties.add(
            EDIT_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.publication.edit_basic_sheet",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(editBasicSheet, itemModel),
            editBasicSheet.getSaveCancelSection().getCancelButton());

        basicProperties.setDisplayComponent(
            getPublicationPropertySheet(itemModel, selectedLangParameter)
        );

        segmentedPanel.addSegment(
            new Label(new GlobalizedMessage(
                "publications.ui.publication.basic_properties",
                SciPublicationsConstants.BUNDLE)
            ),
            basicProperties);
    }

    protected void addSteps(ItemSelectionModel itemModel,
                            AuthoringKitWizard parent) {
        addStep(
            new PublicationAuthorsPropertyStep(
                itemModel, parent, selectedLangParameter),
            new GlobalizedMessage(
                "publications.ui.publication.authors",
                SciPublicationsConstants.BUNDLE
            )
        );
        if (isSeriesStepEnabled()) {
            addStep(
                new PublicationSeriesPropertyStep(
                    itemModel, parent, selectedLangParameter),
                new GlobalizedMessage(
                    "publications.ui.publication.series",
                    SciPublicationsConstants.BUNDLE
                )
            );
        }
    }

    protected void addStep(final SimpleEditStep step,
                           final GlobalizedMessage label
    ) {
        segmentedPanel.addSegment(new Label(label), step);
    }

    protected boolean isSeriesStepEnabled() {
        return true;
    }

    protected static class PreFormattedTextFormatter
        extends DomainService
        implements DomainObjectPropertySheet.AttributeFormatter {

        public PreFormattedTextFormatter() {
            super();
        }

        @Override
        public String format(final Object obj,
                             final String attribute,
                             final PageState state) {

            final String str = (String) get(obj, attribute);
            if ((str == null) || str.trim().isEmpty()) {
                return (String) new GlobalizedMessage(
                    "cms.ui.unknown", CmsConstants.CMS_BUNDLE
                ).localize();
            } else {
                return String.format("<pre>%s</pre>", str);
            }
        }

    }

}
