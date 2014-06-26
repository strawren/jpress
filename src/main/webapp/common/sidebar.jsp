<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/variable.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
    	var curMenu = "${menu}";
    	if(curMenu == null || curMenu == '') {
    		curMenu = "menu_home";
    	}
    	
    	var moduleNameSpan = $("#moduleNameSpan");
    	var moduleNameSpanText = "";
    	switch (curMenu) {
		case "menu_home":
			moduleNameSpanText="仪表盘";
			break;
		case "menu_content":
            moduleNameSpanText="文章管理";
            break;
		case "menu_multimedia":
            moduleNameSpanText="图片管理";
            break;
		case "menu_links":
            moduleNameSpanText="链接管理";
            break;
		case "menu_page":
            moduleNameSpanText="页面管理";
            break;
		case "menu_statistics":
            moduleNameSpanText="页面管理";
            break;
		case "menu_outward":
            moduleNameSpanText="外观管理";
            break;
		case "menu_user":
            moduleNameSpanText="用户管理";
            break;
		case "menu_settings":
            moduleNameSpanText="系统设置";
            break;
		default:
			moduleNameSpanText="仪表盘";
			break;
		}
    	moduleNameSpan.text(moduleNameSpanText);
    	
    	$("#" + curMenu).attr("class","active first");
	});
