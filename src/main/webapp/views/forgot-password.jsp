<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Quên mật khẩu</title>
<%@ include file="head.jsp"%>
</head>
<body>
	<%@ include file="topbar.jsp"%>
	<div class="container" style="max-width: 420px">
		<form action="${pageContext.request.contextPath}/forgot-password" method="post" class="card card-body">
			<h2 class="mb-3">Quên mật khẩu</h2>
			<p>Nhập email đã đăng ký, chúng tôi sẽ gửi mã OTP để bạn đặt lại mật khẩu.</p>
			<c:if test="${alert != null}">
				<div class="alert alert-danger">${alert}</div>
			</c:if>
			<div class="mb-3">
				<input type="email" name="email" class="form-control" placeholder="Email" required
					value="<c:out value='${param.email}'/>">
			</div>
			<button type="submit" class="btn btn-primary w-100">Gửi mã OTP</button>
			<p class="mt-3 mb-0 text-center">
				<a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
			</p>
		</form>
	</div>
</body>
</html>
