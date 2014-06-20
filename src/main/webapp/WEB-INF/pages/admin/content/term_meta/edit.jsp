<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>东方亿付电视商务系统--内容管理--仪表盘</title>
        <jsp:include page="/common/meta.jsp"></jsp:include>
        <!-- styles -->
        <jsp:include page="/common/style.jsp"></jsp:include>
        <jsp:include page="/common/scripts.jsp"></jsp:include>
        <script type="text/javascript">

        
        
            var save_status = "${save_status }";
            var hideTime;
            $(document).ready(function() {
                if(save_status != null){
                    $("#" + save_status + "Alert").show();
                }
                var hideTime = setTimeout("hideAlert()",3000);
                
            });
        
            function hideAlert(){
                $("#" + save_status + "Alert").hide();
                window.clearInterval(hideTime);
            } 
        
			$(document).ready(function(){
            	
            	$("#inputSelect").on("change",function(){
                    var metaType = $("#inputSelect option:selected");
                    if(metaType.val() == "term"){
                        $("#valuediv").show();
                    }
                    if(metaType.val() == "post"){
                        $("#valuediv").hide();
                    }
                });
            	
            });
                
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
                            <h2><i class="icofont-retweet"></i> 内容 <small>电视商务商户管理系统 &rsaquo; 内容管理模块&rsaquo; 分类目录&rsaquo;属性编辑</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            <div class="row-fluid">
                                <div class="span12">
                                    <div id="element" class="row-fluid">
                                        <!--span-->
                                        <div class="span12">
                                            <!--box-->
                                            <div class="box corner">
                                                <!--box header-->
                                                <div class="box-header grd-white color-silver-dark corner-top">
                                                    <span><i class="icon-edit"></i>&nbsp;编辑属性</span>
                                                </div><!--/box header-->
                                                <!--box body-->
                                                <div class="box-body">
                                                    <form class="form-horizontal" action="${ctx }/cms/term_meta/edit.html" method="post">
                                                        <input name="id" type="hidden" value="${termMeta.id }"/>
                                                         <input name="createTime" type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${meta.createTime}"/>" />
                                                        <input name="createOperId" type="hidden" value="${termMeta.createOperId }"/>
                                                        <input name="createOperName" type="hidden" value="${termMeta.createOperName }"/>
                                                        <input name="lastUpdTime" type="hidden" value="<fmt:formatDate value='${termMeta.lastUpdTime }' pattern='yyyy-MM-dd HH:mm:ss' />"/>
                                                        <input name="lastUpdOperId" type="hidden" value="${termMeta.lastUpdOperId }"/>
                                                        <input name="lastUpdOperName" type="hidden" value="${termMeta.lastUpdOperName }"/>
                                                        <div class="control-group">
                                                            <label class="control-label">目录/内容名称</label>
                                                            <div class="controls">
                                                                <input name="termId" type="hidden" value="${termMeta.termId }" />
                                                                <input type="text" class="input-block-level grd-white span4" value="${termName }" disabled="disabled"/><br/>
                                                            </div>
                                                        </div>
                                                        <p>
                                                        <div class="control-group">
                                                            <label class="control-label">显示名称</label>
                                                            <div class="controls">
                                                                <input type="text" class="input-block-level grd-white span4" name="name" value="${termMeta.name }"/><br/>
                                                            </div>
                                                        </div>
                                                        <p>
                                                        <div class="control-group">
                                                            <label class="control-label">key</label>
                                                            <div class="controls">
                                                                <input type="text" class="input-block-level grd-white span4" name="key" value="${termMeta.key }" /><br/>
                                                            </div>
                                                        </div>
                                                        <c:if test="${metaType == '' }">
                                                            <div class="control-group">
                                                            <label class="control-label" for="inputSelect">属性类型</label>
                                                            <div class="controls">
                                                                <c:choose>
                                                                    <c:when test="${termMeta.metaType == 'term' }">
                                                                        <select id="inputSelect" name="metaType" data-form="select2" style="width:200px" data-placeholder="">
                                                                            <option value=""></option>
                                                                            <option value="term" selected="selected">目录</option>
                                                                            <option value="post">内容</option>
                                                                        </select>
                                                                    </c:when>
                                                                    <c:when test="${termMeta.metaType == 'post' }">
                                                                        <select id="inputSelect" name="metaType" data-form="select2" style="width:200px" data-placeholder="">
                                                                            <option value=""></option>
                                                                            <option value="term">目录</option>
                                                                            <option value="post" selected="selected">内容</option>
                                                                        </select>
                                                                    </c:when>
                                                                    <c:otherwise> 
                                                                        <select id="inputSelect" name="metaType" data-form="select2" style="width:200px" data-placeholder="">
                                                                            <option value=""></option>
                                                                            <option value="term">目录</option>
                                                                            <option value="post">内容</option>
                                                                        </select>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            
                                                            </div>
                                                        </div>
                                                        </c:if>
                                                        
                                                        <c:if test="${termMeta.metaType == 'post' }">
                                                            <div class="control-group" id="valuediv" style="display: none;">
                                                        </c:if>
                                                        <c:if test="${termMeta.metaType == 'term' }">
                                                            <div class="control-group" id="valuediv">
                                                        </c:if>
                                                                <label class="control-label">值</label>
                                                                <div class="controls">
                                                                    <input type="text" class="input-block-level grd-white span4" id="termMetaValue" name="value" value="${termMeta.value }" /><br/>
                                                                </div>
                                                            </div>
                                                        
                                                
                                                        <div class="control-group">
                                                            <label class="control-label" for="inputSelect">值格式</label>
                                                            <div class="controls">
                                                                <input type="text" class="input-block-level grd-white span4" name="valueFormat" value="${termMeta.valueFormat }"/><br/>
                                                            </div>
                                                        </div>
                                                        <div class="control-group">
                                                            <label class="control-label">值类型</label>
                                                            <div class="controls">
                                                                <c:choose>
                                                                    <c:when test="${termMeta.valueType == 'num' }">
                                                                        <select name="valueType">
                                                                            <option value=""></option>
                                                                            <option value="num" selected="selected">数字(0.00)</option>
                                                                            <option value="date">日期(yyyy-MM-dd)</option>
                                                                            <option value="str">文本</option>
                                                                            <option value="file">文件</option>
                                                                        </select>
                                                                    </c:when>
                                                                    <c:when test="${termMeta.valueType == 'date' }">
                                                                        <select name="valueType">
                                                                            <option value=""></option>
                                                                            <option value="num" >数字(0.00)</option>
                                                                            <option value="date" selected="selected">日期(yyyy-MM-dd)</option>
                                                                            <option value="str">文本</option>
                                                                            <option value="file">文件</option>
                                                                        </select>
                                                                    </c:when>
                                                                    <c:when test="${termMeta.valueType == 'str' }">
                                                                        <select name="valueType">
                                                                            <option value=""></option>
                                                                            <option value="num" >数字(0.00)</option>
                                                                            <option value="date" >日期(yyyy-MM-dd)</option>
                                                                            <option value="str" selected="selected">文本</option>
                                                                            <option value="file">文件</option>
                                                                        </select>
                                                                    </c:when>
                                                                    <c:when test="${termMeta.valueType == 'file' }">
                                                                        <select name="valueType">
                                                                            <option value=""></option>
                                                                            <option value="num" >数字(0.00)</option>
                                                                            <option value="date" >日期(yyyy-MM-dd)</option>
                                                                            <option value="str" >文本</option>
                                                                            <option value="file" selected="selected">文件</option>
                                                                        </select>
                                                                    </c:when>
                                                                    <c:otherwise> 
                                                                        <select name="valueType">
                                                                            <option value=""></option>
                                                                            <option value="num" >数字(0.00)</option>
                                                                            <option value="date" >日期(yyyy-MM-dd)</option>
                                                                            <option value="str" >文本</option>
                                                                            <option value="file" >文件</option>
                                                                        </select>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                        <div class="control-group">
                                                            <label class="control-label">描述</label>
                                                            <div class="controls">
                                                                <textarea id="inputEditorSimple" name="miscDesc" class="span6" rows="6" data-form="wysihtml5" placeholder="">${termMeta.miscDesc }</textarea><br/>
                                                            </div>
                                                        </div>
                                                        
                                                        
                                                        <div class="form-actions">
                                                            <button type="submit" class="btn btn-primary">保存修改</button>
                                                            <button type="button" class="btn" onclick="javascript:history.go(-1);">返回</button>
                                                        </div>
                                                    </form>
                                                
                                                
                                                </div><!--/box body-->
                                            </div><!--/box-->
                                        </div><!--/span--> 
                                    </div><!--/element-->
                                    
                                    
                                </div><!--/span-->
                            </div><!--/row-fluid-->
                        </div><!--/content-body -->
                            
                            <!--/dashboar-->
                        </div><!--/content-body -->
                    </div><!-- /span content -->
                </div><!-- /span side-right -->
        </section>

        <jsp:include page="/common/footer.jsp"></jsp:include>
        
    </body>
</html>

