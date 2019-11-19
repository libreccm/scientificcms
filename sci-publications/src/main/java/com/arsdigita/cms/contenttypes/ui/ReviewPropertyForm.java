/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.event.FormSubmissionListener;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ReviewPropertyForm extends ArticleInJournalPropertyForm
    implements FormProcessListener, FormInitListener, FormSubmissionListener {

    public static final String ID = "ReviewEdit";

    private ReviewPropertiesStep step;

    public ReviewPropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public ReviewPropertyForm(
        final ItemSelectionModel itemModel,
        final ReviewPropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        this.step = step;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {
        super.addWidgets();
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        super.init(event);

        final PageState state = event.getPageState();
        getReviewed().setVisible(state, false);

//        FormData data = fse.getFormData();
//        Review review = (Review) initBasicWidgets(fse);               
    }

    @Override
    public void process(final FormSectionEvent event) 
        throws FormProcessException {
        super.process(event);

//        FormData data = fse.getFormData();
//        Review review = (Review) processBasicWidgets(fse);      
    }

}
