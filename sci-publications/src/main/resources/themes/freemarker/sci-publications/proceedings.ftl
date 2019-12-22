<#import "/ccm-cms/content-item.ftl" as ContentItem>

<#--filedoc
    Functions for processing Proceedings items.
-->

<#--doc
    Gets the URL of the detail view of the proceedings item.

    @param proceedings The proceedings item to use.

    @return The URL of the detail view of the proceedings.
-->
<#function getHref proceedings>
    <#return ContentItem.generateContentItemLink(proceedings.uuid)>
</#function>

<#--doc
    Gets the URL of the detail of a paper (InProceedings item) of a proceedings 
    publication.

    @param paper The InProceedings item to use.

    @return The URL of the detail view of the paper.
-->
<#function getPaperHref paper>
    <#return ContentItem.generateContentItemLink(paper.uuid)>
</#function>