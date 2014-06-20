<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>东方亿付电视商务系统--内容管理--仪表盘</title>
        <jsp:include page="/common/meta.jsp"></jsp:include>
        <!-- styles -->
        <!-- 
        <link href="${ctx }/css/clivia-admin-all.css" rel="stylesheet" />
        -->
        <jsp:include page="/common/style.jsp"></jsp:include>
         
        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
        <script src="${ctx }/scripts/ghtml5/ghtml5.js"></script>
        <![endif]-->
        <jsp:include page="/common/scripts.jsp"></jsp:include>
        <script type="text/javascript">
        
            var MenuModel = {};
            $(document).ready(function() {
            	var setting1 = {
                    edit: {
                        enable: true,
                        showRemoveBtn: false,
                        showRenameBtn: false,
                        drag:{
                        	isCopy: true,
                            isMove: true,
                            prev: true,
                            inner: true,
                            next: true
                        }
                    },
                    data: {
                    	simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId",
                            rootPId: 0
                        },
                        key:{
                            name:"title",
                            title:""
                        }
                    },
                    callback: {
                        beforeDrag: beforeDrag,
                        beforeDrop: beforeDrop
                    }
                };
            	
            	var setting2 = {
                    edit: {
                        enable: true,
                        showRemoveBtn: true,
                        showRenameBtn: true,
                    },
                    data: {
                    	simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "menuParentId",
                            rootPId: 0
                        },
                        key:{
                            name:"title",
                            title:""
                        }
                    }
                };
                
            	var zNodes1 = ${postsArrList};
            	
                function beforeDrag(treeId, treeNodes) {
                    for (var i=0,l=treeNodes.length; i<l; i++) {
                        if (treeNodes[i].drag === false) {
                            return false;
                        }
                    }
                    return true;
                }
                
                function beforeDrop(treeId, treeNodes, targetNode, moveType) {
                    return targetNode ? targetNode.drop !== false : true;
                }
                
                $.fn.zTree.init($("#menuSrcTree"), setting1, zNodes1);
                $.fn.zTree.init($("#menuTree"), setting2);
                
                $('#createNewMenuBtn').click(function(){
                	var newMenuName = $.trim($('#newMenuName').val());
                	var newMenuSlugName = $.trim($('#newMenuSlugName').val());
                	var newMenuDesc = $.trim($('#newMenuDesc').val());
                	if(!newMenuName || newMenuName.length==0){
                		$('#alertMsg').text("菜单名称不能为空").show();
                		return;
                	}
                	if(!newMenuSlugName || newMenuSlugName.lenght==0){
                		$('#alertMsg').text("菜单别名不能为空").show();
                        return;
                	}

                	$.ajax({
                		type: 'POST',
                        url: '${ctx}/cms/outward/createNewMenu.html',
                        data: 'newMenuName=' + newMenuName 
                              + '&newMenuSlugName=' + newMenuSlugName 
                              + '&newMenuDesc=' + newMenuDesc,
                        dataType: 'json',
                        cache : false,
                        success: function(msg) {
                        	if(msg.tipMsg != null){
                        		$('#alertMsg').text(msg.tipMsg).show();
                        		return;
                        	}
                        	else{
                        		$('#editMenuName').val(newMenuName);
                        	}
                            $('#btnCancel').click();
                        },
                        error: function () {
                        	alert("error!!!");
                        } 
                	});
                });
                
                $('#createNewMenuModal').on('hidden', function() {
                    //window.location.reload();
                    var newMenuName = $.trim($('#newMenuName').val());
                	window.location.href = "${ctx}/cms/outward/menu.html?menu=menu_outward&newMenuName=" + newMenuName;
                });
                
                var selEditMenuEvent = $('#selEditMenu').change(function(){
                	var selEditMenuId = $("#selEditMenu").val();
                	var selEditMenuName =  $('#selEditMenu').find("option:selected").text();
                	$('#editMenuName').val(selEditMenuName);
                    $('#editMenuId').val(selEditMenuId);
                    //alert("gaowm");
                	
                    $.ajax({
                        type: 'POST',
                        url: '${ctx}/cms/outward/showEditMenuTree.html',
                        data: 'editMenuId=' + selEditMenuId,
                        dataType: 'json',
                        cache : false,
                        success: function(msg) {
                            $.fn.zTree.init($("#menuTree"), setting2, msg);
                        },
                        error: function () {
                            alert("error!!!");
                        } 
                    });
                });
                
                //初始化右边菜单树
                selEditMenuEvent.change();
                
                $('#saveMenuBtn').click(function(){
                    $('#saveMenuBtn').attr("disabled", "true");
                	var treeObj = $.fn.zTree.getZTreeObj("menuTree");
                	var nodes = treeObj.getNodes();
                	if(!nodes || nodes.length ==0){
                		$('#alertSelMsg').text("菜单不能为空，您可以选择删除重建菜单").show();
                		$('#saveMenuBtn').removeAttr("disabled");
                		return;
                	}
                	var nodesSimpleArr = treeObj.transformToArray(nodes);
                	var treeParams = '[';
                	for(var i=0; i<nodesSimpleArr.length; i++){
                		if(treeParams != '['){
                			treeParams = treeParams + ',';
                		}
                		
                		var slug = nodesSimpleArr[i].slug;
                		if (!slug && typeof(slug)!="undefined" && slug!=0)
                		{
                		    //alert("is null");
                		    slug = '';
                		}　
                		//组织发送后数据格式，json数组，只组织需要的字段，父子关系是伪post父子关系，保存在postmeta中
                		treeParams = treeParams + '{"id":' + nodesSimpleArr[i].id 
                			         + ',"menuParentId":' + nodesSimpleArr[i].menuParentId
                			         + ',"title":"' + nodesSimpleArr[i].title
                			         + '","slug":"' + slug
                			         + '","tarObjType":"' + nodesSimpleArr[i].tarObjType + '"}';
                	}
                	treeParams = treeParams + ']';
                	//alert(treeParams);
                	
                	$('#alertSelMsg').hide();
                	var editMenuId = $('#editMenuId').val();
                    $.ajax({
                    	type: 'POST',
                        url: '${ctx}/cms/outward/saveMenu.html',
                        data: 'editMenuId=' + editMenuId + '&nodesArr=' + treeParams,
                        dataType: 'json',
                        cache : false,
                        success: function(msg) {
                            if(msg.failMsg != null){
                            	$('#alertSelMsg').text(msg.failMsg).show();
                            }
                            else{
                            	$('#alertSuccMsg').text("菜单保存成功").show();   
                            	$('#selEditMenu').one("linkChange", function(event, a){
                            		//alert(a);
                            		selEditMenuEvent.change();
                            	});
                            	$("#selEditMenu").trigger("linkChange", ["Hello!!!"]);
                            	$('#saveMenuBtn').removeAttr("disabled");
                            }
                        },
                        error: function () {
                            alert("error!!!");
                        } 
                    });
                });
                
                $('#addLink').click(function(){
                	var linkName = $.trim($('#linkName').val());
                	var linkUrl = $.trim($('#linkUrl').val());
                	
                	var editMenuId = $('#editMenuId').val();
                	if(!editMenuId || editMenuId.length == 0){
                		$('#alertAddLinkMsg').text("请先选择编辑的菜单").show();
                        return;
                	}
                	
                	if(!linkName || linkName.length == 0){
                		$('#alertAddLinkMsg').text("链接名不能为空").show();
                		return;
                	}
                	
                	$('#alertAddLinkMsg').hide();
                	
                	if(!linkUrl || linkUrl.length == 0){
                        $('#alertAddLinkMsg').text("链接URL不能为空").show();
                        return;
                    }
                	$('#alertAddLinkMsg').hide();
                	
                	$.ajax({
                		type: 'POST',
                        url: '${ctx}/cms/outward/addLinkToMenu.html',
                        data: 'linkName=' + linkName + '&linkUrl=' + linkUrl + '&editMenuId=' + editMenuId,
                        dataType: 'json',
                        cache : false,
                        success: function(msg) {
                            if(msg.result == 'success'){
                            	$('#alertSuccMsg').text("添加链接成功").show();
                            	$('#selEditMenu').one("linkChange", function(event, a){
                                    //alert(a);
                                    selEditMenuEvent.change();
                                });
                                $("#selEditMenu").trigger("linkChange", ["Hello!!!"]);
                            }
                            else{
                            	$('#alertSelMsg').text("添加链接失败").show();                                                               
                            }
                        },
                        error: function () {
                            alert("error!!!");
                        } 
                	});
                });
                
                MenuModel.addTermOrTagToMenu = function(termOrTagId, termOrTagName, dataType, slug){
                	var editMenuId = $('#editMenuId').val();
                	//alert(termOrTagName);
                    if(!editMenuId || editMenuId.length == 0){
                        $('#alertAddTermOrTagMsg').text("请先选择编辑的菜单").show();
                        return;
                    }
                    $('#alertAddTermOrTagMsg').hide();
                    
                    $.ajax({
                        type: 'POST',
                        url: '${ctx}/cms/outward/addTermOrTagToMenu.html',
                        data: 'termOrTagId=' + termOrTagId + '&editMenuId=' + editMenuId + '&termOrTagName=' + termOrTagName + '&dataType=' + dataType + '&slug=' + slug,
                        dataType: 'json',
                        //async: false,
                        cache: false,
                        success: function(msg) {
                            if(msg.result == 'success'){
                                $('#alertSuccMsg').text("添加到菜单成功").show();
                                $('#selEditMenu').one("linkChange", function(event, a){
                                    //alert(a);
                                    selEditMenuEvent.change();
                                });
                                $("#selEditMenu").trigger("linkChange", ["Hello!!!"]);
                            }
                            else{
                                $('#alertSelMsg').text("添加到菜单失败").show();                                                               
                            }
                        },
                        error: function () {
                            alert("error!!!");
                        } 
                    });
                };
                
                $('#searchTermOrTag').click(function(){
                	var inputTermOrTagName = $('#inputTermOrTagName').val();
                	$('#alertAddTermOrTagMsg').hide();
                	
                	$.ajax({
                		type: 'POST',
                        url: '${ctx}/cms/outward/getTermOrTagByName.html',
                        data: 'inputTermOrTagName=' + inputTermOrTagName,
                        dataType: 'json',
                        cache : false,
                        success: function(msg) {
	                         if(msg != null){
	                        	 var str = '';
	                        	 for(var i=0; i<msg.length; i++){
	                        		 str += "<tr><td>" + msg[i].id + "</td><td>" + msg[i].name + "</td><td>" + msg[i].dataType + "</td><td><button type='button' class='btn btn-info' onclick=" + "MenuModel.addTermOrTagToMenu('" + msg[i].id + "','" + msg[i].name + "','" + msg[i].dataType + "','" + msg[i].slug + "');>添加到菜单</button></td></tr>";
	                        	 };
	                        	 //alert(str);
	                        	 $('#termOrTagTable>tbody').html(str);
	                        	 //"<c:forEach var='term' items='${msg }'><tr><td>${term.id }</td><td>${term.name }</td><td><button type='button' class='btn btn-info' onclick='MenuModel.addTermToMenu('${term.id }', '${term.name }');'>添加到菜单</button></td></tr></c:forEach>"
		                     }
		                     else{
		                    	 $('#alertAddTermOrTagMsg').text("没有找到数据").show();  
		                    	 $('#termOrTagTable>tbody').html('');
		                    	 //$('#termTable>tbody').html('<tr><td>1</td><td>2</td><td>3</td></tr>');
		                    	 //$("<tr><td>1</td><td>2</td><td>3</td></tr>").appendTo("#termTable>tbody");
		                     }
                        },
                        error: function () {
                            alert("error!!!");
                        } 

                    });
                });
                
                //删除菜单
                $('#delMenuBtn').click(function(){
                	var menuId = $("#selEditMenu").val();
                	var menuName = $('#editMenuName').val();
                	
                	$.ajax({
                        type: 'POST',
                        url: '${ctx}/cms/outward/delMenu.html',
                        data: 'menuId=' + menuId + '&menuName=' + menuName,
                        dataType: 'json',
                        cache : false,
                        success: function(msg) {
                            if(msg.result == 'success'){
                                window.location.href = "${ctx}/cms/outward/menu.html?menu=menu_outward&alertSuccMsg=" + "删除菜单成功";
                            }
                            else{
                                $('#alertSelMsg').text(msg.result).show();                                                               
                            }
                        },
                        error: function () {
                            alert("error!!!");
                        } 
                    });
                });
                
            });
            
        </script>
    </head>

    <body>
        <jsp:include page="/common/header.jsp"></jsp:include>
        
        <!-- section content -->
        <section class="section">
            <div class="row-fluid">
                <!-- span side-left -->
                <div class="span1">
                	<jsp:include page="/common/cms/sidebar.jsp"></jsp:include>
                </div><!-- span side-left -->
                
                <!-- span content -->
                <div class="span11">
                    <!-- content -->
                    <div class="content">
                        <!-- content-header -->
                        <div class="content-header">
                            <h2><i class="icofont-retweet"></i> 外观 <small>电视商务商户管理系统 &rsaquo; 外观管理模块&rsaquo; 菜单</small></h2>
                        </div><!-- /content-header -->
                        <div class="content-body">
                            
                            <!-- tab resume content -->
                            <div class="alert alert-error" <c:if test="${empty msg}">style="display: none;"</c:if> id="formMsg">
                            ${msg}
                            </div>
                            <div class="alert alert-success" <c:if test="${empty succMsg}">style="display: none;"</c:if> id="succMsg">
                            ${succMsg}
                            </div>
                            <div class="alert alert-success" style="display: none;" id="alertSuccMsg"></div>
                            <div class="alert alert-error" style="display: none;" id="alertSelMsg"></div>
                            <input type="hidden" id="editMenuId" />
                            <div class="row-fluid">
                                <div class="span12">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <form class="form-horizontal">
                                            <div class="control-group">
                                                <label class="control-label" for="">选择要编辑的菜单:</label>
                                                <div class="controls">
                                                    <select id="selEditMenu">
                                                        <c:forEach items="${menuList }" var="menu">
                                                            <c:if test="${menu.name == newMenuName}">
                                                                <option value="${menu.id }" selected="selected">${menu.name }</option>
                                                            </c:if>
                                                            <c:if test="${menu.name != newMenuName}">
                                                                <option value="${menu.id }">${menu.name }</option>
                                                            </c:if>            
                                                        </c:forEach>
                                                    </select>
                                                    <!-- <input type="button" value="选择" class="btn tbn-small btn-info" id="selMenuId">  -->
                                                    <span>或</span>
                                                    <a href="#createNewMenuModal" role="button" class="btn btn-small btn-info" data-toggle="modal" data-backdrop="static" id="addMediaBtn">创建新菜单</a>
                                                </div>
                                            </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                           </div>
                           <div class="row-fluid">
                                <div class="span4">
                                     <div class="row-fluid">
                                     
                                     <div class="span12">
                                    <div class="accordion" id="accordion">
                                        <div class="accordion-group">
                                            <div class="accordion-heading">
                                                <a class="accordion-toggle bg-teal color-white collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse1"><i class="icon-file"></i>页面</a>
                                            </div>
                                            <div id="collapse1" class="accordion-body in collapse" style="height: auto;">
                                                <div class="accordion-inner">
                                                    <form action="${ctx}/cms/outward/searchPages.html?menu=menu_outward" method="post" id="searchPages">
	                                                    <div class="dataTables_filter" id="datatables_filter" >
	                                                        <ul id="menuSrcTree" class="ztree" style="width:370px; overflow:auto;"></ul>
	                                                    </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-group">
                                            <div class="accordion-heading">
                                                <a class="accordion-toggle bg-blue color-white collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse2"><i class="icon-share"></i>链接</a>
                                            </div>
                                            <div id="collapse2" class="accordion-body collapse" style="height: 0px;">
                                                <div class="accordion-inner">
                                                                                                                                                        链接名称（必填）<input name="linkName" type="text" class="regular-text ltr" id="linkName" value=""  /> <br>
                                                                                                                                                        链接URL（必填） <input name="linkUrl" type="text" class="regular-text ltr" id="linkUrl" value=""  /> <br>
                                                         <div class="alert alert-error" style="display: none;" id="alertAddLinkMsg"></div>                                                                                               
                                                         <button class="btn btn-primary" id="addLink">确定</button> 
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-group">
                                            <div class="accordion-heading">
                                                <a class="accordion-toggle bg-green color-white" data-toggle="collapse" data-parent="#accordion" href="#collapse3"><i class="icon-folder-open"></i>分类目录/标签</a>
                                            </div>
                                            <div id="collapse3" class="accordion-body collapse">
                                                <div class="accordion-inner">
                                                    <input type="text" class="input-medium search-query" placeholder="分类名或标签名..." id="inputTermOrTagName">
                                                    <button class="btn btn-primary" id="searchTermOrTag">搜索</button><p>
													<div class="alert alert-error" style="display: none;" id="alertAddTermOrTagMsg"></div>   
													<table class="table table-hover table-striped table-bordered responsive" id="termOrTagTable">
		                                                <thead>
		                                                    <tr>
		                                                        <th>ID</th>
		                                                        <th>名称</th>
		                                                        <th>类型</th>
		                                                        <th>操作</th>
		                                                    </tr>
		                                                </thead>
		                                                <tbody>
		                                                    <c:forEach var="termOrTag" items="${termOrTagsList }">
		                                                        <tr>
	                                                                <td>${termOrTag.id }</td>
	                                                                <td>${termOrTag.name }</td>
	                                                                <td>${termOrTag.dataType }</td>
	                                                                <td><button type="button" class="btn btn-info" onclick="MenuModel.addTermOrTagToMenu('${termOrTag.id }', '${termOrTag.name }', '${termOrTag.dataType }', '${termOrTag.slug }');">添加到菜单</button></td>
                                                                </tr>
		                                                    </c:forEach>
		                                                </tbody>
	                                                </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                     
                                    </div>
                                </div>
                                
                                <div class="span8">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span>菜单名称</span>
                                            <input type="text" value="选择或新建的菜单名称" id="editMenuName" disabled>
                                            <!-- <a class="btn tbn-small btn-info pull-right" id="">保存菜单</a>  -->
                                            <a class="btn tbn-small btn-danger pull-right" id="delMenuBtn">删除菜单</a>
                                        </div>
                                        <div class="box-body">
                                              <div class="control-group">
                                                    <h6>菜单结构</h6>
                                                    <!-- <p>拖放各个项目到您喜欢的顺序，点击右侧的箭头可进行更详细的设置。</p>  -->
                                                    <p>拖放各个项目到您喜欢的顺序，点击右侧的编辑可进行重命名设置。</p>
                                                    <ul id="menuTree" class="ztree" style="width:400px; overflow:auto;"></ul>
                                              </div>
                                              <div class="control-group">
