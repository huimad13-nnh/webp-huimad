<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Sửa video</title>
<%@ include file="../head.jsp"%>
</head>
<body>
	<%@ include file="../topbar.jsp"%>
	<div class="container" style="max-width: 560px">
		<h2>Sửa video</h2>
		<c:if test="${alert != null}">
			<div class="alert alert-danger">${alert}</div>
		</c:if>
		<form action="<c:url value='/video/edit'/>" method="post" enctype="multipart/form-data">
			<input name="id" value="${video.id}" type="hidden">
			<div class="mb-3">
				<label class="form-label" for="title">Tiêu đề</label>
				<input class="form-control" id="title" name="title" required maxlength="255"
					value="<c:out value='${video.title}'/>" />
			</div>
			<div class="mb-3">
				<label class="form-label" for="description">Mô tả</label>
				<textarea class="form-control" id="description" name="description" rows="4"><c:out value="${video.description}" /></textarea>
			</div>
			<div class="mb-3">
				<c:url value="/image?fname=${video.url}" var="videoUrl" />
				<video class="d-block mb-2 w-100" src="${videoUrl}" controls preload="metadata"></video>
				<label class="form-label" for="file">File video mới (bỏ trống nếu giữ video cũ)</label>
				<input class="form-control" id="file" type="file" name="file" accept="video/*" />
			</div>
			<button type="submit" class="btn btn-primary">Cập nhật</button>
			<a href="<c:url value='/video'/>" class="btn btn-link">Quay lại</a>
		</form>
	</div>
</body>
</html>
