<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/variable.jsp"%>

		<!-- section header -->
        <header class="header">
            <!--nav bar helper-->
            <div class="navbar-helper">
                <div class="row-fluid">
                    <!--panel site-name-->
                    <div class="span2">
                        <div class="panel-sitename">
                            <h2><a href="${ctxAdmin }/index.action"><img src="${ctx }/images/logo.png" width="140" style="margin-right:50px;"></a></h2>
                        </div>
                    </div>
                    <!--/panel name-->

                    <div class="span6">
                    	<div class="panel-sitename pull-left">
                            <h2><img src="${ctx }/images/logo-banner.png" style="margin-left:0px;"></h2>
                        </div>
                    </div>
                    
                    <div class="span4">
                    	<ul class="top-menu">
							<li><a class="fullview"></a></li>
							<li><a class="showmenu"></a></li>
							<li><a href="#" title="" class="messages"><i class="new-message"></i></a></li>
							<li class="dropdown" style="border-right: 0px; ">
								<a class="user-menu" data-toggle="dropdown"><img src="${ctx }/images/userpic.png" alt="" /><span>${user.loginName }<b class="caret"></b></span></a>
								<ul class="dropdown-menu">
									<li><a href="#" title="消息"><i class="icon-inbox"></i> 消息 <span class="badge badge-info">0</span></a></li>
									<li><a href="${ctxAdmin }/user/view.action?id=${user.id}" title="个人信息"><i class="icon-user"></i> 个人信息</a></li>
									<li><a href="${ctxAdmin }/setting/view.action" title="设置"><i class="icon-cog"></i> 设置</a></li>
									<li><a href="${ctxAdmin }/logout.action" title="退出"><i class="icon-remove"></i> 退出</a></li>
								</ul>
							</li>
						</ul>
                    </div>
                    
                </div>
            </div><!--/nav bar helper-->
        </header>