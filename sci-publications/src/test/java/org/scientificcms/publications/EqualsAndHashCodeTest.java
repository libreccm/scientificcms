/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import nl.jqno.equalsverifier.EqualsVerifierApi;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.libreccm.tests.categories.UnitTest;
import org.libreccm.testutils.EqualsVerifier;
import org.librecms.assets.Organization;

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
            ArticleInCollectedVolume.class,
            ArticleInJournal.class,
            CollectedVolume.class,
            Expertise.class,
            GreyLiterature.class,
            InProceedings.class,
            InternetArticle.class,
            Journal.class,
            Monograph.class,
            Proceedings.class,
            Publication.class,
            PublicationWithPublisher.class,
            Publisher.class,
            Talk.class,
            UnPublished.class,
            WorkingPaper.class
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
    }

}
