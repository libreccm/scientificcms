/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.libreccm.security;

import nl.jqno.equalsverifier.EqualsVerifierApi;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public final class SecurityEntitiesPrefabProvider {

    private SecurityEntitiesPrefabProvider() {
        // Nothing
    }

    public static void addPrefabEntities(final EqualsVerifierApi<?> verifier) {

        final Role role1 = new Role();
        role1.setRoleId(2001);
        role1.setName("role1");

        final Role role2 = new Role();
        role2.setRoleId(2002);
        role2.setName("role2");

        verifier.withPrefabValues(Role.class, role1, role2);

        final Group group1 = new Group();
        group1.setPartyId(3001);
        group1.setName("group1");

        final Group group2 = new Group();
        group2.setPartyId(3002);
        group2.setName("group2");

        verifier.withPrefabValues(Group.class, group1, group2);

        final User user1 = new User();
        user1.setPartyId(4001);
        user1.setName("user1");

        final User user2 = new User();
        user2.setPartyId(4002);
        user2.setName("user2");

        verifier.withPrefabValues(User.class, user1, user2);
    }

    public static final void addPrefabPermissions(
        final EqualsVerifierApi<?> verifierApi) {

        final Permission permission1 = new Permission();
        permission1.setGrantedPrivilege("privilege1");
        final Permission permission2 = new Permission();
        permission2.setGrantedPrivilege("privilege2");
        verifierApi.withPrefabValues(Permission.class,
                                     permission1,
                                     permission2);
    }

}
