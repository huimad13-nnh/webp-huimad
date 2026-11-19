<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Layout chung. <sitemesh:write> do SiteMesh xử lý, không cần khai báo taglib. --%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title><sitemesh:write property="title" /></title>
<%@ include file="/views/head.jsp"%>
<sitemesh:write property="head" />
</head>
<body>
	<%@ include file="/views/topbar.jsp"%>
	<sitemesh:write property="body" />
	<footer class="container text-muted border-top mt-4 py-3">Learn JPA</footer>
</body>
</html>
