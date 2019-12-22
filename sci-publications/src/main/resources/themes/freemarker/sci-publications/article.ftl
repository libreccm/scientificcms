<#import "/ccm-cms/content-item.ftl" as ContentItem>

<#--filedoc
    Functions for publications of the types ArticleInCollectedVolume.
    ArticleInJournal and InProceedings.
-->

<#--doc
    Constructs the link to the detail view for the article.

    @param article The article to use.

    @return The link to the detail view of the article.
-->
<#function getHref article>
    <#return ContentItem.generateContentItemLink(article.uuid)>
</#function>
