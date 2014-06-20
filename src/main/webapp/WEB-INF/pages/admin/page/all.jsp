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
                
                $("#checkAllPageBtn").on("click",function(){
                	var self = $(this);
                	var checked = self.attr("checked");
                	var pageTable = $("#pageTable");
                	if (checked) {
                		pageTable.find("input:checkbox").attr("checked",true);
					}else{
						pageTable.find("input:checkbox").attr("checked",false);
					}
                });
                
                $("#editAllPageBtn").on("click",function(){
                    
                });
                
                $("#recycleAllPageBtn").on("click",function(){
                	var pageTable = $("#pageTable");
                    var checkedPages = pageTable.find("input:checked");
                    
                    if (checkedPages.length==0) {
                    	alert("请至少选择一项");
						return;
					}
                    
                    var pages = [];
                    for (var i = 0; i < checkedPages.length; i++) {
                    	var cPage = checkedPages[i];
                    	pages.push($(cPage).val());
					}
                    var pages = pages.join(",");

                    removePage(pages);
                });
                
                $(document).on("page.change",function(event,pagenum){
                	 $("#pageNumInput").val(pagenum);
                	 $("#pageSearchForm").submit();
                });
                
                $("#searchBtn").on('click',function(){
                	$("#pageNumInput").val(1);
                	$("#pageSearchForm").submit();
                });
            });
      
            function removePage(ids){
            	if(!confirm("确定将这些页面删除?")){
                    return 
                }
            	
            	window.location.href="${ctx}/cms/page/remove.html?ids="+ids;
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
                	<jsp:include page="/common/cms/sidebar.jsp"></jsp:include>
                </div><!-- span side-left -->
                
                <!-- span content -->
                <div class="span11">
                    <!-- content -->
                    <div class="content">
                        <!-- content-header -->
                        <div class="content-header">
                            <h2><i class="icofont-retweet"></i> 页面 <small>电视商务商户管理系统 &rsaquo; 页面管理模块&rsaquo; 所有页面</small></h2>
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
                                            <span><a href="${ctx}/cms/page/all.html?menu=menu_page&type=0">全部(${allPageCount})</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a href="${ctx}/cms/page/all.html?menu=menu_page&type=1">已发布(${allPublishPageCount})</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a href="${ctx}/cms/page/all.html?menu=menu_page&type=2">待发布(${allDraftPageCount})</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a href="${ctx}/cms/page/all.html?menu=menu_page&type=3">待删除(${allDeletePageCount})</a></span>
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
                                                            <a class="btn btn-small btn-danger" id="recycleAllPageBtn">批量删除</a>
                                                            <a class="btn tbn-small btn-info" href="${ctx}/cms/page/new.html?menu=menu_page">新增</a>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="span6">
                                                <form action="${ctx}/cms/page/all.html" method="get" id="pageSearchForm">
                                                    <div class="dataTables_filter" id="datatables_filter" >
                                                        <label>
                                                        <input type="hidden" name="pageNum"  id="pageNumInput" value="${page.pageNum}" />
                                                        
                                                        <input type="hidden" name="menu" value="menu_page">
                                                        <input type="hidden" name="type" value="${type}">
                                                        <input type="text" aria-controls="datatables" name="search" value="${search}">
                                                        <input type="button" class="btn btn-small btn-success" id="searchBtn" value="搜索">
                                                        </label>
                                                    </div>
                                                </form>
                                                </div>
                                            </div>
                                             
                                             <table class="table table-hover table-striped responsive">
                                                <thead>
                                                    <tr>
                                                        <th><input type="checkbox" id="checkAllPageBtn"></th>
                                                        <th>标题</th>
                                                        <th>作者</th>
                                                        <th>日期</th>
                                                        <th>状态</th>
                                                        <th>操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="pageTable">
                                                <c:forEach items="${page.result}" var="p">
                                                    <tr>
                                                        <td><input type="checkbox" value="${p.id}"></td>
                                                        <td>
                                                        <div>${p.title}<c:if test="${p.status=='I'}"><span style="color: red;">(删除)</span></c:if></div>
                                                        <%-- <div>${p.content}</div> --%>
                                                        </td>
                                                        <td>${p.showOwner}</td>
                                                        <td><fmt:formatDate value="${p.createTime}" pattern="yyyy年MM月dd日 "/></td>
                                                        <td>
                                                        <c:if test="${p.postStatus=='pub'}">已发布</c:if>
                                                        <c:if test="${p.postStatus=='drt'}">待审核</c:if>
                                                        </td>
                                                        <td>
                                                                <div class="btn-group">
                                                                <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">操作<span class="caret"></span></button>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="${ctx}/cms/page/edit.html?menu=menu_page&id=${p.id}">编辑</a></li>
                                                                    <li><a href="#" onclick="removePage(${p.id})">删除</a></li>
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

