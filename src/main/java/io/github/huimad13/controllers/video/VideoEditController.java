package io.github.huimad13.controllers.video;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.github.huimad13.entity.User;
import io.github.huimad13.entity.Video;
import io.github.huimad13.services.VideoService;
import io.github.huimad13.services.impl.VideoServiceImpl;
import io.github.huimad13.utils.Constant;
import io.github.huimad13.utils.UploadUtil;
import io.github.huimad13.utils.ValidationUtil;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/video/edit" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 100, maxRequestSize = 1024 * 1024 * 110)
public class VideoEditController extends HttpServlet {
	VideoService videoService = new VideoServiceImpl();

	/** Video theo id của user đang đăng nhập, hoặc null nếu không tồn tại / không phải của mình. */
	static Video findOwned(VideoService service, HttpServletRequest req) {
		User account = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
		try {
			Video video = service.get(Integer.parseInt(req.getParameter("id")));
			return video != null && video.getUser().getId() == account.getId() ? video : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/** Đọc title, description từ form (dùng chung cho add/edit). */
	static Video readForm(HttpServletRequest req) {
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		Video video = new Video();
		video.setTitle(title == null ? null : title.trim());
		video.setDescription(description == null ? null : description.trim());
		return video;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Video video = findOwned(videoService, req);
		if (video == null) {
			resp.sendRedirect(req.getContextPath() + "/video");
			return;
		}
		req.setAttribute("video", video);
		req.getRequestDispatcher("/views/video/edit-video.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Video old = findOwned(videoService, req);
		if (old == null) {
			resp.sendRedirect(req.getContextPath() + "/video");
			return;
		}
		Video video = readForm(req);
		video.setId(old.getId());

		String error = ValidationUtil.validate(video);
		if (error != null) {
			req.setAttribute("alert", error);
			req.setAttribute("video", old);
			req.getRequestDispatcher("/views/video/edit-video.jsp").forward(req, resp);
			return;
		}

		// null = không chọn file mới -> service giữ file cũ
		video.setUrl(UploadUtil.save(req.getPart("file")));
		videoService.edit(video);
		resp.sendRedirect(req.getContextPath() + "/video");
	}
}
