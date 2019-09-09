/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.CreationSelector;

import org.scientificcms.publications.Expertise;
import org.scientificcms.publications.contenttypes.ExpertiseItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class ExpertiseCreateForm 
    extends AbstractPublicationCreateForm<Expertise, ExpertiseItem>{

    public ExpertiseCreateForm(final ItemSelectionModel itemModel,
                               final CreationSelector creationSelector,
                               final StringParameter selectedLanguageParam) {
        super(itemModel, creationSelector, selectedLanguageParam);
    }

    @Override
    protected Expertise createPublication() {
        return new Expertise();
    }
    
}
