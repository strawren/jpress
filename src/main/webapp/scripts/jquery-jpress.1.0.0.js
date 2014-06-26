            //全选、删除、分页公用js
			$(function(){
	            $("#selectAllChkBox").click(function() {      
	                var flag = $("#selectAllChkBox").is(':checked');
	                $("input[id^='list_item_']").each(function () {
	                	$(this).attr("checked", flag);
	                });
	            });
	        	
	            $("input[id^='list_item_']").each(function() {
	                $(this).click(function() {
	                    if ($("input[id^='list_item_']:checked").length == $("input[id^='list_item_']").length) {
	                        $("#selectAllChkBox").attr("checked", "checked");
	                    }
	                    else {
	                    	$("#selectAllChkBox").removeAttr("checked");
	                    }
	                });
	            });
	            
	          	//删除确认
	            $(".batchDeleteBtn").confirm({
    				'title' : '删除确认',
    				'message' : '删除后，数据无法再恢复，您确认删除吗？',
    				'action' : function(){$("#listForm4Del").submit();},
    				'preAction' : function() {
    					var flag = true;
    					if($("input[id^='list_item_']:checked").length == 0){
    						flag = false;
    						$.alert("", "您还没有选中要删除的信息！");
    					}
    					return flag;
    				}
	            });
	            
	            //删除确认
	            $(".deleteOneItem").confirm({
    				'title' : '删除确认',
    				'message' : '删除后，数据无法再恢复，您确认删除吗？',
    				'action' : ''
	            });
				
	        });
			$(document).on("page.change",function(event,pagenum){
			     $("#pageNum").val(pagenum);
			     $("#searchForm").submit();
			});