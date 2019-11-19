/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.Talk;
import org.scientificcms.publications.contenttypes.TalkItem;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = TalkItem.class)
public class TalkRenderer extends AbstractPublicationRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void renderPublication(
        final Publication publication,
        final Locale language,
        final Map<String, Object> publicationData
    ) {
        final Talk talk;
        if (publication instanceof Talk) {
            talk = (Talk) publication;
        } else {
            return;
        }
        
        publicationData.put("dateOfTalk", talk.getDateOfTalk());
        publicationData.put("event", talk.getEvent());
        publicationData.put("place", talk.getPlace());
    }

}
