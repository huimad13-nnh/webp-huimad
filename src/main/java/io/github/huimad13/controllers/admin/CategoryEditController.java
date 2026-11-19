package io.github.huimad13.controllers.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.github.huimad13.entity.Category;
import io.github.huimad13.services.CategoryService;
import io.github.huimad13.services.impl.CategoryServiceImpl;
import io.github.huimad13.utils.UploadUtil;
import io.github.huimad13.utils.ValidationUtil;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/admin/category/edit" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class CategoryEditController extends HttpServlet {
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Category category = find(req.getParameter("id"));
		if (category == null) {
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");
			return;
		}
		req.setAttribute("category", category);
		req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String error = UploadUtil.check(req, "icon", UploadUtil.IMAGE_EXTS);
		Category old = find(req.getParameter("cateid"));
		if (old == null) {
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");
			return;
		}
		String catename = req.getParameter("catename");
		Category category = new Category();
		category.setCateid(old.getCateid());
		category.setCatename(catename == null ? null : catename.trim());

		if (error == null) {
			error = ValidationUtil.validate(category);
		}
		if (error != null) {
			// Giữ tên vừa nhập để người dùng sửa tiếp, ảnh vẫn là ảnh cũ
			category.setIcon(old.getIcon());
			req.setAttribute("alert", error);
			req.setAttribute("category", category);
			req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
			return;
		}

		// null = không chọn ảnh mới -> service giữ ảnh cũ
		category.setIcon(UploadUtil.save(req.getPart("icon")));
		cateService.edit(category);
		resp.sendRedirect(req.getContextPath() + "/admin/category/list");
	}

	/** Danh mục theo id, hoặc null nếu id sai định dạng / không tồn tại. */
	private Category find(String id) {
		try {
			return cateService.get(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
