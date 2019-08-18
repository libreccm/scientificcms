/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.pagemodel.contentitems;

import org.librecms.assets.ContactableEntity;
import org.librecms.assets.Organization;
import org.librecms.assets.Person;
import org.librecms.contentsection.ContentItem;
import org.librecms.pagemodel.assets.AbstractAssetRenderer;
import org.librecms.pagemodel.assets.AssetRenderers;
import org.librecms.pagemodel.contentitems.AbstractContentItemRenderer;
import org.librecms.pagemodel.contentitems.ContentItemRenderer;
import org.scientificcms.contenttypes.sciproject.Contact;
import org.scientificcms.contenttypes.sciproject.Membership;
import org.scientificcms.contenttypes.sciproject.SciProject;
import org.scientificcms.contenttypes.sciproject.Sponsoring;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ContentItemRenderer(renders = SciProject.class)
@RequestScoped
public class SciProjectRenderer extends AbstractContentItemRenderer {

    private static final long serialVersionUID = 1L;

    @Inject
    private AssetRenderers assetRenderers;

    @Override
    protected void renderItem(final ContentItem item,
                              final Locale language,
                              final Map<String, Object> result) {

        final SciProject project;
        if (item instanceof SciProject) {
            project = (SciProject) item;
        } else {
            return;
        }

        result.put("begin", project.getBegin());
        result.put("end", project.getEnd());

        result.put("shortDescription",
                   project.getShortDescription().getValue(language));
        result.put("projectDescription",
                   project.getProjectDescription().getValue(language));
        result.put("funding",
                   project.getFunding().getValue(language));
        result.put("fundingVolume",
                   project.getFundingVolume().getValue(language));

        result.put("contacts",
                   project
                       .getContacts()
                       .stream()
                       .map(contact -> renderContact(contact, language))
                       .collect(Collectors.toList())
        );
        result.put("members",
                   project
                       .getMembers()
                       .stream()
                       .map(member -> renderMember(member, language))
                       .collect(Collectors.toList()));
        result.put("sponsoring",
                   project
                       .getSponsoring()
                       .stream()
                       .map(sponsoring -> renderSponsoring(sponsoring, language))
                       .collect(Collectors.toList()));
    }

    @Override
    protected AssetRenderers getAssetRenderers() {
        return assetRenderers;
    }

    private Map<String, Object> renderContact(final Contact contact,
                                              final Locale language) {

        final Map<String, Object> result = new HashMap<>();
        result.put("contactId", contact.getContactId());
        result.put("contactType", contact.getContactType());
        result.put("order", contact.getOrder());

        final ContactableEntity contactable = contact.getContactable();
        final AbstractAssetRenderer contactableRenderer = assetRenderers
            .findRenderer(contactable.getClass());
        result.put("contactable", contactableRenderer.render(contactable,
                                                             language));

        return result;
    }

    private Map<String, Object> renderMember(final Membership membership,
                                             final Locale language) {

        final Map<String, Object> result = new HashMap<>();
        result.put("membershipId", membership.getMembershipId());
        result.put("role", membership.getRole());
        result.put("status", membership.getStatus().toString());

        final Person member = membership.getMember();
        final AbstractAssetRenderer memberRenderer = assetRenderers
            .findRenderer(member.getClass());
        result.put("member", memberRenderer.render(member, language));

        return result;
    }

    private Map<String, Object> renderSponsoring(final Sponsoring sponsoring,
                                                 final Locale language) {

        final Map<String, Object> result = new HashMap<>();
        result.put("sponsoringId", sponsoring.getSponsoringId());
        result.put("fundingCode", sponsoring.getFundingCode());
        result.put("order", sponsoring.getOrder());

        final Organization sponsor = sponsoring.getSponsor();
        final AbstractAssetRenderer sponsorRenderer = assetRenderers
            .findRenderer(sponsor.getClass());
        result.put("sponsor", sponsorRenderer.render(sponsor, language));

        return result;
    }

}
