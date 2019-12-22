<#import "/ccm-cms/content-item.ftl" as ContentItem>

<#--doc
    Functions for the processing collected volumes.
-->

<#--doc
    Constructs the the link to the detail view to a collected volume.

    @param collectedVolume The Collected Volume to use.

    @return The link to the detail view of the Collected Volume.
-->
<#function getHref collectedVolume>
    <#return ContentItem.generateContentItemLink(collectedVolume.uuid)>
</#function>