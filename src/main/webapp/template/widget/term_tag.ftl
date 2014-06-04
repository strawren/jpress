<#macro tagList grpupName >

<table cellpadding="0" cellspacing="0" border="0" align="right">
	<tr>
		<@gainTermTaxonomyDirective filter_EQS_GROUP_NAME = grpupName filter_EQS_TAXONOMY="tag">
			<#list termTaxonomyList as tagModel>
				<@gainTermMetaDirective filter_EQL_TERM_ID= "${tagModel.termId}" filter_EQS_KEY = "sys_feature_prcture"  >
					<td width="227" align="left"><a href="term.html?menu=${menu}&termId=${tagModel.termId}"><img src="${postMetaTool.getImageUrl(termMeta)}" /></a></td>
				</@gainTermMetaDirective>
			</#list>
		</@gainTermTaxonomyDirective>
	</tr>
</table>
</#macro>


<#macro singleTag grpupName>
	<@gainTermTaxonomyDirective filter_EQS_GROUP_NAME = grpupName filter_EQS_TAXONOMY="tag" >
			<@gainTermMetaDirective filter_EQL_TERM_ID= "${termTaxonomy.termId}" filter_EQS_KEY = "sys_feature_prcture" >
				<td height="332"><a href="term.html?menu=${menu}&termId=${termTaxonomy.termId}"><img src="${postMetaTool.getImageUrl(termMeta)}" width="344" height="332" /></a></td>
			</@gainTermMetaDirective>
	</@gainTermTaxonomyDirective>
</#macro>