/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.CreationSelector;

import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.contenttypes.ProceedingsItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ProceedingsCreateForm 
    extends AbstractPublicationCreateForm<Proceedings, ProceedingsItem>{

    public ProceedingsCreateForm(final ItemSelectionModel itemModel,
                                 final CreationSelector creationSelector,
                                 final StringParameter selectedLanguageParam) {
        super(itemModel, creationSelector, selectedLanguageParam);
    }

    @Override
    protected Proceedings createPublication() {
        return new Proceedings();
    }
    
}
