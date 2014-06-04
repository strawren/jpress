<#macro postList termId pageNum=1 numPerPage=4 >
    <@postListDirective termId="${termId}" pageNum="${pageNum}" numPerPage="${numPerPage}">
                          <tr><td colspan="2" height="385" valign="top">
                                <table cellpadding="0" cellspacing="0" border="0" align="left">
                                    <#list 0..3 as itemIndex >
                                    <#assign post = postPage.result[itemIndex]>
                                    <#if post >
                                    <td width="252" valign="top">
                                        
                                            <table cellpadding="0" cellspacing="0" border="0" width="252" height="375" align="center" style="background:url(${themeUrl}/images/classlistshidebg.jpg);" id="item_${post.id}">
                                            <tr><td valign="top">
                                            
                                            <table cellpadding="0" cellspacing="0" border="0" align="center" width="237">
                                                <tr><td width="237" height="10"></td></tr>
                                                <tr><td height="25" align="right"></td></tr>
                                                <#--<tr><td height="20"></td></tr>-->
                                                <tr><td height="200"><img src="${mediaUrl}${postMetaTool.getImageUrlByIndex(post,"sys_item_no","sys_item_image_path","1")}" width="230" height="260"/></td></tr>
                                                <tr><td height="10"></td></tr>
                                                <tr><td height="55">
                                                        <table cellpadding="0" cellspacing="0" border="0" style="color:#838383; font-size:16px;">
                                                            <tr><td width="53" height="54" background="${themeUrl}/images/pricebg.jpg" style="color:#FFFFFF; font-size:12px; font-weight:bold;" align="center"><a href="item.html?menu=${menu}&termId=${termId}&postId=${post.id}" onMouseOver="changeBKG('item_${post.id}')" onMouseOut="reBKG('item_${post.id}')" class="white" id="item_href_${itemIndex}"><br /><br />${postMetaTool.getMetaValue(post,"sys_key_present_price")}</a></td>
                                                                <td width="10"></td>
                                                                <td width="160" valign="bottom" style="padding-bottom:3px; font-weight:bold;">${postMetaTool.getMetaValue(post,"sys_key_item_brand")}&nbsp;${post.slug}<br /><font style="text-decoration:line-through;">原价：￥${postMetaTool.getMetaValue(post,"sys_key_old_price")}</font></td>
                                                            </tr>
                                                        </table>
                                                </td></tr>
                                            </table>
                                            
                                            </td></tr>
                                            </table>
                                            
                                            
                                        </td>
                                        <td width="3"></td>
                                        <#else>
                                        <td width="252" valign="top">
                                        </td>
                                        <td width="3"></td>
                                        </#if>
                                        </#list>
                          <td width="86" valign="top">
                                            <table cellpadding="0" cellspacing="0" border="0" width="79" height="372" background="${themeUrl}/images/classlistnavbg.jpg" align="right">
                                                <tr><td height="15"></td></tr>
                                                <tr><td height="40"></td></tr>
                                                <tr><td align="center" height="54"><a href="<#if postPage.pageNum==1>javascript:void(0);<#else>term.html?menu=${menu}&termId=${termId}&pageNum=${postPage.pageNum-1}</#if>" onMouseOver="swichImage('upBtnImage','${themeUrl}/images/upbottonshow.jpg')" onMouseOut="swichImage('upBtnImage','${themeUrl}/images/upbotton.jpg')"><img id="upBtnImage" src="${themeUrl}/images/upbotton.jpg" /></a></td></tr>
                                                <tr><td height="10"></td></tr>
                                                <tr><td align="center" height="25" style="font-size:18px; font-weight:bold; color:#424242;"><font style="color:#F56431;">${postPage.pageNum}</font>/${postPage.getTotalPages()}</td></tr>
                                                <tr><td height="10"></td></tr>
                                                <tr><td height="54" align="center"><a href="<#if postPage.pageNum==postPage.getTotalPages()>javascript:void(0);<#else>term.html?menu=${menu}&termId=${termId}&pageNum=${postPage.pageNum+1}</#if>" onMouseOver="swichImage('downBtnImage','${themeUrl}/images/downbottonshow.jpg')" onMouseOut="swichImage('downBtnImage','${themeUrl}/images/downbotton.jpg')"><img id="downBtnImage" src="${themeUrl}/images/downbotton.jpg" /></a></td></tr>
                                                <tr><td height="10"></td></tr>
                                                <tr><td align="center"><a href="javascript:history.go(-1);"><img src="${themeUrl}/images/backbotton.jpg" /></a></td></tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                          </td></tr>
    </@postListDirective>
</#macro>                                        