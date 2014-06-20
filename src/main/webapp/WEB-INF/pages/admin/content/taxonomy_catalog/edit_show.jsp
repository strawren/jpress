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
                            <div class="row-fluid">
                                <div class="span12">
                                    <div id="element" class="row-fluid">
                                        <!--span-->
                                        <div class="span12">
                                            <!--box-->
                                            <div class="box corner">
                                                <!--box header-->
                                                <div class="box-header grd-white color-silver-dark corner-top">
                                                    <span><i class="icon-edit"></i>&nbsp;编辑分类目录</span>
                                                </div><!--/box header-->
                                                <!--box body-->
                                                <div class="box-body">
                                                	<form class="form-horizontal" action="${ctx }/cms/taxonomy/term_tax_update.html" method="post">
                                                        <input name="termId" type="hidden" value="${term.id }"/>
                                                         <input name="createTime" type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${term.createTime}"/>" />
                                                        <input name="createOperId" type="hidden" value="${term.createOperId }"/>
                                                        <input name="createOperName" type="hidden" value="${term.createOperName }"/>
                                                        <input name="lastUpdTime" type="hidden" value="<fmt:formatDate value='${term.lastUpdTime }' pattern='yyyy-MM-dd HH:mm:ss' />"/>
                                                        <input name="lastUpdOperId" type="hidden" value="${term.lastUpdOperId }"/>
                                                        <input name="lastUpdOperName" type="hidden" value="${term.lastUpdOperName }"/>
                                                        <input name="taxLastUpdTime" type="hidden" value="<fmt:formatDate value='${term.taxLastUpdTime }' pattern='yyyy-MM-dd HH:mm:ss' />"/>
                                                        <div class="control-group">
                                                            <label class="control-label" for="inputAuto">名称</label>
                                                            <div class="controls">
                                                            <input type="text" class="grd-white" name="name" value="${term.name }"/><br/>
                                                                                                                                                                    这将是它在站点上显示的名字。
                                                            </div>
                                                        </div>
                                                        <p>
                                                        <div class="control-group">
                                                            <label class="control-label">别名</label>
                                                            <div class="controls">
                                                                <input type="text" class="grd-white" name="slug" value="${term.slug }" /><br/>
                                                                “别名”是在URL中使用的别称，它可以令URL更美观。通常使用小写，只能包含字母，数字和连字符（-）。
                                                            </div>
                                                        </div>
                                                        <p>
                                                        <div class="control-group" id="menuContentDiv">  
                                                            <label class="control-label">父级</label>                             
                                                            <div class="controls">
                                                            <input id="parentPageInput" type="text" readonly value="${termName }" style="width:120px;" id="menuBtn" href="#" onclick="showMenu(); return false;"/><br/>
                                                            <input id="parentId" name="parentId" type="hidden" value="${term.parentId }" />
                                                            <input id="id" name="id" type="hidden" value="${term.id }" />
                                                                                                                                                                分类目录和标签不同，它可以有层级关系。您可以有一个“音乐”分类目录，在这个目录下可以有叫做“流行”和“古典”的子目录。
                                                            <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
                                                                <ul id="parentPageTree" class="ztree" style="margin-top:0; width:120px;"></ul>
                                                            </div>
                                                            </div>
                                                        </div>
                                                        <div class="control-group">
                                                            <label class="control-label">描述</label>
                                                            <div class="controls">
                                                                <textarea id="inputEditorSimple" name="miscDesc" class="span10" rows="6" data-form="wysihtml5" placeholder="">${term.miscDesc }</textarea><br/>
                                                                                                                                                              描述只会在一部分主题中显示。
                                                            </div>
                                                        </div>
                                                        
                                                        
                                                        <div class="form-actions">
                                                            <button type="submit" class="btn btn-primary">保存修改</button>
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
        
    </body>
</html>

