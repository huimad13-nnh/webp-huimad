<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Hồ sơ của tôi</title>
</head>
<body>
	<div class="container" style="max-width: 560px">
		<h2>Hồ sơ của tôi</h2>
		<c:if test="${info != null}">
			<div class="alert alert-success">${info}</div>
		</c:if>
		<c:if test="${alert != null}">
			<div class="alert alert-danger">${alert}</div>
		</c:if>
		<form action="<c:url value='/profile'/>" method="post" enctype="multipart/form-data" class="card card-body">
			<div class="mb-3 text-center">
				<c:choose>
					<c:when test="${not empty profile.avatar}">
						<img src="<c:out value='${profile.avatar}'/>" alt="Ảnh đại diện" class="rounded-circle border"
							style="width: 120px; height: 120px; object-fit: cover">
					</c:when>
					<c:otherwise>
						<div class="rounded-circle border bg-light d-inline-block" style="width: 120px; height: 120px"></div>
					</c:otherwise>
				</c:choose>
				<div class="mt-2"><b><c:out value="${profile.userName}" /></b></div>
			</div>
			<div class="mb-3">
				<label class="form-label" for="avatar">Ảnh đại diện mới (bỏ trống nếu giữ ảnh cũ, tối đa 5MB)</label>
				<input class="form-control" id="avatar" type="file" name="avatar" accept=".jpg,.jpeg,.png,.gif,.webp" />
			</div>
			<div class="mb-3">
				<label class="form-label" for="fullname">Họ tên</label>
				<input class="form-control" id="fullname" name="fullname" maxlength="255"
					value="<c:out value='${profile.fullName}'/>" />
			</div>
			<div class="mb-3">
				<label class="form-label" for="phone">Số điện thoại</label>
				<input class="form-control" id="phone" name="phone" pattern="\d{9,11}" inputmode="numeric"
					value="<c:out value='${profile.phone}'/>" />
			</div>
			<div class="mb-3">
				<label class="form-label" for="email">Email</label>
				<input class="form-control" id="email" type="email" name="email" required
					value="<c:out value='${profile.email}'/>" />
				<div class="form-text">Đổi email: hệ thống gửi mã OTP tới email mới, nhập đúng mã mới đổi.</div>
			</div>
			<button type="submit" class="btn btn-primary">Cập nhật</button>
		</form>
	</div>
</body>
</html>
