<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>东方亿付电视商务系统--内容管理--仪表盘</title>
        <jsp:include page="/common/meta.jsp"></jsp:include>
   		<link rel="stylesheet" type="text/css" href="${ctx}/scripts/uploadify/uploadify.css" />
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
        <script type="text/javascript" src="${ctx}/scripts/uploadify/jquery.uploadify.min.js"></script>
        <script type="text/javascript">
           $(function(){
        	   $("#searchBtn").on('click',function(){
                   $("#pageNumInput").val(1);
                   $("#pageSearchForm").submit();
               });
        	   
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
        	            //var post = $.parseJSON(data);
        	        }
        	    });
        	   
        	   $('#uploadAttachmentModal').on('hidden', function () {
        		   window.location.reload();
        		   });
        	   
        	   $("#checkAllPageBtn").on("click",function(){
                   var self = $(this);
                   var checked = self.attr("checked");
                   var pageTable = $("#pageTable");
                   if (checked) {
                       pageTable.find("input:checkbox").attr("checked",true);
                   }else{
                       pageTable.find("input:checkbox").attr("checked",false);
                   }
               });
        	   
        	   $("#recycleAllPageBtn").on("click",function(){
                   var pageTable = $("#pageTable");
                   var checkedPages = pageTable.find("input:checked");
                   
                   if (checkedPages.length==0) {
                       alert("请至少选择一项");
                       return;
                   }
                   
                   var pages = [];
                   for (var i = 0; i < checkedPages.length; i++) {
                       var cPage = checkedPages[i];
                       pages.push($(cPage).val());
                   }
                   var pages = pages.join(",");

                   removeMedia(pages);
               });
           });
           
           $(document).on("page.change",function(event,pagenum){
               $("#pageNumInput").val(pagenum);
               $("#pageSearchForm").submit();
          });
           
           function removeMedia(ids){
               if(!confirm("确定将这些文件删除?")){
                   return 
               }
               
               window.location.href="${ctx}/cms/media/remove.html?ids="+ids;
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
                            <h2><i class="icofont-retweet"></i> 多媒体 <small>电视商务商户管理系统 &rsaquo; 多媒体管理模块&rsaquo; 媒体库</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            
                            <div class="row-fluid">
                                <div class="span12">
                                    <div class="box corner-all">
                                        <div class="box-header grd-white corner-top">
                                                 <span><i class="icon-music"></i>&nbsp;媒体库</span>
                                        </div>
                                        <div class="box-body">
                                            <div id="datatables_wrapper" class="dataTables_wrapper form-inline" role="grid">
                                            <div class="row-fluid">
                                                
                                                <div class="span6">
                                                    <div id="datatables_length" >
                                                        <label>
                                                            <!-- <select size="1" name="datatables_length" aria-controls="datatables" >
                                                            <option value="1" selected="selected" >批量操作</option>
                                                            <option value="2" selected="selected" >编辑</option>
                                                            <option value="3" selected="selected" >移至回收站</option>
                                                            </select> 
                                                            <a class="btn btn-small" >应用</a> -->
                                                            <!-- <a class="btn btn-small" id="editAllPageBtn">编辑</a> -->
                                                            <a class="btn btn-small btn-danger" id="recycleAllPageBtn">批量删除</a>
                                                            <!-- Button to trigger modal -->
                                                            <a href="#uploadAttachmentModal" role="button" class="btn btn-small btn-info" data-toggle="modal" data-backdrop="static" id="addMediaBtn">添加</a>
                                                        </label>
                                                    </div>
                                                </div>
                                                
                                                             <!-- Modal -->
                                                            <div id="uploadAttachmentModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                                              <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                                <h3 id="myModalLabel">上传媒体文件</h3>
                                                              </div>
                                                              <form action="">
                                                              <div class="modal-body">
                                                                <p><button class="btn btn-primary" id="selectFileBtn">上传</button></p>
                                                              </div>
                                                              <div class="modal-footer">
                                                                <button class="btn" data-dismiss="modal" aria-hidden="true" id="btnCancel">关闭</button>
                                                                <!-- <button class="btn btn-primary" id="doUploadBtn">上传</button> -->
                                                              </div>
                                                              </form>
                                                            </div> 
                                                
                                                <div class="span6">
                                                <form action="${ctx}/cms/media/index.html?menu=menu_multimedia" method="post" id="pageSearchForm">
                                                    <div class="dataTables_filter" id="datatables_filter" >
                                                        <label>
                                                        <input type="hidden" name="pageNum"  id="pageNumInput" value="${page.pageNum}" />
                                                        <input type="text" aria-controls="datatables" name="search" value="${search}">
                                                        <input type="button" class="btn btn-small btn-success" id="searchBtn" value="搜索">
                                                        </label>
                                                    </div>
                                                </form>
                                                </div>
                                            </div>
                                             
                                             <table class="table table-hover table-striped responsive">
                                                <thead>
                                                    <tr>
                                                        <th><input type="checkbox" id="checkAllPageBtn"></th>
                                                        <th>标题</th>
                                                        <th>缩略图</th>
                                                        <th>地址</th>
                                                        <th>作者</th>
                                                        <th>日期</th>
                                                        <th>操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="pageTable">
                                                <c:forEach items="${page.result}" var="p">
                                                    <tr>
                                                        <td><input type="checkbox" value="${p.id}"></td>
                                                        <td>
                                                        <div>${p.title}</div>
                                                        </td>
                                                        <td>
                                                        <div style="width: 80px;height: 60px;">
                                                            <c:if test="${p.mimeType=='jpg'}">
                                                            <img class="img-polaroid" alt="${p.title}" src="${mediaUrl}${p.guid}">
                                                            </c:if>
                                                            <c:if test="${p.mimeType=='png'}">
                                                            <img class="img-polaroid" alt="${p.title}" src="${mediaUrl}${p.guid}">
                                                            </c:if>
                                                            <c:if test="${p.mimeType=='gif'}">
                                                            <img class="img-polaroid" alt="${p.title}" src="${mediaUrl}${p.guid}">
                                                            </c:if>
                                                        </div>
                                                        </td>
                                                        <td width="10%">${mediaUrl}${p.guid}</td>
                                                        <td>${p.showOwner}</td>
                                                        <td><fmt:formatDate value="${p.createTime}" pattern="yyyy年MM月dd日 "/></td>
                                                        <td>
                                                                <div class="btn-group">
                                                                <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">操作<span class="caret"></span></button>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="${ctx}/cms/media/edit.html?menu=menu_multimedia&id=${p.id}">编辑</a></li>
                                                                    <li><a href="#" onclick="removeMedia(${p.id})">删除</a></li>
                                                                </ul>
                                                                </div><!-- /btn-group -->
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                             
                                            <jsp:include page="/common/pagination.jsp"></jsp:include>
                                             
                                             </div><!-- /box-body -->
                                    </div><!-- /box -->
                                </div><!-- /span -->
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

