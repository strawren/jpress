<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>东方亿付电视商务系统--外观管理--静态化</title>
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
                $("#addLinkBtn").on("click",function(){
                    var visible = $("#visibleCheck").attr("checked");
                    if(visible=='checked'){
                        $("#visible").val("clse");
                    }else{
                        $("#visible").val("open");
                    }
                    if (checkForm()) {
                        $("#addLinkForm").submit();
                    }
                });
                
            });
      
            //表单验证
            function checkForm(){
                var name = $.trim($("#addLinkForm").find("#name").val());
                var url = $.trim($("#addLinkForm").find("#url").val());
                if (!name||name.length==0) {
                    $("#formMsg").text("链接名称不能为空").show();
                    return false;
                }

                if (!url||url.length==0) {
                    $("#formMsg").text("链接地址不能为空").show();
                    return false;
                }
                
                if (url.indexOf("http://") != 0||url.length<10) {
                    $("#formMsg").text("链接地址格式不对,请以http://开头,例如http://cn.wordpress.org/").show();
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
                            <h2><i class="icofont-retweet"></i> 设置 <small>电视商务商户管理系统 &rsaquo; 外观管理模块&rsaquo; 静态化</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
               <div class="content-body">
                            
                            <!-- tab resume content -->
                            <div class="alert alert-error" <c:if test="${empty msg}">style="display: none;"</c:if> id="formMsg">
                            ${msg}
                            </div>
                            <div class="row-fluid">
                            
                                <div class="span9">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icon-globe"></i>&nbsp;静态化</span>
                                        </div>
                                        <div class="box-body">
                                                                                                                       建设中...
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="span3">
                                    <div class="box corner-all">
<!--                                         <div class="box-header corner-top grd-white"> -->
<!--                                             <span><i class="icon-share"></i>应用</span> -->
<!--                                         </div> -->
<!--                                         <div class="box-body"> -->
<!--                                             <div class="control-group"> -->
<!--                                              <a class="btn tbn-small btn-info" id="draftPageBtn">保存</a> -->
<!--                                              <a class="btn tbn-small pull-right">预览</a> -->
<!--                                              <a class="btn tbn-small pull-right btn-info" id="publishPageBtn">发布</a> -->
<!--                                             </div> -->
<!--                                             <div class="control-group"> -->
<!--                                                 <span>&nbsp;<i class="icon-map-marker"></i>&nbsp;状态：<span id="pageStatusSpan">草稿</span></span>&nbsp;&nbsp;&nbsp;<a href="#" id="pageStatusEditBtn">编辑</a> -->
<!--                                             </div> -->
<!--                                             <div class="control-group" style="display: none;"> -->
<!--                                                 <select id="pageStatusSelect"> -->
<!--                                                     <option value="drt">草稿</option> -->
<!--                                                     <option value="wai">等待复审</option> -->
<!--                                                 </select> -->
<!--                                                 <a href="#" id="pageStatusEditConfirmBtn" class="btn btn-small btn-info" >确定</a> -->
<!--                                                 <a href="#" id="pageStatusEditCancelBtn">取消</a> -->
<!--                                             </div> -->
<!--                                             <input type="radio" name="openStatus" value="3"><span>密码保护</span><br> -->
<!--                                         </div> -->
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
