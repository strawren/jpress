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
            
            function delConfirm(termId, metaId){
           	   if(!confirm("数据删除将不可恢复，确认删除吗?")){
                      return 
                  }
              	   window.location.href="${ctx }/cms/term_meta/delete.html?termid=" + termId + "&metaId=" + metaId;
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
                            <h2><i class="icofont-retweet"></i> 内容 <small>电视商务商户管理系统 &rsaquo; 内容管理模块&rsaquo; 分类目录&rsaquo;属性</small></h2>
                        </div><!-- /content-header -->
                        <!-- content-body -->
                        <div class="content-body">
                            <div class="alert alert-error" id="failAlert" hidden="none">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>提示：</strong> 分类目录保存失败！！
                            </div>
                            <div class="alert alert-success" id="succAlert" hidden="none">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>提示：</strong> 分类目录保存成功！！
                            </div>
                            <!-- tab resume content -->
                            <div class="row-fluid">
                            <form action="${ctx}/cms/term_meta/meta_add.html" name="meta" method="post">
                                <div class="span6">
                                <input name="status" type="hidden" value="${meta.status }" />
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icofont-plus-sign"></i>&nbsp;添加属性</span>
                                        </div>
                                        <div class="box-body">
                                                <div class="control-group">
                                                    <label class="control-label">目录/内容名称</label>
                                                    <div class="controls">
                                                        <input name="termId" type="hidden" value="${termId }" />
                                                        <input type="text" class="input-block-level grd-white" value="${termName }" disabled="disabled"/><br/>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label">显示名称</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" name="name" value="${meta.name }"/><br/>
                                                    </div>
                                                </div>
                                                <p>
                                                <div class="control-group">
                                                    <label class="control-label">key</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" name="key" value="${meta.key }" /><br/>
                                                    </div>
                                                </div>
                                                <c:if test="${metaType == null}">
                                                    <div class="control-group">
                                                        <label class="control-label" for="inputSelect">属性类型</label>
                                                        <div class="controls">
                                                            <select id="inputSelect" name="metaType" data-form="select2" style="width:200px" data-placeholder="">
                                                                <option value=""></option>
                                                                <option value="term">分类</option>
                                                                <option value="post">商品</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <c:if test="${metaType == 'post'}">
                                                    <input type="hidden" name="metaType" value="post" />
                                                </c:if>
                                                <div class="control-group" id="valuediv" style="display: none;">
                                                    <label class="control-label">值</label>
                                                    <div class="controls">
                                                        <input type="text"  class="input-block-level grd-white" name="value" value="${meta.value }" /><br/>
                                                    </div>
                                                </div>
                                                
                                                <div class="control-group">
                                                    <label class="control-label">值类型</label>
                                                    <div class="controls">
                                                        <select name="valueType">
                                                            <option value=""></option>
                                                            <option value="num">数字(0.00)</option>
                                                            <option value="date">日期(yyyy-MM-dd)</option>
                                                            <option value="str">文本</option>
                                                            <option value="file">文件</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                
                                                 <p>
                                                <div class="control-group">
                                                    <label class="control-label">描述</label>
                                                    <div class="controls">
                                                        <textarea id="inputEditorSimple" name="miscDesc" class="span10" rows="6" data-form="wysihtml5" placeholder="">${term.miscDesc }</textarea><br/>
                                                    </div>
                                                </div>
                                                <p>
                                                <div class="form-actions">
                                                    <input type="reset" class="btn" value="重置" />
                                                    <input type="submit" class="btn btn-primary" value="添加新分类目录" />
                                                </div>
                                        </div>
                                    </div>
                                </div>
                                </form>
                                
                                <div class="span6">
                                    <div class="span12">
                                    <div class="box corner-all">
                                        <div class="box-header grd-white corner-top">
                                            <span><i class="icon-list"></i>&nbsp;属性列表</span>
                                        </div>
                                        <div>
                                            <table id="datatables" class="table table-bordered table-striped responsive">
                                                <thead>
                                                    <tr>
                                                        <!-- <th>目录名称</th> -->
                                                        <th>序号</th>
                                                        <th>属性类型</th>
                                                        <th>显示名称</th>
                                                        <!-- <th>值类型</th>
                                                        <th>值格式</th> -->
                                                        <th>key</th>
                                                        <th>操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                     <c:forEach items="${termMetaList }" var="meta">
                                                        <tr class="odd gradeX">
                                                           <%--  <td>${termName }</td> --%>
                                                           <td>${meta.showOrder }</td>
                                                           <c:if test="${meta.metaType == 'post'}">
                                                                 <td>内容</td>
                                                           </c:if>
                                                           <c:if test="${meta.metaType == 'term'}">
                                                                 <td>分类</td>
                                                           </c:if>
                                                            <td>${meta.name }</td>
                                                            <%-- <td>${meta.valueType }</td>
                                                            <td>${meta.valueFormat }</td> --%>
                                                            <td>${meta.key }</td>
                                                            <td>
                                                                <div class="btn-group">
                                                                <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">操作<span class="caret"></span></button>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="${ctx }/cms/term_meta/edit_show.html?metaId=${meta.id }&metaType=${metaType}">编辑</a></li>
                                                                    <li><a href="#" onclick="delConfirm(${termId },${meta.id })">删除</a></li>
                                                                    <%-- <li><a href="${ctx }/cms/taxonomy/term_view.html?termId=${meta.id }">查看</a></li> --%>
                                                                </ul>
                                                                </div><!-- /btn-group -->
                                                            </td>
                                                           
                                                        </tr>
                                                    </c:forEach> 
                                                </tbody>
                                            </table>
                                            
                                        </div><!-- /box-body -->
                                    </div><!-- /box -->
                                </div><!-- /span -->
                            </div><!--/datatables-->
                                </div><!-- tab resume update -->
                                
                                
                            </div><!-- tab stat -->
                            
                            <!--/dashboar-->
                        </div><!--/content-body -->
                    </div><!-- /span content -->
                </div><!-- /span side-right -->
        </section>

        <jsp:include page="/common/footer.jsp"></jsp:include>
        
    </body>
</html>

