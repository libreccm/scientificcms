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
public class CollectedVolumeArticlesStep extends SimpleEditStep {

    private static final String ADD_ARTICLE_SHEET_NAME = "addArticle";

    public CollectedVolumeArticlesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, parent, selectedLangParam, null);
    }

    public CollectedVolumeArticlesStep(
        final ItemSelectionModel itemModel,
        final AuthoringKitWizard parent,
        final StringParameter selectedLangParam,
        final String prefix
    ) {
        super(itemModel, parent, selectedLangParam, prefix);

        final BasicItemForm addArticleSheet = new CollectedVolumeArticlesAddForm(
            itemModel, selectedLangParam
        );
        add(
            ADD_ARTICLE_SHEET_NAME,
            new GlobalizedMessage(
                "publications.ui.collected_volume.add_article",
                SciPublicationsConstants.BUNDLE
            ),
            new WorkflowLockedComponentAccess(addArticleSheet, itemModel),
            addArticleSheet.getSaveCancelSection().getCancelButton());

        final CollectedVolumeArticlesTable articlesTable
                                               = new CollectedVolumeArticlesTable(
                itemModel,
                selectedLangParam
            );
        setDisplayComponent(articlesTable);
    }

}
