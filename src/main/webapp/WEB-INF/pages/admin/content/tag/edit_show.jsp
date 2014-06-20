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
         	        	var imgUrl = "${siteUrl}${ctx}/cms/media/getMedia.html?guid="+post.guid;
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
        		  $("#tagForm").submit();
        	  
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
                            <div class="row-fluid">
                                <div class="span12">
                                    <div id="element" class="row-fluid">
                                        <!--span-->
                                        <div class="span12">
                                            <!--box-->
                                            <div class="box corner">
                                                <!--box header-->
                                                <div class="box-header grd-white color-silver-dark corner-top">
                                                    <span><i class="icon-edit"></i>&nbsp;编辑标签</span>
                                                </div><!--/box header-->
                                                <!--box body-->
                                                <div class="box-body">
                                                	<form class="form-horizontal" action="${ctx }/cms/tag/tag_eidt.html" method="post" id="tagForm">
                                                        <input type="hidden" name="postId" id="postId" value="${imgPost.id }" /><!-- 特色图Id -->
                                                        <input name="id" type="hidden" value="${term.id }"/>
                                                        <input name="createTime" type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${term.createTime}"/>" />
                                                        <input name="createOperId" type="hidden" value="${term.createOperId }"/>
                                                        <input name="createOperName" type="hidden" value="${term.createOperName }"/>
                                                        <input name="lastUpdTime" type="hidden" value="<fmt:formatDate value='${term.lastUpdTime }' type="time" pattern='yyyy-MM-dd HH:mm:ss' />"/>
                                                        <input name="lastUpdOperId" type="hidden" value="${term.lastUpdOperId }"/>
                                                        <input name="lastUpdOperName" type="hidden" value="${term.lastUpdOperName }"/>
                                                        <div class="control-group">
                                                            <label class="control-label" for="inputAuto">名称</label>
                                                            <div class="controls">
                                                            <input type="text" class="grd-white" id="name" name="name" value="${term.name }"/><br/>
                                                                                                                                                                    这将是它在站点上显示的名字。
                                                            </div>
                                                        </div>
                                                        <p>
                                                        <div class="control-group">
                                                            <label class="control-label">别名</label>
                                                            <div class="controls">
                                                                <input type="text" class="grd-white" id="slug" name="slug" value="${term.slug }" /><br/>
                                                                “别名”是在URL中使用的别称，它可以令URL更美观。通常使用小写，只能包含字母，数字和连字符（-）。
                                                            </div>
                                                        </div>
                                                        
                                                        <div class="control-group">
                                                            <label class="control-label">组名</label>
                                                            <div class="controls">
                                                                <input type="text" class="grd-white" id="groupName" name="groupName" value="${term.groupName }" /><br/>
                                                                                                                                                                           将同共同组名的菜单分为一组(可以为空)
                                                            </div>
                                                        </div>
                                                        
                                                        <p>
                                                        <c:if test="${imgPost != null }">
                                                            <div class="control-group">
                                                                <label class="control-label">特色图像</label>
                                                                <div class="controls">
                                                                    <img class="img-polaroid" id="imageView"  style="width: 80px;height: 60px;" alt="${imgPost.title}" src="${ctx}/cms/media/getMedia.html?guid=${imgPost.guid}">
                                                                    <a href="#uploadAttachmentModal" role="button" class="btn btn-small btn-info" data-toggle="modal" data-backdrop="static" id="addMediaBtn">更改</a>
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${imgPost == null }">
                                                            <div class="control-group">
                                                                <div class="controls">
                                                                    <img class='img-polaroid' style="width: 80px;height: 60px;display: none;" alt='${post.title}' id="imageView" src='' >
                                                                    <a href="#uploadAttachmentModal" role="button" class="btn btn-small btn-info" data-toggle="modal" data-backdrop="static" id="addMediaBtn">点击添加特色图像</a>
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                        <div class="control-group">
                                                            <label class="control-label">描述</label>
                                                            <div class="controls">
                                                                <textarea id="inputEditorSimple" name="miscDesc" class="span10" rows="6" data-form="wysihtml5" placeholder="">${term.miscDesc }</textarea><br/>
                                                                                                                                                              描述只会在一部分主题中显示。
                                                            </div>
                                                        </div>
                                                        
                                                        
                                                        <div class="form-actions">
                                                            <button type="button" onclick="submitForm()" class="btn btn-primary">保存修改</button>
                                                            <button type="button" class="btn" onclick="javascript:history.go(-1);">退出</button>
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

