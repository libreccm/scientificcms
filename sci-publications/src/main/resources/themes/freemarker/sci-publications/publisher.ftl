<#--filedoc
    Functions for processing publishers.
-->

<#--doc
    Gets the name of a publisher.

    @param publisher The publisher to use.

    @return The name of the publisher.
-->
<#function getName publisher>
    <#return publisher.publisherName>
</#function>

<#--doc
    Gets the place of the publisher.

    @param publisher The publisher to use.

    @return The place of the publisher.
-->
<#function getPlace publisher>
    <#return publisher.place>
</#function>