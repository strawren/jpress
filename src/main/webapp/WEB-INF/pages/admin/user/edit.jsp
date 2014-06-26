<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>${appName }--用户--${model.id lt 1 ? "添加用户" : "编辑用户"}</title>
        <jsp:include page="/common/meta.jsp"></jsp:include>
        <jsp:include page="/common/style.jsp"></jsp:include>
        <jsp:include page="/common/scripts.jsp"></jsp:include>
        <script type="text/javascript">
        	var createFlag = ${model.id lt 1 ? "true" : "false"};
            $(document).ready(function(){
                $("#saveFormBtn").on("click",function(){
                    if (checkParams()) {
                        $("#editForm").submit();
                    }
                });
            });
            
            function checkParams(){
                var userEmail = $.trim($("#editForm").find("#userEmail").val());
                if (!userEmail || userEmail.length == 0) {
                    $("#formMsg").text("电子邮件地址不能为空").show();
                    return false;
                }
                var loginPwd = $.trim($("#editForm").find("#loginPwd").val());
                var loginPwd2 = $.trim($("#editForm").find("#loginPwd2").val());
                if (loginPwd != loginPwd2) {
                    $("#formMsg").text("两次密码输入不一致").show();
                    return false;
                }
                if(loginPwd.length == 0 && (!createFlag)) {
                	
                }
                else {
                	if(loginPwd.length == 0) {
                		$("#formMsg").text("密码不能为空").show();
                        return false;
                	}
                	else {
                		var pwdTestFlag = loginPwd.match("^[A-Za-z0-9]{8,12}$");
	                     if (!pwdTestFlag) {
	                         $("#formMsg").text("密码最少8位，最大12位，且必须包含数字和字母").show();
	                         return false;
	                     }
                	}
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
                            <h2><i class="icofont-retweet"></i> 用户 <small>${appName } &rsaquo; 用户&rsaquo; ${model.id lt 1 ? "添加用户" : "编辑用户"}</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            <div class="alert alert-error" <c:if test="${empty message}">style="display: none;"</c:if> id="formMsg">
                            	${message}
                            </div>
                            <div class="row-fluid">
                                <div class="span9">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="${model.id lt 1 ? 'icofont-plus-sign' : 'icon-edit'}"></i>&nbsp;${model.id lt 1 ? "添加用户" : "编辑用户"}</span>
                                        </div>
                                        <div class="box-body">
                                            <form id="editForm" method="post" action="${ctxAdmin }/user/save.action">
                                            	<input name="id" type="hidden" value="${model.id }" />
                                                <table class="form-table">
                                                    <tr valign="top">
                                                    	<th scope="row"><label for="loginName">登陆名</label></th>
                                                    	<td>
                                                       		<input name="loginName" type="text" class="regular-text ltr" id="loginName" value="${model.loginName }" ${model.id lt 1 ? "" : "readonly='readonly'"} />*
                                                   		</td>
                                                    </tr>
                                                    <tr valign="top">
                                                    	<th scope="row"><label for="userEmail">电子邮件</label></th>
                                                    	<td>
                                                       		<input name="userEmail" type="text" class="regular-text ltr" id="userEmail" value="${model.userEmail }"  />* 用于密码找回等功能
                                                   	 	</td>
                                                    </tr>
                                                    <tr valign="top">
                                                    	<th scope="row"><label for="userName">姓名</label></th>
                                                    	<td>
                                                       		<input name="userName" type="text" class="regular-text ltr" id="userName" value="${model.userName }"  />
                                                    	</td>
                                                    </tr>
                                                    <tr valign="top">
                                                    	<th scope="row"><label for="nickName">昵称</label></th>
                                                    	<td>
                                                       		<input name="nickname" type="text" class="regular-text ltr" id="nickName" value="${model.nickname }"  />
                                                    	</td>
                                                    </tr>
                                                     <tr valign="top">
                                                    	<th scope="row"><label for="showName">显示名称</label></th>
                                                    	<td>
                                                       		<input name="showName" type="text" class="regular-text ltr" id="nickName" value="${model.showName }"  />
                                                    	</td>
                                                    </tr>
                                                    <tr valign="top">
                                                    	<th scope="row"><label for="loginPwd">密码</label></th>
                                                    	<td>
                                                       		<input name="loginPwd" type="password" class="regular-text ltr" id="loginPwd" value=""  />密码最少8位，最大12位，且必须包含数字和字母 ${model.id lt 1 ? "" : "(如果不填默认不修改密码）"}
                                                    	</td>
                                                    </tr>
                                                    <tr valign="top">
                                                    	<th scope="row"><label for="loginPwd2">重复密码</label></th>
                                                    	<td>
                                                       		<input name="loginPwd2" type="password" class="regular-text ltr" id="loginPwd2" value=""  />
                                                    	</td>
                                                    </tr>
                                                    <tr valign="top">
                                                    	<th scope="row"><label for="userLevel">用户级别</label></th>
                                                    	<td>
                                                       		<input name="userLevel" type="text" class="regular-text ltr" id="userLevel" value="${userLevel }" readonly="readonly" />新建用户默认初始级别，由全局参数设定
                                                    	</td>
                                                    </tr>
                                                </table>
                                                
                                                <div class="form-actions">
                                                    &nbsp;&nbsp;&nbsp; <button type="button" class="btn btn-primary" id="saveFormBtn" >保存</button>
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

