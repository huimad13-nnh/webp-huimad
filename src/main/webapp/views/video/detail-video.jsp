<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title><c:out value="${video.title}" /></title>
</head>
<body>
	<div class="container" style="max-width: 900px">
		<c:url value="/image?fname=${video.url}" var="videoUrl" />
		<video class="w-100 bg-dark rounded" src="${videoUrl}" controls autoplay></video>
		<h2 class="mt-3"><c:out value="${video.title}" /></h2>
		<p class="text-muted">
			Đăng bởi <b><c:out value="${video.user.fullName}" /></b> · ${video.createdDate.toLocalDate()}
		</p>
		<p style="white-space: pre-line"><c:out value="${video.description}" /></p>
		<a class="btn btn-link px-0" href="javascript:history.back()">← Quay lại</a>
	</div>
</body>
</html>
