/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.assets.AssetSearchWidget;
import com.arsdigita.cms.ui.authoring.BasicItemForm;
import com.arsdigita.globalization.GlobalizedMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.PublicationManager;
import org.scientificcms.publications.SciPublicationsConfig;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.Series;
import org.scientificcms.publications.assets.SeriesAsset;
import org.scientificcms.publications.contenttypes.PublicationItem;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationSeriesAddForm
    extends BasicItemForm
    implements FormProcessListener, FormInitListener {

    private static final Logger LOGGER = LogManager.getLogger(
        PublicationSeriesAddForm.class
    );

    private static final String SERIES_SEARCH = "series";

    private final static SciPublicationsConfig CONFIG = SciPublicationsConfig
        .getConfig();

    private PublicationPropertiesStep step;

    private AssetSearchWidget seriesSearchWidget;

    private ItemSelectionModel itemModel;

    private TextField volumeOfSeries;

    public PublicationSeriesAddForm(final ItemSelectionModel itemModel,
                                    final StringParameter selectedLanguageParam) {
        super("SeriesEntryForm", itemModel, selectedLanguageParam);
        this.itemModel = itemModel;
    }

    @Override
    protected void addWidgets() {

        seriesSearchWidget = new AssetSearchWidget(
            SERIES_SEARCH,
            SeriesAsset.class
        );
        seriesSearchWidget.setLabel(
            new GlobalizedMessage(
                "publications.ui.series.select_series",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(seriesSearchWidget);

        final ParameterModel volumeOfSeriesParam = new StringParameter(
            SciPublicationsController.VOLUME_OF_SERIES
        );
        volumeOfSeries = new TextField(volumeOfSeriesParam);
        volumeOfSeries.setLabel(
            new GlobalizedMessage(
                "publications.ui.series.volume_of_series",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(volumeOfSeries);
    }

    @Override
    public void init(FormSectionEvent event) throws FormProcessException {

        final PageState state = event.getPageState();

        setVisible(state, true);
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {

        final FormData data = event.getFormData();
        final PageState state = event.getPageState();

        final PublicationItem<?> item
                                     = (PublicationItem<?>) getItemSelectionModel()
                .getSelectedObject(state);

        if (!(getSaveCancelSection().getCancelButton().isSelected(state))) {

            Series series = (Series) data.get(SERIES_SEARCH);

            final PublicationManager publicationManager = CdiUtil
                .createCdiUtil().findBean(PublicationManager.class);
            publicationManager.addSeries(
                series,
                item.getPublication(),
                (String) volumeOfSeries.getValue(state)
            );
        }

        init(event);
    }

}
