<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
    <head>
        <title>东方亿付电视商务系统--外观管理--主题</title>
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
            	//页面状态编辑切换功能
                $("#pageStatusEditBtn").on("click",function(){
                    $(this).hide();
                    $(this).parent().next().show();
                    
                });
            	
                //发布时间编辑
                $("#publishDateEdit").on("click",function(){
                    $(this).hide();
                    $(this).parent().next().show();
                    
                });
                
                //主题文件树设置
                var setting = {
                    check: {
                    	enable: false,
                        //chkStyle: "radio",
                        //radioType: "all"
                    },
                    callback: {
                        onClick: selectFile
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId",
                            rootPId: 0
                        },
                        key:{
                            name:"fileName",
                            title:"fileName"
                        }
                    }
                };
                
                
                
                var zThemeFileNodes = ${themeFileArray};
                //console.log(zThemeFileNodes);
                $.fn.zTree.init($("#themeFileTree"), setting, zThemeFileNodes);
                
                function selectFile(event, treeId, treeNode){
                    //alert(treeNode.tId + ", " + treeNode.fileName + "," + treeNode.checked);
                    //var editor=$('#fileContent').xheditor();
                    //editor.focus();
                    
                    if(treeNode.fileType == 'directory'){
                    	$('#fileTreeMsg').text("当前选项为文件夹，请选择要编辑的文件").show();
                    	//editor.setSource('文件内容...');
                    	$('#srcFileName').text("");
                    	$('#srcFileUrl').text("");
                    	$('#fileContent').val("");
                    	return;
                    }
                    $('#fileTreeMsg').hide();
                    $('#srcFileName').html(treeNode.fileName);
                    $('#srcFileUrl').html(treeNode.fileUrl);
                    
                    $.ajax({
                    	type: 'POST',
                        url: '${ctx}/cms/outward/editTemplateFile.html',
                        data: 'fileUrl=' + treeNode.fileUrl + '&fileName=' + treeNode.fileName + '&fileContent=' + $('#fileContent').val(),
                        dataType: 'json',
                        cache : false,
                        success: function(msg) {
                        	if(msg.result == 'fail'){
                        		$('#formMsgFail').text(msg.descMsg).show();
                        	}
                        	else{
                        		//alert(msg.result);
                        		$('#fileContent').val(msg.result);
                        		
                        		//$('#formMsgSucc').text("成功").show();
                        		//editor.setSource('');
                        		//editor.focus();
                        		//editor.toggleSource(true);   //切换源代码模式
                        		//editor.toggleShowBlocktag(true);   //显示段落标签
                        		//editor.settings.internalStyle=true;
                        		//editor.settings.inlineStyle=true;
                        		//editor.settings.internalScript=true;
                        		//editor.settings.inlineScript=true;
                                //editor.setSource(msg.result); 
                                //editor.pasteHTML(msg.result); 
                        		//editor.pasteText(msg.result);                      		
                        	}
                        },
                        error: function () {
                            alert("error!!!");
                        } 
                    });
               };
               
               $('#commitFileContent').click(function(){
            	   var srcFileName = $.trim($('#srcFileName').text());
            	   var srcFileUrl = $.trim($('#srcFileUrl').text());
            	   if(srcFileName.indexOf('您选择') >= 0 || srcFileUrl.indexOf('您选择') >= 0){
            		   $('#formMsgFail').text('请先选择需要编辑保存的文件').show();
            		   return;
            	   }
            	   $('#formMsgFail').hide();
            	   
            	   //var editor=$('#fileContent').xheditor();
            	   //alert(editor.getSource());
            	   
            	   //alert(srcFileName + ' : ' + srcFileUrl);
            	   
            	   $.ajax({
            		   type: 'POST',
                       url: '${ctx}/cms/outward/updateTemplateFile.html',
                       //data: 'fileUrl=' + srcFileUrl + '&fileContent=' + editor.getSource() + '&fileName=' + srcFileName,
                       data: {"fileUrl" : srcFileUrl, "fileContent" : $('#fileContent').val()},
                       dataType: 'json',
                       cache : false,
                       success: function(msg) {
                           if(msg.result == 'success'){
                        	   $('#formMsgSucc').text('文件保存成功').show();  
                           }
                           else{
                        	   $('#formMsgFail').text('文件保存失败').show();                               
                           }
                       },
                       error: function () {
                           alert("error!!!");
                       } 
            	   });
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
                            <h2><i class="icofont-retweet"></i> 外观 <small>电视商务商户管理系统 &rsaquo; 主题管理模块&rsaquo; 主题</small></h2>
                        </div><!-- /content-header -->
                        
                        <!-- content-body -->
                        <div class="content-body">
                            
                            <!-- tab resume content -->
                            <div class="alert alert-error" <c:if test="${empty msg}">style="display: none;"</c:if> id="formMsgFail">
                            ${msg}
                            </div>
                            <div class="alert alert-success" <c:if test="${empty msg}">style="display: none;"</c:if> id="formMsgSucc">
                            ${msg}
                            </div>
                            <div class="alert alert-error" style="display: none;" id="fileTreeMsg"></div>
                            <div class="row-fluid">
                            
                                <div class="span9">
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icon-globe"></i>&nbsp;主题</span>
                                        </div>
                                        <div class="box-body">
                                            <form method="post" action="${ctx}/cms/outward/updateTemplateFile.html" id="commitFileContentForm">
                                                <span style="font-size:15px;">文件名：</span>
                                                <span id="srcFileName">您选择的文件名...</span>
                                                <br />
                                                <span style="font-size:15px;">路径：&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                                <span id="srcFileUrl">您选择的文件所在路径...</span>
                                                <br /><br />
	                                            <div class="control-group">
	                                                <div class="controls">
	                                                    <button type="button" class="btn btn-primary" id="commitFileContent">提交编辑内容</button>
	                                                    &nbsp;&nbsp;<span class="help-inline" style="color:red">请谨慎编辑提交主题模板</span><br />
                                                        <textarea class="span12" rows="20" data-form="wysihtml5" placeholder="" name="fileContent" id="fileContent">文件内容...
                                                        </textarea>
	                                                </div>
	                                            </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="span3">
<!--                                     <div class="box corner-all"> -->
<!--                                         <div class="box-header corner-top grd-white"> -->
<!--                                               <span><i class="icon-share"></i>发布</span> -->
<!--                                         </div> -->
<!--                                         <div class="box-body"> -->
<!--                                             <div class="control-group"> -->
<!--                                                 <span>&nbsp;<i class="icon-map-marker"></i>&nbsp;状态：<span id="pageStatusSpan"> -->
<!--                                                                                                                                             草稿  -->
<!--                                                 </span> -->
<!--                                                 </span>&nbsp;&nbsp;&nbsp;<a href="#" id="pageStatusEditBtn">编辑</a> -->
<!--                                             </div> -->
<!--                                             <div class="control-group" style="display: none;"> -->
<!--                                                 <select id="pageStatusSelect"> -->
<!--                                                     <option value="pub" selected="selected">已发布</option> -->
<!--                                                     <option value="drt">草稿</option> -->
<!--                                                     <option value="wai">等待复审</option> -->
<!--                                                 </select> -->
<!--                                                 <a href="#" id="pageStatusEditConfirmBtn" class="btn btn-small" >确定</a> -->
<!--                                                     &nbsp;&nbsp;&nbsp; -->
<!--                                                 <a href="#" id="pageStatusEditCancelBtn">取消</a> -->
<!--                                             </div> -->
<!--                                             <div class="control-group"> -->
<!--                                                 <span>&nbsp;<i class="typicn-time"></i>&nbsp;立即发布</span>&nbsp;&nbsp;&nbsp;<a href="#" id="publishDateEdit">编辑</a> -->
<!--                                             </div> -->
<!--                                             <div class="control-group" style="display: none;"> -->
<!--                                                 <label class="control-label" for="inputDate">发布日期</label> -->
<!--                                                 <div class="controls"> -->
<!--                                                     <div class="input-append date" data-form="datepicker" data-date="2012-12-02" data-date-format="yyyy-mm-dd"> -->
<!--                                                         <span class="add-on"><i class="icon-th"></i></span> -->
<!--                                                         <input id="inputDate" class="grd-white span10" data-form="datepicker" size="16" data-date-format="yyyy-mm-dd" type="text" value="" /> -->
<!--                                                     </div> -->
<!--                                                 </div> -->
<!--                                                 <a href="#" id="postDateBtn" class="btn btn-small" >确定</a> -->
<!--                                                 &nbsp;&nbsp;&nbsp; -->
<!--                                                 <a href="#" id="publishDateClose">取消</a> -->
<!--                                             </div> -->
<!--                                             <div class="control-group"> -->
<!--                                                 <button  class="btn btn-primary" type="button" id="publishBtn" onclick="submitForm('','edit')">更&nbsp;&nbsp;新</button> -->
<!--                                             </div>  -->
<!--                                         </div> -->
<!--                                     </div> -->
                                    
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icon-book"></i>&nbsp;选择主题</span>
                                        </div>
                                        <div class="box-body " style="background: url('../img/noise.png');">
                                            <span>默认编辑当前启用主题模板，如需切换主题，请选择菜单：设置=>常规</span><br /><br />
                                            <select id="themeSelect">
                                                <c:forEach items="${themeList }" var="themeName">
                                                    <c:if test="${themeName == currTemplateName}">
                                                        <option value="${themeName }" selected="selected">${themeName }</option>
                                                    </c:if>
                                                    <c:if test="${themeName != currTemplateName}">
                                                        <option value="${themeName }" disabled>${themeName }</option>                                                    
                                                    </c:if>
                                                </c:forEach>
                                                <option value="" disabled>家乐福(清凉夏日，不可选择)</option>
                                            </select>
<!--                                             <a href="#" id="pageStatusEditConfirmBtn" class="btn btn-small" >确定</a> -->
<!--                                                 &nbsp;&nbsp;&nbsp; -->
<!--                                             <a href="#" id="pageStatusEditCancelBtn" class="btn btn-small">取消</a> -->
                                        </div>
                                    </div>
                                    
                                    <div class="box corner-all">
                                        <div class="box-header corner-top grd-white">
                                            <span><i class="icon-list"></i>&nbsp;主题模板</span>
                                        </div>
                                        <div class="box-body " style="background: url('../img/noise.png');">
                                            <ul id="themeFileTree" class="ztree" style="background-color: white;border-style: none;width: 220px;height: 260px;"></ul>
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
