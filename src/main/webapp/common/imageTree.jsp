<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

 
<!-- Modal -->
<div id="browseImageModal" class="modal hide fade" style="width: 800px;" tabindex="-1" role="dialog" aria-labelledby="browseImageModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="browseImageModalLabel">浏览图片</h3>
  </div>
  <div class="modal-body">
    <div class="container-fluid">
      <div class="row-fluid" style="height: 400px;">
        <div class="span6">
          <ul id="imageTree" class="ztree" style="width:350px;height: 400px;"></ul>
        </div>
        <div class="span6 well" id="imageViewDiv" style="overflow:auto;height: 400px;">
            
        </div>
      </div>
    </div>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    <button class="btn btn-primary" id="selectImageBtn">确定</button>
  </div>
</div>
<script>
 
    $(function(){
    	$.ajax("${ctx}/cms/media/imageTree.html")
    	.done(function(imageTree){
    		initTree(imageTree);
    	});
    	
    	$("#selectImageBtn").on('click',function(){
    		var treeObj = $.fn.zTree.getZTreeObj("imageTree");
    		var nodes = treeObj.getSelectedNodes();
    		if(nodes&&nodes.length>0){
    			$("#browseImageModal").modal('hide');
    			$(document).trigger("itemImages",[itemImages]);
    		}else{
    			alert("请至少选择一个目录!");
    		}
    	});
    	
    });
    
    function initTree(imageTree){
    	var setting = {
    			callback: {
    				beforeClick: function(treeId, treeNode, clickFlag){
    					return !treeNode.isParent;
    				},
    				onClick: function(event, treeId, treeNode){
    					showImage(treeNode.id);
    				}
                }
    	};
        var zNodes = imageTree;
        var imageTree = $.fn.zTree.init($("#imageTree"), setting, zNodes);
        imageTree.expandAll(true);
        
        var node = imageTree.getNodeByParam("isParent", false, null);
        imageTree.selectNode(node);
        imageTree.setting.callback.onClick(null, imageTree.setting.treeId, node);
    }
    
    var itemImages = [];
    function showImage(path){
    	$.ajax({
    		   type: "POST",
    		   url: "${ctx}/cms/media/getImagesByPath.html",
    		   data: "path="+path
    		})
        .done(function(images){
        	itemImages = images;
        	$("#imageViewDiv").empty();
            for (var index = 0; index < images.length; index++) {
				var imagePath = images[index];
				var splitArray = imagePath.split("/");
				var imageName = splitArray[splitArray.length-1];
				var html = '<a class="thumbnail">'+imageName+'<img src="${mediaUrl}'+imagePath+'" style="width: 260px; height: 180px;"></a>';
				$("#imageViewDiv").append(html);
			}
        });
    }
</script>