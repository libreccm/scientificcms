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

import org.scientificcms.publications.SciPublicationsConstants;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationSeriesPropertyStep extends SimpleEditStep {

    private static final String ADD_SERIES_SHEET_NAME = "addSeries";

    public PublicationSeriesPropertyStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParameter) {
        this(itemModel, parent, selectedLanguageParameter, null);
    }

    public PublicationSeriesPropertyStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLanguageParameter,
        final String prefix
    ) {
        super(itemModel, parent, selectedLanguageParameter, prefix);

        final BasicItemForm addSeriesSheet = new PublicationSeriesAddForm(
            itemModel, selectedLanguageParameter
        );
        add(ADD_SERIES_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.series.add_series",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(addSeriesSheet, itemModel),
            addSeriesSheet.getSaveCancelSection().getCancelButton());

        final PublicationSeriesTable seriesTable = new PublicationSeriesTable(
            itemModel, this, selectedLanguageParameter
        );
        setDisplayComponent(seriesTable);
    }

}
