<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>${appName }--用户--添加用户</title>
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
            $(document).ready(function(){
            	$('[data-form=datepicker]').datepicker();
            	$('[data-form=colorpicker]').colorpicker();
            	$("#saveUser").on("click",function(){
					if (checkParams()) {
					    $("#addUserForm").submit();
					}
                });
            });
            
            function checkParams(){
            	var loginName = $.trim($("#addUserForm").find("#loginName").val());
                if (!loginName || loginName.length==0) {
                    $("#formMsg").text("用户名不能为空").show();
                    return false;
                }
                var userEmail = $.trim($("#addUserForm").find("#userEmail").val());
                if (!userEmail || userEmail.length==0) {
                    $("#formMsg").text("电子邮件地址不能为空").show();
                    return false;
                }
                var loginPwd = $.trim($("#addUserForm").find("#loginPwd").val());
                if (!loginPwd || loginPwd.length==0) {
                    $("#formMsg").text("密码不能为空").show();
                    return false;
                }
                var pwdTestFlag = loginPwd.match("^[A-Za-z0-9]{8,12}$");
                if (!pwdTestFlag) {
                	$("#formMsg").text("密码最少8位，最大12位，且必须包含数字和字母").show();
                    return false;
                }
                var loginPwd2 = $.trim($("#addUserForm").find("#loginPwd2").val());
                if (!loginPwd2 || loginPwd2.length==0) {
                    $("#formMsg").text("重复密码不能为空").show();
                    return false;
                }
                if (loginPwd != loginPwd2) {
                	$("#formMsg").text("二次密码输入不一致").show();
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
                	<jsp:include page="/common/sidebar.jsp"></jsp:include>
                </div><!-- span side-left -->
                
                <!-- span content -->
                <div class="span11">
                    <!-- content -->
                    <div class="content">
                        <!-- content-header -->
                        <div class="content-header">
                            <h2><i class="icofont-retweet"></i> 用户 <small>${appName }&rsaquo; 用户&rsaquo; 添加用户</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            <div class="alert alert-error" <c:if test="${empty msg}">style="display: none;"</c:if> id="formMsg">
                            ${msg}
                            </div>
                            <div class="row-fluid">
                                <div class="span9">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icofont-plus-sign"></i>&nbsp;新增用户</span>
                                        </div>
                                        <div class="box-body">
                                            <form id="addUserForm" method="post" action="${ctxAdmin }/user/saveUser.action?menu=menu_user">
                                                <table class="form-table">
	                                                <tr valign="top">
	                                                <th scope="row"><label for="loginName">用户名（必填）</label></th>
	                                                <td>
	                                                   <input name="loginName" type="text" class="regular-text ltr" id="loginName" value=""  />
	                                                </td>
	                                                </tr>
                                                    <tr valign="top">
                                                    <th scope="row"><label for="userEmail">电子邮件（必填）</label></th>
                                                    <td>
                                                       <input name="userEmail" type="text" class="regular-text ltr" id="userEmail" value=""  />
                                                       <p class="description">用于通知密码等。</p>
                                                    </td>
                                                    </tr>
                                                    <tr valign="top">
                                                    <th scope="row"><label for="userName">姓名</label></th>
                                                    <td>
                                                       <input name="userName" type="text" class="regular-text ltr" id="userName" value=""  />
                                                    </td>
                                                    </tr>
                                                    <tr valign="top">
                                                    <th scope="row"><label for="nickName">昵称</label></th>
                                                    <td>
                                                       <input name="nickName" type="text" class="regular-text ltr" id="nickName" value=""  />
                                                    </td>
                                                    </tr>
                                                    <tr valign="top">
                                                    <th scope="row"><label for="loginPwd">密码（必填）</label></th>
                                                    <td>
                                                       <input name="loginPwd" type="password" class="regular-text ltr" id="loginPwd" value=""  />
                                                       <p class="description">密码最少8位，最大12位，且必须包含数字和字母。</p>
                                                    </td>
                                                    </tr>
                                                    <tr valign="top">
                                                    <th scope="row"><label for="loginPwd2">重复密码（必填）</label></th>
                                                    <td>
                                                       <input name="loginPwd2" type="password" class="regular-text ltr" id="loginPwd2" value=""  />
                                                    </td>
                                                    </tr>
                                                    <tr valign="top">
                                                    <th scope="row"><label for="userLevel">用户级别</label></th>
                                                    <td>
                                                       <input name="userLevel" type="text" class="regular-text ltr" id="userLevel" value="${userLevel }" disabled />
                                                       <p class="description">新建用户默认初始级别，由全局参数设定。</p>
                                                    </td>
                                                    </tr>
                                                </table>
                                                <div class="form-actions"> &nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-primary" id="saveUser" >保存</button>
                                                </div>
                                            </form>
                                        
                                        
                                        </div> <!-- /box body -->
                                    </div><!-- /box -->
                                </div><!-- /span -->
                                
                                <div class="span3">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span>提示</span>
                                        </div>
                                        <div class="box-body">
                                            <div class="control-group">
                                                <i class="icon-map-marker"></i>&nbsp;<span>初始密码请及时修改</span><br>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
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

