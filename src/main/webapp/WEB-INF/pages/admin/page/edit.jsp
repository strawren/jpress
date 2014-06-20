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
            $(document).ready(function() {
            	//页面状态编辑切换功能
                $("#pageStatusEditBtn").on("click",function(){
                	$(this).hide();
                	$(this).parent().next().show();
                	
                });
                $("#pageStatusEditConfirmBtn").on("click",function(){
                	$(this).parent().hide();
                	$("#pageStatusEditBtn").show();
                	
                	var pageStatusSelect = $("#pageStatusSelect option:selected");
                	$("#postStatus").val(pageStatusSelect.val());
                	$("#pageStatusSpan").text(pageStatusSelect.text());
                	
                });
                
                $("#pageStatusEditCancelBtn").on("click",function(){
                    $(this).parent().hide();
                    $("#pageStatusEditBtn").show();
                });
                
              //页面公开度编辑切换功能
                $("#pageOpenStatusEditBtn").on("click",function(){
                    $(this).hide();
                    $(this).parent().next().show();
                    
                });
                
                $("#pageOpenStatusEditConfirmBtn").on("click",function(){
                    $(this).parent().hide();
                    $("#pageOpenStatusEditBtn").show();
                    
                    var pageOpenStatusChecked = $(":radio[name='openStatus']:checked");
                    $("#pingStatus").val(pageOpenStatusChecked.val());
                    $("#pageOpenStatusSpan").text(pageOpenStatusChecked.next().text());
                });
                
                $("#pageOpenStatusEditCancelBtn").on("click",function(){
                    $(this).parent().hide();
                    $("#pageOpenStatusEditBtn").show();
                });
                
                
                
              //发布页面功能
                $("#publishPageBtn").on("click",function(){
                	setMenuOrder();
                	$("#postStatus").val("pub");

                	if (checkForm()) {
                        $("#publishPageForm").submit();
                    }
                });
              
                //设置页面Order
                function setMenuOrder(){
                    var order = $("#pageOrder").val();
                    var pageOrder = 0;
                    order = parseInt(order);
                    if (order&&order>0) {
                    	pageOrder = order;
					}
                    $("#menuOrder").val(pageOrder);
                }
                //保存草稿功能
                $("#draftPageBtn").on("click",function(){
                	setMenuOrder();
                    $("#postStatus").val("drt");
                    if (checkForm()) {
                    	$("#publishPageForm").submit();
					}
                });
                
                //表单验证
                function checkForm(){
                	var title = $.trim($("#title").val());
                	var content = $.trim($("#content").val());
                	if (!title||title.length==0) {
                		$("#formMsg").text("页面标题不能为空").show();
						return false;
					}

                	if (!content||content.length==0) {
                		$("#formMsg").text("页面内容不能为空").show();
                        return false;
                    }
                	
                	$("#formMsg").hide();
                	return true;
                }
                
                //设置父级页面名称
                var parentId = ${page.parentId};
                for (var i = 0; i < parentPageNodes.length; i++) {
					var parentPageNode = parentPageNodes[i];
					if (parentPageNode.id==parentId) {
						parentPageName = parentPageNode.title;
						$("#parentPageInput").val(parentPageName);
						
						break;
					}
				}
            });
      
            //选择父级页面相关功能
            var setting = {
                    view: {
                        dblClickExpand: false
                    },
                    data: {
                    	simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId",
                            rootPId: 0,
                        },
                        key:{
                        	name:"title",
                        	title:""
                        }
                    },
                    callback: {
                        onClick: onClick
                    }
                };

                var parentPageNodes = ${parentPages};
                //移除当前页面
                var pageId = ${page.id};
                for (var i = 0; i < parentPageNodes.length; i++) {
                    var parentPageNode = parentPageNodes[i];
                    if (parentPageNode.id==pageId) {
                    	parentPageNodes.splice(i,1);                        
                        break;
                    }
                }
                

                function onClick(e, treeId, treeNode) {
                    var zTree = $.fn.zTree.getZTreeObj("parentPageTree"),
                    node = zTree.getSelectedNodes()[0];
                    $("#parentPageInput").val(node.title);
                    $("#parentId").val(node.id);
                    $("#menuContent").fadeOut("fast");
                }

                function showMenu() {
                    var cityObj = $("#parentPageInput");
                    var cityOffset = $("#parentPageInput").offset();
                    var menuContentDivOffset = $("#menuContentDiv").parent().offset();
                    $("#menuContent").css({left:cityOffset.left-menuContentDivOffset.left + "px", top:cityOffset.top + cityObj.outerHeight()-menuContentDivOffset.top + "px"}).slideDown("fast");
                    $("body").bind("mousedown", onBodyDown);
                }
                function hideMenu() {
                    $("#menuContent").fadeOut("fast");
                    $("body").unbind("mousedown", onBodyDown);
                }
                function onBodyDown(event) {
                    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                        hideMenu();
                    }
                }
                
                $(function(){
                        $.fn.zTree.init($("#parentPageTree"), setting, parentPageNodes).expandAll(true);;
                });
                //end
                
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
                            <h2><i class="icofont-retweet"></i> 页面 <small>电视商务商户管理系统 &rsaquo; 页面管理模块&rsaquo; 新建页面</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
               <div class="content-body">
                            
                            <!-- tab resume content -->
                            <div class="alert alert-error" style="display: none;" id="formMsg">
                            </div>
                            <div class="row-fluid">
                            
                                <div class="span9">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icofont-plus-sign"></i>&nbsp;编辑页面</span>
                                        </div>
                                        <div class="box-body">
                                            <form method="post" action="${ctx}/cms/page/update.html" id="publishPageForm">
                                            <div class="control-group">
                                                <div class="controls">
                                                   <input type="hidden" name="id" value="${page.id}">
                                                   <input type="hidden" name="parentId" id="parentId" value="${page.parentId}">
                                                   <input type="hidden" name="postStatus" id="postStatus" value="${page.postStatus}">
                                                   <input type="hidden" name="pingStatus" id="pingStatus" value="${page.pingStatus}">
                                                   <input type="hidden" name="menuOrder" id="menuOrder" value="${page.menuOrder}">
                                                   <input type="hidden" name="lastUpdTime" value="<fmt:formatDate value="${page.lastUpdTime}" type="both"/>">
                                                   <input type="text" class="span8" placeholder="在此输入标题" name="title" id="title" value="${page.title}">
                                                </div>
                                            </div>
                                            <a class="btn tbn-small btn-info"><i class="icon-music"></i>添加媒体</a>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <textarea class="span12 xheditor" rows="20" data-form="wysihtml5" placeholder="" name="content" id="content">${page.content}</textarea>
                                                </div>
                                            </div>
                                        </form>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="span3">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icon-share"></i>发布</span>
                                        </div>
                                        <div class="box-body">
                                            <div class="control-group">
                                             <c:if test="${page.postStatus!='pub'}">
                                             <a class="btn tbn-small btn-info" id="draftPageBtn">保存草稿</a>
                                             </c:if>
                                             
                                             <!-- <a class="btn tbn-small pull-right">预览</a> -->
                                             <c:if test="${page.postStatus=='pub'}">
                                             <a class="btn tbn-small pull-right btn-info" id="publishPageBtn">更新</a>
                                             </c:if>
                                             <c:if test="${page.postStatus!='pub'}">
                                             <a class="btn tbn-small pull-right btn-info" id="publishPageBtn">发布</a>
                                             </c:if>
                                            </div>
                                            <div class="control-group">
                                                <span>&nbsp;<i class="icon-map-marker"></i>&nbsp;状态：<span id="pageStatusSpan"><c:if test="${page.postStatus=='drt'}">草稿</c:if><c:if test="${page.postStatus=='wai'}">等待复审</c:if><c:if test="${page.postStatus=='pub'}">发布</c:if></span></span>&nbsp;&nbsp;&nbsp;<a href="#" id="pageStatusEditBtn">编辑</a>
                                            </div>
                                            <div class="control-group" style="display: none;">
                                                <select id="pageStatusSelect">
                                                    <option value="drt" <c:if test="${page.postStatus=='drt'}">selected="selected"</c:if> >草稿</option>
                                                    <option value="wai" <c:if test="${page.postStatus=='wai'}">selected="selected"</c:if> >等待复审</option>
                                                    <option value="pub" <c:if test="${page.postStatus=='pub'}">selected="selected"</c:if> >发布</option>
                                                </select>
                                                <a href="#" id="pageStatusEditConfirmBtn" class="btn btn-small" >确定</a>
                                                <a href="#" id="pageStatusEditCancelBtn">取消</a>
                                            </div>
                                            <div class="control-group">
                                                <span>&nbsp;<i class="icon-eye-open"></i>&nbsp;公开度：<span id="pageOpenStatusSpan"><c:if test="${page.pingStatus=='open'}">开放</c:if><c:if test="${page.pingStatus=='clse'}">关闭</c:if></span></span>&nbsp;&nbsp;&nbsp;<a href="#" id="pageOpenStatusEditBtn">编辑</a>
                                            </div>
                                            <div class="control-group" style="display: none;">
                                                <input type="radio" name="openStatus"  value="open" <c:if test="${page.pingStatus=='open'}">checked="checked"</c:if> ><span>开放</span><br>
                                                <input type="radio" name="openStatus" value="clse" <c:if test="${page.pingStatus=='clse'}">checked="checked"</c:if> ><span>关闭</span><br>
                                                 <!-- <input type="radio" name="openStatus" value="3"><span>密码保护</span><br> -->
                                                <a href="#" id="pageOpenStatusEditConfirmBtn" class="btn btn-small" >确定</a>
                                                <a href="#" id="pageOpenStatusEditCancelBtn">取消</a>
                                            </div>
                                            <!-- <div class="control-group">
                                                <span>立即发布</span>&nbsp;&nbsp;&nbsp;<a href="#">编辑</a>
                                            </div> -->
                                            <!-- <div class="control-group">
                                                <a class="btn tbn-small">移至回收站</a>
                                                <a class="btn tbn-small pull-right" id="publishPageBtn">发布</a>
                                            </div> -->
                                        </div>
                                    </div>
                                    
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icon-list"></i>页面属性</span>
                                        </div>
                                        
                                        <div class="box-body">
                                             <div class="control-group" id="menuContentDiv">                               
                                                    <div class="controls">
                                                        &nbsp;&nbsp;父级：<input id="parentPageInput" type="text" readonly value="" style="width:120px;" id="menuBtn" href="#" onclick="showMenu(); return false;"/>
                                                        <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
                                                            <ul id="parentPageTree" class="ztree" style="margin-top:0; width:120px;"></ul>
                                                        </div>
                                                    </div>
                                                    
                                              </div>
          
                                             <div class="control-group">
                                                    <label class="control-label">排序</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" name="pageOrder"  id="pageOrder" value="${page.menuOrder}" /><br/>
                                                    </div>
                                              </div>
                                        </div>
                                    </div>
                                </div>
                                
                              </div>
                              <!-- tab resume update -->
                                
                                
                            </div><!-- tab stat -->
                            
                            <!--/dashboar-->
                        </div>
                        <!--/content-body -->
                	</div><!-- /span content -->
                </div><!-- /span side-right -->
            </div>
        </section>

        <jsp:include page="/common/footer.jsp"></jsp:include>
    </body>
</html>

