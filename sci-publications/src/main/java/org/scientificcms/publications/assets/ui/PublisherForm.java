/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets.ui;

import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.NotEmptyValidationListener;
import com.arsdigita.bebop.parameters.NotNullValidationListener;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ui.assets.AbstractAssetForm;
import com.arsdigita.cms.ui.assets.AssetPane;
import com.arsdigita.globalization.GlobalizedMessage;

import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.assets.PublisherAsset;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublisherForm extends AbstractAssetForm<PublisherAsset> {

    private TextField nameField;

    private TextField placeField;

    public PublisherForm(final AssetPane assetPane) {
        super(assetPane);
    }

    @Override
    protected void addWidgets() {
        final ParameterModel nameParam = new StringParameter(
            PublisherFormController.NAME
        );
        nameField = new TextField(nameParam);
        nameField.setLabel(
            new GlobalizedMessage(
                "publications.ui.publisher.name",
                SciPublicationsConstants.BUNDLE
            )
        );
        nameField.addValidationListener(new NotNullValidationListener());
        nameField.addValidationListener(new NotEmptyValidationListener());
        add(nameField);

        final ParameterModel placeParam = new StringParameter(
            PublisherFormController.PLACE
        );
        placeField = new TextField(placeParam);
        placeField.setLabel(
            new GlobalizedMessage(
                "publications.ui.publisher.place",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(placeField);

    }

    @Override
    protected Class<PublisherAsset> getAssetClass() {
        return PublisherAsset.class;
    }

    @Override
    protected void showLocale(final PageState state) {
        // Nothing
    }

    @Override
    protected Map<String, Object> collectData(final FormSectionEvent event)
        throws FormProcessException {

        final PageState state = event.getPageState();

        final Map<String, Object> data = new HashMap<>();

        data.put(
            PublisherFormController.NAME, nameField.getValue(state)
        );

        data.put(
            PublisherFormController.PLACE, placeField.getValue(state)
        );

        return data;
    }

}
