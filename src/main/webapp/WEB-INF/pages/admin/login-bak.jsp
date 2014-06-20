<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/variable.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>${appName}--登录</title>
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
	        	$("#ranValCodeImg").on("click", function(){
                    $(this).attr('src', "${ctxAdmin}/random.action");
                });
	        });
	        
        </script>   
    </head>

    <body>
        <!-- section header -->
        <header class="header" data-spy="affix" data-offset-top="0">
            <!--nav bar helper-->
            <div class="navbar-helper">
                <div class="row-fluid">
                    <!--panel site-name-->
                    <div class="span2">
                        <div class="panel-sitename">
                            <h2><a href="index.action"><img src="${rootPath }/images/logo.png" width="140" style="margin-right:50px;"></a></h2>
                        </div>
                    </div>
                    <!--/panel name-->
                </div>
            </div><!--/nav bar helper-->
        </header>
        
        <!-- section content -->
        <section class="section">
            <div class="container">
                <div class="signin-form row-fluid">
                    <!--Sign In-->
                    <div class="span5 offset1">
                        
                    </div><!--/Sign In-->
                    <!--Sign Up-->
                    <div class="span5" style="margin-left:100px;">
                        <div class="box corner-all">
                            <div class="box-header grd-teal color-white corner-top">
                                <span>请输入登入帐号</span>
                            </div>
                            <div class="box-body bg-white">
                                <form id="loginIn" method="post" class="form-horizontal" action="${ctxAdmin}/login.action">
                                    <div class="control-group">
                                        <label class="control-label">用户名</label>
                                        <div class="controls">
                                        	<input type="text" class="input-block-level" name="loginName" id="loginName" value="${loginName }" />
                                        </div>	
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">密 码</label>
                                        <div class="controls">
                                            <input type="password" class="input-block-level" name="loginPwd" id="loginPwd" />
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">验证码</label>
                                        <div class="controls">
                                            <input type="text" class="input-block-level"  name="ranValCode" id="ranValCode" /> 
                                            <img id="ranValCodeImg" title="点击更换" src="${ctxAdmin}/random.action" style="cursor:pointer;" />&nbsp;&nbsp;点击图片换一张。
                                            <div <c:if test="${empty error}">style="display: none;"</c:if> id="errorMsg" style="color:red;">${error }</div>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">
                                            <input type="checkbox" data-form="uniform" name="rememberMe" id="rememberMe" /> 记住我
                                        </label>
                                    </div>
                                    <div class="form-actions">
                                        <input type="hidden" name="loginFlag" id="loginFlag" value="Y" />
                                        <input type="submit" class="btn btn-block btn-large btn-primary" value="登陆" />
                                        <p class="recover-account">忘记密码 <a href="#modal-recover" class="link" data-toggle="modal">？</a></p>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div><!--/Sign Up-->
                </div><!-- /row -->
            </div><!-- /container -->
            
            <!-- modal recover -->
            <div id="modal-recover" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="modal-recoverLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 id="modal-recoverLabel">Reset password <small>Username Or Email Address</small></h3>
                </div>
                <div class="modal-body">
                    <form id="form-recover" method="post">
                        <div class="control-group">
                            <div class="controls">
                                <input type="text" data-validate="{required: true, email:true, messages:{required:'Please enter field email', email:'Please enter a valid email address'}}" name="recover" />
                                <p class="help-block helper-font-small">Enter your username or email address and we will send you a link to reset your password.</p>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
                    <input type="submit" form="form-recover" class="btn btn-primary" value="Send reset link" />
                </div>
            </div><!-- /modal recover-->
        </section>

        <div id="footer">
    		<span class="color8">东方亿付旗下网站：</span>
        	<a href="#" target="_blank">东方亿付官网</a>|
            <a href="#" target="_blank">亿付数字官网</a>|
            <a href="#" target="_blank">东方俱乐部</a> <br/>
            <a href="#">关于东方亿付</a>|
            <a href="#">联系东方亿付</a>|
            <a href="#">安全策略</a>|
            <a href="#">帮助中心</a><br/>
           	<span class="copy">©</span>2014 上海亿付数字技术有限公司商户系统版权所有<span class="mar_l">沪ICP证110560</span><span class="mar_l">沪公网安备110102004918</span>
            <div class="suggest">
            	<a href="#"><img src="${ctx }/images/renzheng/rz1.gif"></a> 
                <a href="#"><img src="${ctx }/images/renzheng/rz2.gif"></a> 
                <a href="#"><img src="${ctx }/images/renzheng/rz3.gif"></a> 
                <a href="#"><img src="${ctx }/images/renzheng/rz4.gif"></a> 
                <a href="#"><img src="${ctx }/images/renzheng/rz5.gif"></a> 
                <a href="#"><img src="${ctx }/images/renzheng/rz6.gif"></a> 
                <a href="#"><img src="${ctx }/images/renzheng/rz7.gif"></a> 
                <a href="#"><img src="${ctx }/images/renzheng/rz8.gif"></a> 
            </div>
       	</div>
    </body>
</html>

