<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>东方亿付电视商务系统--内容管理--统计报表</title>
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
                $("#accessQueryBtn").on('click',function(){
                	var preDate = $("#preDate").val();
                	var curDate = $("#curDate").val();
                    var url = getReqUrl();
                    
                    $("#queryResultDiv").load(url, {'preDate':preDate,'curDate':curDate});
                });
                
                $("#accessExportBtn").on('click',function(){
                	var preDate = $("#preDate").val();
                    var curDate = $("#curDate").val();
                	var url = getReqUrl();
                	window.location.href = url+"?preDate="+preDate+"&curDate="+curDate+"&export="+true;
                });
                
                function getReqUrl(){
                	var queryType = $("#queryType").val();

                    var url = "${ctx}/cms/statistics/countByTerm.html";
                    switch (queryType) {
                    case '1':
                        url = "${ctx}/cms/statistics/countByTerm.html";
                        break;
                    case '2':
                        url = "${ctx}/cms/statistics/countByItem.html";
                        break;
                    case '3':
                        url = "${ctx}/cms/statistics/countByUser.html";   
                        break;
                    default:
                        break;
                    }
                    return url;
                }
                
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
                            <h2><i class="icofont-retweet"></i> 访问统计 <small>电视商务商户管理系统 &rsaquo; 访问统计</small></h2>
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
                                        </div>
                                        <div class="box-body">
                                            <div id="datatables_wrapper" class="dataTables_wrapper form-inline" role="grid">
                                            <div class="row-fluid">
                                                <div>
                                                    <div>
                                                        <label>统计方式:</label>
                                                        <select class="span2" id="queryType">
                                                        <option value="1" selected="selected" >商品分类</option>
                                                        <option value="2" >商品编号</option>
                                                        <option value="3" >用户</option>
                                                        </select> 
                                                        <label>时间范围:</label>
                                                        <input class="Wdate span2" type="text" onClick="WdatePicker()" id="preDate" value="${preDate}" readonly="readonly">~<input class="Wdate span2" type="text" onClick="WdatePicker()" id="curDate" value="${curDate}" readonly="readonly">
                                                        <a class="btn btn-small btn-info" id="accessQueryBtn">查询</a>
                                                        <a class="btn btn-small btn-info"  id="accessExportBtn">导出</a>
                                                    </div>
                                                </div>
                                            </div>
                                             <hr>
                                             <div id="queryResultDiv">
                                                <p>无数据</p>
                                             </div>
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