<!--                                                     <h6>菜单设置</h6> -->
<!--                                                     <form class="form-horizontal"> -->
<!--                                                         <div class="control-group"> -->
<!--                                                                 <label class="control-label" for="">自动添加页面:</label> -->
<!--                                                                 <div class="controls"> -->
<!--                                                                      <input type="checkbox"><span>自动将所有顶级页面添加到此菜单</span> -->
<!--                                                                 </div> -->
<!--                                                          </div> -->
<!--                                                          <div class="control-group"> -->
<!--                                                                 <label class="control-label" for="">主题位置:</label> -->
<!--                                                                 <div class="controls"> -->
<!--                                                                      <input type="checkbox"><span>Top Navigation</span></br> -->
<!--                                                                      <input type="checkbox"><span>Top Navigation</span></br> -->
<!--                                                                      <input type="checkbox" checked="checked"><span>Top Navigation（当前设置：bottom menu）</span></br> -->
<!--                                                                 </div> -->
<!--                                                          </div> -->
<!--                                                     </form> -->
		                                            <a class="btn tbn-small btn-info" id="saveMenuBtn">保存菜单</a>
		                                            <!-- <a class="btn tbn-small btn-danger pull-right" id="addLinkBtn">删除菜单</a> -->
                                              </div>
                                    </div>
                                </div>
                                
                              </div>
                              <!-- tab resume update -->
                                
                                
                            </div><!-- tab stat -->
                         
                        </div><!--/content-body -->
                	</div><!-- /span content -->
                </div><!-- /span side-right -->
            </div>
        </section>

        <jsp:include page="/common/footer.jsp"></jsp:include>
        <div id="createNewMenuModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="createMenuModalLabel" aria-hidden="true">
           <div class="modal-header">
             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
             <h3 id="createMenuModalLabel">创建新菜单</h3>
           </div>
           <div class="modal-body">
             <table class="form-table">
                 <tr valign="top">
                 <th scope="row"><label for="newMenuName">菜单名称（必填）</label></th>
                 <td>
                    <input name="newMenuName" type="text" class="regular-text ltr" id="newMenuName" value=""  />
                 </td>
                 </tr>
                 <tr valign="top">
                 <th scope="row"><label for="newMenuSlugName">别名（必填）</label></th>
                 <td>
                    <input name="newMenuSlugName" type="text" class="regular-text ltr" id="newMenuSlugName" value=""  />
                 </td>
                 </tr>
                 <tr valign="top">
                 <th scope="row"><label for="newMenuDesc">菜单描述</label></th>
                 <td>
                    <input name="newMenuDesc" type="text" class="regular-text ltr" id="newMenuDesc" value=""  />
                 </td>
                 </tr>
             </table>
             <div class="alert alert-error" style="display: none;" id="alertMsg"></div>
             <button class="btn btn-primary" id="createNewMenuBtn">确定</button>
           </div>
           <div class="modal-footer">
             <button class="btn" data-dismiss="modal" aria-hidden="true" id="btnCancel">关闭</button>
           </div>
        </div> 
    </body>
</html>

