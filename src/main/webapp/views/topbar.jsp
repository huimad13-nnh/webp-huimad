<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<nav class="navbar navbar-dark bg-dark mb-4">
	<div class="container">
		<a class="navbar-brand" href="${ctx}/">Learn JPA</a>
		<div class="text-light">
			<a class="link-light me-3" href="${ctx}/home">Trang chủ</a>
			<c:choose>
				<c:when test="${sessionScope.account == null}">
					<a class="link-light" href="${ctx}/login">Đăng nhập</a> |
					<a class="link-light" href="${ctx}/register">Đăng ký</a>
				</c:when>
				<c:otherwise>
					<c:if test="${sessionScope.account.roleid == 1}">
						<a class="link-light me-3" href="${ctx}/admin/category/list">Quản lý danh mục</a>
					</c:if>
					<a class="link-light me-3" href="${ctx}/video">Video của tôi</a>
					<a class="link-light me-3 text-decoration-none" href="${ctx}/profile">
						<c:if test="${not empty sessionScope.account.avatar}">
							<img src="<c:out value='${sessionScope.account.avatar}'/>" alt="" class="rounded-circle"
								style="width: 28px; height: 28px; object-fit: cover">
						</c:if>
						Xin chào <b><c:out value="${sessionScope.account.fullName}" /></b>
					</a> |
					<a class="link-light" href="${ctx}/logout">Đăng xuất</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</nav>
