<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>东方亿付电视商务系统--内容管理--仪表盘</title>
        <jsp:include page="/common/meta.jsp"></jsp:include>
        <link rel="stylesheet" type="text/css" href="${ctx}/scripts/uploadify/uploadify.css" />
        <!-- styles -->
        <jsp:include page="/common/style.jsp"></jsp:include>
        <jsp:include page="/common/scripts.jsp"></jsp:include>
        <script type="text/javascript" src="${ctx}/scripts/uploadify/jquery.uploadify.min.js"></script>
        <script type="text/javascript">

        
        
            var save_status = "${save_status }";
    		var hideTime;
            $(document).ready(function() {
                if(save_status != null){
                    $("#" + save_status + "Alert").show();
                }
                var hideTime = setTimeout("hideAlert()",3000);
                
                
                
         	   $('#selectFileBtn').uploadify({
         		    'multi':true,
         	        'swf':'${ctx}/scripts/uploadify/uploadify.swf',
         	        'uploader':'${ctx}/cms/media/upload.html',
         	        'buttonClass':'btn btn-small btn-info',
         	        'buttonText':'选择文件',
         	        'fileSizeLimit' : '200MB',
         	        'fileObjName' : 'uploadFile',
         	        'onUploadSuccess' : function(file, data, response) {
         	        	//获取上传文件的GUID
        	            var post = $.parseJSON(data);
         	        	$("#postId").val(post.id);
         	        	var imgUrl = "${mediaUrl}"+post.guid;
         	        	$("#imageView").attr("src",imgUrl).attr("alt",post.title).show();
         	        	$("#addMediaBtn").text("更改");
         	        }
         	    });
         	   
            });
        
            function hideAlert(){
            	$("#" + save_status + "Alert").hide();
            	window.clearInterval(hideTime);
            } 
            
          //表单验证
            function checkForm(){
            	var name = $.trim($("#name").val());
            	var slug = $.trim($("#slug").val());
            	if (!name||name.length==0) {
            		$("#formMsg").text("标签名称不能为空").show();
					return false;
				}

            	if (!slug||slug.length==0) {
            		$("#formMsg").text("标签别名不能为空").show();
                    return false;
                }
            	
            	$("#formMsg").hide();
            	return true;
            }
          
          function submitForm(){
        	  if(checkForm())
        		  var slug = $("#slug").val();
    		  $.ajax({
                  type: "POST",
                  url: '${ctx}/cms/tag/check_slug.html',  
                  data: {"slug":slug},
                  datatype : "json",
                  success: function(data){
                      if(data){
                    	  $("#tagForm").submit(); 
                      }else{
                    	  $("#formMsg").text("标签别名已经存在!!").show();
                      }
                  }
              });	
        		  
        	  
          }
          
          function delConfirm(termId){
         	   if(!confirm("数据删除将不可恢复，确认删除吗?")){
                    return 
                }
            	window.location.href="${ctx }/cms/tag/tag_delete.html?tagId=" + termId
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
                            <h2><i class="icofont-retweet"></i> 内容 <small>电视商务商户管理系统 &rsaquo; 内容管理模块&rsaquo; 标签</small></h2>
                        </div><!-- /content-header -->
                        <!-- content-body -->
                        <div class="content-body">
                            <div class="alert alert-error" style="display: none;" id="formMsg">
                            </div>
                            <div class="alert alert-error" id="failAlert" hidden="none">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>提示：</strong> 标签保存失败！！
                            </div>
                            <div class="alert alert-success" id="succAlert" hidden="none">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>提示：</strong> 标签保存成功！！
                            </div>
                            <!-- tab resume content -->
                            <div class="row-fluid">
                            <form action="${ctx}/cms/tag/tag_add.html" name="cat" method="post" id="tagForm">
                                <div class="span6">
                                <input type="hidden" name="postId" id="postId" value="${post.id }" /><!-- 特色图Id -->
                                    <div class="box corner-all " >
                                        <div class="box-header corner-top grd-white" >
                                            <span><i class="icofont-plus-sign"></i>&nbsp;添加新标签</span>
                                        </div>
                                        <div class="box-body">
                                                <div class="control-group">
                                                    <label class="control-label">名称</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" name="name" id="name" value="${term.name }"/><br/>
                                                                                                                                                        这将是它在站点上显示的名字。
                                                    </div>
                                                </div>
                                                <p>
                                                <div class="control-group">
                                                    <label class="control-label">别名</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" name="slug" id="slug" value="${term.slug }" /><br/>
                                                        “别名”是在URL中使用的别称，它可以令URL更美观。通常使用小写，只能包含字母，数字和连字符（-）。
                                                    </div>
                                                </div>
                                                
                                                <div class="control-group">
                                                    <label class="control-label">组名</label>
                                                    <div class="controls">
                                                        <input type="text" class="input-block-level grd-white" name="groupName" id="groupName" value="${term.groupName }" /><br/>
                                                                                                                                                      将同共同组名的菜单分为一组(可以为空)
                                                    </div>
                                                </div>
                                                
                                               <img class='img-polaroid' style="width: 80px;height: 60px;display: none;" alt='${post.title}' id="imageView" src='' >
                                                
                                                
                                                <!-- Button to trigger modal -->
                                                <a href="#uploadAttachmentModal" role="button" class="btn btn-small btn-info" data-toggle="modal" data-backdrop="static" id="addMediaBtn">点击添加特色图像</a>
                                                <div class="control-group">
                                                    <label class="control-label">描述</label>
                                                    <div class="controls">
                                                        <textarea id="inputEditorSimple" name="miscDesc" class="span10" rows="6" data-form="wysihtml5" placeholder="">${term.miscDesc }</textarea><br/>
                                                                                                                                                      描述只会在一部分主题中显示。
                                                    </div>
                                                </div>
                                                <p>
                                                <div class="form-actions">
                                                    <input type="reset" class="btn" value="重置" />
                                                    <input type="button" onclick="submitForm()" class="btn btn-primary" value="添加新标签" />
                                                </div>
                                        </div>
                                    </div>
                                </div>
                                </form>
                                
                                <div class="span6">
                                    <div class="span12">
                                    <div class="box corner-all">
                                        <div class="box-header grd-white corner-top">
                                            <span><i class="icon-list"></i>&nbsp;标签</span>
                                        </div>
                                        <div>
                                            <table id="datatables" class="table table-bordered table-striped responsive">
                                                <thead>
                                                    <tr>
                                                        <th>序号</th>
                                                        <th>名称</th>
                                                        <th>图像描述</th>
                                                        <th>别名</th>
                                                        <th>组名</th>
                                                        <th>文章</th>
                                                        <th>操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                     <c:forEach items="${tagList }" var="term" varStatus="x">
                                                        <tr class="odd gradeX">
                                                            <td>${x.index + 1}</td>
                                                            <td>${term.name }</td>
                                                            <td>${term.miscDesc }</td>
                                                            <td>${term.slug }</td>
                                                            <td>${term.groupName }</td>
                                                            <td class="center">${term.postCount }</td>
                                                            <td>
                                                                <div class="btn-group">
                                                                <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">操作<span class="caret"></span></button>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="${ctx }/cms/tag/edit_tag_show.html?menu=menu_content&tagId=${term.id }">编辑</a></li>
                                                                    <li><a href="#" onclick="delConfirm(${term.id })">删除</a></li>
                                                                    <%-- <li><a href="${ctx }/cms/taxonomy/term_view.html?tagId=${term.id }">查看</a></li> --%>
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
        <!-- Modal -->
        <div id="uploadAttachmentModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">上传媒体文件</h3>
          </div>
          <div class="modal-body">
            <p><button class="btn btn-primary" id="selectFileBtn">上传</button></p>
          </div>
          <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true" id="btnCancel">关闭</button>
            <!-- <button class="btn btn-primary" id="doUploadBtn">上传</button> -->
          </div>
        </div> 
    </body>
</html>

