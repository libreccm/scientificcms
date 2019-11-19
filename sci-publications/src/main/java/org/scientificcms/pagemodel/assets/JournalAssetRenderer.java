/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.assets;

import org.librecms.contentsection.Asset;
import org.librecms.pagemodel.assets.AbstractAssetRenderer;
import org.librecms.pagemodel.assets.AssetRenderer;
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
@AssetRenderer(renders = JournalAsset.class)
public class JournalAssetRenderer extends AbstractAssetRenderer {

    @Override
    protected void renderAsset(
        final Asset asset,
        final Locale language,
        final Map<String, Object> result
    ) {
        final JournalAsset journalAsset;
        if (asset instanceof JournalAsset) {
            journalAsset = (JournalAsset) asset;
        } else {
            return;
        }

        final Journal journal = journalAsset.getJournal();
        final Map<String, Object> journalData = new HashMap<>();
        journalData.put(
            "description", journal.getDescription().getValue(language)
        );
        journalData.put("firstYear", journal.getFirstYear());
        journalData.put("issn", journal.getIssn());
        journalData.put("journalId", journal.getJournalId());
        journalData.put("lastYear", journal.getLastYear());
        journalData.put("symbol", journal.getSymbol());
        journalData.put("title", journal.getTitle());
        journalData.put("uuid", journal.getUuid());
        
        result.put("journal", journalData);
    }

}
