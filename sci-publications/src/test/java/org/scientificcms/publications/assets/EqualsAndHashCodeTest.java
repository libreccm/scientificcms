/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import nl.jqno.equalsverifier.EqualsVerifierApi;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.libreccm.categorization.Category;
import org.libreccm.core.CcmObject;
import org.libreccm.security.Permission;
import org.libreccm.security.SecurityEntitiesPrefabProvider;
import org.libreccm.tests.categories.UnitTest;
import org.libreccm.testutils.EqualsVerifier;
import org.librecms.contentsection.ItemAttachment;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.Journal;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.Publisher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RunWith(Parameterized.class)
@org.junit.experimental.categories.Category(UnitTest.class)
public class EqualsAndHashCodeTest extends EqualsVerifier {

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Class<?>> data() {

        return Arrays.asList(new Class<?>[]{
            CollectedVolumeAsset.class,
            JournalAsset.class,
            ProceedingsAsset.class,
            PublisherAsset.class
        });
    }

    public EqualsAndHashCodeTest(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected void addPrefabValues(final EqualsVerifierApi<?> verifier) {

        final CollectedVolume volume1 = new CollectedVolume();
        volume1.getTitle().addValue(Locale.ENGLISH, "Test 1");
        final CollectedVolume volume2 = new CollectedVolume();
        volume2.getTitle().addValue(Locale.ENGLISH, "Test 2");
        verifier.withPrefabValues(CollectedVolume.class, volume1, volume2);

        final Journal journal1 = new Journal();
        journal1.setTitle("Journal 1");
        journal1.setFirstYear(1980);
        final Journal journal2 = new Journal();
        journal2.setTitle("Journal 2");
        journal2.setFirstYear(2004);
        verifier.withPrefabValues(Journal.class, journal1, journal2);

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

        final Proceedings proceedings1 = new Proceedings();
        proceedings1.getTitle().addValue(Locale.ENGLISH, "Proceedings 1");
        final Proceedings proceedings2 = new Proceedings();
        proceedings2.getTitle().addValue(Locale.ENGLISH, "Proceedings 2");
        verifier.withPrefabValues(Proceedings.class,
                                  proceedings1,
                                  proceedings2);
        
        SecurityEntitiesPrefabProvider.addPrefabPermissions(verifier);
        
        final Category category1 = new Category();
        category1.setName("category1");
        final Category category2 = new Category();
        category2.setName("category2");
        verifier.withPrefabValues(Category.class, category1, category2);
        
        final CcmObject ccmObject1 = new CcmObject();
        ccmObject1.setDisplayName("obj1");
        final CcmObject ccmObject2 = new CcmObject();
        ccmObject2.setDisplayName("obj2");
        verifier.withPrefabValues(CcmObject.class, ccmObject1, ccmObject2);
    }

}
