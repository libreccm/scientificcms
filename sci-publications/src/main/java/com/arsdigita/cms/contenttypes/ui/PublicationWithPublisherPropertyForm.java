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
import com.arsdigita.bebop.event.FormSubmissionListener;
import com.arsdigita.bebop.event.ParameterEvent;
import com.arsdigita.bebop.event.ParameterListener;
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.IntegerParameter;
import com.arsdigita.bebop.parameters.ParameterData;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.globalization.GlobalizedMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.PublicationWithPublisher;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.PublicationWithPublisherItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationWithPublisherPropertyForm
    extends PublicationPropertyForm
    implements FormProcessListener,
               FormInitListener,
               FormSubmissionListener {

    private static final Logger LOGGER = LogManager.getLogger(
        PublicationWithPublisherPropertyForm.class
    );

    public static final String ID = "PublicationWithPublisherEdit";

    private static final String PUBLISHER_SEARCH = "publisher";

    private final PublicationWithPublisherPropertiesStep step;

    private final ItemSelectionModel itemModel;

    private final StringParameter selectedLangParam;

    private PublisherSearchWidget publisherSearch;

    public PublicationWithPublisherPropertyForm(
        final ItemSelectionModel itemModel,
        final StringParameter selectedLangParam
    ) {
        this(itemModel, null, selectedLangParam);
    }

    public PublicationWithPublisherPropertyForm(
        ItemSelectionModel itemModel,
        PublicationWithPublisherPropertiesStep step,
        final StringParameter selectedLangParam
    ) {
        super(itemModel, step, selectedLangParam);
        this.step = step;
        this.itemModel = itemModel;
        this.selectedLangParam = selectedLangParam;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        final ParameterModel isbn10Param = new StringParameter(
            SciPublicationsWithPublisherController.ISBN10
        );
        final TextField isbn10 = new TextField(isbn10Param);
        isbn10.setMaxLength(17);
        isbn10.addValidationListener(new ParameterListener() {

            public void validate(final ParameterEvent event)
                throws FormProcessException {
                ParameterData data = event.getParameterData();
                String value = (String) data.getValue();

                if (value.isEmpty()) {
                    return;
                }

                value = value.replace("-", "");

                if (value.length() != 10) {
                    data.invalidate();
                    data.addError(
                        new GlobalizedMessage(
                            "publications.ui.invalid_isbn10",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                }

                try {
                    Long num = Long.parseLong(value);
                } catch (NumberFormatException ex) {
                    data.invalidate();
                    data.addError(
                        new GlobalizedMessage(
                            "publications.ui.invalid_isbn10",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                }
            }

        });
        isbn10.setLabel(new GlobalizedMessage(
            "publications.ui.with_publisher.isbn10"));
        add(isbn10);

        final ParameterModel isbn13Param = new StringParameter(
            SciPublicationsWithPublisherController.ISBN13
        );
        final TextField isbn13 = new TextField(isbn13Param);
        isbn13.setMaxLength(17);
        isbn13.addValidationListener(new ParameterListener() {

            public void validate(final ParameterEvent event)
                throws FormProcessException {
                ParameterData data = event.getParameterData();
                String value = (String) data.getValue();

                if (value.isEmpty()) {
                    return;
                }

                value = value.replace("-", "");

                if (value.length() != 13) {
                    data.invalidate();
                    data.addError(
                        new GlobalizedMessage(
                            "publications.ui.invalid_isbn13",
                            SciPublicationsConstants.BUNDLE
                        )
                    );
                }

                try {
                    Long num = Long.parseLong(value);
                } catch (NumberFormatException ex) {
                    data.invalidate();
                    data.addError(new GlobalizedMessage(
                        "publications.ui.invalid_isbn13",
                        SciPublicationsConstants.BUNDLE
                    )
                    );
                }
            }

        });
        isbn13.setLabel(new GlobalizedMessage(
            "publications.ui.with_publisher.isbn13"));
        add(isbn13);

        final ParameterModel volumeParam = new IntegerParameter(
            SciPublicationsWithPublisherController.VOLUME
        );
        final TextField volume = new TextField(volumeParam);
        volume.setLabel(
            new GlobalizedMessage(
                "publications.ui.collected_volume.volume",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(volume);

        final ParameterModel numberOfVolumesParam = new IntegerParameter(
            SciPublicationsWithPublisherController.NUMBER_OF_VOLUMES
        );
        TextField numberOfVolumes = new TextField(numberOfVolumesParam);
        numberOfVolumes.setLabel(
            new GlobalizedMessage(
                "publications.ui.collected_volume.number_of_volumes",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(numberOfVolumes);

        final ParameterModel numberOfPagesParam = new IntegerParameter(
            SciPublicationsWithPublisherController.NUMBER_OF_PAGES
        );
        final TextField numberOfPages = new TextField(numberOfPagesParam);
        numberOfPages.setLabel(
            new GlobalizedMessage(
                "publications.ui.collected_volume.number_of_pages",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(numberOfPages);

        ParameterModel editionModel = new StringParameter(
            SciPublicationsWithPublisherController.EDITION
        );

        final TextField edition = new TextField(editionModel);
        edition.setLabel(
            new GlobalizedMessage(
                "publications.ui.collected_volume.edition",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(edition);

    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        final FormData data = event.getFormData();
        super.init(event);

        final PublicationWithPublisherItem<?> publicationItem
                                                  = (PublicationWithPublisherItem) super
                .initBasicWidgets(event);

        final PublicationWithPublisher publication = publicationItem
            .getPublication();

        data.put(
            SciPublicationsWithPublisherController.ISBN10,
            publication.getIsbn10()
        );
        data.put(
            SciPublicationsWithPublisherController.ISBN13,
            publication.getIsbn10()
        );
        data.put(
            SciPublicationsWithPublisherController.VOLUME,
            publication.getVolume()
        );
        data.put(
            SciPublicationsWithPublisherController.NUMBER_OF_VOLUMES,
            publication.getNumberOfVolumes()
        );
        data.put(
            SciPublicationsWithPublisherController.NUMBER_OF_PAGES,
            publication.getNumberOfPages()
        );
        data.put(
            SciPublicationsWithPublisherController.EDITION,
            publication.getEdition()
        );
    }

    @Override
    public void process(final FormSectionEvent event)
        throws FormProcessException {

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        super.process(event);

        final PublicationWithPublisherItem<?> item
                                                  = (PublicationWithPublisherItem) super
                .processBasicWidgets(event);

        if ((item != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {
            final PublicationWithPublisher publication = item
                .getPublication();

            final Map<String, Object> data = new HashMap<>();

            data.put(
                SciPublicationsWithPublisherController.ISBN10,
                formData.get(SciPublicationsWithPublisherController.ISBN10)
            );

            data.put(
                SciPublicationsWithPublisherController.ISBN13,
                formData.get(SciPublicationsWithPublisherController.ISBN13)
            );

            data.put(
                SciPublicationsWithPublisherController.VOLUME,
                formData.get(SciPublicationsWithPublisherController.VOLUME)
            );

            data.put(
                SciPublicationsWithPublisherController.NUMBER_OF_PAGES,
                formData.get(
                    SciPublicationsWithPublisherController.NUMBER_OF_PAGES
                )
            );

            data.put(
                SciPublicationsWithPublisherController.NUMBER_OF_VOLUMES,
                formData.get(
                    SciPublicationsWithPublisherController.NUMBER_OF_VOLUMES
                )
            );

            data.put(
                SciPublicationsWithPublisherController.EDITION,
                formData.get(SciPublicationsWithPublisherController.EDITION)
            );

            final Locale selectedLocale = SelectedLanguageUtil.selectedLocale(
                state, selectedLangParam
            );

            final SciPublicationsWithPublisherController controller = CdiUtil
                .createCdiUtil()
                .findBean(SciPublicationsWithPublisherController.class);
            controller.savePublication(
                publication.getPublicationId(), selectedLocale, data
            );
        }
    }

}
