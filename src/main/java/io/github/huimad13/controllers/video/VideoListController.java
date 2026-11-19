package io.github.huimad13.controllers.video;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.github.huimad13.entity.User;
import io.github.huimad13.services.VideoService;
import io.github.huimad13.services.impl.VideoServiceImpl;
import io.github.huimad13.utils.Constant;

// Video của user đang đăng nhập, 6 video/trang: /video?page=2
@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/video" })
public class VideoListController extends HttpServlet {
	VideoService videoService = new VideoServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User account = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
		int totalPages = videoService.countPagesByUser(account.getId());
		int page;
		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (NumberFormatException e) {
			page = 1;
		}
		page = Math.max(1, Math.min(page, Math.max(totalPages, 1)));

		req.setAttribute("videoList", videoService.getByUser(account.getId(), page));
		req.setAttribute("page", page);
		req.setAttribute("totalPages", totalPages);
		req.getRequestDispatcher("/views/video/list-video.jsp").forward(req, resp);
	}
}
