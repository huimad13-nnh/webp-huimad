package vn.iotstar.controllers.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.entity.Category;
import vn.iotstar.services.CategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;
import vn.iotstar.utils.UploadUtil;
import vn.iotstar.utils.ValidationUtil;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/admin/category/edit" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class CategoryEditController extends HttpServlet {
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Category category = cateService.get(Integer.parseInt(req.getParameter("id")));
		if (category == null) {
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");
			return;
		}
		req.setAttribute("category", category);
		req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String catename = req.getParameter("catename");
		Category category = new Category();
		category.setCateid(Integer.parseInt(req.getParameter("cateid")));
		category.setCatename(catename == null ? null : catename.trim());

		String error = ValidationUtil.validate(category);
		if (error != null) {
			req.setAttribute("alert", error);
			req.setAttribute("category", cateService.get(category.getCateid()));
			req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
			return;
		}

		// null = không chọn ảnh mới -> service giữ ảnh cũ
		category.setIcon(UploadUtil.save(req.getPart("icon")));
		cateService.edit(category);
		resp.sendRedirect(req.getContextPath() + "/admin/category/list");
	}
}
