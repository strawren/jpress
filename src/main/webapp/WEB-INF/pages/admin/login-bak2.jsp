<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/variable.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>${appName}--登录</title>
        <jsp:include page="/common/meta.jsp"></jsp:include>
        <link href="${ctx }/css/admin-login.css" rel="stylesheet" />
        <script src="${ctx }/scripts/jquery.1.8.1.min.js"></script>
        
        <script type="text/javascript">
	        $(document).ready(function(){  
	        	$("#ranValCodeImg").on("click", function(){
                    $(this).attr('src', "${ctxAdmin}/random.action");
                });
	        });
        </script>   
    </head>

    <body id="j_id2" class="skin-default">
		<div id="j_id4" style="width:100%;height:100%;">
			<div id="j_id5" style="width:100%;height:100%;">
				<div class="header">
					<div class="title_font">OperaMasks HR</div>
				</div>
			</div>
			
			<div id="center" style="width:100%;" class="content">
				<div id="contentWrapper">
					<table id="j_id6">
						<tbody>
							<tr><td class="login_left_desc" colspan="1" rowspan="1">
								<table id="j_id8">
									<tbody>
										<tr><td>
											<table id="j_id9">
												<tbody>
													<tr><td colspan="2" rowspan="1">
														<span style="color: #1C3A70; font-size: 18px; font-weight: bold;">OperaMasks HR 系统</span>
													</td></tr>
													<tr><td colspan="1" rowspan="1"><img src="${ctx }/images/logging_icon1.gif"/></td>
														<td colspan="1" rowspan="1">
															<span>
																<b style="display: block; margin-top: 20px">示例简介</b>
																HR演示系统以企业内部人力资源管理系统为原型，通过该示例，您可以体验到使用OperaMasks开发一个小型系统的富客户端体验。
															</span>
														</td>
													</tr>
													<tr><td colspan="1" rowspan="1"><img src="${ctx }/images/logging_icon2.gif" style="margin-top: 40px;"/></td>
														<td colspan="1" rowspan="1">
															<span>
																<b style="display: block; margin-top: 40px;">功能模块</b> 该示例包含部门管理，模块管理，部门培训分析，职级培训统计等功能。
															</span>
														</td>
													</tr>
												</tbody>
											</table>
											</td>
											<td colspan="1" rowspan="1">
												<img src="${ctx }/images/logging_middle.gif" style="margin: 0px 20px 0px 20px;"/></td>
											</tr>
										</tbody>
									</table>
								</td>
								<td class="rightForm" colspan="1" rowspan="1">
									<form id="loginForm" name="loginForm" method="post" action="" class="loginForm">
										<div class="formBorder" align="left">
											<img src="${ctx }/images/userlogin.gif" style="margin: 0px 0px 10px 10px;" />
											<br/>
											<table id="loginForm:j_id17" style="margin-left:40px" class="loginLeftTable">
												<tbody>
													<tr><td class="formLabel" colspan="1" rowspan="1">用户名：</td>
														<td class="inputTD" colspan="1" rowspan="1">
															<table id="loginForm:username_outer">
																<tr><td></td><td>
																	<div class="x-form-item"><div class="x-form-element">
																		<input id="loginForm:username" name="loginForm:username" type="text" style="" class="inputfield"/>
																	</div></div>
																</td></tr>
															</table>
														</td>
													</tr>
													<tr><td colspan="1" rowspan="1"></td>
														<td class="vbErrorDiv" colspan="1" rowspan="1" style="height: 18px;">用户名必须在1位以上，且不包括中文等特殊字符。</td>
													</tr>
													<tr><td class="formLabel" colspan="1" rowspan="1">密码：</td>
														<td class="inputTD" colspan="1" rowspan="1">
															<table id="loginForm:password_outer" cellspacing="0" cellpadding="0">
																<tr><td></td><td>
																	<div class="x-form-item"><div class="x-form-element">
																		<input id="loginForm:password" name="loginForm:password" type="password" style="" class="inputfield"/>
																	</div></div>
																</td></tr>
															</table>
														</td>
													</tr>
													<tr><td colspan="1" rowspan="1"></td>
														<td class="vbErrorDiv" colspan="1" rowspan="1" style="height: 18px;">密码必须在1位以上，且不包括中文等特殊字符。</td>
													</tr>
													<tr><td class="formLabel" colspan="1" rowspan="1">验证码：</td>
														<td class="inputTD" colspan="1" rowspan="1">
															<table id="loginForm:j_id28" class="validateTable">
																<tr><td class="validateInputTD" colspan="1" rowspan="1">
																	<table id="loginForm:validateCode_outer" cellspacing="0" cellpadding="0">
																		<tr><td></td><td>
																			<div class="x-form-item"><div class="x-form-element">
																				<input id="loginForm:validateCode" name="loginForm:validateCode" type="text" style="" class="validateInput"/>
																			</div></div>
																		</td></tr>
																	</table>
																	</td>
																	<td class="validateImg" colspan="1" rowspan="1">
																		<img id="loginForm:validateCodeImg" src="" height="20" style="margin-left: 15px;" width="60"/>
																		<a id="loginForm:refreshValidateCode" href="#" onclick="return OM.ESI('loginForm','','loginForm:refreshValidateCode','',true);" style="cursor: pointer; text-decoration: underline; color: blue; width: 80px; margin-left: 10px; color: #438DBD;">刷新验证码</a>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr><td colspan="1" rowspan="1"></td>
														<td class="vbErrorDiv" colspan="1" rowspan="1" style="height: 18px;">请输入验证码。</td>
													</tr>
													<tr><td colspan="1" rowspan="1"></td>
														<td colspan="1" rowspan="1">
															<table id="loginForm:j_id35">
																<tr><td colspan="1" rowspan="1"><span id="loginForm:loginBtn"></span></td>
																	<td colspan="1" rowspan="1"><label id="loginForm:tips" style="color:red">hello</label></td>
																</tr>
															</table>
														</td>
													</tr>
												</tbody>
											</table>

										</div>
										<span id="loginForm_none"></span>
									</form>
								</td>
							</tr>
						</tbody>
					</table>

				</div>
			</div>
			
			<div id="j_id38" style="width:100%;height:100%;">
				<div class="footer" style="height:25px;width:100%;height:100%;position:absolute; left:0; bottom:0;margin:0px;" align="center">
					<a href="http://www.operamasks.org" target="_blank">OperaMasks首页</a>
					<span>|</span>
					<a href="http://www.operamasks.org/demo" target="_blank">OperaMasks示例中心</a>
					<span>|</span>
					<a href="http://www.operamasks.org/forum/" target="_blank">OperaMasks社区</a>
				</div>
			</div>
		</div>
	</body>
</html>
