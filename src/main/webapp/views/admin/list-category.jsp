<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Danh mục</title>
<%@ include file="../head.jsp"%>
</head>
<body>
	<%@ include file="../topbar.jsp"%>
	<div class="container">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2>Danh sách danh mục</h2>
			<a class="btn btn-primary" href="<c:url value='/admin/category/add'/>">+ Thêm</a>
		</div>
		<form class="d-flex mb-3" action="<c:url value='/admin/category/list'/>" method="get">
			<input class="form-control me-2" name="keyword" placeholder="Tìm theo tên..."
				value="<c:out value='${keyword}'/>">
			<button class="btn btn-outline-secondary">Tìm</button>
		</form>
		<table class="table table-bordered align-middle">
			<thead>
				<tr><th>STT</th><th>Ảnh</th><th>Tên danh mục</th><th>Thao tác</th></tr>
			</thead>
			<tbody>
				<c:forEach items="${cateList}" var="cate" varStatus="STT">
					<tr>
						<td>${STT.index + 1}</td>
						<td>
							<c:if test="${not empty cate.icon}">
								<c:url value="/image?fname=${cate.icon}" var="imgUrl" />
								<img height="80" src="${imgUrl}" alt="" />
							</c:if>
						</td>
						<td><c:out value="${cate.catename}" /></td>
						<td>
							<a href="<c:url value='/admin/category/edit?id=${cate.cateid}'/>">Sửa</a> |
							<a href="<c:url value='/admin/category/delete?id=${cate.cateid}'/>"
								onclick="return confirm('Xoá danh mục này?')">Xoá</a>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty cateList}">
					<tr><td colspan="4" class="text-center text-muted">Không có danh mục nào</td></tr>
				</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>
