<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Thêm danh mục</title>
</head>
<body>
	<div class="container" style="max-width: 560px">
		<h2>Thêm danh mục</h2>
		<c:if test="${alert != null}">
			<div class="alert alert-danger">${alert}</div>
		</c:if>
		<form role="form" action="<c:url value='/admin/category/add'/>" method="post" enctype="multipart/form-data">
			<div class="mb-3">
				<label class="form-label">Tên danh mục:</label>
				<input class="form-control" placeholder="Nhập tên danh mục" name="catename" required />
			</div>
			<div class="mb-3">
				<label class="form-label">Ảnh đại diện</label>
				<input class="form-control" type="file" name="icon" accept="image/*" />
			</div>
			<button type="submit" class="btn btn-primary">Thêm</button>
			<button type="reset" class="btn btn-secondary">Huỷ</button>
			<a href="<c:url value='/admin/category/list'/>" class="btn btn-link">Quay lại</a>
		</form>
	</div>
</body>
</html>
