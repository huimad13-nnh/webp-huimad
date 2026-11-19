<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Thêm video</title>
</head>
<body>
	<div class="container" style="max-width: 560px">
		<h2>Thêm video</h2>
		<c:if test="${alert != null}">
			<div class="alert alert-danger">${alert}</div>
		</c:if>
		<form action="<c:url value='/video/add'/>" method="post" enctype="multipart/form-data">
			<div class="mb-3">
				<label class="form-label" for="title">Tiêu đề</label>
				<input class="form-control" id="title" name="title" required maxlength="255"
					value="<c:out value='${param.title}'/>" />
			</div>
			<div class="mb-3">
				<label class="form-label" for="description">Mô tả</label>
				<textarea class="form-control" id="description" name="description" rows="4"><c:out value="${param.description}" /></textarea>
			</div>
			<div class="mb-3">
				<label class="form-label" for="file">File video (tối đa 100MB)</label>
				<input class="form-control" id="file" type="file" name="file" accept=".mp4,.webm,.ogg,.mov" required />
			</div>
			<button type="submit" class="btn btn-primary">Thêm</button>
			<a href="<c:url value='/video'/>" class="btn btn-link">Quay lại</a>
		</form>
	</div>
</body>
</html>
