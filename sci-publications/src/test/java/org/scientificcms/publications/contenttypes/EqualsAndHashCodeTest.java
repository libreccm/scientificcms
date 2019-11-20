/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import nl.jqno.equalsverifier.EqualsVerifierApi;
import nl.jqno.equalsverifier.Warning;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.libreccm.categorization.Category;
import org.libreccm.core.CcmObject;
import org.libreccm.security.SecurityEntitiesPrefabProvider;
import org.libreccm.tests.categories.UnitTest;
import org.libreccm.testutils.EqualsVerifier;
import org.libreccm.workflow.Workflow;
import org.librecms.assets.Organization;
import org.librecms.contentsection.ContentItem;
import org.librecms.contentsection.ContentSection;
import org.librecms.lifecycle.Lifecycle;
import org.librecms.lifecycle.LifecycleDefinition;
import org.scientificcms.publications.CollectedVolume;
import org.scientificcms.publications.Journal;
import org.scientificcms.publications.Proceedings;
import org.scientificcms.publications.Publication;
import org.scientificcms.publications.assets.PrefabAssetsProvider;

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
            ArticleInCollectedVolumeItem.class,
            ArticleInJournalItem.class,
            CollectedVolumeItem.class,
            ExpertiseItem.class,
            GreyLiteratureItem.class,
            InProceedingsItem.class,
            InternetArticleItem.class,
            MonographItem.class,
            ProceedingsItem.class,
            TalkItem.class,
            WorkingPaperItem.class
        });
    }

    public EqualsAndHashCodeTest(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected void addSuppressWarnings(final EqualsVerifierApi<?> verifier) {
        super.addSuppressWarnings(verifier);
        verifier.suppress(Warning.REFERENCE_EQUALITY);
    }

    @Override
    protected void addPrefabValues(
        final nl.jqno.equalsverifier.EqualsVerifierApi<?> verifier) {

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

//        final Publisher publisher1 = new Publisher();
//        publisher1.setName("publisher1");
//        final Publisher publisher2 = new Publisher();
//        publisher2.setName("publisher2");
//        verifier.withPrefabValues(Publisher.class, publisher1, publisher2);
        final Organization organization1 = new Organization();
        organization1.setName("Orga 1");
        final Organization organization2 = new Organization();
        organization2.setName("Orga 2");
        verifier.withPrefabValues(Organization.class,
                                  organization1,
                                  organization2);

        final Proceedings proceedings1 = new Proceedings();
        proceedings1.getTitle().addValue(Locale.ENGLISH, "Proceedings 1");
        final Proceedings proceedings2 = new Proceedings();
        proceedings2.getTitle().addValue(Locale.ENGLISH, "Proceedings 2");
        verifier.withPrefabValues(Proceedings.class,
                                  proceedings1,
                                  proceedings2);

        final ContentSection section1 = new ContentSection();
        section1.setDisplayName("section1");
        final ContentSection section2 = new ContentSection();
        section2.setDisplayName("section2");
        verifier.withPrefabValues(ContentSection.class, section1, section2);

        final Workflow workflow1 = new Workflow();
        workflow1.getName().addValue(Locale.ENGLISH, "workflow1");
        final Workflow workflow2 = new Workflow();
        workflow2.getName().addValue(Locale.ENGLISH, "workflow2");
        verifier.withPrefabValues(Workflow.class, workflow1, workflow2);

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

        final ContentItem item1 = new ContentItem();
        item1.setDisplayName("item1");
        final ContentItem item2 = new ContentItem();
        item2.setDisplayName("item2");
        verifier.withPrefabValues(ContentItem.class, item1, item2);

        PrefabAssetsProvider.addPrefabAssetsAndAttachments(verifier);

        final LifecycleDefinition lifecycleDef1 = new LifecycleDefinition();
        lifecycleDef1.setDefinitionId(-100);
        lifecycleDef1.getLabel().addValue(Locale.ENGLISH, "def1");
        final LifecycleDefinition lifecycleDef2 = new LifecycleDefinition();
        lifecycleDef2.setDefinitionId(-110);
        lifecycleDef2.getLabel().addValue(Locale.ENGLISH, "def2");
        verifier.withPrefabValues(LifecycleDefinition.class,
                                  lifecycleDef1,
                                  lifecycleDef2);
        final Lifecycle lifecycle1 = new Lifecycle();
        lifecycle1.setDefinition(lifecycleDef1);
        lifecycle1.setStarted(true);
        final Lifecycle lifecycle2 = new Lifecycle();
        lifecycle2.setDefinition(lifecycleDef2);
        lifecycle2.setStarted(false);
        verifier.withPrefabValues(Lifecycle.class, lifecycle1, lifecycle2);

        final Publication publication1 = new Publication();
        publication1.getTitle().addValue(Locale.ENGLISH, "Alpha");
        final Publication publication2 = new Publication();
        publication2.getTitle().addValue(Locale.ENGLISH, "Bravo");
        verifier.withPrefabValues(Publication.class, publication1, publication2);
    }

}
