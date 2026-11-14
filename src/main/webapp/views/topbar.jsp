<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<nav class="navbar navbar-dark bg-dark mb-4">
	<div class="container">
		<a class="navbar-brand" href="${ctx}/">Learn JPA</a>
		<div class="text-light">
			<c:choose>
				<c:when test="${sessionScope.account == null}">
					<a class="link-light" href="${ctx}/login">Đăng nhập</a> |
					<a class="link-light" href="${ctx}/register">Đăng ký</a>
				</c:when>
				<c:otherwise>
					<c:if test="${sessionScope.account.roleid == 1}">
						<a class="link-light me-3" href="${ctx}/admin/category/list">Quản lý danh mục</a>
					</c:if>
					Xin chào <b><c:out value="${sessionScope.account.fullName}" /></b> |
					<a class="link-light" href="${ctx}/logout">Đăng xuất</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</nav>
