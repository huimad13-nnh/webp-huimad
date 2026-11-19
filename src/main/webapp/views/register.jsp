<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Đăng ký</title>
</head>
<body>
	<div class="container" style="max-width: 480px">
		<form action="${pageContext.request.contextPath}/register" method="post" class="card card-body">
			<h2 class="mb-3">Tạo tài khoản mới</h2>
			<c:if test="${alert != null}">
				<div class="alert alert-danger">${alert}</div>
			</c:if>
			<div class="mb-3">
				<input type="text" placeholder="Tài khoản" name="username" class="form-control" required
					minlength="3" maxlength="50"
					value="<c:out value='${param.username}'/>">
			</div>
			<div class="mb-3">
				<input type="password" placeholder="Mật khẩu" name="password" class="form-control" required
					minlength="3" maxlength="255" autocomplete="new-password">
			</div>
			<div class="mb-3">
				<input type="email" placeholder="Email" name="email" class="form-control" required maxlength="255"
					value="<c:out value='${param.email}'/>">
			</div>
			<div class="mb-3">
				<input type="text" placeholder="Họ tên" name="fullname" class="form-control" maxlength="255"
					value="<c:out value='${param.fullname}'/>">
			</div>
			<div class="mb-3">
				<input type="text" placeholder="Số điện thoại" name="phone" class="form-control"
					pattern="\d{9,11}" title="Số điện thoại gồm 9-11 chữ số" inputmode="numeric"
					value="<c:out value='${param.phone}'/>">
			</div>
			<button type="submit" class="btn btn-success w-100">Đăng ký</button>
			<p class="mt-3 mb-0 text-center">
				Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
			</p>
		</form>
	</div>
</body>
</html>
