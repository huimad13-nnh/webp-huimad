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
@WebServlet(urlPatterns = { "/admin/category/add" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class CategoryAddController extends HttpServlet {
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String catename = req.getParameter("catename");
		Category category = new Category();
		category.setCatename(catename == null ? null : catename.trim());

		String error = ValidationUtil.validate(category);
		if (error != null) {
			req.setAttribute("alert", error);
			req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);
			return;
		}

		category.setIcon(UploadUtil.save(req.getPart("icon")));
		cateService.insert(category);
		resp.sendRedirect(req.getContextPath() + "/admin/category/list");
	}
}
