<#macro carrefourPostList termId pageNum=1 numPerPage=8 >
    <@postListDirective termId="${termId}" pageNum="${pageNum}" numPerPage="${numPerPage}">
                          <tr><td colspan="2" height="385" valign="top">
                                <table cellpadding="0" cellspacing="0" border="0" align="left">
                                    <tr>
                                        <td align="left">
                                            <table cellpadding="0" cellspacing="0" align="left" class="classtab8">
                                    <#list 0..11 as itemIndex >
                                    <#assign post = postPage.result[itemIndex]>
                                    <#if post >
                                                    <td width="170" valign="top">
                                                    
                                                        <table cellpadding="0" cellspacing="0" border="0" width="170" height="185" align="center" style="background:url(${themeUrl}/images/class8shidebg.jpg);" id="item_${post.id}">
                                            <tr><td valign="top">
                                                    
                                                    
                                                        <table cellpadding="0" cellspacing="0" border="0" align="center" width="150">
                                                            <tr><td height="10"></td></tr>
                                                            <tr><td align="right" height="15"><input type="checkbox" disabled="disabled" id="checkBox_${post.id}" style="z-index:999;"/></td></tr>
                                                            <tr>
                                                              <td height="110" align="center"><img src="${mediaUrl}${postMetaTool.getImageUrlByIndex(post,"sys_item_no","sys_item_image_path","1")}"  width="150" height="110" /></td></tr>
                                                            <tr><td>
                                                                    <table cellpadding="0" cellspacing="0" border="0" align="left">
                                                                        <tr><td width="19"><input type="image" id="item_href_${itemIndex}" src="${themeUrl}/images/list2font.png" onclick="CarrefourModel.addItem(${post.id})" onfocus="changeBKG('item_${post.id}')" onblur="reBKG('item_${post.id}')"></td>
                                                                            <td width="51" style="font-size:12px; color:#FFF; font-weight:bold; line-height:19px;">${postMetaTool.getMetaValue(post,"sys_key_present_price")} <br /><font style="text-decoration:line-through">${postMetaTool.getMetaValue(post,"sys_key_old_price")}</font></td>
                                                                            <td width="80" class="classtab12font">${postMetaTool.getMetaValue(post,"sys_key_item_brand")}<br/>${post.slug}</td>
                                                                        </tr>
                                                                    </table>
                                                            </td></tr>
                                                        </table>
                                                        
                                                        </td></tr>
                                                        </table>
                                                        
                                                        
                                                    </td>
                                          <#else>
                                             <td width="170" valign="top">
                                             <table cellpadding="0" cellspacing="0" border="0" width="170" height="185" align="center" >
                                             <tr><td valign="top">
                                                        <table cellpadding="0" cellspacing="0" border="0" align="center" width="150">
                                                            <tr><td height="10"></td></tr>
                                                            <tr><td align="right" height="15"></td></tr>
                                                            <tr>
                                                              <td height="110" align="center"></td></tr>
                                                            <tr><td>
                                                                     
                                                            </td></tr>
                                                        </table>
                                                        
                                                        </td></tr>
                                             </table>
                                             </td>
                                          </#if>
                                          <#if itemIndex = "5">
                                                    </tr>
                                                <tr>
                                          </#if>
                                        </#list>
                           </tr>
                                            </table>
                                        </td>
                                        <td width="86" valign="top">
                                            <table cellpadding="0" cellspacing="0" border="0" width="79" height="372" background="${themeUrl}/images/classlistnavbg.jpg" align="right">
                                                <tr><td height="10"></td></tr>
                                                <tr><td align="center" height="61"><a href="javascript:CarrefourModel.order();"><img src="${themeUrl}/images/ljdgbotton.jpg" /></a></td></tr>
                                                <tr><td height="40"></td></tr>
                                                <tr><td align="center" height="54"><a href="javascript:CarrefourModel.lastPage();"onMouseOver="swichImage('upBtnImage','${themeUrl}/images/upbottonshow.jpg')" onMouseOut="swichImage('upBtnImage','${themeUrl}/images/upbotton.jpg')"><img id="upBtnImage" src="${themeUrl}/images/upbotton.jpg" /></a></td></tr>
                                                <tr><td height="10"></td></tr>
                                                <tr><td align="center" height="25" style="font-size:18px; font-weight:bold; color:#424242;"><font style="color:#F56431;">${postPage.pageNum}</font>/${postPage.getTotalPages()}</td></tr>
                                                <tr><td height="10"></td></tr>
                                                <tr><td height="54" align="center"><a href="javascript:CarrefourModel.nextPage();" onMouseOver="swichImage('downBtnImage','${themeUrl}/images/downbottonshow.jpg')" onMouseOut="swichImage('downBtnImage','${themeUrl}/images/downbotton.jpg')"><img id="downBtnImage" src="${themeUrl}/images/downbotton.jpg" /></a></td></tr>
                                                <tr><td height="10"></td></tr>
                                                <tr><td align="center"><a href="javascript:history.go(-1);"><img src="${themeUrl}/images/backbotton.jpg" /></a></td></tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                          </td></tr>                       
    </@postListDirective>
    <div style="color:red;"></div>
    <script type="text/javascript">
        var CarrefourModel = {};

        CarrefourModel.addItem = function(itemId){
            var checkBoxId = 'checkBox_' + itemId;
            var checkBox = document.getElementById(checkBoxId);
            var isChecked = checkBox.checked;
            if(!isChecked) {
                checkBox.checked = true;
                reBKG('item_'+itemId);
                changeBKG('item_'+itemId);
                CarrefourModel.addItemToCart(itemId,true);
            }else{
                checkBox.checked = false;
                reBKG('item_'+itemId);
                changeBKG('item_'+itemId);
                CarrefourModel.addItemToCart(itemId,false); 
            }
        }
        
        CarrefourModel.addItemToCart = function(itemId,flag){
                var param = "itemId="+itemId+"&flag="+flag+"&remove="+!flag;
                if(flag){
                    param += "&postId="+itemId+"&termId=${termId}";
                }
                
                $.ajax({
                   url: "addItemToCart.do",
                   cache: false,
                   type: "POST",
                   data: param,
                   success: function(msg){
                        var itemCountSpan = document.getElementById('itemCountSpan');
                        var itemSize =itemCountSpan.innerHTML;
                        if(flag){
                            itemSize++;
                        }else{
                            itemSize--;
                        }
                        itemCountSpan.innerHTML = itemSize;
                   }
                });
        }
        
        CarrefourModel.checkSelectedItems = function(){
            var selectedItems = '${postMetaTool.itemKeyToString(session_cart)}';
            if(!selectedItems){
                return;
            }
            selectedItems = selectedItems.split(",");
            for(i=0;i<selectedItems.length;i++){
                var itemId = selectedItems[i];
                var checkBoxId = 'checkBox_' + itemId;
                var checkBox = document.getElementById(checkBoxId);
                if(checkBox){
                    checkBox.checked = "checked";
                }
            }
        }
        
        CarrefourModel.order = function(){
            var href = 'toCart.do';
            window.location.href = href;
        }
        
        CarrefourModel.nextPage = function(){
            var pageNum = ${postPage.pageNum};
            var totalPages = ${postPage.getTotalPages()};
            if(pageNum==totalPages){
               return;
            }
            var href = 'carrefour.html?menu=${menu}&termId=${termId}&pageNum='+(pageNum+1);
            window.location.href = href;
        }
        
        CarrefourModel.lastPage = function(){
            var pageNum = ${postPage.pageNum};
            if(pageNum==1){
               return;
            }
            var href = 'carrefour.html?menu=${menu}&termId=${termId}&pageNum='+(pageNum-1);
            window.location.href = href;
        }
        
        CarrefourModel.checkSelectedItems();
    </script>
</#macro>            