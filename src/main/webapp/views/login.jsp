<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Đăng nhập</title>
<%@ include file="head.jsp"%>
</head>
<body>
	<%@ include file="topbar.jsp"%>
	<div class="container" style="max-width: 420px">
		<form action="${pageContext.request.contextPath}/login" method="post" class="card card-body">
			<h2 class="mb-3">Đăng nhập</h2>
			<c:if test="${alert != null}">
				<div class="alert alert-danger">${alert}</div>
			</c:if>
			<c:if test="${param.registered != null}">
				<div class="alert alert-success">Đăng ký thành công, mời bạn đăng nhập.</div>
			</c:if>
			<div class="mb-3">
				<input type="text" placeholder="Tài khoản" name="username" class="form-control"
					value="<c:out value='${param.username}'/>">
			</div>
			<div class="mb-3">
				<input type="password" placeholder="Mật khẩu" name="password" class="form-control">
			</div>
			<div class="form-check mb-3">
				<input class="form-check-input" type="checkbox" name="remember" id="remember">
				<label class="form-check-label" for="remember">Nhớ tôi (lưu cookie 30 phút)</label>
			</div>
			<button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
			<p class="mt-3 mb-0 text-center">
				Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
			</p>
		</form>
	</div>
</body>
</html>
