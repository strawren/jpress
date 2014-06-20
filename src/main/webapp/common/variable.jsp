
<%
	String rootPath = (String)application.getAttribute("rootPath");
	String adminPath = (String)application.getAttribute("adminPath");

	
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + rootPath;
    String ctxAdmin = basePath + adminPath;
	String errorMsgColor="red";
	request.setAttribute("ctx", basePath);
	request.setAttribute("ctxAdmin", ctxAdmin);
%>
