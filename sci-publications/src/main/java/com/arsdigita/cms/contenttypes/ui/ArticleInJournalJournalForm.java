/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.globalization.GlobalizedMessage;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.ArticleInJournal;
import org.scientificcms.publications.Journal;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.ArticleInJournalItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ArticleInJournalJournalForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener {
    
    private JournalSearchWidget journalSearch;
    private final String JOURNAL_SEARCH = "journal";
    //private final static PublicationsConfig config = new PublicationsConfig();

    public ArticleInJournalJournalForm(
        final ItemSelectionModel itemSelectionModel,
        final StringParameter selectedLanguageParam
    ) {
        
        super(
            "ArticleInJournalJournal",
            itemSelectionModel,
            selectedLanguageParam
        );
    }
    
     @Override
    protected void addWidgets() {
        
        journalSearch = new JournalSearchWidget(JOURNAL_SEARCH);
        journalSearch.setLabel(
            new GlobalizedMessage(
                            "publications.ui.articleInJournal.selectJournal",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(journalSearch);
    }

     @Override
    public void init(final FormSectionEvent event) throws FormProcessException {
        final PageState state = event.getPageState();

        setVisible(state, true);
    }
    
     @Override
    public void process(final FormSectionEvent event) 
        throws FormProcessException {
        final FormData data = event.getFormData();
        final PageState state = event.getPageState();
        
        final ArticleInJournalItem articleItem = (ArticleInJournalItem) getItemSelectionModel().
                getSelectedObject(state); 
        final ArticleInJournal article = articleItem.getPublication();

        if (this.getSaveCancelSection().getSaveButton().isSelected(state)) {
            final Journal journal = (Journal) data.get(JOURNAL_SEARCH);

            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final ArticleInJournalController controller = cdiUtil
            .findBean(ArticleInJournalController.class);
            controller.setJournal(
                article.getPublicationId(), journal.getJournalId()
            );
        }

        init(event);
    }

}
