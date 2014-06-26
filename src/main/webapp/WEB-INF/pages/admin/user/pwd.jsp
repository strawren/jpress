<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>${appName }--用户--修改密码</title>
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
                var oldPwd = $.trim($("#editForm").find("#oldPwd").val());
                var newPwd = $.trim($("#editForm").find("#newPwd").val());
                var newPwd2 = $.trim($("#editForm").find("#newPwd2").val());
                
                if (newPwd != newPwd2) {
                    $("#formMsg").text("两次密码输入不一致").show();
                    return false;
                }
               	if(oldPwd.length == 0) {
               	   $("#formMsg").text("原始密码不能为空").show();
                   return false;
               	}
           		var pwdTestFlag = newPwd.match("^[A-Za-z0-9]{8,12}$");
                if (!pwdTestFlag) {
                    $("#formMsg").text("新密码最少8位，最大12位，且必须包含数字和字母").show();
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
                            <h2><i class="icofont-retweet"></i> 用户 <small>${appName } &rsaquo; 用户&rsaquo; 修改密码</small></h2>
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
                                            <span><i class="icon-edit"></i>&nbsp;修改密码</span>
                                        </div>
                                        <div class="box-body">
                                            <form id="editForm" method="post" action="${ctxAdmin }/user/pwd.action">
                                                <table class="form-table">
                                                    <tr valign="top">
                                                    	<th scope="row"><label for="loginName">登录名</label></th>
                                                    	<td>
                                                       		<input name="loginName" type="text" class="regular-text ltr" id="loginName" value="${user.loginName }" readonly="readonly" />
                                                   		</td>
                                                    </tr>
                                                    <tr valign="top">
                                                    	<th scope="row"><label for=oldPwd>原密码</label></th>
                                                    	<td>
                                                       		<input name="oldPwd" type="password" class="regular-text ltr" id="oldPwd" value="" /> * 
                                                    	</td>
                                                    </tr>
                                                    <tr valign="top">
                                                    	<th scope="row"><label for="newPwd">密码</label></th>
                                                    	<td>
                                                       		<input name="newPwd" type="password" class="regular-text ltr" id="newPwd" value="" /> * 密码最少8位，最大12位，且必须包含数字和字母 
                                                    </tr>
                                                    <tr valign="top">
                                                    	<th scope="row"><label for="newPwd2">重复密码</label></th>
                                                    	<td>
                                                       		<input name="newPwd2" type="password" class="regular-text ltr" id="newPwd2" value="" /> * 密码最少8位，最大12位，且必须包含数字和字母 
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
                                                <i class="icon-map-marker"></i>&nbsp;<span>如果忘记密码，请在登陆页面上使用密码找回功能</span><br>
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

