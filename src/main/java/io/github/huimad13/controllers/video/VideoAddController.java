package io.github.huimad13.controllers.video;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import io.github.huimad13.entity.User;
import io.github.huimad13.entity.Video;
import io.github.huimad13.services.VideoService;
import io.github.huimad13.services.impl.VideoServiceImpl;
import io.github.huimad13.utils.Constant;
import io.github.huimad13.utils.UploadUtil;
import io.github.huimad13.utils.ValidationUtil;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/video/add" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 100, maxRequestSize = 1024 * 1024 * 110)
public class VideoAddController extends HttpServlet {
	VideoService videoService = new VideoServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/video/add-video.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String error = UploadUtil.check(req, "file", UploadUtil.VIDEO_EXTS);
		Video video = VideoEditController.readForm(req);
		Part file = error == null ? req.getPart("file") : null;

		if (error == null) {
			error = ValidationUtil.validate(video);
		}
		if (error == null && (file == null || file.getSize() == 0)) {
			error = "Chưa chọn file video";
		}
		if (error != null) {
			req.setAttribute("alert", error);
			req.getRequestDispatcher("/views/video/add-video.jsp").forward(req, resp);
			return;
		}

		video.setUrl(UploadUtil.save(file));
		video.setUser((User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT));
		videoService.insert(video);
		resp.sendRedirect(req.getContextPath() + "/video");
	}
}
