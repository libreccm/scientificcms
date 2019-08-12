/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.contenttypes.sciproject;

import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.configuration.Configuration;
import org.libreccm.configuration.ConfigurationManager;
import org.libreccm.configuration.Setting;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Configuration
public class SciProjectConfig {

    @Setting
    private String contactTypesBundleName
                       = "org.scientificcms.contenttypes.sciproject.DefaultContactTypes";
    
    @Setting
    private boolean enableSponsor = true;
    
    @Setting
    private boolean enableFunding = true;

    public static SciProjectConfig getConfig() {
        final ConfigurationManager confManager = CdiUtil.createCdiUtil()
            .findBean(ConfigurationManager.class);
        return confManager.findConfiguration(SciProjectConfig.class);
    }

    public SciProjectConfig() {
        super();
    }

    public String getContactTypesBundleName() {
        return contactTypesBundleName;
    }

    public void setContactTypesBundleName(final String contactTypesBundle) {
        this.contactTypesBundleName = contactTypesBundle;
    }

    public boolean isEnableSponsor() {
        return enableSponsor;
    }

    public void setEnableSponsor(final boolean enableSponsor) {
        this.enableSponsor = enableSponsor;
    }

    public boolean isEnableFunding() {
        return enableFunding;
    }

    public void setEnableFunding(final boolean enableFunding) {
        this.enableFunding = enableFunding;
    }
    
    

}
