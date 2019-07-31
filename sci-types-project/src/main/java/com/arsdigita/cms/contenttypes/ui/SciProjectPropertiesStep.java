/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.AuthoringKitWizard;
import com.arsdigita.cms.ui.authoring.SimpleEditStep;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectPropertiesStep extends SimpleEditStep {
    
    public SciProjectPropertiesStep(ItemSelectionModel itemModel,
                                    AuthoringKitWizard parent,
                                    StringParameter selectedLanguageParam) {
        super(itemModel, parent, selectedLanguageParam);
    }
    
}
