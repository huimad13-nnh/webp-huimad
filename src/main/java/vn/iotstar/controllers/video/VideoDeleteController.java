package vn.iotstar.controllers.video;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.entity.Video;
import vn.iotstar.services.VideoService;
import vn.iotstar.services.impl.VideoServiceImpl;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/video/delete" })
public class VideoDeleteController extends HttpServlet {
	VideoService videoService = new VideoServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Chỉ xoá được video của chính mình
		Video video = VideoEditController.findOwned(videoService, req);
		if (video != null) {
			videoService.delete(video.getId());
		}
		resp.sendRedirect(req.getContextPath() + "/video");
	}
}