</script>

                    <!--side bar-->
                    <aside class="side-left">
                        <ul class="sidebar">
                            <li id="menu_home" >
                                <a href="${ctxAdmin}/index.action?menu=menu_home" title="仪表盘">
                                    <div class="helper-font-24">
                                        <i class="icofont-dashboard"></i>
                                    </div>
                                    <span class="sidebar-text">仪表盘</span>
                                </a>
                            </li>
                            
                            <li id="menu_content">
                                <a href="${ctx }/cms/article/list.action?menu=menu_content" title="内容">
                                    <div class="helper-font-24">
                                        <i class="icofont-edit"></i>
                                    </div>
                                    <span class="sidebar-text">文章</span>
                                </a>
                                <ul class="sub-sidebar-form corner-top shadow-white">
                                    <li>
                                        <a href="${ctxAdmin}/article/list.action?menu=menu_content&postStatus=all" title="所有内容" class="corner-all">
                                            <i class="icofont-file"></i>
                                            <span class="sidebar-text">所有文章</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/article/add_show.action?menu=menu_content" title="新写内容" class="corner-all">
                                            <i class="icofont-book"></i>
                                            <span class="sidebar-text">新增文章</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/taxonomy/taxonomy_index.action?menu=menu_content" title="分类目录" class="corner-all">
                                            <i class="icon-folder-open"></i>
                                            <span class="sidebar-text">文章分类管理</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/tag/tag_index.action?menu=menu_content" title="标签" class="corner-all">
                                            <i class="icon-tags"></i>
                                            <span class="sidebar-text">文章标签管理</span>
                                        </a>
                                    </li>
                                     <li>
                                        <a href="${ctxAdmin}/taxonomy/sys_term_add.action?menu=menu_content" title="属性" class="corner-all">
                                            <i class=" icon-tasks"></i>
                                            <span class="sidebar-text">文章属性管理</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            
                            <li id="menu_multimedia">
                                <a href="${ctxAdmin}/media/index.action?menu=menu_multimedia" title="form">
                                    <div class="helper-font-24">
                                        <i class="icofont-camera-retro"></i>
                                    </div>
                                    <span class="sidebar-text">图片</span>
                                </a>
                                <%-- <ul class="sub-sidebar-form corner-top shadow-white">
                                    <li>
                                        <a href="${ctxAdmin}/media/index.action?menu=menu_multimedia" title="媒体库" class="corner-all">
                                            <i class=" icofont-retweet"></i>
                                            <span class="sidebar-text">媒体库</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/media/index.action?menu=menu_multimedia" title="添加" class="corner-all">
                                            <i class=" icofont-plus-sign"></i>
                                            <span class="sidebar-text">添加</span>
                                        </a>
                                    </li>
                                </ul> --%>
                            </li>
                            
                            <li id="menu_links">
                                <a href="${ctxAdmin}/link/all.action?menu=menu_links" title="链接">
                                    <div class="helper-font-24">
                                        <i class="icofont-link"></i>
                                    </div>
                                    <span class="sidebar-text">链接</span>
                                </a>
                                <ul class="sub-sidebar-form corner-top shadow-white">
                                    <li>
                                        <a href="${ctxAdmin}/link/all.action?menu=menu_links" title="全部链接" class="corner-all">
                                            <i class="icofont-external-link"></i>
                                            <span class="sidebar-text">全部链接</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/link/new.action?menu=menu_links" title="添加" class="corner-all">
                                            <i class="icofont-plus"></i>
                                            <span class="sidebar-text">添加</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/link/category.action?menu=menu_links" title="链接分类" class="corner-all">
                                            <i class=" icon-list-alt"></i>
                                            <span class="sidebar-text">链接分类</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            
                            <li id="menu_page">
                                <a href="${ctxAdmin}/page/all.action?menu=menu_page" title="页面">
                                    <div class="helper-font-24">
                                        <i class="icofont-pencil"></i>
                                    </div>
                                    <span class="sidebar-text">页面</span>
                                </a>
                                <ul class="sub-sidebar-form corner-top shadow-white">
                                    <li>
                                        <a href="${ctxAdmin}/page/all.action?menu=menu_page" title="所有页面" class="corner-all">
                                            <i class="icon-th-large"></i>
                                            <span class="sidebar-text">所有页面</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/page/new.action?menu=menu_page" title="新建页面" class="corner-all">
                                            <i class="icon-tags"></i>
                                            <span class="sidebar-text">新建页面</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            
                            <li id="menu_comment">
                                <a href="${ctxAdmin}/comment/list.action?menu=menu_comment" title="评论">
                                    <div class="helper-font-24">
                                        <i class="icofont-edit"></i>
                                    </div>
                                    <span class="sidebar-text">评论</span>
                                </a>
                            </li>
                            
                            <li id="menu_outward">
                                <a href="${ctxAdmin}/outward/theme.action?menu=menu_outward" title="外观">
                                    <div class="helper-font-24">
                                        <i class="icofont-align-justify"></i>
                                    </div>
                                    <span class="sidebar-text">外观</span>
                                </a>
                                <ul class="sub-sidebar-form corner-top shadow-white">
                                    <li>
                                        <a href="${ctxAdmin}/outward/theme.action?menu=menu_outward" title="主题" class="corner-all">
                                            <i class="icofont-text-height"></i>
                                            <span class="sidebar-text">主题</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/outward/menu.action?menu=menu_outward" title="菜单" class="corner-all">
                                            <i class="icofont-film"></i>
                                            <span class="sidebar-text">菜单</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/outward/static.action?menu=menu_outward" title="静态化" class="corner-all">
                                            <i class="icofont-lemon"></i>
                                            <span class="sidebar-text">静态化</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            
                            <li id="menu_user">
                                <a href="${ctxAdmin}/user/list.action?menu=menu_user" title="用户">
                                    <div class="helper-font-24">
                                        <i class="icofont-user"></i>
                                    </div>
                                    <span class="sidebar-text">用户</span>
                                </a>
                                <ul class="sub-sidebar-form corner-top shadow-silver-dark">
                                	<li>
                                        <a href="${ctxAdmin}/user/list.action?menu=menu_user" title="所有用户">
                                            <i class="icofont-list-alt"></i>
                                            <span class="sidebar-text">所有用户</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/user/edit.action?menu=menu_user" title="添加用户">
                                            <i class="icofont-plus-sign"></i>
                                            <span class="sidebar-text">添加用户</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/user/view.action?menu=menu_user&id=${user.id}" title="我的资料">
                                            <i class="icofont-user-md"></i>
                                            <span class="sidebar-text">我的资料</span>
                                        </a>
                                    </li>
	                                <li>
                                        <a href="${ctxAdmin}/page.action?menu=menu_user&page=/user/pwd" title="密码修改">
                                            <i class="icofont-paste"></i>
                                            <span class="sidebar-text">密码修改</span>
                                         </a>
                                     </li>
                                </ul>
                            </li>
                            
                            
                            <li id="menu_settings">
                                <a href="${ctxAdmin}/settings/regular.action?menu=menu_settings" title="设置">
                                    <div class="helper-font-24">
                                        <i class="icofont-th-large"></i>
                                    </div>
                                    <span class="sidebar-text">设置</span>
                                </a>
                                <ul class="sub-sidebar-form corner-top shadow-silver-dark">
                                    <li>
                                        <a href="${ctxAdmin}/settings/regular.action?menu=menu_settings" title="常规">
                                            <i class="icofont-check"></i>
                                            <span class="sidebar-text">常规</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctxAdmin}/settings/compose.action?menu=menu_settings" title="撰写">
                                            <i class="icofont-paste"></i>
                                            <span class="sidebar-text">撰写</span>
                                        </a>
                                    </li>
<!--                                     <li> -->
<%--                                         <a href="${ctxAdmin}/user/multimedia.action?menu=menu_settings" title="多媒体"> --%>
<!--                                             <div class="helper-font-24"> -->
<!--                                                 <i class="icofont-random"></i> -->
<!--                                             </div> -->
<!--                                             <span class="sidebar-text">多媒体</span> -->
<!--                                         </a> -->
<!--                                     </li> -->
                                </ul>
                            </li>
                        </ul>
                    </aside><!--/side bar -->