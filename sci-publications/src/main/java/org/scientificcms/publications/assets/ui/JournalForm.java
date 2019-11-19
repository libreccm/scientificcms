/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets.ui;

import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.event.ParameterEvent;
import com.arsdigita.bebop.event.ParameterListener;
import com.arsdigita.bebop.form.TextArea;
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.IntegerParameter;
import com.arsdigita.bebop.parameters.ParameterData;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ui.assets.AbstractAssetForm;
import com.arsdigita.cms.ui.assets.AssetPane;
import com.arsdigita.globalization.GlobalizedMessage;

import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.assets.JournalAsset;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class JournalForm extends AbstractAssetForm<JournalAsset> {

    private TextField symbolField;

    private TextField issnField;

    private TextField firstYearField;

    private TextField lastYearField;

    private TextArea descriptionArea;

    public JournalForm(final AssetPane assetPane) {
        super(assetPane);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        final ParameterModel symbolModel = new StringParameter(
            JournalFormController.SYMBOL
        );
        symbolField = new TextField(symbolModel);
        symbolField.setLabel(
            new GlobalizedMessage(
                "publications.ui.journal.symbol",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(symbolField);

        final ParameterModel issnParam = new StringParameter(
            JournalFormController.ISSN
        );
        issnField = new TextField(issnParam);
        issnField.addValidationListener(new ParameterListener() {

            @Override
            public void validate(final ParameterEvent event) throws
                FormProcessException {
                final ParameterData data = event.getParameterData();
                String value = (String) data.getValue();

                if (value.isEmpty()) {
                    return;
                }

                value = value.replace("-", "");

                if (value.length() != 8) {
                    data.invalidate();
                    data.addError(
                        new GlobalizedMessage(
                            "publications.ui.invalid_issn",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                }

                try {
                    final Long num = Long.parseLong(value);
                } catch (NumberFormatException ex) {
                    data.invalidate();
                    data.addError(
                        new GlobalizedMessage(
                            "publications.ui.invalid_issn",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                }
            }

        });
        issnField.setLabel(
            new GlobalizedMessage(
                "publications.ui.journal.issn", SciPublicationsConstants.BUNDLE
            )
        );
        add(issnField);

        final ParameterModel firstYearParam = new IntegerParameter(
            JournalFormController.FIRST_YEAR
        );
        firstYearField = new TextField(firstYearParam);
        firstYearField.setLabel(
            new GlobalizedMessage(
                "publications.ui.journal.firstYearOfPublication",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(firstYearField);

        final ParameterModel lastYearParam = new IntegerParameter(
            JournalFormController.LAST_YEAR
        );
        lastYearField = new TextField(lastYearParam);
        lastYearField.setLabel(
            new GlobalizedMessage(
                "publications.ui.journal.lastYearOfPublication",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(lastYearField);

        final ParameterModel descriptionParam = new StringParameter(
            JournalFormController.DESCRIPTION
        );
        descriptionArea = new TextArea(descriptionParam);
        descriptionArea.setLabel(
            new GlobalizedMessage(
                "publications.ui.journal.abstract",
                SciPublicationsConstants.BUNDLE
            )
        );
        descriptionArea.setCols(60);
        descriptionArea.setRows(18);
        add(descriptionArea);
    }

    @Override
    protected Class<JournalAsset> getAssetClass() {
        return JournalAsset.class;
    }

    @Override
    protected void showLocale(final PageState state) {
        final Long selectedAssetId = getSelectedAssetId(state);

        if (selectedAssetId != null) {
            final Map<String, Object> data = getController()
                .getAssetData(
                    selectedAssetId, getAssetClass(), getSelectedLocale(state)
                );

            descriptionArea
                .setValue(
                    state,
                    data.get(JournalFormController.DESCRIPTION)
                );
        }
    }

    @Override
    protected Map<String, Object> collectData(
        final FormSectionEvent event) throws FormProcessException {

        final PageState state = event.getPageState();

        final Map<String, Object> data = new HashMap<>();

        data.put(
            JournalFormController.DESCRIPTION, descriptionArea.getValue(state)
        );

        data.put(
            JournalFormController.FIRST_YEAR, firstYearField.getValue(state)
        );

        data.put(
            JournalFormController.ISSN, issnField.getValue(state)
        );

        data.put(
            JournalFormController.LAST_YEAR, lastYearField.getValue(state)
        );

        data.put(
            JournalFormController.SYMBOL, symbolField.getValue(state)
        );

        return data;
    }

}
