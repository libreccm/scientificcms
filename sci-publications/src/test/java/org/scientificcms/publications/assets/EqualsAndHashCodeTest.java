/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.assets;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.libreccm.categorization.Category;
import org.libreccm.core.CcmObject;
import org.libreccm.l10n.LocalizedString;
import org.libreccm.security.SecurityEntitiesPrefabProvider;
import org.libreccm.tests.categories.UnitTest;
import org.libreccm.testutils.EqualsVerifier;
import org.libreccm.workflow.Workflow;
import org.librecms.assets.Organization;
import org.librecms.contentsection.ContentItem;
import org.librecms.contentsection.ContentSection;
import org.librecms.contentsection.ContentType;
import org.librecms.contentsection.ItemAttachment;
import org.librecms.lifecycle.Lifecycle;

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
            JournalAsset.class,
            PublisherAsset.class
        });
    }

    public EqualsAndHashCodeTest(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected void addPrefabValues(
        final nl.jqno.equalsverifier.EqualsVerifier<?> verifier) {

//        final JournalAsset journal1 = new JournalAsset();
//        journal1.setFirstYear(1953);
//        journal1.setSymbol("foo");
//
//        final JournalAsset journal2 = new JournalAsset();
//        journal2.setSymbol("bar");
//
//        verifier.withPrefabValues(JournalAsset.class, journal1, journal2);
//
//        final PublisherAsset publisher1 = new PublisherAsset();
//        publisher1.setName("Muster Verlag");
//        publisher1.setPlace("Musterburg");
//
//        final PublisherAsset publisher2 = new PublisherAsset();
//        publisher2.setName("Example Press");
//        publisher2.setPlace("Riverton");
//
//        verifier.withPrefabValues(PublisherAsset.class, publisher1, publisher2);
//
//        final ContentSection contentSection1 = new ContentSection();
//        contentSection1.setObjectId(401);
//        contentSection1.setDisplayName("section1");
//
//        final ContentSection contentSection2 = new ContentSection();
//        contentSection2.setObjectId(402);
//        contentSection2.setDisplayName("section2");
//
//        verifier.withPrefabValues(ContentSection.class,
//                                  contentSection1,
//                                  contentSection2);
//
//        final ContentType contentType1 = new ContentType();
//        contentType1.setObjectId(501);
//        contentType1.setDisplayName("type-1");
//
//        final ContentType contentType2 = new ContentType();
//        contentType2.setObjectId(502);
//        contentType2.setDisplayName("type-2");
//
//        verifier.withPrefabValues(ContentType.class,
//                                  contentType1,
//                                  contentType2);
//
//        final ContentItem item1 = new ContentItem();
//        item1.setObjectId(601);
//        item1.setDisplayName("item1");
//
//        final ContentItem item2 = new ContentItem();
//        item2.setObjectId(602);
//        item2.setDisplayName("item2");
//
//        verifier.withPrefabValues(ContentItem.class, item1, item2);
//
//        final Lifecycle lifecycle1 = new Lifecycle();
//        lifecycle1.setLifecycleId(801);
//        lifecycle1.setStarted(true);
//
//        final Lifecycle lifecycle2 = new Lifecycle();
//        lifecycle2.setLifecycleId(802);
//        lifecycle2.setStarted(false);
//
//        verifier.withPrefabValues(Lifecycle.class, lifecycle1, lifecycle2);
//
//        final Workflow workflow1 = new Workflow();
//        final LocalizedString workflow1Name = new LocalizedString();
//        workflow1Name.addValue(Locale.ROOT, "workflow1");
//        workflow1.setName(workflow1Name);
//
//        final Workflow workflow2 = new Workflow();
//        final LocalizedString workflow2Name = new LocalizedString();
//        workflow2Name.addValue(Locale.ROOT, "workflow2");
//        workflow2.setName(workflow2Name);
//
//        verifier.withPrefabValues(Workflow.class, workflow1, workflow2);
//
//        final CcmObject ccmObj1 = new CcmObject();
//        ccmObj1.setObjectId(1001);
//        ccmObj1.setDisplayName("obj1");
//
//        final CcmObject ccmObj2 = new CcmObject();
//        ccmObj2.setObjectId(1002);
//        ccmObj2.setDisplayName("obj2");
//
//        verifier.withPrefabValues(CcmObject.class, ccmObj1, ccmObj2);
//
//        SecurityEntitiesPrefabProvider.addPrefabEntities(verifier);
//
//        final Category category1 = new Category();
//        category1.setObjectId(5001);
//        category1.setName("category1");
//
//        final Category category2 = new Category();
//        category2.setCategoryOrder(5002);
//        category2.setName("category2");
//
//        verifier.withPrefabValues(Category.class, category1, category2);
//
//        final Organization organization1 = new Organization();
//        organization1.setName("orga1");
//
//        final Organization organization2 = new Organization();
//        organization1.setName("orga2");
//
//        verifier.withPrefabValues(Organization.class,
//                                  organization1,
//                                  organization2);
//
//        final ItemAttachment<?> itemAttachment1 = new ItemAttachment<>();
//        itemAttachment1.setUuid("927ac9de-029d-4233-9015-1135eb861c34");
//
//        final ItemAttachment<?> itemAttachment2 = new ItemAttachment<>();
//        itemAttachment2.setUuid("d1bd98a1-75c2-4e61-8f9f-2e2eadd30812");
//
//        verifier.withPrefabValues(ItemAttachment.class,
//                                  itemAttachment1,
//                                  itemAttachment2);
    }

}