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
        
          //选择父级页面相关功能
            var setting = {
                    view: {
                        dblClickExpand: false
                    },
                    data: {
                    	simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId",
                            rootPId: 0
                        },
                        key:{
                        	name:"name",
                        	title:""
                        }
                    },
                    callback: {
                        onClick: onClick
                    }
                };

                var parentPageNodes = ${parentList};

                function onClick(e, treeId, treeNode) {
                    var zTree = $.fn.zTree.getZTreeObj("parentPageTree"),
                    node = zTree.getSelectedNodes()[0];
                    $("#parentPageInput").val(node.name);
                    $("#parentId").val(node.id);
                    $("#menuContent").fadeOut("fast");
                }

                function showMenu() {
                    var cityObj = $("#parentPageInput");
                    var cityOffset = $("#parentPageInput").offset();
                    var menuContentDivOffset = $("#menuContentDiv").parent().offset();
                    $("#menuContent").css({left:cityOffset.left-menuContentDivOffset.left + "px", top:cityOffset.top + cityObj.outerHeight()-menuContentDivOffset.top + "px"}).slideDown("fast");
                    $("body").bind("mousedown", onBodyDown);
                }
                function hideMenu() {
                    $("#menuContent").fadeOut("fast");
                    $("body").unbind("mousedown", onBodyDown);
                }
                function onBodyDown(event) {
                    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                        hideMenu();
                    }
                }
                
                $(function(){
                        $.fn.zTree.init($("#parentPageTree"), setting, parentPageNodes).expandAll(true);;
                });
                
              	//表单验证
                function checkForm(){
                	var name = $.trim($("#name").val());
                	var slug = $.trim($("#slug").val());
                	if (!name||name.length==0) {
                		$("#formMsg").text("分类名称不能为空").show();
						return false;
					}

                	if (!slug||slug.length==0) {
                		$("#formMsg").text("分类别名不能为空").show();
                        return false;
                    }
                	
                	$("#formMsg").hide();
                	return true;
                }
              	
              function submitForm(){
            	  if(checkForm()){
            		  var slug = $("#slug").val();
            		  $.ajax({
                          type: "POST",
                          url: '${ctx}/cms/taxonomy/check_slug.html',  
                          data: {"slug":slug},
                          datatype : "json",
                          success: function(data){
                              if(data){
                            	$("#termForm").submit();
                              }else{
                            	  $("#formMsg").text("分类别名已经存在!!").show();
                              }
                          }
                      });	
            		  
            	  }
            	  
              }
              
              function delConfirm(termId){
              	   if(!confirm("数据删除将不可恢复，确认删除吗?")){
                         return 
                     }
                 	   window.location.href="${ctx }/cms/taxonomy/term_tax_delete.html?termId=" + termId;
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
                            <h2><i class="icofont-retweet"></i> 内容 <small>电视商务商户管理系统 &rsaquo; 内容管理模块&rsaquo; 分类目录</small></h2>
                        </div><!-- /content-header -->
                        <!-- content-body -->
                        <div class="content-body">
                            <div class="alert alert-error" style="display: none;" id="formMsg">
                            </div>
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
                            <form action="${ctx}/cms/taxonomy/addTaxonomy.html" name="cat" method="post" id="termForm">
                                
                                <div class="span6">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icofont-plus-sign"></i>&nbsp;添加新分类目录</span>
                                        </div>
                                        <div class="box-body">
                                                <div class="control-group">
                                                    <label class="control-label">名称</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" id="name" name="name" value="${term.name }"/><br/>
                                                                                                                                                      <!--   这将是它在站点上显示的名字。 -->
                                                    </div>
                                                </div>
                                                <p>
                                                <div class="control-group">
                                                    <label class="control-label">别名</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" id="slug" name="slug" value="${term.slug }" /><br/>
                                                        <!-- “别名”是在URL中使用的别称，它可以令URL更美观。通常使用小写，只能包含字母，数字和连字符（-）。 -->
                                                    </div>
                                                </div>
                                                 <p>
                                                 <div class="control-group" id="menuContentDiv">  
                                                 <label class="control-label">父级</label>                             
                                                    <div class="controls">
                                                        <input id="parentPageInput" type="text" readonly value="" style="width:120px;" id="menuBtn" href="#" onclick="showMenu(); return false;"/><br/>
                                                        <input id="parentId" name="parentId" type="hidden" value="" />
                                                                                                                                                        <!-- 分类目录和标签不同，它可以有层级关系。您可以有一个“音乐”分类目录，在这个目录下可以有叫做“流行”和“古典”的子目录。 -->
                                                        <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
                                                            <ul id="parentPageTree" class="ztree" style="margin-top:0; width:120px;"></ul>
                                                        </div>
                                                    </div>
                                                    
                                              </div>
                                                <div class="control-group">
                                                    <label class="control-label">描述</label>
                                                    <div class="controls">
                                                        <textarea id="inputEditorSimple" name="miscDesc" class="span10" rows="6" data-form="wysihtml5" placeholder="">${term.miscDesc }</textarea><br/>
                                                                                                                                                     <!--  描述只会在一部分主题中显示。 -->
                                                    </div>
                                                </div>
                                                <p>
                                                <div class="form-actions">
                                                    <input type="reset" class="btn" value="重置" />
                                                    <input type="button" onclick="submitForm()" class="btn btn-primary" value="添加新分类目录" />
                                                </div>
                                        </div>
                                    </div>
                                </div>
                                </form>
                                
                                <div class="span6">
                                    <div class="span12">
                                    <div class="box corner-all">
                                        <div class="box-header grd-white corner-top">
                                            <span><i class="icon-list"></i>&nbsp;分类目录</span>
                                        </div>
                                        <div>
                                            <table id="datatables" class="table table-bordered table-striped responsive">
                                                <thead>
                                                    <tr>
                                                        <th>名称</th>
                                                        <th>图像描述</th>
                                                        <th>别名</th>
                                                        <th>文章</th>
                                                        <th>操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                     <c:forEach items="${viewList }" var="term">
                                                        <tr class="odd gradeX">
                                                            <td>${term.name }</td>
                                                            <td>${term.miscDesc }</td>
                                                            <td>${term.slug }</td>
                                                            <td class="center">${term.postCount }</td>
                                                            <td>
                                                                <div class="btn-group">
                                                                <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">操作<span class="caret"></span></button>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="${ctx }/cms/taxonomy/edit_term_show.html?termId=${term.id }">编辑</a></li>
                                                                    <li><a href="#" onclick="delConfirm(${term.id })">删除</a></li>
                                                                     <li><a href="${ctx }//cms/term_meta/list_meta.html?termId=${term.id }">属性</a></li>
                                                                    <li><a href="${ctx }/cms/taxonomy/term_view.html?termId=${term.id }">查看</a></li>
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

