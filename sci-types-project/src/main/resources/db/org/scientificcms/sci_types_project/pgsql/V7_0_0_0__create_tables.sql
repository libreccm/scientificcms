    create table SCI_PROJECT.PROJECT_CONTACTS (
       CONTACT_ID int8 not null,
        CONTACT_TYPE varchar(255),
        CONTACT_ORDER int8,
        CONTACTABLE_ID int8,
        PROJECT_ID int8,
        primary key (CONTACT_ID)
    );

    create table SCI_PROJECT.PROJECT_CONTACTS_AUD (
       CONTACT_ID int8 not null,
        REV int4 not null,
        REVTYPE int2,
        CONTACT_TYPE varchar(255),
        CONTACT_ORDER int8,
        CONTACTABLE_ID int8,
        PROJECT_ID int8,
        primary key (CONTACT_ID, REV)
    );

    create table SCI_PROJECT.PROJECT_DESCS (
       OBJECT_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (OBJECT_ID, LOCALE)
    );

    create table SCI_PROJECT.PROJECT_DESCS_AUD (
       REV int4 not null,
        OBJECT_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, OBJECT_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PROJECT.PROJECT_FUNDING (
       OBJECT_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (OBJECT_ID, LOCALE)
    );

    create table SCI_PROJECT.PROJECT_FUNDING_AUD (
       REV int4 not null,
        OBJECT_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, OBJECT_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PROJECT.PROJECT_FUNDING_VOLUME (
       OBJECT_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (OBJECT_ID, LOCALE)
    );

    create table SCI_PROJECT.PROJECT_FUNDING_VOLUME_AUD (
       REV int4 not null,
        OBJECT_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, OBJECT_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PROJECT.PROJECT_MEMBERSHIPS (
       MEMBERSHIP_ID int8 not null,
        MEMBER_ROLE varchar(255),
        STATUS varchar(255),
        MEMBER_ID int8,
        PROJECT_ID int8,
        primary key (MEMBERSHIP_ID)
    );

    create table SCI_PROJECT.PROJECT_MEMBERSHIPS_AUD (
       MEMBERSHIP_ID int8 not null,
        REV int4 not null,
        REVTYPE int2,
        MEMBER_ROLE varchar(255),
        STATUS varchar(255),
        MEMBER_ID int8,
        PROJECT_ID int8,
        primary key (MEMBERSHIP_ID, REV)
    );

    create table SCI_PROJECT.PROJECT_SHORT_DESCS (
       OBJECT_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (OBJECT_ID, LOCALE)
    );

    create table SCI_PROJECT.PROJECT_SHORT_DESCS_AUD (
       REV int4 not null,
        OBJECT_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, OBJECT_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PROJECT.PROJECTS (
       PROJECT_BEGIN date,
        PROJECT_END date,
        OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PROJECT.PROJECTS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        PROJECT_BEGIN date,
        PROJECT_END date,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PROJECT.SPONSORING (
       SPONSORING_ID int8 not null,
        FUNDING_CODE varchar(512),
        SPONSOR_ORDER int8,
        PROJECT_ID int8,
        SPONSOR_ID int8,
        primary key (SPONSORING_ID)
    );

    create table SCI_PROJECT.SPONSORING_AUD (
       SPONSORING_ID int8 not null,
        REV int4 not null,
        REVTYPE int2,
        FUNDING_CODE varchar(512),
        SPONSOR_ORDER int8,
        PROJECT_ID int8,
        SPONSOR_ID int8,
        primary key (SPONSORING_ID, REV)
    );

    alter table SCI_PROJECT.PROJECT_CONTACTS 
       add constraint FKgnma88ye77fb0vdmpuepyb2y7 
       foreign key (CONTACTABLE_ID) 
       references CCM_CMS.CONTACTABLE_ENTITIES;

    alter table SCI_PROJECT.PROJECT_CONTACTS 
       add constraint FK21fku49mpakv86hra725t3sok 
       foreign key (PROJECT_ID) 
       references SCI_PROJECT.PROJECTS;

    alter table SCI_PROJECT.PROJECT_CONTACTS_AUD 
       add constraint FK15m4nnwrt964isduiuk8bbeg8 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PROJECT.PROJECT_DESCS 
       add constraint FKfh5goal8j4gsjhf65gxm11gxu 
       foreign key (OBJECT_ID) 
       references SCI_PROJECT.PROJECTS;

    alter table SCI_PROJECT.PROJECT_DESCS_AUD 
       add constraint FKfq5fyp71qmrc9y0h6px8vae7l 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PROJECT.PROJECT_FUNDING 
       add constraint FKqmq8depjmuly0lixjljwnwut5 
       foreign key (OBJECT_ID) 
       references SCI_PROJECT.PROJECTS;

    alter table SCI_PROJECT.PROJECT_FUNDING_AUD 
       add constraint FK664s3oomoj23e9hvbh97hg5dw 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PROJECT.PROJECT_FUNDING_VOLUME 
       add constraint FKf6lg5n24snpplnvctdknh4mrv 
       foreign key (OBJECT_ID) 
       references SCI_PROJECT.PROJECTS;

    alter table SCI_PROJECT.PROJECT_FUNDING_VOLUME_AUD 
       add constraint FKgnnop98oxffgs7bp9bspajhw9 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PROJECT.PROJECT_MEMBERSHIPS 
       add constraint FKdbcet4i6wg7jinmaa0wchqujp 
       foreign key (MEMBER_ID) 
       references CCM_CMS.PERSONS;

    alter table SCI_PROJECT.PROJECT_MEMBERSHIPS 
       add constraint FK54cqbnqwnv7e13p7k842nmjvt 
       foreign key (PROJECT_ID) 
       references SCI_PROJECT.PROJECTS;

    alter table SCI_PROJECT.PROJECT_MEMBERSHIPS_AUD 
       add constraint FKjdf8o2a5cuusgee0v43n562y4 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PROJECT.PROJECT_SHORT_DESCS 
       add constraint FKqbbb85ebs497yad3gx07kdt1e 
       foreign key (OBJECT_ID) 
       references SCI_PROJECT.PROJECTS;

    alter table SCI_PROJECT.PROJECT_SHORT_DESCS_AUD 
       add constraint FK4ijysk1fk31o5kljgpc2p7yq6 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PROJECT.PROJECTS 
       add constraint FKqlmls86i354oxprglxg5j9xxy 
       foreign key (OBJECT_ID) 
       references CCM_CMS.CONTENT_ITEMS;

    alter table SCI_PROJECT.PROJECTS_AUD 
       add constraint FKss0w64wsb71r39npak6tg963b 
       foreign key (OBJECT_ID, REV) 
       references CCM_CMS.CONTENT_ITEMS_AUD;

    alter table SCI_PROJECT.SPONSORING 
       add constraint FKe8posjrbtsvd7t8q3k8l7ybmy 
       foreign key (PROJECT_ID) 
       references SCI_PROJECT.PROJECTS;

    alter table SCI_PROJECT.SPONSORING 
       add constraint FKt63gl8j92rua5risu9cnhd3py 
       foreign key (SPONSOR_ID) 
       references CCM_CMS.ORGANIZATIONS;

    alter table SCI_PROJECT.SPONSORING_AUD 
       add constraint FK7ci9e1fuqi8vbjcp9x70747se 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;
