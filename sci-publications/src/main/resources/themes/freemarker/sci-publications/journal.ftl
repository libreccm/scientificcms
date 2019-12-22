<#import "/ccm-cms/content-item.ftl" as ContentItem>

<#--filedoc
    Functions for processing the the data of Journals.
-->

<#--doc
    Gets the value of the `firstYear` property of the journal.

    @param journal The journal to use.

    @return The value of the `firstYear` property of the Journal.
-->
<#function getFirstYear(journal)>
    <#return journal.firstYear>
</#function>

<#--doc
    Constructs the link to the detail view of the journal.

    @param journal The journal to use.

    @return The URL of the detail view of the Journal.
-->
<#function getHref journal>
    <#return ContentItem.generateContentItemLink(journal.uuid)>
</#function>

<#--doc
    Gets the ISSN of the Journal.

    @param journal The journal to use.

    @return The ISSN of the Journal.
-->
<#function getIssn journal>
    <#return journal.issn>
</#function>

<#--doc
    Gets the value of the `lastYear` property of the journal.

    @param journal The journal to use.

    @return The value of the `firstYear` property of the Journal.
-->
<#function getLastYear(journal)>
    <#return journal.lastYear>
</#function>

<#--doc
    Gets the title of the Journal.

    @param journal The journal to use.

    @return The title of the journal.
-->
<#function getTitle journal>
    <#return journal.title>
</#function>



