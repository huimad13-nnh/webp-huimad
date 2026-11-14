<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Sửa danh mục</title>
<%@ include file="../head.jsp"%>
</head>
<body>
	<%@ include file="../topbar.jsp"%>
	<div class="container" style="max-width: 560px">
		<h2>Sửa danh mục</h2>
		<c:if test="${alert != null}">
			<div class="alert alert-danger">${alert}</div>
		</c:if>
		<c:url value="/admin/category/edit" var="edit" />
		<form role="form" action="${edit}" method="post" enctype="multipart/form-data">
			<input name="cateid" value="${category.cateid}" type="hidden">
			<div class="mb-3">
				<label class="form-label">Tên danh mục:</label>
				<input type="text" class="form-control" name="catename" required
					value="<c:out value='${category.catename}'/>" />
			</div>
			<div class="mb-3">
				<c:if test="${not empty category.icon}">
					<c:url value="/image?fname=${category.icon}" var="imgUrl" />
					<img class="d-block mb-2" width="100" src="${imgUrl}" alt="">
				</c:if>
				<label class="form-label">Ảnh đại diện (bỏ trống nếu giữ ảnh cũ)</label>
				<input class="form-control" type="file" name="icon" accept="image/*" />
			</div>
			<button type="submit" class="btn btn-primary">Cập nhật</button>
			<button type="reset" class="btn btn-secondary">Reset</button>
			<a href="<c:url value='/admin/category/list'/>" class="btn btn-link">Quay lại</a>
		</form>
	</div>
</body>
</html>
