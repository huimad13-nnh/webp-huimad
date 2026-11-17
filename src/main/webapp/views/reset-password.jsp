<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Đặt lại mật khẩu</title>
<%@ include file="head.jsp"%>
</head>
<body>
	<%@ include file="topbar.jsp"%>
	<div class="container" style="max-width: 420px">
		<form action="${pageContext.request.contextPath}/reset-password" method="post" class="card card-body">
			<h2 class="mb-3">Đặt lại mật khẩu</h2>
			<p>
				Mã OTP đã được gửi tới <b><c:out value="${sessionScope.pendingReset.user.email}" /></b>
				(hiệu lực 5 phút).
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
			<div class="mb-3">
				<input type="password" name="password" class="form-control" placeholder="Mật khẩu mới" required
					autocomplete="new-password">
			</div>
			<div class="mb-3">
				<input type="password" name="confirm" class="form-control" placeholder="Nhập lại mật khẩu mới" required
					autocomplete="new-password">
			</div>
			<button type="submit" name="action" value="reset" class="btn btn-primary w-100 mb-2">Đổi mật khẩu</button>
			<!-- formnovalidate: gửi lại mã không cần điền các ô trên -->
			<button type="submit" name="action" value="resend" class="btn btn-outline-secondary w-100" formnovalidate>Gửi lại mã</button>
			<p class="mt-3 mb-0 text-center">
				Sai email? <a href="${pageContext.request.contextPath}/forgot-password">Nhập lại</a>
			</p>
		</form>
	</div>
</body>
</html>
