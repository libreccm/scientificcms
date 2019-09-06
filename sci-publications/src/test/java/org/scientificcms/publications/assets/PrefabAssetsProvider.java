/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import nl.jqno.equalsverifier.EqualsVerifierApi;
import org.librecms.contentsection.ItemAttachment;
import org.scientificcms.publications.Publisher;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public final class PrefabAssetsProvider {

    private PrefabAssetsProvider() {
        // Nothing
    }

    public static final void addPrefabAssetsAndAttachments(
        final EqualsVerifierApi<?> verifier) {

        final Publisher publisher1 = new Publisher();
        publisher1.setName("publisher1");
        final Publisher publisher2 = new Publisher();
        publisher2.setName("publisher2");
        verifier.withPrefabValues(Publisher.class, publisher1, publisher2);

        final PublisherAsset publisherAsset1 = new PublisherAsset();
        publisherAsset1.setPublisher(publisher1);
        final PublisherAsset publisherAsset2 = new PublisherAsset();
        publisherAsset2.setPublisher(publisher2);
        verifier.withPrefabValues(PublisherAsset.class,
                                  publisherAsset1,
                                  publisherAsset2);

        final ItemAttachment<PublisherAsset> attachment1
                                                 = new ItemAttachment<>();
        attachment1.setAsset(publisherAsset1);
        final ItemAttachment<PublisherAsset> attachment2
                                                 = new ItemAttachment<>();
        attachment2.setAsset(publisherAsset2);
        verifier.withPrefabValues(ItemAttachment.class,
                                  attachment1,
                                  attachment2);
    }

}
