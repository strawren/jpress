<@postDetailDirective postId="${postId}" >
<table cellpadding="0" cellspacing="0" border="0" align="center">
    <tr><td width="640">
            <#assign videoImage='${postMetaTool.getImageUrlByIndex(post,"sys_item_no","sys_item_image_path","2")}'>
            <#if videoImage><#assign videoImagePath='${mediaUrl}${videoImage}'><#else><#assign videoImagePath='${mediaUrl}articlebigimg.jpg'></#if>
            <table cellpadding="0" cellspacing="0" border="0" align="left" style="margin-top:4px;">
                    <tr><td width="92" valign="top">
                            <table cellpadding="0" cellspacing="0" border="0" align="left">
                                <tr><td width="92" height="93" align="center"><a href="javascript:playVideo('${postMetaTool.getMetaValue(post,"item_video_id")}');" onMouseOver="changeItemBigImage('${videoImagePath}')"><img src="${videoImagePath}" width="92" height="92" /></a></td></tr>
                                <tr><td height="93" align="center"><a href="#" onMouseOver="changeItemBigImage('${mediaUrl}${postMetaTool.getImageUrlByIndex(post,"sys_item_no","sys_item_image_path","3")}')"> <img src="${mediaUrl}${postMetaTool.getImageUrlByIndex(post,"sys_item_no","sys_item_image_path","3")}" width="92" height="92" /></a></td></tr>
                                <tr><td height="93" align="center"><a href="#" onMouseOver="changeItemBigImage('${mediaUrl}${postMetaTool.getImageUrlByIndex(post,"sys_item_no","sys_item_image_path","4")}')"><img src="${mediaUrl}${postMetaTool.getImageUrlByIndex(post,"sys_item_no","sys_item_image_path","4")}" width="92" height="92" /></a></td></tr>
                                <tr><td height="93" align="center"><a href="#" onMouseOver="changeItemBigImage('${mediaUrl}${postMetaTool.getImageUrlByIndex(post,"sys_item_no","sys_item_image_path","5")}')"><img src="${mediaUrl}${postMetaTool.getImageUrlByIndex(post,"sys_item_no","sys_item_image_path","5")}" width="92" height="92"/></a></td></tr>
                            </table>
                        </td>
                        <td width="3"></td>
                        <td width="532" height="377" background="${themeUrl}/images/articleshowbg.jpg" align="center">
                            <img src="${videoImagePath}" width="500" height="349" id="itemBigImage"/> 
                        </td>
                    </tr>
            </table>

        </td>
        <td width="470" valign="top">
            <table cellpadding="0" cellspacing="0" border="0" align="left" width="455" style="margin-top:4px;">
                <tr><td height="59"  colspan="2" background="${themeUrl}/images/articletextbg.jpg" style=" text-indent:40px;"><input type="text" style="border:none; height:30px; line-height:30px; background:none; width:390px; color:#626262;" value="请输入您的手机号码" id="phoneInput" maxlength="11" onfocus="clearTip()" onblur="showTip()" onkeyup="phoneChange()"/></td></tr>
                <tr><td align="left" height="47" style="padding-left:5px;"><a href="javascript:orderItem(${postId});"><img src="${themeUrl}/images/articleljdgbottonshide.jpg" id="orderItemBtn"/></a></td>
                  <td align="right" style="padding-right:5px;"><a href="javascript:history.go(-1);"><img src="${themeUrl}/images/articlebackbotton.jpg" /></a></td>
              </tr>
                <tr><td  colspan="2">
                        <table cellpadding="0" cellspacing="0" border="0" align="center" width="448" height="260" bgcolor="#DFDAD6" style="margin-top:4px;">
                            <tr><td height="58"><img src="${themeUrl}/images/articleewmbg_r1_c1.jpg" height="58" width="448" /></td></tr>
                            <#assign qrCodeImage='${postMetaTool.getImageUrlByIndex(post,"sys_item_no","sys_item_image_path","6")}'>
                            <tr><td height="202" align="center" valign="top"><img src="<#if qrCodeImage>${mediaUrl}${qrCodeImage}<#else>${mediaUrl}articleewmbg_r2_c2.jpg</#if>" width="196" height="185" /></td></tr>
                        </table>
                </td></tr>
            </table>
        </td>
    </tr>
</table>
</@postDetailDirective>