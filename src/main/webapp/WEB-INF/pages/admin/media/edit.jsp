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
            $(function(){
            	$("#updateMediaBtn").on("click",function(){
                    if (checkForm()) {
                        $("#updateMediaForm").submit();
                    }
                });
            });
                
          //表单验证
            function checkForm(){
                var title = $.trim($("#title").val());
                if (!title||title.length==0) {
                    $("#formMsg").text("文件名称不能为空").show();
                    return false;
                }

                $("#formMsg").hide();
                return true;
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
                            <h2><i class="icofont-retweet"></i> 多媒体 <small>电视商务商户管理系统 &rsaquo; 多媒体管理模块&rsaquo; 编辑媒体</small></h2>
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
                                            <span><i class="icofont-plus-sign"></i>&nbsp;编辑媒体</span>
                                        </div>
                                        <div class="box-body">
                                            <form method="post" action="${ctx}/cms/media/update.html" id="updateMediaForm">
                                            <div class="control-group">
                                                <div class="controls">
                                                   <input type="hidden" name="id" value="${media.id}">
                                                   <input type="hidden" name="lastUpdTime" value="<fmt:formatDate value="${media.lastUpdTime}" type="both"/>">
                                                   <input type="text" class="span8" placeholder="在此输入文件名" name="title" id="title" value="${media.title}">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <div class="controls media" >
                                                            <c:if test="${media.mimeType=='jpg'}">
                                                            <img class="img-polaroid media-object" alt="${media.title}" src="${mediaUrl}${media.guid}">
                                                            </c:if>
                                                            <c:if test="${media.mimeType=='png'}">
                                                            <img class="img-polaroid media-object" alt="${media.title}" src="${mediaUrl}${media.guid}">
                                                            </c:if>
                                                            <c:if test="${media.mimeType=='gif'}">
                                                            <img class="img-polaroid media-object" alt="${media.title}" src="${mediaUrl}${media.guid}">
                                                            </c:if>
                                                </div>
                                            </div>
                                        </form>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="span3">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icon-list"></i>保存</span>
                                            <a class="btn tbn-small pull-right btn-info" id="updateMediaBtn">更新</a>
                                        </div>
                                        
                                        <div class="box-body">
                                             <div class="control-group">
                                                    <div class="controls">
                                                        <p>上传于：<fmt:formatDate value="${media.createTime}" pattern="yyyy年MM月dd日 "/></p>
                                                        <p style="word-break:break-all;">文件URL：<br/>
                                                        ${mediaUrl}${media.guid}
                                                        </p>
                                                        <p>文件名： ${media.title}</p>
                                                        <p>文件类型： ${media.mimeType}</p>
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

