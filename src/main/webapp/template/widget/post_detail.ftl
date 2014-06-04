<#macro postDetail postId >
    <@postDetailDirective postId="${postId}" >
    <table cellpadding="0" cellspacing="0" border="0" align="right" width="908" height="132" bgcolor="#DFDBD4">
      <tr>
        <td>
            <table cellpadding="0" cellspacing="0" border="0" align="center" width="888" height="112">
                <tr>
                    <td colspan="2" height="40" style="border-bottom:1px solid #CBC9C6; font-size:20px; font-weight:bold; color:#646464; text-indent:10px;" align="left">
                        ${post.title}
                    </td>
                </tr>
                <tr>
                    <td width="50%" style="font-size:16px; color:#646464; text-indent:10px;">润 有 价：￥<font style="font-size:40px; color:#f56531; font-weight:bold;">${postMetaTool.getMetaValue(post,"sys_key_present_price")}</font></td>
                    <td width="50%" style="font-size:16px; color:#646464;">${postMetaTool.getMetaValue(post,"sys_item_content_1")}</td>
                </tr>
                <tr>
                    <td width="50%" style="font-size:16px; color:#646464; text-indent:10px;">
商品编号：<font style="font-weight:bold; font-size:14px;">${postMetaTool.getMetaValue(post,"sys_item_no")}</font></td>
                    <td width="50%" style="font-size:16px; color:#646464;">${postMetaTool.getMetaValue(post,"sys_item_content_2")}</td>
                </tr>
            </table>
        </td>
      </tr>
</table>
    </@postDetailDirective>
</#macro>