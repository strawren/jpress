<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/variable.jsp"%>

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
                
                $("#checkAllLinkBtn").on("click",function(){
                	var self = $(this);
                	var checked = self.attr("checked");
                	var linkTable = $("#linkTable");
                	if (checked) {
                		linkTable.find("input:checkbox").attr("checked",true);
					}else{
						linkTable.find("input:checkbox").attr("checked",false);
					}
                });
                
                $("#deleteAllLinkBtn").on("click",function(){
                	var linkTable = $("#linkTable");
                    var checkedLinks = linkTable.find("input:checked");
                    
                    if (checkedLinks.length==0) {
                    	alert("请至少选择一项");
						return;
					}
                    
                    var links = [];
                    for (var i = 0; i < checkedLinks.length; i++) {
                    	var cLink = checkedLinks[i];
                    	links.push($(cLink).val());
					}
                    var links = links.join(",");

                    removeLink(links);
                });
                
                $(document).on("page.change",function(event,pagenum){
                    $("#pageNumInput").val(pagenum);
                    $("#linkSearchForm").submit();
               });
               
               $("#searchBtn").on('click',function(){
                   $("#pageNumInput").val(1);
                   $("#linkSearchForm").submit();
               });
            });
      
            function removeLink(ids){
            	if(!confirm("确定将这些链接删除?")){
                    return 
                }
            	
            	window.location.href="${ctxAdmin}/link/remove.html?ids="+ids;
            }
        </script>
    </head>

    <body>
        <jsp:include page="/common/header.jsp"></jsp:include>
        
        <!-- section content -->
        <section class="section">
            <div class="row-fluid">
                <!-- span side-left -->
                <div class="span1">
                	<jsp:include page="/common/sidebar.jsp"></jsp:include>
                </div><!-- span side-left -->
                
                <!-- span content -->
                <div class="span11">
                    <!-- content -->
                    <div class="content">
                        <!-- content-header -->
                        <div class="content-header">
                            <h2><i class="icofont-retweet"></i> 链接 <small>电视商务商户管理系统 &rsaquo; 链接管理模块&rsaquo; 所有链接</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            
                            <div class="row-fluid">
                                <div class="span12">
                                    <div class="box corner-all">
                                        <div class="box-header grd-white corner-top">
                                           <!--  <div class="header-control">
                                                <a data-box="collapse"><i class="icofont-caret-up"></i></a>
                                                <a data-box="close" data-hide="bounceOutRight">×</a>
                                            </div> -->
                                            <span><i class="icon-list"></i>&nbsp;所有链接</span>
                                        </div>
                                        <div class="box-body">
                                            <div id="datatables_wrapper" class="dataTables_wrapper form-inline" role="grid">
                                            <div class="row-fluid">
                                                <div class="span6">
                                                    <div id="datatables_length" >
                                                        <label>
                                                            <!-- <select size="1" name="datatables_length" aria-controls="datatables" >
                                                            <option value="1" selected="selected" >批量操作</option>
                                                            <option value="2" selected="selected" >编辑</option>
                                                            <option value="3" selected="selected" >移至回收站</option>
                                                            </select> 
                                                            <a class="btn btn-small" >应用</a> -->
                                                            <!-- <a class="btn btn-small" id="editAllPageBtn">编辑</a> -->
                                                            <a class="btn btn-small btn-danger" id="deleteAllLinkBtn">批量删除</a>
                                                            <a class="btn tbn-small btn-info" href="${ctxAdmin}/link/new.action?menu=menu_links">新增</a>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="span6">
                                                <form action="${ctxAdmin}/link/all.action" method="get" id="linkSearchForm" >
                                                    <div class="dataTables_filter" id="datatables_filter" >
                                                        <label>
                                                        <input type="hidden" name="pageNum"  id="pageNumInput" value="${page.pageNum}" />
                                                        <input type="hidden" name="menu" value="menu_page">
                                                        <input type="hidden" name="type" value="${type}">
                                                        <input type="text" aria-controls="datatables" name="search" value="${search}">
                                                        <input type="submit" class="btn btn-small btn-success" id="searchBtn" value="搜索">
                                                        </label>
                                                    </div>
                                                </form>
                                                </div>
                                            </div>
                                             
                                             <table class="table table-hover table-striped responsive">
                                                <thead>
                                                    <tr>
                                                        <th><input type="checkbox" id="checkAllLinkBtn"></th>
                                                        <th>名称</th>
                                                        <th>URL</th>
                                                        <th>分类目录</th>
                                                        <th>可见性</th>
                                                        <th>操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="linkTable">
                                                <c:forEach items="${page.result}" var="link">
                                                    <tr>
                                                        <td><input type="checkbox" value="${link.id}"></td>
                                                        <td>
                                                        <div>${link.name}
                                                        </td>
                                                        <td>${link.url}</td>
                                                        <td>${link.linkCategoryName}</td>
                                                        <td>
                                                        <c:if test="${link.visible=='open'}">可见</c:if>
                                                        <c:if test="${link.visible=='clse'}">私密</c:if>
                                                        </td>
                                                        <td>
                                                                <div class="btn-group">
                                                                <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">操作<span class="caret"></span></button>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="${ctxAdmin}/link/edit.action?menu=menu_links&id=${link.id}">编辑</a></li>
                                                                    <li><a href="#" onclick="removeLink(${link.id})">删除</a></li>
                                                                </ul>
                                                                </div><!-- /btn-group -->
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            <jsp:include page="/common/pagination.jsp"></jsp:include>
                                             
                                        </div><!-- /box-body -->
                                    </div><!-- /box -->
                                </div><!-- /span -->
                            </div>
                            <!--/dashboar-->
                        </div><!--/content-body -->
                	</div><!-- /span content -->
                </div><!-- /span side-right -->
            </div>
        </section>

        <jsp:include page="/common/footer.jsp"></jsp:include>
        
    </body>
</html>

