package io.github.huimad13.controllers.video;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.github.huimad13.entity.Video;
import io.github.huimad13.services.VideoService;
import io.github.huimad13.services.impl.VideoServiceImpl;

// Xem chi tiết + play 1 video: /video/detail?id=1 (không cần đăng nhập)
@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/video/detail" })
public class VideoDetailController extends HttpServlet {
	VideoService videoService = new VideoServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Video video = null;
		try {
			video = videoService.get(Integer.parseInt(req.getParameter("id")));
		} catch (NumberFormatException e) {
			// id sai -> 404 bên dưới
		}
		if (video == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		req.setAttribute("video", video);
		req.getRequestDispatcher("/views/video/detail-video.jsp").forward(req, resp);
	}
}
