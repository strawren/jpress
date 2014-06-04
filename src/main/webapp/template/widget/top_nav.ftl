
<#macro topNav menuBarSlug menuNum>
	<@menuNavDirective menuBarSlug = menuBarSlug  menuNum = menuNum>
		<table cellpadding="0" cellspacing="0" border="0" align="left" class="menutab">
        	<tr>
        		<#list menuList as menu>
        			<td><a href="main.html"><img src="${request_themeUrl}/images/${menu.imgName}" /></a></td>
        		</#list>
                <td width="70"></td>
                <td width="10" style="border-left:1px solid #CCCCCC; padding:0px;"></td>
                <td width="50" style=" padding:0px; background:url(images/menugwc.jpg) no-repeat center center; color:#FFFFFF;"><a href="shopping cart.html" class="white"><span style="display:inline-block; width:55px; height:20px; text-align:left; text-indent:27px;">17</span></a></td>
            </tr>
        </table>
    </@menuNavDirective>
</#macro>

<#macro subMenu menuBarSlug menuSlug >
	<@menuNavDirective menuBarSlug = menuBarSlug menuSlug = menuSlug >
		 <table cellpadding="0" cellspacing="0" border="0" align="right">
	         <tr>
	         	<#list menuList as menu>
	         		<@gainPostMetaDirective postId="${menu.id}" key="target_menu_object_id" >
		         		<#if type == "term">
			         			<@gainTermMetaDirective filter_EQL_TERM_ID="${postMeta.value}" filter_EQS_KEY="sys_term_picture">
				         			<td width="115" align="right"><a href="carrefour.html?menu=menu2&termId=${termMeta.termId}" onmouseover="chageMenuImgToValid('${menu.id}','${termMeta.value}')"  onmouseout="chageMenuImgToInvalid('${menu.id}','${termMeta.value}')" ><img id="submenu_${menu.id}" src="${themeUrl}/term_images/${termMeta.value}"/></a></td>
				         		</@gainTermMetaDirective>
			         	</#if>
			         	<#if type == "post">
			         		<!-- 菜单属性 -->
				         	<@gainPostMetaDirective postId="${menu.id}" key="target_menu_object_id" >
				         		<!-- 菜单指向的post属性 -->
			         			<@gainPostMetaDirective postId="${postMeta.value}" key="sys_term_picture">
			         				<!-- 属性对应的具体post -->
				         			<@gainPostDirective postId = "${postMeta.value}">
					         			<td width="115" align="right"><a href="post.html?menu=menu2&termId=${menu.id}" onmouseout="chageMenuImgToValid('${menu.id}','${termMeta.value }')" onmouseover="chageMenuImgToInvalid('${menu.id}','${termMeta.value}')" ><img id="submenu_${menu.id}" src="${themeUrl}/term_images/${termMeta.value}"/></a></td>
					         		</@gainPostDirective>
				         		</@gainPostMetaDirective>
				         	</@gainPostMetaDirective>	
			         	</#if>		
	         		</@gainPostMetaDirective>
		        </#list>
	         </tr>
         </table>
    </@menuNavDirective>
</#macro>
<script type="text/javascript">

	
	function chageMenuImgToInvalid(menuId,menuImg){
		var url = "${themeUrl}/term_images/"
		var imgTemp = "submenu_" + menuId;
		document.getElementById(imgTemp).src=url + menuImg; 
	}
	
	function chageMenuImgToValid(menuId,menuImg){
		var url = "${themeUrl}/term_images/"
		var imgTemp = "submenu_" + menuId;
		var index = menuImg.indexOf(".")
		var suffix = menuImg.substring(index,menuImg.length)
		var imgName = menuImg.substring(0,index-2);
		var shwoImage = imgName + suffix;
		document.getElementById(imgTemp).src=url + shwoImage; 
	}

</script>
<script type="text/javascript">
	function changeBKG(id){
	document.getElementById(id).style.background = "url(images/class8showbg.jpg)";
	}
	function reBKG(id){
	document.getElementById(id).style.background = "url(images/class8shidebg.jpg)";
	}
</script>
