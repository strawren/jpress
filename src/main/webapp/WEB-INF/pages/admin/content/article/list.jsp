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
                $(document).on("page.change",function(event,pagenum){
                     var href= "${ctx}/cms/article/list.html?pageNum="+pagenum;
                     window.location.href = href;
               });
        
            $(document).ready(function() {
            	
            	/* $('#termSet').change(function(){  
            		  var termType=$(this).children('option:selected').val();//这就是selected的值  
            		  window.location.href="${ctx }/cms/article/list.html?menu=menu_content&postStatus=all&termSet=" + termType;//页面跳转并传参  
            	}); */  
            	
                //页面状态编辑切换功能
                $("#pageStatusEditBtn").on("click",function(){
                    $(this).hide();
                    $(this).parent().next().show();
                    
                });
                $("#pageStatusEditConfirmBtn").on("click",function(){
                    $(this).parent().hide();
                    $("#pageStatusEditBtn").show();
                    
                    var pageStatusSelect = $("#pageStatusSelect option:selected");
                    if(pageStatusSelect.val() == "wai"){
                    	$("#savedrtDiv").hide();
                    	$("#saveAndpenddrtDiv").show();
                    }
		            if(pageStatusSelect.val() == "drt"){
		            	$("#saveAndpenddrtDiv").hide();
		            	$("#savedrtDiv").show();
                    }
		            
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
                
                //添加标签
                $("#tagEditBtn").on("click",function(){
                    var flag = true;
                    if(flag){
                        $(this).parent().next().show();
                        flag = false;
                    }else{
                        $(this).parent().next().hide();
                        flag = true;
                    }
                });
                
                //发布时间编辑
                $("#publishDateEdit").on("click",function(){
                    $(this).hide();
                    $(this).parent().next().show();
                    
                });
                
                $("#publishDateClose").on("click",function(){
                    $(this).parent().hide();
                    $("#publishDateEdit").show();
                });
                
                $("#postDateBtn").on("click",function(){
                    $(this).parent().hide();
                    $("#publishDateEdit").show();
                    
                    $("#postDate").val($("#inputDate").val());
                });
                
                $('[data-form=datepicker]').datepicker();
                
            });
            
            function delConfirm(delId){
         	   if(!confirm("数据删除将不可恢复，确认删除吗?")){
                    return 
                }
            	   window.location.href="${ctx }/cms/article/del_article.html?postId=" + delId;
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
                            <h2><i class="icofont-retweet"></i> 内容 <small>电视商务商户管理系统 &rsaquo; 内容管理模块&rsaquo; 所有内容</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                       <div class="content-body">
                             <!--datatables tools-->
                            <div class="row-fluid">
                                <div class="span12">
                                    <div class="box corner-all">
                                        <div class="box-header grd-white corner-top">
                                            <div class="header-control">
                                                <!-- <a data-box="collapse"><i class="icofont-caret-up"></i></a>
                                                <a data-box="close" data-hide="bounceOutRight">&times;</a> -->
                                                 <a class="btn tbn-small btn-info" href="${ctx }/cms/article/add_show.html?menu=menu_content">新增</a><br/>&nbsp;
                                            </div>
                                            <span><i class="icon-list-alt"></i>&nbsp;所有内容</span>
                                            
                                        </div>
                                        <div class="box-body" style="background: url('../img/noise.png');">
                                        <%-- <select class="pull-right" id="termSet" name="termSet">
                                            <option value="">选择分类目录查询</option>
                                            <c:forEach items="${termList }" var="term">
                                                <option value="${term.slug }" <c:if test="${termTaxonomy == term.slug }">selected="selected"</c:if>>${term.name }</option>
                                            </c:forEach>
                                        </select> --%>
                                            <table id="datatablestools" class="table table-hover responsive">
                                                <thead>
                                                    <tr>
                                                        <th>商品编号</th>
                                                        <th>标题</th>
                                                        <th>作者</th>
                                                        <th>分类目录</th>
                                                        <th>状态</th>
                                                        <th>最后修改日期</th>
                                                        <th>操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${postList }" var="post">
                                                        <tr class="gradeA">
                                                            <th>${post.articleNumber }</th>
                                                            <td>${post.title }</td>
                                                            <td>${post.showOwner }</td>
                                                            <td>${post.termName }</td>
                                                            <c:if test="${post.postStatus == 'pub' }">
                                                                <td>发布</td>
                                                            </c:if>
                                                            <c:if test="${post.postStatus == 'drt' }">
                                                                <td>草稿</td>
                                                            </c:if>
                                                            <c:if test="${post.postStatus == 'wai' }">
                                                                <td>待审核</td>
                                                            </c:if>
                                                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${post.lastUpdTime }"/></td>
                                                            <td>
                                                                <div class="btn-group">
                                                                <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">操作<span class="caret"></span></button>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="${ctx }/cms/article/edit_show.html?menu=menu_content&postId=${post.id }">编辑</a></li>
                                                                    <li><a href="#" onclick="delConfirm(${post.id })">删除</a></li>
                                                                    <%-- <li><a href="${ctx }/cms/taxonomy/term_view.html?termId=${post.id }">查看</a></li> --%>
                                                                </ul>
                                                                </div><!-- /btn-group -->
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <%@ include file="/common/pagination.jsp"%>
                                        </div><!-- /box-body -->
                                    </div><!-- /box -->
                                </div><!-- /span -->
                            </div><!--/datatables tools-->        
                                        
                       </div>
                            
                    </div>
                        <!--/content-body -->
                    </div><!-- /span content -->
                </div><!-- /span side-right -->
        </section>

        <jsp:include page="/common/footer.jsp"></jsp:include>
    </body>
</html>

