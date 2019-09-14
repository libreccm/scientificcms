/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.configuration.Configuration;
import org.libreccm.configuration.ConfigurationManager;
import org.libreccm.configuration.Setting;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Configuration
public class SciPublicationsConfig {

    @Setting
    private boolean firstPublishedPropertyEnabled = false;

    @Setting
    private boolean languagePropertyEnabled = false;

    @Setting
    private boolean abstractHtmlEnabled = true;

    @Setting
    private boolean shortDescriptionHtmlEnabled = true;

    @Setting
    private boolean miscHtmlEnabled = true;

    public SciPublicationsConfig() {
        super();
    }

    public static SciPublicationsConfig getConfig() {
        final ConfigurationManager confManager = CdiUtil
            .createCdiUtil()
            .findBean(ConfigurationManager.class);
        return confManager.findConfiguration(SciPublicationsConfig.class);
    }

    public boolean isFirstPublishedPropertyEnabled() {
        return firstPublishedPropertyEnabled;
    }

    public void setFirstPublishedPropertyEnabled(
        final boolean firstPublishedPropertyEnabled
    ) {
        this.firstPublishedPropertyEnabled = firstPublishedPropertyEnabled;
    }

    public boolean isLanguagePropertyEnabled() {
        return languagePropertyEnabled;
    }

    public void setLanguagePropertyEnabled(
        final boolean languagePropertyEnabled
    ) {
        this.languagePropertyEnabled = languagePropertyEnabled;
    }

    public boolean isAbstractHtmlEnabled() {
        return abstractHtmlEnabled;
    }

    public void setAbstractHtmlEnabled(final boolean abstractHtmlEnabled) {
        this.abstractHtmlEnabled = abstractHtmlEnabled;
    }

    public boolean isMiscHtmlEnabled() {
        return miscHtmlEnabled;
    }

    public void setMiscHtmlEnabled(final boolean miscHtmlEnabled) {
        this.miscHtmlEnabled = miscHtmlEnabled;
    }

    public boolean isShortDescriptionHtmlEnabled() {
        return shortDescriptionHtmlEnabled;
    }

    public void setShortDescriptionHtmlEnabled(
        final boolean shortDescriptionHtmlEnabled
    ) {
        this.shortDescriptionHtmlEnabled = shortDescriptionHtmlEnabled;
    }

}
