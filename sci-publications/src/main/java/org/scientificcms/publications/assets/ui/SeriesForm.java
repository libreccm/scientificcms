/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets.ui;

import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.form.TextArea;
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ui.assets.AbstractAssetForm;
import com.arsdigita.cms.ui.assets.AssetPane;
import com.arsdigita.globalization.GlobalizedMessage;

import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.assets.SeriesAsset;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SeriesForm extends AbstractAssetForm<SeriesAsset> {

    private TextField titleField;

    private TextArea descriptionArea;

    public SeriesForm(final AssetPane assetPane) {
        super(assetPane);
    }

    @Override
    protected void addWidgets() {
        super.addWidgets();

        final ParameterModel titleParam = new StringParameter(
            SeriesFormController.TITLE
        );
        titleField = new TextField(titleParam);
        titleField.setLabel(
            new GlobalizedMessage(
                "publications.ui.series.title",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(titleField);

        final ParameterModel descriptionParam = new StringParameter(
            SeriesFormController.DESCRIPTION
        );
        descriptionArea = new TextArea(descriptionParam);
        descriptionArea.setLabel(
            new GlobalizedMessage(
                "publications.ui.series.abstract",
                SciPublicationsConstants.BUNDLE
            )
        );
        descriptionArea.setCols(60);
        descriptionArea.setRows(18);
        add(descriptionArea);
    }

    @Override
    protected Class<SeriesAsset> getAssetClass() {
        return SeriesAsset.class;
    }

    @Override
    protected void showLocale(final PageState state) {
        final Long selectedAssetId = getSelectedAssetId(state);

        if (selectedAssetId != null) {
            final Map<String, Object> data = getController()
                .getAssetData(
                    selectedAssetId, getAssetClass(), getSelectedLocale(state)
                );

            titleField.setValue(
                state,
                data.get(SeriesFormController.TITLE)
            );

            descriptionArea.setValue(
                state,
                data.get(SeriesFormController.DESCRIPTION)
            );
        }
    }

    @Override
    protected Map<String, Object> collectData(FormSectionEvent event)
        throws FormProcessException {
        final PageState state = event.getPageState();

        final Map<String, Object> data = new HashMap<>();

        data.put(
            SeriesFormController.DESCRIPTION, descriptionArea.getValue(state)
        );

        data.put(
            SeriesFormController.TITLE, titleField.getValue(state)
        );

        return data;
    }

}
