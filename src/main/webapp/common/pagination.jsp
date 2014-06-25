<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="row-fluid">
    <div class="span6">
        <div class="dataTables_info" id="datatablestools_info">共<span id="totalPagesSpan">0</span>页,<span id="totalCountSpan">0</span>条记录,每页<span id="numPerPageSpan">10</span>条,当前第<span id="currentPageSpan">0</span>页</div>
    </div>
    <div class="span6">
        <div class="dataTables_paginate paging_bootstrap pagination">
            <ul id="pagination">
                <li class="prev" id="pagePrev"><a href="javascript:void(0);">&laquo;</a></li>
                <li class="next" id="pageNext"><a href="javascript:void(0);">» </a></li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
    	var totalCount = ${page.totalCount};
    	if (!totalCount) {
    		totalCount=0;
		}
    	$("#totalCountSpan").text(totalCount);
    	var numPerPage = ${page.numPerPage};
    	if (!numPerPage) {
    		numPerPage=10;
        }
    	$("#numPerPageSpan").text(numPerPage);
    	var currentPage = ${page.pageNum};
    	if (!currentPage) {
    		currentPage=0;
        }
    	$("#currentPageSpan").text(currentPage);
    	var totalPages = ${page.totalPages};
    	if (!totalPages) {
    		totalPages=0;
        }
    	$("#totalPagesSpan").text(totalPages);
    	
    	$("#pagePrev").attr("data-page-num",currentPage-1);
    	$("#pageNext").attr("data-page-num",currentPage+1);
    	
    	if (currentPage==1) {
			$("#pagePrev").addClass("disabled");
		}
    	
    	if (currentPage==totalPages) {
            $("#pageNext").addClass("disabled");
        }
    	
    	for (var i = currentPage-4; i <= totalPages; i++) {
    		if (i<1) {
				continue;
			}
    		if (i>(currentPage+5)) {
				break;
			}
    		var element = $('<li data-page-num="'+i+'"><a href="javascript:void(0);">'+i+'</a></li>');
    		element.insertBefore("#pageNext");
    		
    		if (i==currentPage) {
    			element.addClass("active");
			}
		}
    	
    	$("#pagination").find("li").on("click",function(){
            var pageNum = $(this).attr("data-page-num");
            if (pageNum != currentPage && pageNum > 0 && pageNum <= totalPages) {
                $(document).trigger("page.change", pageNum);
            }
        });
    	
    	if (totalPages<1) {
			$("#pagination").hide();
		}
	});
</script>
