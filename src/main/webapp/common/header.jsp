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
									<li><a href="#" title=""><i class="icon-user"></i>Profile</a></li>
									<li><a href="#" title=""><i class="icon-inbox"></i>Messages<span class="badge badge-info">9</span></a></li>
									<li><a href="#" title=""><i class="icon-cog"></i>Settings</a></li>
									<li><a href="#" title=""><i class="icon-remove"></i>Logout</a></li>
								</ul>
							</li>
						</ul>
                    </div>
                    
                </div>
            </div><!--/nav bar helper-->
        </header>