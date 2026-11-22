<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Xác nhận email mới</title>
</head>
<body>
	<div class="container" style="max-width: 420px">
		<form action="${pageContext.request.contextPath}/profile/verify" method="post" class="card card-body">
			<h2 class="mb-3">Xác nhận email mới</h2>
			<p>
				Mã OTP đã được gửi tới <b><c:out value="${sessionScope.pendingEmail.user.email}" /></b>
				(hiệu lực 5 phút). Nhập đúng mã thì email mới được cập nhật.
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
			<button type="submit" name="action" value="verify" class="btn btn-primary w-100 mb-2">Xác nhận</button>
			<button type="submit" name="action" value="resend" class="btn btn-outline-secondary w-100" formnovalidate>Gửi lại mã</button>
			<p class="mt-3 mb-0 text-center">
				Sai email? <a href="${pageContext.request.contextPath}/profile">Quay lại hồ sơ</a>
			</p>
		</form>
	</div>
</body>
</html>
