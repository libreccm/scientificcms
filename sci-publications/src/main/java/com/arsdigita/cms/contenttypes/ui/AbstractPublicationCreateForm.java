/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.CreationSelector;
import com.arsdigita.cms.ui.authoring.PageCreateForm;

import org.librecms.contentsection.ContentItemInitializer;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.contenttypes.PublicationItem;

import java.util.Locale;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 * @param <P>
 * @param <T>
 */
public abstract class AbstractPublicationCreateForm<P extends Publication, T extends PublicationItem<P>>
    extends PageCreateForm {

    public AbstractPublicationCreateForm(
        final ItemSelectionModel itemModel,
        final CreationSelector creationSelector,
        final StringParameter selectedLanguageParam
    ) {
        super(itemModel, creationSelector, selectedLanguageParam);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ContentItemInitializer<?> getItemInitializer(
        final FormData data, final PageState state) {

        return item -> ((T) item).setPublication(
            createPublication(data)
        );
    }

    protected P createPublication(final FormData data) {

        final Locale locale = new Locale((String) data.get(LANGUAGE));
        final String title = (String) data.get(TITLE);

        final P publication = createPublication();
        publication.getTitle().addValue(locale, title);

        return publication;
    }

    protected abstract P createPublication();

}
