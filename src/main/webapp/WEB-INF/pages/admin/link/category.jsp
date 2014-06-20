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
                
            	
            	$("#batchRemoveLinkCategoryBtn").on("click",function(){
                    var linkTable = $("#linkTable");
                    var checkedLinkCategories = linkTable.find("input:checked");
                    
                    if (checkedLinkCategories.length==0) {
                        alert("请至少选择一项");
                        return;
                    }
                    
                    var linkCategories = [];
                    for (var i = 0; i < checkedLinkCategories.length; i++) {
                        var cLinkCategory = checkedLinkCategories[i];
                        linkCategories.push($(cLinkCategory).val());
                    }
                    var linkCategories = linkCategories.join(",");

                    removeLinkCategory(linkCategories);
                });
            	
            	//表单验证
            	$("#saveLinkCategoryBtn").on("click",function(){
            		var linkCategoryName = $.trim($("#linkCategoryName").val());
            		if (!linkCategoryName) {
            			$("#formMsg").text("页面内容不能为空").show();
                        return false;
					}
            		
            		$("#saveLinkCategoryForm").submit();
            	});
            	
            	$(document).on("page.change",function(event,pagenum){
                    $("#pageNumInput").val(pagenum);
                    $("#linkCategorySearchForm").submit();
               });
               
               $("#searchBtn").on('click',function(){
                   $("#pageNumInput").val(1);
                   $("#linkCategorySearchForm").submit();
               });
            });
      
            //批量删除链接分类目录
            function removeLinkCategory(ids){
                if(!confirm("确定将这些链接分类目录删除?")){
                    return 
                }
                
                window.location.href="${ctx}/cms/link/removeCategory.html?ids="+ids;
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
                            <h2><i class="icofont-retweet"></i> 链接 <small>电视商务商户管理系统 &rsaquo; 链接管理模块&rsaquo; 链接分类</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            
                            <div class="alert alert-error" <c:if test="${empty msg}">style="display: none;"</c:if> id="formMsg">
                            ${msg}
                            </div>
                            <!-- tab resume content -->
                            <div class="row-fluid">
                            
                                <div class="span4">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <!-- <div class="header-control">
                                                <a data-box="collapse"><i class="icofont-caret-up"></i></a>
                                            </div> -->
                                            <span><i class="icofont-plus-sign"></i>&nbsp;添加链接分类目录</span>
                                        </div>
                                        <div class="box-body ">
                                            <form action="${ctx}/cms/link/addCategory.html" method="post" id="saveLinkCategoryForm">
                                                <div class="control-group">
                                                    <label class="control-label">名称</label>
                                                    <div class="controls">
                                                        <input type="hidden" name="id" value="${linkCategory.id}"/>
                                                        <input type="hidden" name="lastUpdTime" value="<fmt:formatDate value="${linkCategory.lastUpdTime}" type="both"/>">
                                                        <input type="text" class="input-block-level grd-white" name="name" value="${linkCategory.name}" id="linkCategoryName"/><br/>
                                                    </div>
                                                </div>
                                                <p>
                                                <div class="control-group">
                                                    <label class="control-label">别名</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" name="slug"  value="${linkCategory.slug}" id="linkCategorySlug"/><br/>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label">描述</label>
                                                    <div class="controls">
                                                        <textarea name="miscDesc" class="span12" rows="6" data-form="wysihtml5"  id="linkCategoryMiscDesc">${linkCategory.miscDesc}</textarea><br/>
                                                    </div>
                                                </div>
                                                <p>
                                                <div class="form-actions">
                                                    <input type="button" class="btn btn-info" value="保存" id="saveLinkCategoryBtn"/>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="span8">
                                    <div class="span12">
                                    <div class="box corner-all">
                                        <div class="box-header grd-white corner-top">
                                            <!-- <div class="header-control">
                                                <a data-box="collapse"><i class="icofont-caret-up"></i></a>
                                                <a data-box="close" data-hide="bounceOutRight">&times;</a>
                                            </div> -->
                                            <span><i class="icon-list"></i>&nbsp;链接分类目录</span>
                                        </div>
                                        <div class="box-body">
                                            <div id="datatables_wrapper" class="dataTables_wrapper form-inline" role="grid">
                                            <div class="row-fluid">
                                                <div class="span6">
                                                    <div id="datatables_length" >
                                                        <label>
                                                            <a class="btn btn-small btn-danger" id="batchRemoveLinkCategoryBtn">批量删除</a>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="span6">
                                                <form action="${ctx}/cms/link/category.html?menu=menu_links" method="get" id="linkCategorySearchForm" >
                                                    <div class="dataTables_filter" id="datatables_filter" >
                                                        <label>
                                                        <input type="hidden" name="pageNum"  id="pageNumInput" value="${page.pageNum}" />
                                                        <input type="text" aria-controls="datatables" name="search" value="${search}">
                                                        <input type="button" class="btn btn-small btn-success" id="searchBtn" value="搜索">
                                                        </label>
                                                    </div>
                                                </form>
                                                </div>
                                            </div>
                                             
                                             <table id="datatables" class="table table-bordered table-striped responsive">
                                                <thead>
                                                    <tr>
                                                        <th><input type="checkbox" id="checkAllLinkBtn"></th>
                                                        <th>名称</th>
                                                        <th>描述</th>
                                                        <th>别名</th>
                                                        <th>链接数</th>
                                                        <th>操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="linkTable">
                                                     <c:forEach items="${page.result}" var="term">
                                                        <tr class="odd gradeX">
                                                            <td><input type="checkbox" value="${term.id}"></td>
                                                            <td>${term.name }</td>
                                                            <td>${term.slug }</td>
                                                            <td>${term.miscDesc }</td>
                                                            <td class="center">${term.linkCount }</td>
                                                            <td>
                                                                <div class="btn-group">
                                                                <button class="btn dropdown-toggle btn-info" data-toggle="dropdown">操作<span class="caret"></span></button>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="${ctx }/cms/link/editCategory.html?id=${term.id }">编辑</a></li>
                                                                    <li><a href="#" onclick="removeLinkCategory(${term.id})">删除</a></li>
                                                                </ul>
                                                                </div><!-- /btn-group -->
                                                            </td>
                                                           
                                                        </tr>
                                                    </c:forEach> 
                                                </tbody>
                                            </table>
                                            <jsp:include page="/common/pagination.jsp"></jsp:include>
                                             
                                        </div>
                                    </div><!-- /box-body -->
                                    </div><!-- /box -->
                                </div><!-- /span -->
                            </div><!--/datatables-->
                                </div><!-- tab resume update -->
                                
                                
                            </div><!--/content-body -->
                	</div><!-- /span content -->
                </div><!-- /span side-right -->
            </div>
        </section>

        <jsp:include page="/common/footer.jsp"></jsp:include>
        
    </body>
</html>

