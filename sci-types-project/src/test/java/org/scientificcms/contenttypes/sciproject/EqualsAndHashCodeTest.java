/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.contenttypes.sciproject;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.libreccm.categorization.Category;
import org.libreccm.core.CcmObject;
import org.libreccm.l10n.LocalizedString;
import org.libreccm.security.Group;
import org.libreccm.security.Role;
import org.libreccm.security.SecurityEntitiesPrefabProvider;
import org.libreccm.security.User;
import org.libreccm.tests.categories.UnitTest;
import org.libreccm.testutils.EqualsVerifier;
import org.libreccm.workflow.Workflow;
import org.librecms.assets.ContactableEntity;
import org.librecms.assets.Person;
import org.librecms.assets.PostalAddress;
import org.librecms.contentsection.AttachmentList;
import org.librecms.contentsection.ContentItem;
import org.librecms.contentsection.ContentSection;
import org.librecms.contentsection.ContentType;
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
            Contact.class,
            Membership.class,
            SciProject.class
        });
    }

    public EqualsAndHashCodeTest(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected void addPrefabValues(
        final nl.jqno.equalsverifier.EqualsVerifier<?> verifier) {

        final Contact contact1 = new Contact();
        contact1.setContactId(10);
        contact1.setContactType("test");
        contact1.setOrder(1);

        final Contact contact2 = new Contact();
        contact2.setContactId(11);
        contact2.setContactType("test");
        contact2.setOrder(2);

        verifier.withPrefabValues(Contact.class, contact1, contact2);

        final Membership membership1 = new Membership();
        membership1.setMembershipId(101);

        final Membership membership2 = new Membership();
        membership2.setMembershipId(102);

        verifier.withPrefabValues(Membership.class, membership1, membership2);

        final SciProject project1 = new SciProject();
        project1.setObjectId(201);
        project1.setDisplayName("test1");

        final SciProject project2 = new SciProject();
        project2.setObjectId(202);
        project2.setDisplayName("test2");

        verifier.withPrefabValues(SciProject.class, project1, project2);

        final PostalAddress postalAddress1 = new PostalAddress();
        postalAddress1.setAddress("test street 1");
        postalAddress1.setCity("Test City");
        postalAddress1.setPostalCode("12345");

        final PostalAddress postalAddress2 = new PostalAddress();
        postalAddress2.setAddress("Musterstraße 1");
        postalAddress2.setCity("Musterstadt");
        postalAddress2.setPostalCode("56789");

        verifier.withPrefabValues(PostalAddress.class,
                                  postalAddress1,
                                  postalAddress2);

        final Person contactable1 = new Person();
        contactable1.setObjectId(301);

        final Person contactable2 = new Person();
        contactable2.setObjectId(302);

        verifier.withPrefabValues(ContactableEntity.class, contactable1,
                                  contactable2);

        final Person person1 = new Person();
        person1.setObjectId(303);

        final Person person2 = new Person();
        person2.setObjectId(304);

        verifier.withPrefabValues(Person.class, person1, person2);

        final ContentSection contentSection1 = new ContentSection();
        contentSection1.setObjectId(401);
        contentSection1.setDisplayName("section1");

        final ContentSection contentSection2 = new ContentSection();
        contentSection2.setObjectId(402);
        contentSection2.setDisplayName("section2");

        verifier.withPrefabValues(ContentSection.class,
                                  contentSection1,
                                  contentSection2);

        final ContentType contentType1 = new ContentType();
        contentType1.setObjectId(501);
        contentType1.setDisplayName("type-1");

        final ContentType contentType2 = new ContentType();
        contentType2.setObjectId(502);
        contentType2.setDisplayName("type-2");

        verifier.withPrefabValues(ContentType.class,
                                  contentType1,
                                  contentType2);

        final ContentItem item1 = new ContentItem();
        item1.setObjectId(601);
        item1.setDisplayName("item1");

        final ContentItem item2 = new ContentItem();
        item2.setObjectId(602);
        item2.setDisplayName("item2");

        verifier.withPrefabValues(ContentItem.class, item1, item2);

        final AttachmentList attachmentList1 = new AttachmentList();
        attachmentList1.setListId(701);
        attachmentList1.setName("list1");

        final AttachmentList attachmentList2 = new AttachmentList();
        attachmentList2.setListId(702);
        attachmentList2.setName("list2");

        verifier.withPrefabValues(AttachmentList.class,
                                  attachmentList1,
                                  attachmentList2);

        final Lifecycle lifecycle1 = new Lifecycle();
        lifecycle1.setLifecycleId(801);
        lifecycle1.setStarted(true);

        final Lifecycle lifecycle2 = new Lifecycle();
        lifecycle2.setLifecycleId(802);
        lifecycle2.setStarted(false);

        verifier.withPrefabValues(Lifecycle.class, lifecycle1, lifecycle2);

        final Workflow workflow1 = new Workflow();
        final LocalizedString workflow1Name = new LocalizedString();
        workflow1Name.addValue(Locale.ROOT, "workflow1");
        workflow1.setName(workflow1Name);

        final Workflow workflow2 = new Workflow();
        final LocalizedString workflow2Name = new LocalizedString();
        workflow2Name.addValue(Locale.ROOT, "workflow2");
        workflow2.setName(workflow2Name);

        verifier.withPrefabValues(Workflow.class, workflow1, workflow2);

        final CcmObject ccmObj1 = new CcmObject();
        ccmObj1.setObjectId(1001);
        ccmObj1.setDisplayName("obj1");

        final CcmObject ccmObj2 = new CcmObject();
        ccmObj2.setObjectId(1002);
        ccmObj2.setDisplayName("obj2");

        verifier.withPrefabValues(CcmObject.class, ccmObj1, ccmObj2);

        SecurityEntitiesPrefabProvider.addPrefabEntities(verifier);

        final Category category1 = new Category();
        category1.setObjectId(5001);
        category1.setName("category1");

        final Category category2 = new Category();
        category2.setCategoryOrder(5002);
        category2.setName("category2");

        verifier.withPrefabValues(Category.class, category1, category2);
    }

}
