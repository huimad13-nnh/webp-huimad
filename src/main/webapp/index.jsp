<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Learn JPA</title>
</head>
<body>
	<div class="container">
		<h2>Learn JPA: Login/Register + CRUD Category (Hibernate + Validation)</h2>
		<ul>
			<li><a href="${ctx}/login">Đăng nhập (Session + Cookie)</a></li>
			<li><a href="${ctx}/register">Đăng ký</a></li>
			<li><a href="${ctx}/admin/category/list">CRUD Category (cần đăng nhập admin)</a></li>
			<li><a href="${ctx}/home">Trang chủ: 10 video mới nhất</a></li>
			<li><a href="${ctx}/video">Video của tôi: CRUD + phân trang (cần đăng nhập)</a></li>
		</ul>
		<p class="text-muted">Tài khoản mẫu: admin/123, manager/123, user/123</p>
	</div>
</body>
</html>
