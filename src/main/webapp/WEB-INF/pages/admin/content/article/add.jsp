<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.*"%> 
<%@ page import="java.text.*"%>

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
            $(document).on("itemImages",function(event, images){
            	
            	var str = "_1";
            	var viewPath = '${mediaUrl}';
            	var allImgPath = "";
            	for(var i = 0;i<images.length; i++){
            		allImgPath += images[i] + "," ;
            		if(images[i].indexOf(str) != -1){
            			viewPath += images[i];
            		}
            	}
            	
            	$("#imgPath").val(allImgPath.substring(0,allImgPath.length - 1));
            	$("#imageView").attr("src",viewPath).show();
            	$("#addImg").text("更改");
        	});
        
            $(document).ready(function() {
                
                //页面状态编辑切换功能
                $("#pageStatusEditBtn").on("click",function(){
                    $(this).hide();
                    $(this).parent().next().show();
                    
                });
                $("#pageStatusEditConfirmBtn").on("click",function(){
                    $(this).parent().hide();
                    $("#pageStatusEditBtn").show();
                    
                    var pageStatusSelect = $("#pageStatusSelect option:selected");
                    if(pageStatusSelect.val() == "wai"){
                        $("#savedrtDiv").hide();
                        $("#saveAndpenddrtDiv").show();
                    }
                    if(pageStatusSelect.val() == "drt"){
                        $("#saveAndpenddrtDiv").hide();
                        $("#savedrtDiv").show();
                    }
                    
                    $("#postStatus").val(pageStatusSelect.val());
                    $("#pageStatusSpan").text(pageStatusSelect.text());
                    
                });
                
                $("#pageStatusEditCancelBtn").on("click",function(){
                    $(this).parent().hide();
                    $("#pageStatusEditBtn").show();
                });
                
              //页面公开度编辑切换功能
                $("#pageOpenStatusEditBtn").on("click",function(){
                    $(this).hide();
                    $(this).parent().next().show();
                    
                });
                
                $("#pageOpenStatusEditConfirmBtn").on("click",function(){
                    $(this).parent().hide();
                    $("#pageOpenStatusEditBtn").show();
                    
                    var pageOpenStatusChecked = $(":radio[name='openStatus']:checked");
                    $("#pingStatus").val(pageOpenStatusChecked.val());
                    $("#pageOpenStatusSpan").text(pageOpenStatusChecked.next().text());
                });
                
                $("#pageOpenStatusEditCancelBtn").on("click",function(){
                    $(this).parent().hide();
                    $("#pageOpenStatusEditBtn").show();
                });
                
                
                //发布时间编辑
                $("#publishDateEdit").on("click",function(){
                    $(this).hide();
                    $(this).parent().next().show();
                    
                });
                
                $("#publishDateClose").on("click",function(){
                    $(this).parent().hide();
                    $("#publishDateEdit").show();
                });
                
                $("#postDateBtn").on("click",function(){
                    $(this).parent().hide();
                    $("#publishDateEdit").show();
                    
                    $("#postDate").val($("#inputDate").val());
                });
                
                $('[data-form=datepicker]').datepicker();
                
                //图片上传时使用
                /* $('#selectFileBtn').uploadify({
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
         	        	$("#imgPostId").val(post.id);
         	        	var imgUrl = "${mediaUrl}"+post.guid;
         	        	$("#imageView").attr("src",imgUrl).attr("alt",post.title).show();
         	        	$("#addMediaBtnSav").text("更改");
         	        }
         	    }); */
                
            });
            
            
            function nodeClick(event, treeId, treeNode){
                
                $.ajax({
                    type: "POST",
                    url: '${ctx}/cms/term_meta/get_post_meta.html',  
                    data: {"termId":treeNode.id},
                    datatype : "json",
                    beforeSend: function(){
                       
                    },
                    success: function(list){
                        var checked = treeNode.checked;
                        
                        var srcMeta = $("#postMetaDiv").html();
                        for(var i=0;i<list.length;i++){
                            var item = list[i];
                            if(checked){
                            	var str = "";
                            	//如果是文本
                            	if(item.valueType == 'num'  || item.valueType == 'str'){
                            		str = '<label class="control-label" for="inputAuto" id="'+item.key+'_lab">'+item.name+'</label><div class="controls" id="'+item.key+'_div"><input type="text" class="span8" name="'+item.key+'_'+item.id+'" id="" value=""></div>'
                            	}
                            	//如果是时间
                            	if(item.valueType == 'date'){
                            		str = '<label class="control-label" for="inputAuto" id="'+item.key+'_lab">'+item.name+'</label><div class="controls" id="'+item.key+'_div"><input type="text" class="span8" name="'+item.key+'_'+item.id+'" id="" value=""></div>'
                            	}
                            	//如果是时间
                            	if(item.valueType == 'file'){
                            		str = '<label class="control-label" for="inputAuto" id="'+item.key+'_lab">'+item.name+'</label><div class="controls" id="'+item.key+'_div"><input type="text" class="span8" name="'+item.key+'_'+item.id+'" id="" value=""></div>'
                            	}
                                $(str).appendTo($("#postMetaDiv"));
                            }
                            if(!checked){
                                $("#" + item.key + "_lab").remove();
                                $("#" + item.key + "_div").remove();
                            }
                        }
                        
                    }
                });
                
                
            }
            //分类目录树设置
            var settingTerm = {
                    check: {
                        enable: true
                    },
                    callback: {
                        onCheck: nodeClick
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
                    }
                };
            
          //标签树设置
            var settingTag = {
                    check: {
                        enable: true
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
                    }
                };
            
            

                var zNodes = ${parentList};
                var zTagsNodes = ${tagsJsonList};
                
                var code;
                
                function setCheck() {
                    var zTree = $.fn.zTree.getZTreeObj("treeAll"),
                    py = $("#py").attr("checked")? "p":"",
                    sy = $("#sy").attr("checked")? "s":"",
                    pn = $("#pn").attr("checked")? "p":"",
                    sn = $("#sn").attr("checked")? "s":"",
                    type = { "Y" : "", "N" : "" };
                    zTree.setting.check.chkboxType = type;
                    showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
                }
                function showCode(str) {
                    if (!code) code = $("#code");
                    code.empty();
                    code.append("<li>"+str+"</li>");
                }
                
                $(document).ready(function(){
                    $.fn.zTree.init($("#treeAll"), settingTerm, zNodes);
                	$.fn.zTree.init($("#tagTree"), settingTag, zTagsNodes);
                    setCheck();
                    geTerm();
                    geTagTerm();
                    $("#py").bind("change", setCheck);
                    $("#sy").bind("change", setCheck);
                    $("#pn").bind("change", setCheck);
                    $("#sn").bind("change", setCheck);
                    var termIdList = ${checkTermId };
                    if(${checkTermId } != ""){geTagTerm()
                        geTerm();
                    }
                    if(${tagTermTaxIdList } != ""){
                    	geTagTerm();
                    }
                    
                });
                
                //获取分类目录id
                function getCheckTerm(){
                    var treeObj = $.fn.zTree.getZTreeObj("treeAll");
                    var nodes = treeObj.getCheckedNodes(true);
                    if(nodes != ""){
                        var strVal = "";
                        var termIdStr = "";
                        for (var i=0; i < nodes.length; i++) {
                            var node = nodes[i];
                            var nodeId = node.taxonomyId;
                            var termId = node.id;
                            if(strVal == ""){
                                strVal = nodeId;
                                termIdStr = termId;
                            }else{
                                strVal = strVal + "," + nodeId;
                                termIdStr = termIdStr + "," + termId;
                            }
                        }
                        $("#termTaxIds").val(strVal);
                        $("#termIds").val(termIdStr);
                        return true;
                    }
                    
                    return false;
                }
                
              //获取tag id
                function getCheckTag(){
                    var treeObj = $.fn.zTree.getZTreeObj("tagTree");
                    var nodes = treeObj.getCheckedNodes(true);
                    var strVal = "";
                    for (var i=0; i < nodes.length; i++) {
                        var node = nodes[i];
                        var nodeId = node.taxonomyId;
                        if(strVal == ""){
                            strVal = nodeId;
                        }else{
                            strVal = strVal + "," + nodeId;
                        }
                    }
                    $("#tagIds").val(strVal);
                    
                }
                
               function checkForm(){
            	   
        	   	   var title = $.trim($("#title").val());
        	   	   var slug = $.trim($("#slug").val());
                   var content = $.trim($("#content").val());
                   var imgPath = $.trim($("#imgPath").val());
                   
               	   if (!title||title.length==0) {
               		$("#formMsg").text("内容标题不能为空").show();
    					return false;
    					}
               	   
               		if (!slug||slug.length==0) {
                   		$("#formMsg").text("内容别名不能为空").show();
        				return false;
    					}
               	   
               	   /* if (!content||content.length==0) {
               	   	$("#formMsg").text("内容不能为空").show();
                       return false;
                   	} */
                   	if (!imgPath||imgPath.length==0) {
                   	   	$("#formMsg").text("请添加商品图片").show();
                           return false;
                       	}
               	   
               	   if(!getCheckTerm()){
               		$("#formMsg").text("至少选择一个分类").show();
               		return false;
               	   }
               	   
               	   $("#formMsg").hide();
               	   
               	   return true;
            	   
               }
                
                //form提交
                function submitForm(postStatus, operateType){
                	getCheckTag();
                	if(checkForm()){
                    	var pageOpenStatusChecked = $(":radio[name='openStatus']:checked");
                        $("#pingStatus").val(pageOpenStatusChecked.val());
                        if(postStatus == 'drt' || postStatus == 'pub'){
                        	$("#postStatus").val(postStatus);
                        }
                        /* if(operateType == "add"){
                        	var slug = $("#slug").val(); */
                            /* $.ajax({
                                type: "POST",
                                url: '${ctx}/cms/article/check_slug.html',  
                                data: {"slug":slug},
                                datatype : "json",
                                success: function(data){
                                    if(data){
                                    	$("#publishContentForm").submit();
                                    }else{
                                  	  $("#formMsg").text("内容别名已经存在!!").show();
                                    }
                                }
                            }); */
                        /* } */
                       /*  if(operateType == "edit"){ */
                        	$("#publishContentForm").submit();
                       /*  } */
                  }else{
                    return;
                  }
                }
                
                //获取内容分类目录
                 function geTerm(){
                    var termIdList = ${checkTermId };
                    var treeObj = $.fn.zTree.getZTreeObj("treeAll");
                    var nodes = treeObj.getNodes();
                     if(nodes != "" && termIdList != ""){
                        for (var i=0; i < nodes.length; i++) {
                             for(var n=0;n<termIdList.length;n++){
                                if(nodes[i].taxonomyId == termIdList[n]){
                                    treeObj.checkNode(nodes[i], true, true);
                                }
                            }  
                        }
                    }  
                    
                } 
                
               //获取内容关联的tag
                 function geTagTerm(){
                    var termIdList = ${tagTermTaxIdList };
                    var treeObj = $.fn.zTree.getZTreeObj("tagTree");
                    var nodes = treeObj.getNodes();
                     if(nodes != "" && termIdList != ""){
                        for (var i=0; i < nodes.length; i++) {
                             for(var n=0;n<termIdList.length;n++){
                                if(nodes[i].taxonomyId == termIdList[n]){
                                    treeObj.checkNode(nodes[i], true, true);
                                }
                            }  
                        }
                    }  
                    
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
                            <h2><i class="icofont-retweet"></i> 内容 <small>电视商务商户管理系统 &rsaquo; 内容管理模块&rsaquo; 新建内容</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
               <div class="content-body">
                            <jsp:include page="/common/imageTree.jsp"></jsp:include>
                            <!-- tab resume content -->
                            <div class="alert alert-error" style="display: none;" id="formMsg">
                            </div>
                            <div class="row-fluid">
                            
                                <div class="span9">
                                    <div class="box corner-all">
                                        <c:if test="${post == null }">
                                            <div class="box-header corner-top grd-white">
                                                <span><i class="icofont-plus-sign"></i>&nbsp;撰写新文章</span>
                                            </div>
                                        </c:if>
                                        <c:if test="${post != null }">
                                            <div class="box-header corner-top grd-white">
                                                <span><i class="icon-edit"></i>&nbsp;编辑文章</span>
                                            </div>
                                        </c:if>
                                        <div class="box-body">
                                            <c:if test="${flag == 'add' }">
                                                <form method="post" action="${ctx}/cms/article/publish_article.html" id="publishContentForm">
                                            </c:if>
                                            <c:if test="${flag == 'edit' }">
                                                <form method="post" action="${ctx}/cms/article/article_edit.html" id="publishContentForm">
                                            </c:if>
                                            
                                            <%-- <input type="hidden" name="imgPostId" id="imgPostId" value="${imgPost.id }" /> --%><!-- 特色图片 -->
                                            <input type="hidden" name="imgPath" id="imgPath" value="${allImgUrl }"/><br/>
                                            <input type="hidden" name="id" value="${post.id }" />
                                            <input type="hidden" id="termTaxIds" name="termTaxIds" value="">
                                            <input type="hidden" id="tagIds" name="tagIds" value="">
                                            <input type="hidden" id="termIds" name="termIds" value="">
                                            <input type="hidden" name="postStatus" id="postStatus" value="${post.postStatus }">
                                            <input type="hidden" name="pingStatus" id="pingStatus" value="${post.pingStatus }">
                                            <input type="hidden" name="postDate" id="postDate" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>">
                                            
                                            
                                            <input name="createTime" type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${post.createTime}"/>" />
                                            <input name="createOperId" type="hidden" value="${post.createOperId }"/>
                                            <input name="createOperName" type="hidden" value="${post.createOperName }"/>
                                            <input name="lastUpdTime" type="hidden" value="<fmt:formatDate value='${post.lastUpdTime }' pattern='yyyy-MM-dd HH:mm:ss' />"/>
                                            <input name="lastUpdOperId" type="hidden" value="${post.lastUpdOperId }"/>
                                            <input name="lastUpdOperName" type="hidden" value="${post.lastUpdOperName }"/>
                                                        
                                            <div class="control-group">
                                                <label class="control-label" for="inputAuto">标题</label>
                                                <div class="controls">
                                                   <input type="text" class="span8" name="title" id="title" value="${post.title }">
                                                </div><br/>
                                                <label class="control-label" for="inputAuto">别名</label>
                                                <div class="controls">
                                                   <input type="text" class="span6" name="slug" id="slug" value="${post.slug }">
                                                </div>
                                            </div>
                                            <div class="control-group" id="postMetaDiv">
                                                <c:if test="${commMetaList != null }">
                                                    <c:forEach items="${commMetaList }" var="commMeta">
                                                        <label class="control-label" for="inputAuto" id="${commMeta.key }_lab">${commMeta.name }</label>
                                                        <div class="controls" id="${commMeta.key }_div">
                                                           <input type="text" class="span6" name="${commMeta.key }_${commMeta.id }" id="metaValue" value="${commMeta.value }">
                                                        </div>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${viewList != null }">
                                                    <c:forEach items="${viewList }" var="meta">
                                                        <label class="control-label" for="inputAuto" id="${meta.key }_lab">${meta.name }</label>
                                                        <div class="controls" id="${meta.key }_div">
                                                           <input type="text" class="span6" name="${meta.key }_${meta.termMetaId }" id="metaValue" value="${meta.value }">
                                                        </div>
                                                    </c:forEach>
                                                
                                                </c:if>
                                            </div>
                                           <!--  <a class="btn tbn-small"><i class="icon-music"></i>添加媒体</a> -->
                                           <!-- Button to trigger modal -->
                                           
                                           <c:if test="${flag == 'edit' }">
                                           
                                                <!-- 特色图像 -->
                                                <%-- <div class="control-group">
                                                    <label class="control-label">特色图像</label>
                                                    <div class="controls">
                                                        <img class="img-polaroid" id="imageView"  style="width: 80px;height: 60px;" alt="${imgPost.title}" src="${ctx}/cms/media/getMedia.html?guid=${imgPost.guid}">
                                                        <a href="#uploadAttachmentModal" role="button" class="btn tbn-small" data-toggle="modal" data-backdrop="static" id="addMediaBtn">更改</a><br>
                                                    </div>
                                                </div> --%>
                                                <img class="img-polaroid" id="imageView"  style="width: 80px;height: 60px;" src="${mediaUrl}${imgUrl} ">
                                                <a href="#browseImageModal" id="addImg" role="button" class="btn btn-small btn-info" data-toggle="modal">更改</a>
                                            </c:if>
                                            <c:if test="${flag == 'add' }">
                                                <!-- Button to trigger modal -->
                                                <img class="img-polaroid" id="imageView"  style="width: 80px;height: 60px; display: none;" alt="${imgPost.title}" src="">
                                                <a href="#browseImageModal" id="addImg" role="button" class="btn btn-small btn-info" data-toggle="modal">添加图片</a>
                                                <!-- 特色图像 -->
                                                <%-- <img class="img-polaroid" id="imageView"  style="width: 80px;height: 60px; display: none;" alt="${imgPost.title}" src="${ctx}/cms/media/getMedia.html?guid=${imgPost.guid}">
                                                <a href="#uploadAttachmentModal" role="button" class="btn tbn-small" data-toggle="modal" data-backdrop="static" id="addMediaBtnSav">点击添加特色图像</a><br> --%>
                                            </c:if>
                                           <img class='img-polaroid' style="width: 80px;height: 60px;display: none;" alt='${post.title}' id="imageView" src='' >
                                            <div class="control-group">
                                                <div class="controls">
                                                    <textarea class="span12 xheditor" rows="20" data-form="wysihtml5" placeholder="" name="content" id="content">
                                                    ${post.content }
                                                    </textarea>
                                                </div>
                                            </div>
                                        </form>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="span3">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                              <span><i class="icon-share"></i>发布</span>
                                        </div>
                                        <div class="box-body">
                                        <!-- 新增 -->
                                            <c:if test="${post == null }">
                                                <div class="control-group" id="savedrtDiv">
                                                 <a class="btn tbn-small" id="draftPageBtn" onclick="submitForm('drt','add')" >保存草稿</a>
                                                 <!-- <a class="btn tbn-small pull-right">预览</a> -->
                                                </div>
                                                <div class="control-group" id="saveAndpenddrtDiv" style="display: none;">
                                                 <a class="btn tbn-small" id="drtAndPendBtn" onclick="submitForm('wai','add')">保存并提交审核</a>
                                                 <!-- <a class="btn tbn-small pull-right">预览</a> -->
                                                </div>
                                                <div class="control-group">
                                                    <span>&nbsp;<i class="icon-map-marker"></i>&nbsp;状态：<span id="pageStatusSpan">草稿</span></span>&nbsp;&nbsp;&nbsp;<a href="#" id="pageStatusEditBtn">编辑</a>
                                                </div>
                                                <div class="control-group" style="display: none;">
                                                    <select id="pageStatusSelect">
                                                        <option value="drt">草稿</option>
                                                        <option value="wai">等待复审</option>
                                                    </select>
                                                    <a href="#" id="pageStatusEditConfirmBtn" class="btn btn-small" >确定</a>
                                                    &nbsp;&nbsp;&nbsp;
                                                    <a href="#" id="pageStatusEditCancelBtn">取消</a>
                                                </div>
                                                <div class="control-group">
                                                    <span>&nbsp;<i class="icon-eye-open"></i>&nbsp;公开度：<span id="pageOpenStatusSpan">开放</span></span>&nbsp;&nbsp;&nbsp;<a href="#" id="pageOpenStatusEditBtn">编辑</a>
                                                </div>
                                                <div class="control-group" style="display: none;">
                                                    <input type="radio" name="openStatus"  value="open" checked="checked"><span>开放</span><br>
                                                    <input type="radio" name="openStatus" value="clse"><span>关闭</span><br>
                                                     <!-- <input type="radio" name="openStatus" value="3"><span>密码保护</span>--><br> 
                                                    <a href="#" id="pageOpenStatusEditConfirmBtn" class="btn btn-small" >确定</a>
                                                    &nbsp;&nbsp;&nbsp;
                                                    <a href="#" id="pageOpenStatusEditCancelBtn">取消</a>
                                                </div>
                                                <div class="control-group">
                                                    <span><i class="typicn-time"></i>&nbsp;立即发布</span>&nbsp;&nbsp;&nbsp;<a href="#" id="publishDateEdit">编辑</a>
                                                </div>
                                                <div class="control-group" style="display: none;">
                                                    <label class="control-label" for="inputDate">发布日期</label>
                                                    <div class="controls">
                                                        <div class="input-append date" data-form="datepicker" data-date="2012-12-02" data-date-format="yyyy-mm-dd">
                                                            <span class="add-on"><i class="icon-th"></i></span>
                                                            <input id="inputDate" class="grd-white span10" data-form="datepicker" size="16" data-date-format="yyyy-mm-dd" type="text" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" />
                                                        </div>
                                                    </div>
                                                    <a href="#" id="postDateBtn" class="btn btn-small" >确定</a>
                                                    &nbsp;&nbsp;&nbsp;
                                                    <a href="#" id="publishDateClose">取消</a>
                                                </div>
                                            </c:if>
                                            <!-- 编辑 -->
                                            <c:if test="${post != null }">
                                                <div class="control-group">
                                                    <span>&nbsp;<i class="icon-map-marker"></i>&nbsp;状态：<span id="pageStatusSpan">
                                                        <c:if test="${post.postStatus == 'drt' }">
                                                                                                                                                                      草稿
                                                        </c:if>
                                                        <c:if test="${post.postStatus == 'wai' }">
                                                                                                                                                                      等待复审
                                                        </c:if>
                                                        <c:if test="${post.postStatus == 'pub' }">
                                                                                                                                                                      已发布
                                                        </c:if>    
                                                    </span>
                                                    </span>&nbsp;&nbsp;&nbsp;<a href="#" id="pageStatusEditBtn">编辑</a>
                                                </div>
                                                <div class="control-group" style="display: none;">
                                                    <select id="pageStatusSelect">
                                                        <c:if test="${post.postStatus == 'pub' }">
                                                            <option value="pub" selected="selected">已发布</option>
                                                            <option value="drt">草稿</option>
                                                            <option value="wai">等待复审</option>
                                                        </c:if>
                                                        <c:if test="${post.postStatus == 'drt' }">
                                                            <option value="pub">已发布</option>
                                                            <option value="drt" selected="selected">草稿</option>
                                                            <option value="wai">等待复审</option>
                                                        </c:if>
                                                        <c:if test="${post.postStatus == 'wai' }">
                                                            <option value="pub">已发布</option>
                                                            <option value="drt">草稿</option>
                                                            <option value="wai" selected="selected">等待复审</option>
                                                        </c:if>
                                                    </select>
                                                    <a href="#" id="pageStatusEditConfirmBtn" class="btn btn-small" >确定</a>
                                                    &nbsp;&nbsp;&nbsp;
                                                    <a href="#" id="pageStatusEditCancelBtn">取消</a>
                                                </div>
                                                <div class="control-group">
                                                    <span>&nbsp;<i class="icon-eye-open"></i>&nbsp;公开度：
                                                    <span id="pageOpenStatusSpan">
                                                        <c:if test="${post.pingStatus == 'open' }">
                                                                                                                                                                      公开
                                                        </c:if>
                                                        <c:if test="${post.pingStatus == 'clse' }">
                                                                                                                                                                       关闭
                                                        </c:if>
                                                    </span>
                                                    </span>&nbsp;&nbsp;&nbsp;<a href="#" id="pageOpenStatusEditBtn">编辑</a>
                                                </div>
                                                <div class="control-group" style="display: none;">
                                                    <c:if test="${post.pingStatus == 'open' }">
                                                        <input type="radio" name="openStatus"  value="open" checked="checked"><span>开放</span><br>
                                                        <input type="radio" name="openStatus" value="clse"><span>关闭</span><br>
                                                    </c:if>
                                                    <c:if test="${post.pingStatus == 'clse' }">
                                                        <input type="radio" name="openStatus"  value="open"><span>开放</span><br>
                                                        <input type="radio" name="openStatus" value="clse" checked="checked"><span>关闭</span><br>
                                                    </c:if>
                                                     <!-- <input type="radio" name="openStatus" value="3"><span>密码保护</span>--><br> 
                                                    <a href="#" id="pageOpenStatusEditConfirmBtn" class="btn btn-small" >确定</a>
                                                    &nbsp;&nbsp;&nbsp;
                                                    <a href="#" id="pageOpenStatusEditCancelBtn">取消</a>
                                                </div>
                                                <div class="control-group">
                                                    <span><i class="typicn-time"></i>&nbsp;立即发布</span>&nbsp;&nbsp;&nbsp;<a href="#" id="publishDateEdit">编辑</a>
                                                </div>
                                                <div class="control-group" style="display: none;">
                                                    <label class="control-label" for="inputDate">发布日期</label>
                                                    <div class="controls">
                                                        <div class="input-append date" data-form="datepicker" data-date="2012-12-02" data-date-format="yyyy-mm-dd">
                                                            <span class="add-on"><i class="icon-th"></i></span>
                                                            <input id="inputDate" class="grd-white span10" data-form="datepicker" size="16" data-date-format="yyyy-mm-dd" type="text" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" />
                                                        </div>
                                                    </div>
                                                    <a href="#" id="postDateBtn" class="btn btn-small" >确定</a>
                                                    &nbsp;&nbsp;&nbsp;
                                                    <a href="#" id="publishDateClose">取消</a>
                                                </div>
                                                
                                                
                                                <div class="control-group">
                                                    <span><i class="typicn-time"></i>&nbsp;发布于：<fmt:formatDate value='${post.postDate }' pattern='yyyy-MM-dd HH:mm:ss' /></span>&nbsp;&nbsp;&nbsp;<a href="#" id="publishDateEdit">编辑</a>
                                                </div>
                                                <div class="control-group" style="display: none;">
                                                    <div class="controls">
                                                        <div class="input-append date" data-form="datepicker" data-date="2012-12-02" data-date-format="yyyy-mm-dd">
                                                            <span class="add-on"><i class="icon-th"></i></span>
                                                            <input id="inputDate" class="grd-white span10" data-form="datepicker" size="16" data-date-format="yyyy-mm-dd" type="text" value="<fmt:formatDate value='${post.postDate }' pattern='yyyy-MM-dd HH:mm:ss' />" />
                                                        </div>
                                                    </div>
                                                    <a href="#" id="postDateBtn" class="btn btn-small" >确定</a>
                                                    &nbsp;&nbsp;&nbsp;
                                                    <a href="#" id="publishDateClose">取消</a>
                                                </div>
                                            </c:if>
                                            <div class="control-group">
                                                <i class="icon-trash"></i>&nbsp;<a href="">移至回收站</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <c:if test="${post == null }">
                                                    <button  class="btn btn-primary" type="button" id="publishBtn" onclick="submitForm('pub','add')">发&nbsp;&nbsp;布</button>
                                                </c:if>
                                                <c:if test="${post != null }">
                                                    <button  class="btn btn-primary" type="button" id="publishBtn" onclick="submitForm('','edit')">更&nbsp;&nbsp;新</button>
                                                </c:if>
                                            </div> 
                                        </div>
                                    </div>
                                    <!-- /tab stat -->
                                    <div class="box-tab corner-all">
                                        <div class="box-header corner-top">
                                            <ul class="nav nav-tabs" id="tab-stat">
                                                <li class="active"><a data-toggle="tab" href="#system-stat">所有分类目录</a></li>
                                                <!-- <li><a data-toggle="tab" href="#server-stat">最常用</a></li> -->
                                            </ul>
                                        </div>
                                        
                                         <div class="box-body">
                                            <div class="tab-content">
                                                <div class="tab-pane fade in active" id="system-stat">
                                                     <ul id="treeAll" class="ztree" style="background-color: white;border-style: none;width: 220px;height: 260px;"></ul>
                                                </div>
                                               <!--  <div class="tab-pane fade" id="server-stat">
                                                     <ul id="treeUse" class="ztree" style="background-color: white;border-style: none;width: 220px;height: 260px;"></ul>
                                                </div> -->
                                            </div>
                                        </div>
                                        
                                        
                                        
                                    </div><!-- /tab start -->
                                    
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icon-tag"></i>&nbsp;标签</span>
                                        </div>
                                        <div class="box-body " style="background: url('../img/noise.png');">
                                                 <ul id="tagTree" class="ztree" style="background-color: white;border-style: none;width: 220px;height: 260px;"></ul>
                                               <!--  <div class="tab-pane fade" id="server-stat">
                                                     <ul id="treeUse" class="ztree" style="background-color: white;border-style: none;width: 220px;height: 260px;"></ul>
                                                </div> -->
                                            <!-- <input type="text" class="span8" placeholder="在此输入标签" name="tag" id="tag">
                                            <input type="button" value="添加" class="btn btn-primary" /><br/>
                                                                                                                        多个标签请用英文逗号（,）分开 <br/>
                                            <input id="tagEditBtn"  value="从常用标签中选择" class="btn btn-link" />
                                            <input onclick="getCheckTerm()" type="button" value="test" /> -->
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
        </section>
        <jsp:include page="/common/footer.jsp"></jsp:include>
        <!-- 图片上传 -->
        <!-- Modal -->
        <!-- <div id="uploadAttachmentModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">上传媒体文件</h3>
          </div>
          <div class="modal-body">
            <p><button class="btn btn-primary" id="selectFileBtn">上传</button></p>
          </div>
          
          <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true" id="btnCancel">关闭</button>
            <button class="btn btn-primary" id="doUploadBtn">上传</button>
          </div> 
        </div> -->
    </body>
</html>

