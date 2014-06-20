<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>${appName }--仪表盘</title>
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
                // try your js
                
            });
            
            function delConfirm(delId){
          	   if(!confirm("数据删除将不可恢复，确认删除吗?")){
                     return 
                 }
             	   window.location.href="${ctx }/cms/article/del_article.action?postId=" + delId;
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
                            <h2><i class="icofont-home"></i> 仪表盘 <small>电视商务商户管理系统 &rsaquo; 内容管理模块</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-breadcrumb -->
                        <div class="content-breadcrumb">
                            <!--breadcrumb-nav-->
                            <ul class="breadcrumb-nav pull-right">
                                <!-- <li class="divider"></li> -->
                                <!-- <li class="btn-group">
                                    <a href="#" class="btn btn-small btn-link">
                                        <i class="icofont-money"></i> 评论 <span class="color-red">(+12)</span>
                                    </a>
                                </li> -->
                                <li class="divider"></li>
                                <li class="btn-group">
                                    <a href="${ctx }/cms/article/list.action?menu=menu_content&postStatus=drt" class="btn btn-small btn-link">
                                        <i class="elusive-pencil"></i> 草稿 <span class="color-red">(+${dtrCount })</span>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li class="btn-group">
                                    <a href="${ctx }/cms/article/list.action?menu=menu_content&postStatus=wai" class="btn btn-small btn-link">
                                        <i class="elusive-flag"></i> 待审核<span class="color-red">(+${waiCount })</span>
                                    </a>
                                </li>
                                
                            </ul><!--/breadcrumb-nav-->
                            
                            <!--breadcrumb-->
                            <ul class="breadcrumb">
                                <li><a href="index.action"><i class="icofont-home"></i> 仪表盘</a> <span class="divider">&rsaquo;</span></li>
                                <li class="active">概览</li>
                            </ul><!--/breadcrumb-->
                        </div><!-- /content-breadcrumb -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            <!-- dashboar -->
                            <!-- shortcut button -->
                            <div class="shortcut-group">
                                <ul class="a-btn-group">
                                    <li>
                                        <a href="${ctx }/cms/article/add_show.action?menu=menu_content" class="a-btn grd-white" rel="tooltip" title="新写内容">
                                            <span></span>
                                            <span><i class="icofont-edit color-silver-dark"></i></span>
                                            <span class="color-silver-dark"><i class="icofont-file color-silver-dark"></i></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/cms/media/index.action?menu=menu_multimedia" class="a-btn grd-white" rel="tooltip" title="添加媒体">
                                            <span></span>
                                            <span><i class="icofont-upload color-silver-dark"></i></span>
                                            <span class="color-silver-dark"><i class="icofont-upload-alt color-silver-dark"></i></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/cms/page/new.action?menu=menu_page" class="a-btn grd-white" rel="tooltip" title="添加页面">
                                            <span></span>
                                            <span><i class="icofont-file color-silver-dark"></i></span>
                                            <span class="color-silver-dark"><i class="icofont-save color-teal"></i></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/cms/taxonomy/taxonomy_index.action?menu=menu_content" class="a-btn grd-white" rel="tooltip" title="分类目录">
                                            <span></span>
                                            <span><i class="icofont-th-list color-silver-dark"></i></span>
                                            <span class="color-silver-dark"><i class="icofont-list-alt color-red"></i></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/cms/user/addUser.action?menu=menu_user" class="a-btn grd-white" rel="tooltip" title="添加用户">
                                            <span></span>
                                            <span><i class="icofont-user color-silver-dark"></i></span>
                                            <span class="color-silver-dark"><i class="icofont-user-md"></i></span>
                                        </a>
                                    </li>
                                    <li class="clearfix"></li>
                                </ul>
                            </div><!-- /shortcut button -->
                            
                            <div class="divider-content"><span></span></div>
                            

                            <!-- tab resume content -->
                            <div class="row-fluid">
                            
                                <!-- tab resume update -->
                                <div class="span12">
                                    <div class="box-tab corner-all">
                                        <div class="box-header corner-top">
                                            <!--tab action-->
                                            <div class="header-control pull-right">
                                                <a data-box="collapse"><i class="icofont-caret-up"></i></a>
                                            </div>
                                            <ul class="nav nav-pills">
                                                <!--tab menus-->
                                                <li class="active"><a data-toggle="tab" href="#recent-drafts">最近草稿</a></li>
                                                <li><a data-toggle="tab" href="#recent-chk-posts">待审核内容</a></li>
                                                <li><a data-toggle="tab" href="#recent-posts">最近内容</a></li>
                                                <li><a data-toggle="tab" href="#recent-comments">最近评论</a></li>
                                            </ul>
                                        </div>
                                        <div class="box-body">
                                            <!-- widgets-tab-body -->
                                            <div class="tab-content">
                                            	<div class="tab-pane fade in active" id="recent-draft">
                                                    <c:forEach items="${latelyList }" var="pub">
                                                        <div class="media">
                                                            <a class="pull-left" href="#">
                                                                <img class="media-object" data-src="${ctx }/scripts/holder.1.6.0.js/64x64" />
                                                            </a>
                                                            <div class="media-body">
                                                                <h4 class="media-heading"><a href="${ctx }/cms/article/edit_show.action?menu=menu_content&postId=${pub.id }">${pub.title }</a><small class="helper-font-small"></small></h4>
                                                                <p></p>
                                                                <div class="btn-group pull-right">
                                                                    <a href="${ctx }/cms/article/edit_show.action?menu=menu_content&postId=${pub.id }" class="btn btn-mini">编辑</a>
                                                                    <a href="#" onclick="delConfirm(${pub.id })" class="btn btn-mini btn-danger">删除</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    
                                                    <a href="${ctx }/cms/article/list.action?menu=menu_content&postStatus=pub" class="btn btn-small btn-link pull-right">View all &rarr;</a>
                                                </div>
                                                
                                                <div class="tab-pane fade in" id="recent-chk-posts">
                                                    <c:forEach items="${waiList }" var="wai">
                                                        <div class="media">
                                                            <a class="pull-left" href="#">
                                                                <img class="media-object" data-src="${ctx }/scripts/holder.1.6.0.js/64x64" />
                                                            </a>
                                                            <div class="media-body">
                                                                <h4 class="media-heading"><a href="${ctx }/cms/article/edit_show.action?menu=menu_content&postId=${wai.id }">${pub.title }</a><small class="helper-font-small"></small></h4>
                                                                <p></p>
                                                                <div class="btn-group pull-right">
                                                                    <a href="${ctx }/cms/article/edit_show.action?menu=menu_content&postId=${wai.id }" class="btn btn-mini">编辑</a>
                                                                    <a href="#" onclick="delConfirm(${wai.id })" class="btn btn-mini btn-danger">删除</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <a href="${ctx }/cms/article/list.action?menu=menu_content&postStatus=wai" class="btn btn-small btn-link pull-right">View all &rarr;</a>
                                                </div>
                                                
                                                <div class="tab-pane fade in" id="recent-posts">
                                                    <c:forEach items="${latelyDrtList }" var="drt">
                                                        <div class="media">
                                                            <a class="pull-left" href="#">
                                                                <img class="media-object" data-src="${ctx }/scripts/holder.1.6.0.js/64x64" />
                                                            </a>
                                                            <div class="media-body">
                                                                <h4 class="media-heading"><a href="${ctx }/cms/article/edit_show.action?menu=menu_content&postId=${drt.id }">${pub.title }</a><small class="helper-font-small"></small></h4>
                                                                <p></p>
                                                                <div class="btn-group pull-right">
                                                                    <!-- <a href="#" class="btn btn-mini">Approve</a> -->
                                                                    <a href="${ctx }/cms/article/edit_show.action?menu=menu_content&postId=${drt.id }" class="btn btn-mini">编辑</a>
                                                                    <a href="#" onclick="delConfirm(${drt.id })" class="btn btn-mini btn-danger">删除</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <a href="${ctx }/cms/article/list.action?menu=menu_content&postStatus=drt" class="btn btn-small btn-link pull-right">View all &rarr;</a>
                                                </div>
                                                
                                            </div><!--/widgets-tab-body-->
                                        </div><!--/box-body-->
                                    </div><!--/box-tab-->
                                </div><!-- tab resume update -->
                                
                                
                            </div><!-- tab stat -->
                            
                            <!--/dashboar-->
                        </div><!--/content-body -->
                	</div><!-- /span content -->
                </div><!-- /span side-right -->
            </div>
        </section>

        <jsp:include page="/common/footer.jsp"></jsp:include>
        
    </body>
</html>

