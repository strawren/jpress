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
            $(document).ready(function() {
                $("#editLinkBtn").on("click",function(){
                	var visible = $("#visibleCheck").attr("checked");
                	if(visible=='checked'){
                		$("#visible").val("clse");
                	}else{
                		$("#visible").val("open");
                	}
                	if (checkForm()) {
                		$("#editLinkForm").submit();
					}
                });
                
            });
      
            //表单验证
            function checkForm(){
                var name = $.trim($("#editLinkForm").find("#name").val());
                var url = $.trim($("#editLinkForm").find("#url").val());
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
                            <h2><i class="icofont-retweet"></i> 链接 <small>电视商务商户管理系统 &rsaquo; 链接管理模块&rsaquo; 新建链接</small></h2>
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
                                            <span><i class="icofont-plus-sign"></i>&nbsp;编辑链接</span>
                                        </div>
                                        <div class="box-body">
                                            <form id="editLinkForm" method="post" action="${ctx}/cms/link/update.html">
                                            <div class="control-group">
                                                    <label class="control-label">名称</label>
                                                    <div class="controls">
                                                        <input type="hidden" name="id" value="${link.id}"/>
                                                        <input type="hidden" name="visible" id="visible" value="${link.visible}"/>
                                                        <input type="hidden" name="lastUpdTime" value="<fmt:formatDate value="${link.lastUpdTime}" type="both"/>">
                                                        <input type="text" class="input-block-level grd-white" name="name" id="name" value="${link.name}"/><br/>
                                                    </div>
                                            </div>
                                            <div class="control-group">
                                                    <label class="control-label">Web地址</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" name="url" id="url" value="${link.url}"/><br/>
                                                    </div>
                                            </div>
                                            <div class="control-group">
                                                    <label class="control-label">描述</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" name="alt" value="${link.alt}"/><br/>
                                                    </div>
                                            </div>
                                            <div class="control-group">
                                                    <label class="control-label">链接分类目录</label>
                                                    <div class="controls">
                                                        <select name="termTaxonomyId">
                                                            <c:forEach items="${linkCategories}" var="linkCategory">
                                                            <option value="${linkCategory.termTaxonomyId}" <c:if test="${link.termTaxonomyId==linkCategory.termTaxonomyId}">selected="selected"</c:if> >${linkCategory.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                            </div>
                                            <div class="control-group">
                                                    <label class="control-label">打开方式</label>
                                                    <div class="controls">
                                                        <input type="radio" class="grd-white" name="target" <c:if test="${link.target=='_blank'}">checked="checked"</c:if> value="_blank"/>_blank — 新窗口或新标签。<br/>
                                                        <input type="radio" class="grd-white" name="target" <c:if test="${link.target=='_top'}">checked="checked"</c:if> value="_top"/>_top — 不包含框架的当前窗口或标签。<br/>
                                                        <input type="radio" class="grd-white" name="target" <c:if test="${link.target=='_none'}">checked="checked"</c:if> value="_none"/>_none — 同一窗口或标签。<br/>
                                                    </div>
                                            </div>
                                        </form>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="span3">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span>保存</span>
                                        </div>
                                        <div class="box-body">
                                              <div class="control-group">
                                                    <input type="checkbox" name="visible" id="visibleCheck" <c:if test="${link.visible=='clse'}">checked="checked"</c:if> ><span>将这个链接设为私密链接</span><br>
                                              </div>
                                              <div class="control-group">
                                                  <a></a><a class="btn tbn-small btn-info" id="editLinkBtn">保存链接</a> </div>
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

