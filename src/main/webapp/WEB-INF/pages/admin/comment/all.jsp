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
                // try your js
                
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
                            <h2><i class="icofont-retweet"></i> 评论 <small>电视商务商户管理系统 &rsaquo; 评论管理模块&rsaquo; 所有评论</small></h2>
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
                                            <span><a href="#">全部(1)</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a href="#">已发布(1)</a></span>
                                        </div>
                                        <div class="box-body">
                                            <div id="datatables_wrapper" class="dataTables_wrapper form-inline" role="grid">
                                            <div class="row-fluid">
                                                <div class="span6">
                                                    <div id="datatables_length" >
                                                        <label>
                                                            <select size="1" name="datatables_length" aria-controls="datatables" >
                                                            <option value="1" selected="selected" >批量操作</option>
                                                            <option value="2" selected="selected" >编辑</option>
                                                            <option value="3" selected="selected" >移至回收站</option>
                                                            </select> 
                                                            <a class="btn btn-small" >应用</a>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="span6">
                                                    <div class="dataTables_filter" id="datatables_filter" >
                                                        <label>
                                                        <input type="text" aria-controls="datatables" >
                                                        <a class="btn btn-small" >搜索</a>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                             
                                             <table class="table table-hover responsive">
                                                <thead>
                                                    <tr>
                                                        <th><input type="checkbox"></th>
                                                        <th>标题</th>
                                                        <th>作者</th>
                                                        <th>评论</th>
                                                        <th>日期</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td><input type="checkbox"></td>
                                                        <td>MarkMarkMarkMarkMarkMarkMarkMarkMarkMarkMarkMarkMarkMarkMark</td>
                                                        <td>Otto</td>
                                                        <td>@mdo</td>
                                                        <td>@mdo</td>
                                                    </tr>
                                                    <tr>
                                                        <td><input type="checkbox"></td>
                                                        <td>Jacob</td>
                                                        <td>Thornton</td>
                                                        <td>@fat</td>
                                                        <td>@mdo</td>
                                                    </tr>
                                                    <tr>
                                                        <td><input type="checkbox"></td>
                                                        <td>Larry</td>
                                                        <td>the Bird</td>
                                                        <td>@twitter</td>
                                                        <td>@mdo</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                             
                                            <div class="row-fluid"><div class="span6"><div class="dataTables_info" id="datatables_info">Showing 1 to 10 of 46 entries</div></div><div class="span6"><div class="dataTables_paginate paging_bootstrap pagination"><ul><li class="prev disabled"><a href="#">← Previous</a></li><li class="active"><a href="#">1</a></li><li><a href="#">2</a></li><li><a href="#">3</a></li><li><a href="#">4</a></li><li><a href="#">5</a></li><li class="next"><a href="#">Next → </a></li></ul></div></div></div></div>
                                            
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

