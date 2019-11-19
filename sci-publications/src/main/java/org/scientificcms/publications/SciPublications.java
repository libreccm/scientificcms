/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.modules.CcmModule;
import org.libreccm.modules.InitEvent;
import org.libreccm.modules.InstallEvent;
import org.libreccm.modules.RequiredModule;
import org.libreccm.modules.ShutdownEvent;
import org.libreccm.modules.UnInstallEvent;
import org.librecms.assets.AssetTypes;
import org.librecms.contenttypes.ContentTypes;
import org.scientificcms.publications.assets.JournalAsset;
import org.scientificcms.publications.assets.PublisherAsset;
import org.scientificcms.publications.assets.SeriesAsset;
import org.scientificcms.publications.contenttypes.ArticleInCollectedVolumeItem;
import org.scientificcms.publications.contenttypes.ArticleInJournalItem;
import org.scientificcms.publications.contenttypes.CollectedVolumeItem;
import org.scientificcms.publications.contenttypes.ExpertiseItem;
import org.scientificcms.publications.contenttypes.GreyLiteratureItem;
import org.scientificcms.publications.contenttypes.InProceedingsItem;
import org.scientificcms.publications.contenttypes.InternetArticleItem;
import org.scientificcms.publications.contenttypes.MonographItem;
import org.scientificcms.publications.contenttypes.ProceedingsItem;
import org.scientificcms.publications.contenttypes.TalkItem;
import org.scientificcms.publications.contenttypes.WorkingPaperItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@org.libreccm.modules.Module(
    requiredModules = {
        @RequiredModule(module = org.librecms.Cms.class)
    }
)
@ContentTypes({
    ArticleInCollectedVolumeItem.class,
    ArticleInJournalItem.class,
    CollectedVolumeItem.class,
    ExpertiseItem.class,
    GreyLiteratureItem.class,
    InProceedingsItem.class,
    InternetArticleItem.class,
    MonographItem.class,
    ProceedingsItem.class,
    TalkItem.class,
    WorkingPaperItem.class
})
@AssetTypes({
    JournalAsset.class,
    PublisherAsset.class,
    SeriesAsset.class
})
public class SciPublications implements CcmModule{

    @Override
    public void install(final InstallEvent event) {
        // Nothing
    }

    @Override
    public void init(final InitEvent event) {
        // Nothing
    }

    @Override
    public void shutdown(final ShutdownEvent event) {
        // Nothing
    }

    @Override
    public void uninstall(final UnInstallEvent event) {
        // Nothing
    }
    
}
