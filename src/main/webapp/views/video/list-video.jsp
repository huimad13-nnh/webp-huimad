<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Video của tôi</title>
</head>
<body>
	<div class="container">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2>Video của tôi</h2>
			<a class="btn btn-primary" href="<c:url value='/video/add'/>">+ Thêm video</a>
		</div>

		<c:set var="showActions" value="${true}" />
		<div class="row g-3">
			<c:forEach items="${videoList}" var="v">
				<%@ include file="card.jspf"%>
			</c:forEach>
		</div>
		<c:if test="${empty videoList}">
			<p class="text-muted">Bạn chưa có video nào.</p>
		</c:if>

		<c:if test="${totalPages > 1}">
			<nav class="mt-4">
				<ul class="pagination">
					<li class="page-item ${page == 1 ? 'disabled' : ''}">
						<a class="page-link" href="<c:url value='/video?page=${page - 1}'/>">«</a>
					</li>
					<c:forEach begin="1" end="${totalPages}" var="i">
						<li class="page-item ${i == page ? 'active' : ''}">
							<a class="page-link" href="<c:url value='/video?page=${i}'/>">${i}</a>
						</li>
					</c:forEach>
					<li class="page-item ${page == totalPages ? 'disabled' : ''}">
						<a class="page-link" href="<c:url value='/video?page=${page + 1}'/>">»</a>
					</li>
				</ul>
			</nav>
		</c:if>
	</div>
</body>
</html>
