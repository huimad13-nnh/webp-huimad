<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- Layout trang quản trị, chuyển từ template Start Bootstrap - Simple Sidebar (MIT).
     Không include head.jsp: styles.css của template đã chứa Bootstrap 5.2.3. --%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="tpl" value="${ctx}/templates/simple-sidebar" />
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title><sitemesh:write property="title" /> - Quản trị</title>
<link rel="icon" type="image/x-icon" href="${tpl}/assets/favicon.ico" />
<link href="${tpl}/css/styles.css" rel="stylesheet" />
<sitemesh:write property="head" />
</head>
<body>
	<div class="d-flex" id="wrapper">
		<!-- Sidebar -->
		<div class="border-end bg-white" id="sidebar-wrapper">
			<div class="sidebar-heading border-bottom bg-light">Learn JPA Admin</div>
			<div class="list-group list-group-flush">
				<a class="list-group-item list-group-item-action list-group-item-light p-3" href="${ctx}/admin/home">Dashboard</a>
				<a class="list-group-item list-group-item-action list-group-item-light p-3" href="${ctx}/admin/category/list">Danh mục</a>
				<a class="list-group-item list-group-item-action list-group-item-light p-3" href="${ctx}/admin/category/add">Thêm danh mục</a>
				<a class="list-group-item list-group-item-action list-group-item-light p-3" href="${ctx}/video">Video của tôi</a>
				<a class="list-group-item list-group-item-action list-group-item-light p-3" href="${ctx}/home">Về trang người dùng</a>
			</div>
		</div>
		<!-- Nội dung -->
		<div id="page-content-wrapper">
			<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
				<div class="container-fluid">
					<button class="btn btn-primary" id="sidebarToggle">Ẩn/hiện menu</button>
					<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
						aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Mở menu">
						<span class="navbar-toggler-icon"></span>
					</button>
					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						<ul class="navbar-nav ms-auto mt-2 mt-lg-0">
							<li class="nav-item dropdown">
								<a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false"><c:out value="${sessionScope.account.fullName}" /></a>
								<div class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
									<a class="dropdown-item" href="${ctx}/home">Trang chủ</a>
									<div class="dropdown-divider"></div>
									<a class="dropdown-item" href="${ctx}/logout">Đăng xuất</a>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</nav>
			<div class="container-fluid py-4">
				<sitemesh:write property="body" />
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="${tpl}/js/scripts.js"></script>
</body>
</html>
