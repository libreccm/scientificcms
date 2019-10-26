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

import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class JournalSearchWidget extends Widget {

    public JournalSearchWidget(final String name) {
        super(new LongParameter(name));
    }

    @Override
    public boolean isCompound() {
        return true;
    }

    @Override
    protected String getType() {
        return "journal-search-widget";
    }

    @Override
    public String getElementTag() {
        return "cms:journal-search-widget";
    }

    @Override
    public void generateWidget(
        final PageState state, final Element parent
    ) {
        final Element widget = parent.newChildElement(getElementTag(),
                                                      CMS.CMS_XML_NS);

        widget.addAttribute("name", getName());

        final Long value = (Long) getValue(state);
        if (value != null) {
            final CdiUtil cdiUtil = CdiUtil.createCdiUtil();
            final JournalSearchWidgetController controller = cdiUtil
                .findBean(JournalSearchWidgetController.class);

            final Map<String, String> data = controller.getData(value);

            final Element selected = widget
                .newChildElement("cms:selected-journal", CMS.CMS_XML_NS);
            selected.addAttribute(
                "journalId",
                data.get(JournalSearchWidgetController.JOURNAL_ID)
            );
            selected.addAttribute(
                "title",
                data.get(JournalSearchWidgetController.TITLE)
            );
            selected.addAttribute(
                "symbol",
                data.get(JournalSearchWidgetController.SYMBOL)
            );
            selected.addAttribute(
                "issn", 
                data.get(JournalSearchWidgetController.ISSN));
            
            exportAttributes(widget);
        }
    }

}
