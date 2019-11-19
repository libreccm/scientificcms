/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets.ui;

import com.arsdigita.cms.ui.assets.AbstractAssetFormController;
import com.arsdigita.cms.ui.assets.IsControllerForAssetType;

import org.scientificcms.publications.Journal;
import org.scientificcms.publications.assets.JournalAsset;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
@IsControllerForAssetType(JournalAsset.class)
public class JournalFormController
    extends AbstractAssetFormController<JournalAsset> {

    protected static final String SYMBOL = "symbol";

    protected static final String ISSN = "issn";

    protected static final String FIRST_YEAR = "firstYear";

    protected static final String LAST_YEAR = "lastYear";

    protected static final String DESCRIPTION = "description";

    
    @Override
    protected Map<String, Object> getAssetData(
        final JournalAsset asset, final Locale selectedLocale
    ) {
        final Map<String, Object> data = new HashMap<>();

        final Journal journal = asset.getJournal();

        data.put(SYMBOL, journal.getSymbol());
        data.put(ISSN, journal.getIssn());
        data.put(FIRST_YEAR, journal.getFirstYear());
        data.put(LAST_YEAR, journal.getLastYear());
        data.put(DESCRIPTION, journal.getDescription().getValue(selectedLocale));

        return data;
    }

    @Override
    public void updateAssetProperties(
        final JournalAsset asset,
        final Locale selectedLocale,
        final Map<String, Object> data
    ) {
        final Journal journal = asset.getJournal();
        
        if (data.containsKey(SYMBOL)) {
            journal.setSymbol((String) data.get(SYMBOL));
        }
        
        if (data.containsKey(ISSN)) {
            journal.setIssn((String) data.get(ISSN));
        }
        
        if (data.containsKey(FIRST_YEAR)) {
            journal.setFirstYear((Integer) data.get(FIRST_YEAR));
        }
        
        if (data.containsKey(LAST_YEAR)) {
            journal.setLastYear((Integer) data.get(LAST_YEAR));
        }
        
        if (data.containsKey(DESCRIPTION)) {
            journal.getDescription().addValue(
                selectedLocale, (String) data.get(DESCRIPTION)
            );
        }
    }

}
