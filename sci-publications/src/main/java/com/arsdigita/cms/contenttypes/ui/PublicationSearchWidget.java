/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.PageState;
import com.arsdigita.bebop.form.Widget;
import com.arsdigita.bebop.parameters.LongParameter;
import com.arsdigita.cms.CMS;
import com.arsdigita.xml.Element;

import org.libreccm.cdi.utils.CdiUtil;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.PublicationWithPublisher;

import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class PublicationSearchWidget extends Widget {

    private Class<? extends Publication> type;

    public PublicationSearchWidget(final String name) {
        super(new LongParameter(name));
    }

    public PublicationSearchWidget(
        final String name, final Class<? extends Publication> type
    ) {
        this(name);
        this.type = type;
    }

    @Override
    public boolean isCompound() {
        return true;
    }

    @Override
    protected String getType() {
        return "publication-search-widget";
    }

    @Override
    protected String getElementTag() {
        return "cms:publication-search-widget";
    }

    @Override
    public void generateWidget(final PageState state,
                               final Element parent) {
        final Element widget = parent.newChildElement(
            getElementTag(), CMS.CMS_XML_NS
        );

        widget.addAttribute("name", getName());

        if (type != null) {
            widget.addAttribute("publication-type", type.getName());
        }

        final Long value = (Long) getValue(state);
        if (value != null) {
            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final PublicationSearchWidgetController controller = cdiUtil
                .findBean(PublicationSearchWidgetController.class);

            final Map<String, String> data = controller.getData(value);

            final Element selected = widget.newChildElement(
                "cms:selected-publication", CMS.CMS_XML_NS
            );
            selected.addAttribute(
                "publicationId",
                data.get(PublicationSearchWidgetController.PUBLICATION_ID)
            );

            selected.addAttribute(
                "title",
                data.get(PublicationSearchWidgetController.TITLE)
            );

            selected.addAttribute(
                "year",
                data.get(PublicationSearchWidgetController.YEAR)
            );

            if (type != null
                    && PublicationWithPublisher.class.isAssignableFrom(type)) {
                selected.addAttribute(
                    "publisher",
                    data.get(PublicationSearchWidgetController.PUBLISHER)
                );
                selected.addAttribute(
                    "place",
                    data.get(PublicationSearchWidgetController.PLACE)
                );
            }
            
            exportAttributes(widget);
        }
    }

}
