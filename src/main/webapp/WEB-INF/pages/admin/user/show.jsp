<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>东方亿付电视商务系统--用户管理模块--用户资料</title>
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
                            <h2><i class="icofont-retweet"></i> 用户  <small>电视商务商户管理系统 &rsaquo; 用户管理模块&rsaquo; 我的资料</small></h2>
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
                                            <span><i class="icon-folder-open"></i>&nbsp;用户资料</span>
                                        </div>
                                        <div class="box-body">
                                            <div class="page-header">
			                                    <div class="pull-right">
			                                        <img data-src="holder.js/100x100/text:best regard" class="img-circle" />
			                                    </div>
			                                    <h3>${cmsUser.loginName}&nbsp;<small>${cmsUser.miscDesc}</small></h3>
			                                </div>
			                                <div class="row-fluid">
			                                    <div class="span2"></div>
			                                    <div class="span4">
                                                    <p>姓名</p>
                                                    <p>昵称</p>
                                                    <p>电子邮件</p>
			                                        
			                                    </div>
			                                    <div class="span4">
                                                    <p>${cmsUser.userName }</p>
                                                    <p>${cmsUser.nickname }</p>
                                                    <p>${cmsUser.userEmail }</p>
			                                    </div>
			                                </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="span3">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span>修改资料</span>
                                        </div>
                                        <div class="box-body">
                                              <div class="control-group">
                                                  <i class="icon-refresh"></i><span>信息变更，需要修改？</span><br>
                                              </div>
                                              <div class="control-group">
                                                  <a></a><a class="btn tbn-small btn-info" id="editUserBtn" href="${ctx}/cms/user/updateShowUser.html?menu=menu_user&uid=${user.id }">编辑</a>
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
