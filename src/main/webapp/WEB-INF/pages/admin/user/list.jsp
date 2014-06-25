<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>${appName }--用户--所有用户</title>
        <jsp:include page="/common/meta.jsp"></jsp:include>
        <jsp:include page="/common/style.jsp"></jsp:include>
        <!--[if lt IE 9]>
        <script src="${ctx }/scripts/ghtml5/ghtml5.js"></script>
        <![endif]-->
        <jsp:include page="/common/scripts.jsp"></jsp:include>
        
        <script type="text/javascript">
	        $(function(){
	            $("#selectAllChkBox").click(function() {      
	                var flag = $("#selectAllChkBox").is(':checked');
	                $("input[id^='list_item_']").each(function () {
	                	$(this).attr("checked", flag);
	                });
	            });
	        	
	            $("input[id^='list_item_']").each(function() {
	                $(this).click(function () {
	                    if ($("input[id^='list_item_']:checked").length == $("input[id^='list_item_']").length) {
	                        $("#selectAllChkBox").attr("checked", "checked");
	                    }
	                    else {
	                    	$("#selectAllChkBox").removeAttr("checked");
	                    }
	                });
	            });
	            
	          	//删除确认
	            $(".batchDeleteBtn").confirm({
    				'title' : '删除确认',
    				'message' : '删除后，数据无法再恢复，您确认删除吗？',
    				'action' : function(){$("#listForm4Del").submit();},
    				'preAction' : function() {
    					var flag = true;
    					$("input[id^='list_item_']").each(function () {
                            if ($("input[id^='list_item_']:checked").length > 0) {
                            	flag = true;
                            }
                            else {
                                flag = false;
                            }
    	                });
    					if(!flag) {
    						$.alert("", "您还没有选中要删除的信息！");
    					}
    					return flag;
    				}
	            });
	            
	            //删除确认
	            $(".deleteOneItem").confirm({
    				'title' : '删除确认',
    				'message' : '删除后，数据无法再恢复，您确认删除吗？',
    				'action' : ''
	            });
				
	        });
			$(document).on("page.change",function(event,pagenum){
			     $("#pageNum").val(pagenum);
			     $("#searchForm").submit();
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
                	<jsp:include page="/common/sidebar.jsp"></jsp:include>
                </div><!-- span side-left -->
                
                <!-- span content -->
                <div class="span11">
                    <!-- content -->
                    <div class="content">
                        <!-- content-header -->
                        <div class="content-header">
                            <h2><i class="icofont-retweet"></i> 用户 <small>${appName } &rsaquo; 用户&rsaquo; 所有用户</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            
                            <div class="row-fluid">
                                <div class="span12">
                                    <div class="box corner-all">
                                        <div class="box-header grd-white corner-top">
                                            <span>
                                                <i class="icon-list"></i>&nbsp;
                                                <a href="${ctxAdmin}/user/list.action">所有用户</a>
                                            </span>
                                        </div>
                                        <div class="box-body">
                                            <div id="datatables_wrapper" class="dataTables_wrapper form-inline" role="grid">
                                            <div class="row-fluid">
                                                <div class="span6">
                                                    <div id="datatables_length" >
                                                        <label>
                                                            <a class="btn btn-small btn-danger batchDeleteBtn" id="batchDeleteBtn">批量删除</a>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="span6">
	                                                <form action="${ctxAdmin}/user/list.action" method="post" id="searchForm">
	                                                    <div class="dataTables_filter" id="datatables_filter" >
	                                                        <label>
	                                                        	<input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}" />
	                                                        	<input type="text" aria-controls="datatables" name="filter_LIKES_LOGIN_NAME" id="search" value="${param.filter_LIKES_LOGIN_NAME}">
	                                                        	<input type="submit" class="btn btn-info btn-small" value="搜索">
	                                                        </label>
	                                                    </div>
	                                                </form>
                                                </div>
                                            </div>
                                             
                                             <form action="${ctxAdmin}/user/del.action" method="post" id="listForm4Del">
	                                             <table class="table table-hover table-striped responsive">
	                                                <thead>
	                                                    <tr>
	                                                        <th><input type="checkbox" id="selectAllChkBox"></th>
	                                                        <th>ID</th>
	                                                        <th>用户名</th>
	                                                        <th>昵称</th>
	                                                        <th>注册时间</th>
	                                                        <th>最后修改日期</th>
	                                                        <th>操作</th>
	                                                    </tr>
	                                                </thead>
	                                                <tbody id="pageTable">
	                                                <c:forEach items="${page.result }" var="one">
	                                                    <tr>
		                                                    <td><input type="checkbox" name="ids" value="${one.id }" id="list_item_${one.id }"></td>
		                                                    <td>${one.id }</td>
		                                                    <td>${one.loginName }</td>
		                                                    <td>${one.nickname }</td>
		                                                    <td><fmt:formatDate value="${one.registerDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                                                    <td><fmt:formatDate value="${one.lastUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                                                    <td>
		                                                        <div class="btn-group">
		                                                        	<button class="btn btn-info dropdown-toggle" data-toggle="dropdown">操作<span class="caret"></span></button>
			                                                        <ul class="dropdown-menu">
			                                                            <li><a href="${ctxAdmin}/user/edit.action?id=${one.id }">编辑</a></li>
			                                                            <li><a href="${ctxAdmin}/user/del.action?id=${one.id }" class="deleteOneItem">删除</a></li>
			                                                        </ul>
		                                                        </div>
		                                                    </td>
	                                                    </tr>                             
	                                                </c:forEach>
	                                                </tbody>
	                                            </table>
	                                        </form>     
                                            
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
            </div>
        </section>

        <jsp:include page="/common/footer.jsp"></jsp:include>
    </body>
</html>

