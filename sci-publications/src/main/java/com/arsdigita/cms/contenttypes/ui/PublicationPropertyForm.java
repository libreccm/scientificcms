/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormData;
import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.Label;
import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.Text;
import com.arsdigita.bebop.event.FormInitListener;
import com.arsdigita.bebop.event.FormProcessListener;
import com.arsdigita.bebop.event.FormSectionEvent;
import com.arsdigita.bebop.event.FormSubmissionListener;
import com.arsdigita.bebop.event.PrintEvent;
import com.arsdigita.bebop.event.PrintListener;
import com.arsdigita.bebop.form.Option;
import com.arsdigita.bebop.form.SingleSelect;
import com.arsdigita.bebop.form.TextArea;
import com.arsdigita.bebop.form.TextField;
import com.arsdigita.bebop.parameters.IntegerParameter;
import com.arsdigita.bebop.parameters.ParameterModel;
import com.arsdigita.bebop.parameters.StringParameter;
import com.arsdigita.cms.ItemSelectionModel;
import com.arsdigita.cms.ui.CMSDHTMLEditor;
import com.arsdigita.cms.ui.authoring.BasicPageForm;
import com.arsdigita.cms.ui.authoring.SelectedLanguageUtil;
import com.arsdigita.globalization.GlobalizedMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.libreccm.cdi.utils.CdiUtil;
import org.libreccm.l10n.GlobalizationHelper;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.SciPublicationsConfig;
import org.scientificcms.publications.SciPublicationsConstants;
import org.scientificcms.publications.contenttypes.PublicationItem;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationPropertyForm
    extends BasicPageForm
    implements FormProcessListener, FormInitListener, FormSubmissionListener {

    private static final Logger LOGGER = LogManager.getLogger(
        PublicationPropertyForm.class
    );

    public static final String ID = "Publication_edit";

    private final static SciPublicationsConfig CONFIG = SciPublicationsConfig
        .getConfig();

    private PublicationPropertiesStep step;

    private final StringParameter selectedLanguageParam;

    public PublicationPropertyForm(final ItemSelectionModel itemModel,
                                   final StringParameter selectedLangParam) {

        this(itemModel, null, selectedLangParam);
    }

    public PublicationPropertyForm(final ItemSelectionModel itemModel,
                                   final PublicationPropertiesStep step,
                                   final StringParameter selectedLangParam) {

        super(ID, itemModel, selectedLangParam);
        this.step = step;
        this.selectedLanguageParam = selectedLangParam;
        addSubmissionListener(this);
    }

    @Override
    protected void addWidgets() {

        super.addWidgets();

        final ParameterModel yearOfPublicationParam = new IntegerParameter(
            "yearOfPublication"
        );
        final TextField yearOfPublication
                            = new TextField(yearOfPublicationParam);
        yearOfPublication.setMaxLength(4);
        yearOfPublication.setLabel(
            new GlobalizedMessage(
                "publications.ui.publication.year_of_publication",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(yearOfPublication);

        final ParameterModel firstPublishedParam = new IntegerParameter(
            SciPublicationsController.YEAR_FIRST_PUBLISHED);
        final TextField firstPublished = new TextField(firstPublishedParam);
        firstPublished.setLabel(
            new GlobalizedMessage(
                "publications.ui.publication.first_published",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(firstPublished);
        final ParameterModel langParam = new StringParameter(
            SciPublicationsController.LANGUAGE_OF_PUBLICATION
        );
        final SingleSelect lang = new SingleSelect(langParam);
        final Locale[] locales = Locale.getAvailableLocales();
        lang.addOption(new Option("", new Text("")));
        Arrays.sort(locales, new Comparator<Locale>() {

                    @Override
                    public int compare(final Locale locale1,
                                       final Locale locale2) {
                        final GlobalizationHelper globalizationHelper = CdiUtil
                            .createCdiUtil()
                            .findBean(GlobalizationHelper.class);
                        final Locale negLocale = globalizationHelper
                            .getNegotiatedLocale();
                        final Collator collator = Collator
                            .getInstance(negLocale);

                        return collator.compare(locale1
                            .getDisplayName(negLocale),
                                                locale2
                                                    .getDisplayName(negLocale));
                    }

                });

        for (Locale locale : locales) {
            final Locale currentLocale = locale;

            final Label optionLabel = new Label(new PrintListener() {

                @Override
                public void prepare(final PrintEvent event) {
                    final Label target = (Label) event.getTarget();
                    final GlobalizationHelper globalizationHelper = CdiUtil
                        .createCdiUtil()
                        .findBean(GlobalizationHelper.class);
                    target.setLabel(
                        currentLocale.getDisplayName(
                            globalizationHelper.getNegotiatedLocale()
                        )
                    );
                }

            });
            lang.addOption(new Option(locale.toString(), optionLabel));
        }
        lang.setLabel(
            new GlobalizedMessage(
                "publications.ui.publication.language",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(lang);

        final ParameterModel abstractParam = new StringParameter(
            SciPublicationsController.ABSTRACT
        );
        final TextArea abstractArea;
        if (CONFIG.isAbstractHtmlEnabled()) {
            abstractArea = new CMSDHTMLEditor(abstractParam);
        } else {
            abstractArea = new TextArea(abstractParam);
        }
        abstractArea.setCols(60);
        abstractArea.setRows(18);
        abstractArea.setLabel(
            new GlobalizedMessage(
                "publications.ui.publication.abstract",
                SciPublicationsConstants.BUNDLE
            )
        );
        add(abstractArea);

        final ParameterModel shortDescParam = new StringParameter(
            SciPublicationsController.SHORT_DESCRIPTION
        );
        final TextArea shortDesc;
        if (CONFIG.isShortDescriptionHtmlEnabled()) {
            shortDesc = new CMSDHTMLEditor(shortDescParam);
        } else {
            shortDesc = new TextArea(shortDescParam);
        }
        shortDesc.setLabel(
            new GlobalizedMessage(
                "publications.ui.publication.short_description",
                SciPublicationsConstants.BUNDLE
            )
        );
        shortDesc.setCols(60);
        shortDesc.setRows(10);
        add(shortDesc);

        final ParameterModel miscParam = new StringParameter(
            SciPublicationsController.MISC
        );
        final TextArea misc;
        if (CONFIG.isMiscHtmlEnabled()) {
            misc = new CMSDHTMLEditor(miscParam);
        } else {
            misc = new TextArea(miscParam);
        }
        misc.setLabel(
            new GlobalizedMessage(
                "publications.ui.publication.misc",
                SciPublicationsConstants.BUNDLE
            )
        );
        misc.setCols(60);
        misc.setRows(18);
        add(misc);
    }

    @Override
    public void init(final FormSectionEvent event) throws FormProcessException {

        final FormData data = event.getFormData();
        final PublicationItem<?> item = (PublicationItem<?>) super
            .initBasicWidgets(event);

        final PageState state = event.getPageState();

        final Locale selectedLanguage = SelectedLanguageUtil
            .selectedLocale(state, selectedLanguageParam);

        data.put(SciPublicationsController.YEAR_OF_PUBLICATION,
                 item.getPublication().getYearOfPublication());
        data.put(SciPublicationsController.YEAR_FIRST_PUBLISHED,
                 item.getPublication().getYearFirstPublished());
        data.put(SciPublicationsController.LANGUAGE_OF_PUBLICATION,
                 item.getPublication().getLanguageOfPublication());
        data.put(
            SciPublicationsController.ABSTRACT,
            item.getPublication().getPublicationAbstract().getValue(
                selectedLanguage
            )
        );
        data.put(
            SciPublicationsController.SHORT_DESCRIPTION,
            item.getPublication().getShortDescription().getValue(
                selectedLanguage
            )
        );
        data.put(
            SciPublicationsController.MISC,
            item.getPublication().getMisc().getValue(selectedLanguage)
        );
    }

    @Override
    public void process(FormSectionEvent event) throws FormProcessException {

        final FormData formData = event.getFormData();
        final PageState state = event.getPageState();
        final PublicationItem<?> item = (PublicationItem) super
            .processBasicWidgets(event);

        if ((item != null)
                && getSaveCancelSection().getSaveButton().isSelected(state)) {

            final Map<String, Object> data = new HashMap<>();

            data.put(
                SciPublicationsController.YEAR_OF_PUBLICATION,
                formData.get(SciPublicationsController.YEAR_OF_PUBLICATION)
            );
            data.put(
                SciPublicationsController.YEAR_FIRST_PUBLISHED,
                formData.get(SciPublicationsController.YEAR_FIRST_PUBLISHED
                )
            );
            data.put(
                SciPublicationsController.LANGUAGE_OF_PUBLICATION,
                new Locale(
                    (String) formData.get(
                        SciPublicationsController.LANGUAGE_OF_PUBLICATION
                    )
                )
            );
            data.put(
                SciPublicationsController.ABSTRACT,
                formData.get(SciPublicationsController.ABSTRACT)
            );
            data.put(
                SciPublicationsController.SHORT_DESCRIPTION,
                formData.get(SciPublicationsController.SHORT_DESCRIPTION)
            );
            data.put(
                SciPublicationsController.MISC,
                formData.get(SciPublicationsController.MISC)
            );

            final Locale selectedLocale = SelectedLanguageUtil.selectedLocale(
                state, selectedLanguageParam
            );

            final SciPublicationsController controller = CdiUtil
                .createCdiUtil()
                .findBean(SciPublicationsController.class);
            controller.savePublication(item.getPublication().getPublicationId(),
                                       selectedLocale,
                                       data);
        }
    }

    @Override
    public void submitted(final FormSectionEvent event)
        throws FormProcessException {
        
        if ((step != null) && getSaveCancelSection().getCancelButton().
            isSelected(event.getPageState())) {
            step.cancelStreamlinedCreation(event.getPageState());
        }
    }

    @Override
    protected GlobalizedMessage getTitleLabel() {
        return new GlobalizedMessage(
            "publications.ui.publication.title",
            SciPublicationsConstants.BUNDLE
        );
    }

}
