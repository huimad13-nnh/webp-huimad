<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Kích hoạt tài khoản</title>
<%@ include file="head.jsp"%>
</head>
<body>
	<%@ include file="topbar.jsp"%>
	<div class="container" style="max-width: 420px">
		<form action="${pageContext.request.contextPath}/verify" method="post" class="card card-body">
			<h2 class="mb-3">Kích hoạt tài khoản</h2>
			<p>
				Mã OTP đã được gửi tới <b><c:out value="${sessionScope.pendingRegister.user.email}" /></b>
				(hiệu lực 5 phút). Nhập đúng mã thì tài khoản
				<b><c:out value="${sessionScope.pendingRegister.user.userName}" /></b> mới được tạo.
			</p>
			<c:if test="${info != null}">
				<div class="alert alert-success">${info}</div>
			</c:if>
			<c:if test="${alert != null}">
				<div class="alert alert-danger">${alert}</div>
			</c:if>
			<div class="mb-3">
				<input type="text" name="otp" class="form-control" placeholder="Mã OTP 6 số" required
					maxlength="6" pattern="\d{6}" inputmode="numeric" autocomplete="one-time-code">
			</div>
			<button type="submit" name="action" value="verify" class="btn btn-primary w-100 mb-2">Kích hoạt</button>
			<!-- formnovalidate: gửi lại mã không cần nhập ô OTP -->
			<button type="submit" name="action" value="resend" class="btn btn-outline-secondary w-100" formnovalidate>Gửi lại mã</button>
			<p class="mt-3 mb-0 text-center">
				Sai email? <a href="${pageContext.request.contextPath}/register">Đăng ký lại</a>
			</p>
		</form>
	</div>
</body>
</html>
