<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>东方亿付电视商务系统--用户管理模块--所有用户</title>
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
        $(document).ready(function () {
        	// 当点击全选框时
            $("#checkAllUsersBtn").click(function () {      
                // 全选或全不选
                var flag = $("#checkAllUsersBtn").is(':checked');   // 判断全选按钮的状态
                // 查找每一个Id以Item结尾的checkbox
                $("[id$='Item']").each(function () {
                // 选中或者取消选中
                $(this).attr("checked", flag);
                });
            });
        	
            // 如果全部选中勾上全选框，全部选中状态时取消了其中一个则取消全选框的选中状态
            $("[id$='Item']").each(function () {
                $(this).click(function () {
                    if ($("[id$='Item']:checked").length == $("[id$='Item']").length) {
                        $("#checkAllUsersBtn").attr("checked", "checked");
                    }
                    else {
                    	$("#checkAllUsersBtn").removeAttr("checked");
                    }
                });
            });
            var flag = true;
            
            // 点击批量删除
            $("#batchDelUsers").click(function () {
            	
            	$("[id$='Item']").each(function () {
            		
                    
                        if ($("[id$='Item']:checked").length > 0 ) {
                        	
                        	flag = true;
                        }
                        else {
                           
                            flag = false;
                        }
                    
                });
            	
            	if (flag == true) {
            		$("#deleteUsers").submit();
            	}
            	else {
            		 alert("您还没有选中要删除的信息！");
            	}
            })
        })
        </script>
        <script type="text/javascript">
			$(document).on("page.change",function(event,pagenum){
			     $("#pageNum").val(pagenum);
			     $("#searchUsers").submit();
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
                            <h2><i class="icofont-retweet"></i> 用户 <small>电视商务商户管理系统 &rsaquo; 用户管理模块&rsaquo; 所有用户</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            
                            <div class="row-fluid">
                                <div class="span12">
                                    <div class="box corner-all">
                                        <div class="box-header grd-white corner-top">
                                            <span>
                                                <i class="icon-list"></i>&nbsp;
                                                <a href="${ctx}/cms/user/all.html?menu=menu_user">所有用户</a>
                                            </span>
                                        </div>
                                        <div class="box-body">
                                            <div id="datatables_wrapper" class="dataTables_wrapper form-inline" role="grid">
                                            <div class="row-fluid">
                                                <div class="span6">
                                                    <div id="datatables_length" >
                                                        <label>
                                                            <a class="btn btn-small btn-danger" id="batchDelUsers">批量删除</a>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="span6">
                                                <form action="${ctx}/cms/user/allUser.html?menu=menu_user" method="post" id="searchUsers">
                                                    <div class="dataTables_filter" id="datatables_filter" >
                                                        <label>
                                                        <input type="hidden" name="pageNum"  id="pageNum" value="${page.pageNum}" />
                                                        <input type="text" aria-controls="datatables" name="search" id="search" value="${search}">
                                                        <input type="submit" class="btn btn-info btn-small" value="搜索">
                                                        </label>
                                                    </div>
                                                </form>
                                                </div>
                                            </div>
                                             
                                             <table class="table table-hover table-striped responsive">
                                                <thead>
                                                    <tr>
                                                        <th><input type="checkbox" id="checkAllUsersBtn"></th>
                                                        <th>ID</th>
                                                        <th>用户名</th>
                                                        <th>昵称</th>
                                                        <th>最后修改日期</th>
                                                        <th>操作</th>
                                                    </tr>
                                                </thead>
                                                <form action="${ctx}/cms/user/deleteUser.html?menu=menu_user" method="post" id="deleteUsers">
                                                <tbody id="pageTable">
                                                <c:forEach items="${page.result }" var="user">
                                                
                                                    <tr>
	                                                    <td><input type="checkbox" name="checkItem" value="${user.id }" id="checkItem"></td>
	                                                    <td>${user.id }</td>
	                                                    <td>${user.loginName }</td>
	                                                    <td>${user.nickname }</td>
	                                                    <td><fmt:formatDate value="${user.lastUpdTime}" pattern="yyyy年MM月dd日 "/></td>
	                                                    <td>
	                                                        <div class="btn-group">
	                                                        <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">操作<span class="caret"></span></button>
	                                                        <ul class="dropdown-menu">
	                                                            <li><a href="${ctx}/cms/user/updateShowUser.html?menu=menu_user&uid=${user.id }">编辑</a></li>
	                                                            <li><a href="${ctx}/cms/user/deleteUser.html?menu=menu_user&uid=${user.id }">删除</a></li>
	                                                        </ul>
	                                                        </div><!-- /btn-group -->
	                                                    </td>
                                                    </tr>                             
                                                </c:forEach>
<%--                                                 <c:forEach items="${pages}" var="page"> --%>
<!--                                                     <tr> -->
<%--                                                         <td><input type="checkbox" value="${page.id}"></td> --%>
<!--                                                         <td> -->
<!--                                                         <div class="wrap"> -->
<%--                                                         <span>${page.title}</span> --%>
<!--                                                             <div class="operate"> -->
<%--                                                                 <span><a href="${ctx}/cms/page/edit.html?menu=menu_page&id=${page.id}">编辑</a><!-- &nbsp;|&nbsp;<a href="#">快速编辑</a> -->&nbsp;|&nbsp;<a href="#" onclick="removePage(${page.id})">移至回收站</a><!-- &nbsp;|&nbsp;<a href="#">预览</a> --></span> --%>
<!--                                                             </div> -->
<!--                                                         </div> -->
<!--                                                         </td> -->
<%--                                                         <td>${page.showOwner}</td> --%>
<%--                                                         <td>${page.commentCount}</td> --%>
<%--                                                         <td><fmt:formatDate value="${page.showDate}" pattern="yyyy年MM月dd日 "/></td> --%>
<!--                                                     </tr> -->
<%--                                                 </c:forEach> --%>
                                                </tbody>
                                               </form>     
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

