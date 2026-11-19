package io.github.huimad13.controllers.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.github.huimad13.entity.Category;
import io.github.huimad13.services.CategoryService;
import io.github.huimad13.services.impl.CategoryServiceImpl;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/admin/category/list" })
public class CategoryListController extends HttpServlet {
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		List<Category> cateList = (keyword == null || keyword.isBlank())
				? cateService.getAll()
				: cateService.search(keyword.trim());
		req.setAttribute("cateList", cateList);
		req.setAttribute("keyword", keyword);
		req.getRequestDispatcher("/views/admin/list-category.jsp").forward(req, resp);
	}
}
