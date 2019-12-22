<#--filedoc
    Functions for processing SciProjects
-->

<#--doc
    Gets the begin date of a SciProject.

    @param item The SciProject to use.

    @return The begin date of the project.
-->
<#function getBegin item>
    <#return item.begin>
</#function>

<#--doc
    Gets the end date of a SciProject.

    @param item The SciProject to use.

    @return The end date of the project.
-->
<#function getEnd item>
    <#return item.end>
</#function>

<#--doc
    Gets the description of a SciProject.

    @param item The SciProject to use.

    @return The description of the project.
-->
<#function getDescription item>
    <#return item.description>
</#function>

<#--doc
    Gets the short description of a SciProject.

    @param item The SciProject to use.

    @return The short description of the project.
-->
<#function getShortDescription item>
    <#return item.shortDescription>
</#function>

<#--doc
    Gets the sponsors of a project.

    @param item The SciProject to use.

    @return A sequence of sponsors (organizations).
-->
<#function getSponsors item>
    <#return item.sponsors>
</#function>

<#--doc
    Gets the name of sponsor.

    @param sponsor The sponsor to use.

    @return The name of the sponsor.
-->
<#function getSponsorName sponsor>
    <#return sponsor.name>
</#function>

<#--doc
    Determines if the sponsor has a funding code.

    @param sponsor The sponsor to use.

    @return `true` if a funding code is found, `false` otherwise.
-->
<#function hasSponsorFundingCode sponsor>
    <#return (sponsor.fundingCode??)>
</#function>

<#--doc
    Gets the funding code of sponsor.

    @param sponsor The sponsor to use.

    @return The funding code of the sponsor.
-->
<#function getSponsorFundingCode sponsor>
    <#return sponsor.fundingCode>
</#function>

<#--doc
    Gets a link to the sponsor.

    @param sponsor The sponsor to use.

    @return A link to the sponsor.
-->

<#function getSponsorLink sponsor>
    <#return sponsor.href>
</#function>

<#--doc
    Determines if a SciProject has value for the `funding` property.

    @param item The SciProject to use.

    @return `true` if a value for the `funding` property is found, `false` 
    otherwise.
-->
<#function hasFunding item>
    <#return (item.funding??)>
</#function>

<#--doc
    Gets the value of the `funding` property.

    @param item The SciProject to use.

    @return The value of the `funding` property.
-->
<#function getFunding item>
    <#return item.funding>
</#function>

<#--doc
    Determines if a SciProject has value for the `fundingVolume` property.

    @param item The SciProject to use.

    @return `true` if a value for the `fundingVolume` property is found, `false` 
    otherwise.
-->
<#function hasFundingVolume item>
    <#return (item.fundingVolume??)>
</#function>

<#--doc
    Gets the value of the `fundingVolume` property.

    @param item The SciProject to use.

    @return The value of the `fundingVolume` property.
-->
<#function getFundingVolume item>
    <#return item.fundingVolume>
</#function>

<#--doc
    Gets the members of a project.

    @param item The SciProject to use.

    @return A sequence of the members (person items) of the project.
-->
<#function getMembers item>
    <#return item.members>
</#function>

<#--doc
    Gets the role of a member of the project.

    @param member The member to use.

    @return The role of the member.
-->
<#function getMemberRole member>
    <#return member.role>
</#function>

<#--doc
    Gets the status of a member of the project.

    @param member The member to use.

    @return The status of the member.
-->
<#function getMemberStatus member>
    <#return member.status>
</#function>

<#--doc
    Gets the ID of a member of the project.

    @param member The member to use.

    @return The ID of the member.
-->
<#function getMemberId member>
    <#return member.id>
</#function>

<#--doc
    Gets the link to the member of the project.

    @param member The member to use.

    @return The link to the member.
-->
<#function getMemberLink member>
    <#return CMS.generateContentItemLink(member.uuid)>
</#function>

<#--doc
    Gets the surname of member of the project.

    @param member The member to use.

    @return The surname of the member.
-->
<#function getMemberSurname member>
    <#return member.surname>
</#function>

<#--doc
    Gets the given name of member of the project.

    @param member The member to use.

    @return The given name of the member.
-->
<#function getMemberGivenName member>
    <#return member.givenName>
</#function>

<#--doc
    Gets the name prefix of member of the project.

    @param member The member to use.
    @depcrecated Use getMemberPrefix

    @return The name prefix of the member.
-->
<#function getMemberTitlePre member>
    <#return getMemberPrefix(member)>
</#function>

<#--doc
    Gets the name prefix of member of the project.

    @param member The member to use.

    @return The name prefix of the member.
-->
<#function getMemberPrefix member>
    <#return member.prefix>
</#function>

<#--doc
    Gets the name suffix of member of the project.

    @param member The member to use.

    @depcrecated use getMemberSuffix


    @return The name suffix of the member.
-->
<#function getMemberTitlePost member>
    <#return getMemberSuffix(member)>
</#function>

<#--doc
    Gets the name suffix of member of the project.

    @param member The member to use.

    @return The name suffix of the member.
-->
<#function getMemberSuffix member>
    <#return member.suffix>
</#function>

<#--doc
    Gets organizations associated with the project.

    @param item The SciProject to use.

    @return A sequence of the organizations associated with the project.
-->
<#function getInvolvedOrganizations item>
    <#return item.involvedOrganizations>
</#function>

<#--doc
    Gets the name of an organization associated with the project.
    
    @param orga The organization to use.

    @return The name of the organization.
-->
<#function getInvolvedOrganizationName orga>
    <#return orga.title>
</#function>

<#--doc
    Gets the link to an organization associated with the project.

    @param orga The organization to use.

    @return The link the the organization associated with the project.
-->
<#function getInvolvedOrganizationLink orga>
    <#return orga.link>
</#function>

