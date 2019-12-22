<#--filedoc
    Functions for processing library signatures.
-->

<#--doc
    Gets the library of the signature.

    @param signature The signature to use.

    @return The library of the signature.
-->
<#function getLibrary signature>
    <#return signature.library>
</#function>

<#--doc
    Gets the libray specific signature.

    @param signature The signature to use.

    @return The signature associated with the book for the a specific library.
-->
<#function getSignature signature>
    <#return signature.signature>
</#function>

<#--doc
    Gets the link to the library.

    @param signature The signature to use.

    @return The link to the library of the signature.
-->
<#function getLibraryLink signature>
    <#return signature.libraryLink>
</#function>

<#--doc
    Gets the value of `misc` property to the signature.

    @param signature The signature to use.

    @return The value of `misc` of the signature.
-->
<#function getMisc signature>
    <#return signature.misc>
</#function>


