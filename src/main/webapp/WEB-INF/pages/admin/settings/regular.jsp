<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>东方亿付电视商务系统--设置管理--全局参数</title>
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
//             $(document).ready(function() {
//                 $("#updateRegular").on("click",function(){
//                     if (checkForm()) {
//                         $("#regularForm").submit();
//                     }
//                 });
//             });
      
//             表单验证
//             function checkForm(){
//                 var emailAdress = $.trim($("#regularForm").find("#emailAdress").val());
//                 if (!emailAdress || emailAdress.length==0) {
//                     $("#formMsg").text("电子邮件地址不能为空").show();
//                     return false;
//                 }
                
//                 $("#formMsg").hide();
//                 return true;
//             }
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
                            <h2><i class="icofont-retweet"></i> 设置 <small>电视商务商户管理系统 &rsaquo; 设置管理模块&rsaquo; 全局参数设置</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            
                            <!-- tab resume content -->
                            <div class="alert alert-error" <c:if test="${empty msg}">style="display: none;"</c:if> id="formMsg">
                            ${msg}
                            </div>
                            <div class="alert alert-sucess" <c:if test="${empty message}">style="display: none;"</c:if> id="message">
                                <p><strong>
                                    ${message }
                                </strong></p>
                            </div>
                            <div class="row-fluid">
                                
                                <div class="span9">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icon-globe"></i>&nbsp;全局参数设置</span>
                                        </div>
                                        <div class="box-body">
                                            <form id="regularForm" method="post" action="${ctx }/cms/settings/updateRegularConfig.html?menu=menu_settings">
												<table class="form-table">
												    <c:forEach items="${optionsList }" var="option" >
												        <tr valign="top">
	                                                    <th scope="row"><label for="${option.code }">${option.name }</label></th>
	                                                    <td>
	                                                       <input id="optionId" type="hidden" name="optionId" value="${option.id }" />
	                                                       <input name="${option.code }" type="text" class="regular-text ltr" id="${option.code }" value="${option.value }"  />
	                                                       <c:if test="${option.miscDesc != null }">
	                                                           <p class="description">${option.miscDesc }</p>
	                                                       </c:if>
	                                                    </td>
	                                                    </tr>
												    </c:forEach>
												
<!-- 													<tr valign="top"> -->
<!-- 													<th scope="row"><label for="isNeedAudit">发布是否需要审核</label></th> -->
<!-- 													<td> -->
<!-- 													   <select name="isNeedAudit" id="isNeedAudit"> -->
<!-- 														    <option value='0' selected="selected" >否</option> -->
<!-- 														    <option value='1' >是</option> -->
<!-- 													   </select> -->
<!-- 													</td> -->
<!-- 													</tr> -->
<!--                                                     <tr valign="top"> -->
<!-- 													<th scope="row"><label for="createUserFlag">创建新用户</label></th> -->
<!-- 													<td>  -->
<!-- 													    <input name="createUserFlag" type="checkbox" id="createUserFlag" value="1"  />可以创建新用户 -->
<!-- 													</td> -->
<!-- 													</tr> -->
<!-- 													<tr valign="top"> -->
<!--                                                     <th scope="row"><label for="defaultNewUserLevel">新用户默认级别</label></th> -->
<!--                                                     <td>  -->
<!--                                                         <select id="defaultNewUserLevel" data-form="select2" style="width:200px"> -->
<!--                                                             <option value="0" selected="selected" >普通用户 </option> -->
<!--                                                             <option value="1" >审核用户 </option> -->
<!--                                                         </select> -->
<!--                                                     </td> -->
<!--                                                     </tr> -->
<!--                                                     <tr valign="top"> -->
<!-- 														<th scope="row"><label for="emailAdress">电子邮件地址 </label></th> -->
<!-- 														<td> -->
<%-- 														    <input name="emailAdress" type="text" id="emailAdress" value="${email }" class="regular-text ltr" /> --%>
<!-- 														    <p class="description">这个电子邮件地址仅为了管理方便而索要，例如新注册用户通知。</p> -->
<!-- 														</td> -->
<!-- 													</tr> -->
												</table>
												<div class="form-actions">
	                                                &nbsp;&nbsp;&nbsp;
	                                                <button type="submit" class="btn btn-primary" id="updateRegular" >保存修改</button>
                                                </div>
											</form>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="span3">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icon-share"></i>说明</span>
                                        </div>
                                        <div class="box-body">
                                            <div>
                                                <span class="label label-info">！注意</span>
                                            </div>
                                            <p>
                                            <div>
                                                <i class="icon-map-marker"></i>&nbsp;参数将对全局起作用。
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
