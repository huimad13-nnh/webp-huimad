<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Trang chủ</title>
<%@ include file="head.jsp"%>
</head>
<body>
	<%@ include file="topbar.jsp"%>
	<div class="container">
		<h2>Trang: ${pageContext.request.servletPath}</h2>
		<c:choose>
			<c:when test="${sessionScope.account == null}">
				<p>Bạn chưa đăng nhập.</p>
			</c:when>
			<c:otherwise>
				<table class="table w-auto">
					<tr><th>Username</th><td><c:out value="${sessionScope.account.userName}" /></td></tr>
					<tr><th>Họ tên</th><td><c:out value="${sessionScope.account.fullName}" /></td></tr>
					<tr><th>Email</th><td><c:out value="${sessionScope.account.email}" /></td></tr>
					<tr><th>Role</th><td>${sessionScope.account.roleid}
						(${sessionScope.account.roleid == 1 ? 'admin' : sessionScope.account.roleid == 2 ? 'manager' : 'user'})</td></tr>
					<tr><th>Session ID</th><td>${pageContext.session.id}</td></tr>
					<tr><th>Cookie remember</th><td>${cookie.username != null ? cookie.username.value : '(không có)'}</td></tr>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
