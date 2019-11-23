    create table SCI_PUBLICATIONS.ARTICLE_IN_COLLECTED_VOLUME_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.ARTICLE_IN_COLLECTED_VOLUME_ITEMS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.ARTICLES_IN_COLLECTED_VOLUME (
       CHAPTER varchar(1024),
        END_PAGE int4,
        START_PAGE int4,
        PUBLICATION_ID int8 not null,
        COLLECTED_VOLUME_ID int8,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.ARTICLES_IN_COLLECTED_VOLUME_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        CHAPTER varchar(1024),
        END_PAGE int4,
        START_PAGE int4,
        COLLECTED_VOLUME_ID int8,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.ARTICLES_IN_JOURNAL (
       END_PAGE int4,
        ISSUE varchar(255),
        PUBLICATION_DATE date,
        START_PAGE int4,
        VOLUME int4,
        PUBLICATION_ID int8 not null,
        JOURNAL_ID int8,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.ARTICLES_IN_JOURNAL_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        END_PAGE int4,
        ISSUE varchar(255),
        PUBLICATION_DATE date,
        START_PAGE int4,
        VOLUME int4,
        JOURNAL_ID int8,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.AUTHORSHIPS (
       AUTHORSHIP_ID int8 not null,
        AUTHOR_ORDER int8,
        EDITOR boolean,
        UUID varchar(36) not null,
        AUTHOR_ID int8,
        PUBLICATION_ID int8,
        primary key (AUTHORSHIP_ID)
    );

    create table SCI_PUBLICATIONS.AUTHORSHIPS_AUD (
       AUTHORSHIP_ID int8 not null,
        REV int4 not null,
        REVTYPE int2,
        AUTHOR_ORDER int8,
        EDITOR boolean,
        UUID varchar(36),
        AUTHOR_ID int8,
        PUBLICATION_ID int8,
        primary key (AUTHORSHIP_ID, REV)
    );

    create table SCI_PUBLICATIONS.COLLECTED_VOLUME_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.COLLECTED_VOLUME_ITEMS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.COLLECTED_VOLUMES (
       OBJECT_ID int8 not null,
        PUBLICATION_ID int8 not null,
        COLLECTED_VOLUME_ID int8,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.COLLECTED_VOLUMES_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        COLLECTED_VOLUME_ID int8,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.EXPERTISE_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.EXPERTISE_ITEMS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.EXPERTISES (
       NUMBER_OF_PAGES int4,
        PLACE varchar(512),
        PUBLICATION_ID int8 not null,
        ORDERER_ID int8,
        ORGANIZATION_ID int8,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.EXPERTISES_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        NUMBER_OF_PAGES int4,
        PLACE varchar(512),
        ORDERER_ID int8,
        ORGANIZATION_ID int8,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.GREY_LITERATURE (
       END_PAGE int4,
        START_PAGE int4,
        PUBLICATION_ID int8 not null,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.GREY_LITERATURE_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        END_PAGE int4,
        START_PAGE int4,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.GREY_LITERATURE_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.GREY_LITERATURE_ITEMS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.IN_PROCEEDINGS (
       endPage int4 not null,
        startPage int4 not null,
        PUBLICATION_ID int8 not null,
        PROCEEDINGS_ID int8,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.IN_PROCEEDINGS_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        endPage int4,
        startPage int4,
        PROCEEDINGS_ID int8,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.INPROCEEDINGS_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.INPROCEEDINGS_ITEMS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.INTERNET_ARTICLE_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.INTERNET_ARTICLE_ITEMS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.INTERNET_ARTICLES (
       DOI varchar(2048),
        EDITION varchar(512),
        ISSN varchar(9),
        LAST_ACCESSED date,
        NUMBER varchar(256),
        NUMBER_OF_PAGES int4,
        PLACE varchar(512),
        PUBLICATION_DATE date,
        URL varchar(2048),
        URN varchar(2048),
        PUBLICATION_ID int8 not null,
        ORGANIZATION_ID int8,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.INTERNET_ARTICLES_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        DOI varchar(2048),
        EDITION varchar(512),
        ISSN varchar(9),
        LAST_ACCESSED date,
        NUMBER varchar(256),
        NUMBER_OF_PAGES int4,
        PLACE varchar(512),
        PUBLICATION_DATE date,
        URL varchar(2048),
        URN varchar(2048),
        ORGANIZATION_ID int8,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.JOURNAL_ASSETS (
       OBJECT_ID int8 not null,
        JOURNAL_ID int8,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.JOURNAL_ASSETS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        JOURNAL_ID int8,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.JOURNAL_DESCRIPTIONS (
       OBJECT_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (OBJECT_ID, LOCALE)
    );

    create table SCI_PUBLICATIONS.JOURNAL_DESCRIPTIONS_AUD (
       REV int4 not null,
        OBJECT_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, OBJECT_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PUBLICATIONS.JOURNALS (
       JOURNAL_ID int8 not null,
        FIRST_YEAR int4,
        ISSN varchar(9),
        LAST_YEAR int4,
        SYMBOL varchar(255),
        title varchar(1024) not null,
        UUID varchar(255),
        primary key (JOURNAL_ID)
    );

    create table SCI_PUBLICATIONS.JOURNALS_AUD (
       JOURNAL_ID int8 not null,
        REV int4 not null,
        REVTYPE int2,
        FIRST_YEAR int4,
        ISSN varchar(9),
        LAST_YEAR int4,
        SYMBOL varchar(255),
        title varchar(1024),
        UUID varchar(255),
        primary key (JOURNAL_ID, REV)
    );

    create table SCI_PUBLICATIONS.MONOGRAPH_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.MONOGRAPHS (
       REVIEWED boolean,
        PUBLICATION_ID int8 not null,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.MONOGRAPHS_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        REVIEWED boolean,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.PROCEEDINGS (
       END_DATE date,
        NAME_OF_CONFERENCE varchar(2048),
        PLACE_OF_CONFERENCE varchar(2048),
        START_DATE date,
        OBJECT_ID int8 not null,
        PUBLICATION_ID int8 not null,
        ORGANIZER_ID int8,
        PROCEEDINGS_ID int8,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.PROCEEDINGS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        END_DATE date,
        NAME_OF_CONFERENCE varchar(2048),
        PLACE_OF_CONFERENCE varchar(2048),
        START_DATE date,
        ORGANIZER_ID int8,
        PROCEEDINGS_ID int8,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.PROCEEDINGS_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_ABSTRACTS (
       PUBLICATION_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (PUBLICATION_ID, LOCALE)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_ABSTRACTS_AUD (
       REV int4 not null,
        PUBLICATION_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, PUBLICATION_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_EDITION (
       OBJECT_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (OBJECT_ID, LOCALE)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_EDITION_AUD (
       REV int4 not null,
        OBJECT_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, OBJECT_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_ITEMS (
       OBJECT_ID int8 not null,
        publication_PUBLICATION_ID int8,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_ITEMS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        publication_PUBLICATION_ID int8,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_MISC (
       PUBLICATION_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (PUBLICATION_ID, LOCALE)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_MISC_AUD (
       REV int4 not null,
        PUBLICATION_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, PUBLICATION_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_SHORT_DESCS (
       PUBLICATION_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (PUBLICATION_ID, LOCALE)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_SHORT_DESCS_AUD (
       REV int4 not null,
        PUBLICATION_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, PUBLICATION_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_TITLES (
       PUBLICATION_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (PUBLICATION_ID, LOCALE)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_TITLES_AUD (
       REV int4 not null,
        PUBLICATION_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, PUBLICATION_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_WITH_PUBLISHER_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.PUBLICATION_WITH_PUBLISHER_ITEMS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.PUBLICATIONS (
       PUBLICATION_ID int8 not null,
        LANGUAGE_OF_PUBLICATION varchar(255),
        PEER_REVIEWED boolean,
        UUID varchar(255) not null,
        YEAR_FIRST_PUBLISHED int4,
        YEAR_OF_PUBLICATION int4,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.PUBLICATIONS_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        REVTYPE int2,
        LANGUAGE_OF_PUBLICATION varchar(255),
        PEER_REVIEWED boolean,
        UUID varchar(255),
        YEAR_FIRST_PUBLISHED int4,
        YEAR_OF_PUBLICATION int4,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER (
       ISBN10 varchar(13),
        ISBN13 varchar(17),
        NUMBER_OF_PAGES int4,
        NUMBER_OF_VOLUMES int4,
        VOLUME int4,
        PUBLICATION_ID int8 not null,
        publisher_PUBLISHER_ID int8,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        ISBN10 varchar(13),
        ISBN13 varchar(17),
        NUMBER_OF_PAGES int4,
        NUMBER_OF_VOLUMES int4,
        VOLUME int4,
        publisher_PUBLISHER_ID int8,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.PUBLISHER_ASSETS (
       OBJECT_ID int8 not null,
        PUBLISHER_ID int8,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.PUBLISHER_ASSETS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        PUBLISHER_ID int8,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.PUBLISHERS (
       PUBLISHER_ID int8 not null,
        NAME varchar(2048) not null,
        PLACE varchar(2048),
        UUID varchar(255) not null,
        primary key (PUBLISHER_ID)
    );

    create table SCI_PUBLICATIONS.PUBLISHERS_AUD (
       PUBLISHER_ID int8 not null,
        REV int4 not null,
        REVTYPE int2,
        NAME varchar(2048),
        PLACE varchar(2048),
        UUID varchar(255),
        primary key (PUBLISHER_ID, REV)
    );

    create table SCI_PUBLICATIONS.SERIES (
       SERIES_ID int8 not null,
        UUID varchar(255) not null,
        primary key (SERIES_ID)
    );

    create table SCI_PUBLICATIONS.SERIES_ASSETS (
       OBJECT_ID int8 not null,
        SERIES_ID int8,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.SERIES_ASSETS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        SERIES_ID int8,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.SERIES_AUD (
       SERIES_ID int8 not null,
        REV int4 not null,
        REVTYPE int2,
        UUID varchar(255),
        primary key (SERIES_ID, REV)
    );

    create table SCI_PUBLICATIONS.SERIES_DESCRIPTIONS (
       SERIES_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (SERIES_ID, LOCALE)
    );

    create table SCI_PUBLICATIONS.SERIES_DESCRIPTIONS_AUD (
       REV int4 not null,
        SERIES_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, SERIES_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PUBLICATIONS.SERIES_TITLES (
       SERIES_ID int8 not null,
        LOCALIZED_VALUE text,
        LOCALE varchar(255) not null,
        primary key (SERIES_ID, LOCALE)
    );

    create table SCI_PUBLICATIONS.SERIES_TITLES_AUD (
       REV int4 not null,
        SERIES_ID int8 not null,
        LOCALIZED_VALUE text not null,
        LOCALE varchar(255) not null,
        REVTYPE int2,
        primary key (REV, SERIES_ID, LOCALIZED_VALUE, LOCALE)
    );

    create table SCI_PUBLICATIONS.TALK_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.TALK_ITEMS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.TALKS (
       DATE_OF_TALK date,
        EVENT varchar(2048),
        PLACE varchar(2048),
        PUBLICATION_ID int8 not null,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.TALKS_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        DATE_OF_TALK date,
        EVENT varchar(2048),
        PLACE varchar(2048),
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.UNPUBLISHED (
       PLACE varchar(512),
        NUMBER_OF_PAGES int4,
        PUBLICATION_ID int8 not null,
        ORGANIZATION int8,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.UNPUBLISHED_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        PLACE varchar(512),
        NUMBER_OF_PAGES int4,
        ORGANIZATION int8,
        primary key (PUBLICATION_ID, REV)
    );

    create table SCI_PUBLICATIONS.VOLUMES_IN_SERIES (
       VOLUME_ID int8 not null,
        UUID varchar(255) not null,
        VOLUME_OF_SERIES varchar(255),
        PUBLICATION_ID int8,
        SERIES_ID int8,
        primary key (VOLUME_ID)
    );

    create table SCI_PUBLICATIONS.VOLUMES_IN_SERIES_AUD (
       VOLUME_ID int8 not null,
        REV int4 not null,
        REVTYPE int2,
        UUID varchar(255),
        VOLUME_OF_SERIES varchar(255),
        PUBLICATION_ID int8,
        SERIES_ID int8,
        primary key (VOLUME_ID, REV)
    );

    create table SCI_PUBLICATIONS.WORKING_PAPER_ITEMS (
       OBJECT_ID int8 not null,
        primary key (OBJECT_ID)
    );

    create table SCI_PUBLICATIONS.WORKING_PAPER_ITEMS_AUD (
       OBJECT_ID int8 not null,
        REV int4 not null,
        primary key (OBJECT_ID, REV)
    );

    create table SCI_PUBLICATIONS.WORKING_PAPERS (
       PUBLICATION_ID int8 not null,
        primary key (PUBLICATION_ID)
    );

    create table SCI_PUBLICATIONS.WORKING_PAPERS_AUD (
       PUBLICATION_ID int8 not null,
        REV int4 not null,
        primary key (PUBLICATION_ID, REV)
    );

    alter table SCI_PUBLICATIONS.PUBLICATIONS 
       add constraint UK_p0xf8bu5qwa2tu5s8bc3bd880 unique (UUID);

    alter table SCI_PUBLICATIONS.PUBLISHERS 
       add constraint UK_cnrkte3uy2wya9fpqnns5ctnr unique (UUID);

    alter table SCI_PUBLICATIONS.SERIES 
       add constraint UK_j2j03064j8dyw0a0fxd63csj7 unique (UUID);

    alter table SCI_PUBLICATIONS.VOLUMES_IN_SERIES 
       add constraint UK_ej660cxyisk87bljiy9yxudlc unique (UUID);

     add constraint FK3933ol31co3yn5ee75b2hmhgp 
       foreign key (TASK_ID) 
       references CCM_CORE.WORKFLOW_ASSIGNABLE_TASKS;

    alter table CCM_CORE.WORKFLOW_TASK_COMMENTS 
       add constraint FKd2ymdg8nay9pmh2nn2whba0j8 
       foreign key (AUTHOR_ID) 
       references CCM_CORE.USERS;

    alter table CCM_CORE.WORKFLOW_TASK_COMMENTS 
       add constraint FKkfqrf9jdvm7livu5if06w0r5t 
       foreign key (TASK_ID) 
       references CCM_CORE.WORKFLOW_TASKS;

    alter table CCM_CORE.WORKFLOW_TASK_DEPENDENCIES 
       add constraint FKy88tppv7ihx0lsn6g64f5lfq 
       foreign key (BLOCKED_TASK_ID) 
       references CCM_CORE.WORKFLOW_TASKS;

    alter table CCM_CORE.WORKFLOW_TASK_DEPENDENCIES 
       add constraint FKrj80uilojn73u9a4xgk3vt0cj 
       foreign key (BLOCKING_TASK_ID) 
       references CCM_CORE.WORKFLOW_TASKS;

    alter table CCM_CORE.WORKFLOW_TASK_DESCRIPTIONS 
       add constraint FKeb7mqbdx3bk7t01vo7kp2hpf 
       foreign key (TASK_ID) 
       references CCM_CORE.WORKFLOW_TASKS;

    alter table CCM_CORE.WORKFLOW_TASK_LABELS 
       add constraint FKf715qud6g9xv2xeb8rrpnv4xs 
       foreign key (TASK_ID) 
       references CCM_CORE.WORKFLOW_TASKS;

    alter table CCM_CORE.WORKFLOW_TASKS 
       add constraint FK1693cbc36e4d8gucg8q7sc57e 
       foreign key (WORKFLOW_ID) 
       references CCM_CORE.WORKFLOWS;

    alter table CCM_CORE.WORKFLOWS 
       add constraint FKrm2yfrs6veoxoy304upq2wc64 
       foreign key (OBJECT_ID) 
       references CCM_CORE.CCM_OBJECTS;

    alter table CCM_CORE.WORKFLOWS 
       add constraint FK9ray5beiny6wm2mi0uwyecay2 
       foreign key (TEMPLATE_ID) 
       references CCM_CORE.WORKFLOWS;

    alter table SCI_PUBLICATIONS.ARTICLE_IN_COLLECTED_VOLUME_ITEMS 
       add constraint FKi2mvh6l6lu7ln886qv0iw4kt0 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS;

    alter table SCI_PUBLICATIONS.ARTICLE_IN_COLLECTED_VOLUME_ITEMS_AUD 
       add constraint FKmiih9fbcp9bolhl4bmcxq7a5f 
       foreign key (OBJECT_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS_AUD;

    alter table SCI_PUBLICATIONS.ARTICLES_IN_COLLECTED_VOLUME 
       add constraint FKkb34jhf75m9vh02foylq1ug4s 
       foreign key (COLLECTED_VOLUME_ID) 
       references SCI_PUBLICATIONS.COLLECTED_VOLUMES;

    alter table SCI_PUBLICATIONS.ARTICLES_IN_COLLECTED_VOLUME 
       add constraint FK73gec0ihpth7ag3rpyx5y03ym 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.ARTICLES_IN_COLLECTED_VOLUME_AUD 
       add constraint FK6c2naayyg232vlysi6eni4n4t 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_AUD;

    alter table SCI_PUBLICATIONS.ARTICLES_IN_JOURNAL 
       add constraint FK9w2hcnd1nv6npjaej1c7frfoq 
       foreign key (JOURNAL_ID) 
       references SCI_PUBLICATIONS.JOURNALS;

    alter table SCI_PUBLICATIONS.ARTICLES_IN_JOURNAL 
       add constraint FK9kiypqq5nrxf7op7xmqpedcr3 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.ARTICLES_IN_JOURNAL_AUD 
       add constraint FKmbvnsne9r7s0dgqf3mjo4jvqg 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_AUD;

    alter table SCI_PUBLICATIONS.AUTHORSHIPS 
       add constraint FK511yle0sh7ynfr0vos2657jxb 
       foreign key (AUTHOR_ID) 
       references CCM_CMS.PERSONS;

    alter table SCI_PUBLICATIONS.AUTHORSHIPS 
       add constraint FK4nh95u54g4v0s929w7ak8eqh9 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.AUTHORSHIPS_AUD 
       add constraint FKpgv1mrosgrhxo1xptfxx6jy5m 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.COLLECTED_VOLUME_ITEMS 
       add constraint FK4mmyng3ibw4blg81mcs7ry09w 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_WITH_PUBLISHER_ITEMS;

    alter table SCI_PUBLICATIONS.COLLECTED_VOLUME_ITEMS_AUD 
       add constraint FKa2pma1qihmdmkoj7htx8cmhhn 
       foreign key (OBJECT_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATION_WITH_PUBLISHER_ITEMS_AUD;

    alter table SCI_PUBLICATIONS.COLLECTED_VOLUMES 
       add constraint FKr3e1xn290dvmxu0y2jcusif0w 
       foreign key (COLLECTED_VOLUME_ID) 
       references SCI_PUBLICATIONS.COLLECTED_VOLUMES;

    alter table SCI_PUBLICATIONS.COLLECTED_VOLUMES 
       add constraint FKh7jwby8k4ujlckp4yh1oj0obo 
       foreign key (OBJECT_ID) 
       references CCM_CMS.ASSETS;

    alter table SCI_PUBLICATIONS.COLLECTED_VOLUMES 
       add constraint FKqq7bm5yk69mck1xqsdgsv7ech 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER;

    alter table SCI_PUBLICATIONS.COLLECTED_VOLUMES_AUD 
       add constraint FKcwvisrc7uwfv09tdpr6iaiste 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER_AUD;

    alter table SCI_PUBLICATIONS.EXPERTISE_ITEMS 
       add constraint FK3fq1ybv4joj1o2vtlpp6fucs9 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS;

    alter table SCI_PUBLICATIONS.EXPERTISE_ITEMS_AUD 
       add constraint FK1g3vfjieiv7undl13aura3o4b 
       foreign key (OBJECT_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS_AUD;

    alter table SCI_PUBLICATIONS.EXPERTISES 
       add constraint FKrqjjaufa0wog2mf6d7e600pja 
       foreign key (ORDERER_ID) 
       references CCM_CMS.ORGANIZATIONS;

    alter table SCI_PUBLICATIONS.EXPERTISES 
       add constraint FK45ut3dfngbmq2qetav5gxvd0w 
       foreign key (ORGANIZATION_ID) 
       references CCM_CMS.ORGANIZATIONS;

    alter table SCI_PUBLICATIONS.EXPERTISES 
       add constraint FK66qyonw6r7injf1stp5bd8cmh 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.EXPERTISES_AUD 
       add constraint FKgxr0llrpe13xe1kqtiigrywl1 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_AUD;

    alter table SCI_PUBLICATIONS.GREY_LITERATURE 
       add constraint FKeuu7p19ry3kahoh3aj2glndky 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.UNPUBLISHED;

    alter table SCI_PUBLICATIONS.GREY_LITERATURE_AUD 
       add constraint FK2a4qi79mlk5w44u5gg9fs4va0 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.UNPUBLISHED_AUD;

    alter table SCI_PUBLICATIONS.GREY_LITERATURE_ITEMS 
       add constraint FKinppdy0jn78pre5vkns698cn1 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS;

    alter table SCI_PUBLICATIONS.GREY_LITERATURE_ITEMS_AUD 
       add constraint FK6okkvauo6pssm6gu3wdwjm7km 
       foreign key (OBJECT_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS_AUD;

    alter table SCI_PUBLICATIONS.IN_PROCEEDINGS 
       add constraint FK7exqhxb70wn9q71ge272of877 
       foreign key (PROCEEDINGS_ID) 
       references SCI_PUBLICATIONS.PROCEEDINGS;

    alter table SCI_PUBLICATIONS.IN_PROCEEDINGS 
       add constraint FKhn0111vgr8cl5tqanypynn360 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.IN_PROCEEDINGS_AUD 
       add constraint FK8fo8evhrsmxsiayyqxhvoidnm 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_AUD;

    alter table SCI_PUBLICATIONS.INPROCEEDINGS_ITEMS 
       add constraint FK966ey6ywsxvnr35867tltt3wu 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS;

    alter table SCI_PUBLICATIONS.INPROCEEDINGS_ITEMS_AUD 
       add constraint FKi7hpcqa4pv1yjsvrqiuobv3ub 
       foreign key (OBJECT_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS_AUD;

    alter table SCI_PUBLICATIONS.INTERNET_ARTICLE_ITEMS 
       add constraint FKppxruv5cc7kc74w3apmtri8ie 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS;

    alter table SCI_PUBLICATIONS.INTERNET_ARTICLE_ITEMS_AUD 
       add constraint FKiny2d24a2nalqd85f4h2rxnl3 
       foreign key (OBJECT_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS_AUD;

    alter table SCI_PUBLICATIONS.INTERNET_ARTICLES 
       add constraint FKl713m6fojno8jbd0ubflk119e 
       foreign key (ORGANIZATION_ID) 
       references CCM_CMS.ORGANIZATIONS;

    alter table SCI_PUBLICATIONS.INTERNET_ARTICLES 
       add constraint FK6uj1d44xmo475khtrymmwm776 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.INTERNET_ARTICLES_AUD 
       add constraint FKgg43dossf4jn8vksjbll8yj8d 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_AUD;

    alter table SCI_PUBLICATIONS.JOURNAL_ASSETS 
       add constraint FKh22ov88fr4isddmuyxjfcfcwd 
       foreign key (JOURNAL_ID) 
       references SCI_PUBLICATIONS.JOURNALS;

    alter table SCI_PUBLICATIONS.JOURNAL_ASSETS 
       add constraint FK161sf4asojdsllhfbysf7sj7p 
       foreign key (OBJECT_ID) 
       references CCM_CMS.ASSETS;

    alter table SCI_PUBLICATIONS.JOURNAL_ASSETS_AUD 
       add constraint FKfs4u1msy8lyd2i508vgxmvsp6 
       foreign key (OBJECT_ID, REV) 
       references CCM_CMS.ASSETS_AUD;

    alter table SCI_PUBLICATIONS.JOURNAL_DESCRIPTIONS 
       add constraint FKpsx3aiufkliaqtgkdny6aa8gs 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.JOURNALS;

    alter table SCI_PUBLICATIONS.JOURNAL_DESCRIPTIONS_AUD 
       add constraint FK3ddapdcyupurmj3fmp1f02s5h 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.JOURNALS_AUD 
       add constraint FK5g815tq8ig2sop0nyk8vofs18 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.MONOGRAPH_ITEMS 
       add constraint FK4wu49k70u575irwdiuje34hfh 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_WITH_PUBLISHER_ITEMS;

    alter table SCI_PUBLICATIONS.MONOGRAPHS 
       add constraint FKolchjrxixeeuedewi6wdotvvi 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER;

    alter table SCI_PUBLICATIONS.MONOGRAPHS_AUD 
       add constraint FKdqqjeyc2i2hm4l80utxf1kqs8 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER_AUD;

    alter table SCI_PUBLICATIONS.PROCEEDINGS 
       add constraint FKmmnnwj9y6esnslb7vwowxg137 
       foreign key (ORGANIZER_ID) 
       references CCM_CMS.ORGANIZATIONS;

    alter table SCI_PUBLICATIONS.PROCEEDINGS 
       add constraint FKaucka74b7c0hcvg8edjo3ulpd 
       foreign key (PROCEEDINGS_ID) 
       references SCI_PUBLICATIONS.PROCEEDINGS;

    alter table SCI_PUBLICATIONS.PROCEEDINGS 
       add constraint FKq5cs05ulosb5r672e5srij9cp 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER;

    alter table SCI_PUBLICATIONS.PROCEEDINGS 
       add constraint FK264qydclelfgj1enyy9tdq4x0 
       foreign key (OBJECT_ID) 
       references CCM_CMS.ASSETS;

    alter table SCI_PUBLICATIONS.PROCEEDINGS_AUD 
       add constraint FKl8bvqmfodha98ximjp7t9c3lb 
       foreign key (OBJECT_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER_AUD;

    alter table SCI_PUBLICATIONS.PROCEEDINGS_ITEMS 
       add constraint FKdsim9s7davro56eq4ymxahjol 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_WITH_PUBLISHER_ITEMS;

    alter table SCI_PUBLICATIONS.PUBLICATION_ABSTRACTS 
       add constraint FK8fbjwx9vl96b13attarip9orx 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.PUBLICATION_ABSTRACTS_AUD 
       add constraint FKf019goctk52kjb27f3upuqt23 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.PUBLICATION_EDITION 
       add constraint FKyujvtaokus0ieluckm4lb1dk 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER;

    alter table SCI_PUBLICATIONS.PUBLICATION_EDITION_AUD 
       add constraint FKax9o0nxp6s544vw2rjt85eduq 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.PUBLICATION_ITEMS 
       add constraint FKn03eberv5b0dgb6w8ry7ae33e 
       foreign key (publication_PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.PUBLICATION_ITEMS 
       add constraint FKrn0ks5nitd5l3o05baoslwet6 
       foreign key (OBJECT_ID) 
       references CCM_CMS.CONTENT_ITEMS;

    alter table SCI_PUBLICATIONS.PUBLICATION_ITEMS_AUD 
       add constraint FK3qa48t8g523ox7n3axndt9cke 
       foreign key (OBJECT_ID, REV) 
       references CCM_CMS.CONTENT_ITEMS_AUD;

    alter table SCI_PUBLICATIONS.PUBLICATION_MISC 
       add constraint FKdydprblytydq71t24ooh8spjk 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.PUBLICATION_MISC_AUD 
       add constraint FKaqvyxash8ycojlnvmp40s0hff 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.PUBLICATION_SHORT_DESCS 
       add constraint FKegp4u1x10npg4p2uqjydjh47g 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.PUBLICATION_SHORT_DESCS_AUD 
       add constraint FK6t3ssndalljbg57bgfxoassho 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.PUBLICATION_TITLES 
       add constraint FKnc32fyqp0ucp8lqxiamo4l8p0 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.PUBLICATION_TITLES_AUD 
       add constraint FKpnqkdo7x7fqo3da1lfctouoid 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.PUBLICATION_WITH_PUBLISHER_ITEMS 
       add constraint FKmfvhj91ra4ymh2651ysf71bip 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS;

    alter table SCI_PUBLICATIONS.PUBLICATION_WITH_PUBLISHER_ITEMS_AUD 
       add constraint FK4qf3we6cid5ymv5v4idcfie3r 
       foreign key (OBJECT_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS_AUD;

    alter table SCI_PUBLICATIONS.PUBLICATIONS_AUD 
       add constraint FKnsacjde3q91fn3xjqr1h3mojr 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER 
       add constraint FK6rdc01ay2i7mtii8rjmku718u 
       foreign key (publisher_PUBLISHER_ID) 
       references SCI_PUBLICATIONS.PUBLISHERS;

    alter table SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER 
       add constraint FKkb2w3u49sa2bmgidigdorakrb 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.PUBLICATIONS_WITH_PUBLISHER_AUD 
       add constraint FKk9v6qs932tyb23s1ldc61sbpe 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_AUD;

    alter table SCI_PUBLICATIONS.PUBLISHER_ASSETS 
       add constraint FKtq1s6r52b6bxk5qif3h2lh3jr 
       foreign key (PUBLISHER_ID) 
       references SCI_PUBLICATIONS.PUBLISHERS;

    alter table SCI_PUBLICATIONS.PUBLISHER_ASSETS 
       add constraint FK6k838fhlxr4i5c88kff012glr 
       foreign key (OBJECT_ID) 
       references CCM_CMS.ASSETS;

    alter table SCI_PUBLICATIONS.PUBLISHER_ASSETS_AUD 
       add constraint FKeynqrmdf8louyo3s6k4ry8dnd 
       foreign key (OBJECT_ID, REV) 
       references CCM_CMS.ASSETS_AUD;

    alter table SCI_PUBLICATIONS.PUBLISHERS_AUD 
       add constraint FKi7mw5r6vr3qe331k4hefxvv9m 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.SERIES_ASSETS 
       add constraint FKjuqe52dx4slgclfm1w6hu8oj9 
       foreign key (SERIES_ID) 
       references SCI_PUBLICATIONS.SERIES;

    alter table SCI_PUBLICATIONS.SERIES_ASSETS 
       add constraint FKaivevid650mqg5ej4pejali67 
       foreign key (OBJECT_ID) 
       references CCM_CMS.ASSETS;

    alter table SCI_PUBLICATIONS.SERIES_ASSETS_AUD 
       add constraint FKibwx1fa2avr1en0f19iontoms 
       foreign key (OBJECT_ID, REV) 
       references CCM_CMS.ASSETS_AUD;

    alter table SCI_PUBLICATIONS.SERIES_AUD 
       add constraint FK5o1f0aspcse562aicvc04j28k 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.SERIES_DESCRIPTIONS 
       add constraint FK74qiddpvtb8228veck5lmwja1 
       foreign key (SERIES_ID) 
       references SCI_PUBLICATIONS.SERIES;

    alter table SCI_PUBLICATIONS.SERIES_DESCRIPTIONS_AUD 
       add constraint FKnky7ijwsb9vrklce4met120u 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.SERIES_TITLES 
       add constraint FKtaur5ng47rhlec15x77x5ibod 
       foreign key (SERIES_ID) 
       references SCI_PUBLICATIONS.SERIES;

    alter table SCI_PUBLICATIONS.SERIES_TITLES_AUD 
       add constraint FKjlsitcnmb4hlcr4creywt3onc 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.TALK_ITEMS 
       add constraint FK195mq1bo20ij0qgaimd7vtrj6 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS;

    alter table SCI_PUBLICATIONS.TALK_ITEMS_AUD 
       add constraint FKaodqlyqr3d8usyuyfiu1og8lv 
       foreign key (OBJECT_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS_AUD;

    alter table SCI_PUBLICATIONS.TALKS 
       add constraint FKa92r1qnweudn3lnmgovg3n6qe 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.TALKS_AUD 
       add constraint FKf50h1wwsmwasj8bbu5ytkmor5 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_AUD;

    alter table SCI_PUBLICATIONS.UNPUBLISHED 
       add constraint FKavgft0bmkn9h1d2t0hsr581cq 
       foreign key (ORGANIZATION) 
       references CCM_CMS.ORGANIZATIONS;

    alter table SCI_PUBLICATIONS.UNPUBLISHED 
       add constraint FK8ekbtdgp9ghpo4v831l1llf5j 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.UNPUBLISHED_AUD 
       add constraint FKnul1y7ojsmqe1tj4hwequbpnm 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATIONS_AUD;

    alter table SCI_PUBLICATIONS.VOLUMES_IN_SERIES 
       add constraint FKhgdxb1bueh4t4cok2j6k047so 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.PUBLICATIONS;

    alter table SCI_PUBLICATIONS.VOLUMES_IN_SERIES 
       add constraint FK4jn6udpkvjbbsr3ce9vp9s39b 
       foreign key (SERIES_ID) 
       references SCI_PUBLICATIONS.SERIES;

    alter table SCI_PUBLICATIONS.VOLUMES_IN_SERIES_AUD 
       add constraint FKcg5dt9klrcic84fa0pfnjw95d 
       foreign key (REV) 
       references CCM_CORE.CCM_REVISIONS;

    alter table SCI_PUBLICATIONS.WORKING_PAPER_ITEMS 
       add constraint FKtks13pm4b5p0f17k23jgmh3ck 
       foreign key (OBJECT_ID) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS;

    alter table SCI_PUBLICATIONS.WORKING_PAPER_ITEMS_AUD 
       add constraint FK1bvqdgejxslosb1i2jhp2ccj5 
       foreign key (OBJECT_ID, REV) 
       references SCI_PUBLICATIONS.PUBLICATION_ITEMS_AUD;

    alter table SCI_PUBLICATIONS.WORKING_PAPERS 
       add constraint FKrgycdcsqjys92pefscuocauba 
       foreign key (PUBLICATION_ID) 
       references SCI_PUBLICATIONS.UNPUBLISHED;

    alter table SCI_PUBLICATIONS.WORKING_PAPERS_AUD 
       add constraint FKdr1f43snxlftynbewpcnqaelo 
       foreign key (PUBLICATION_ID, REV) 
       references SCI_PUBLICATIONS.UNPUBLISHED_AUD;
