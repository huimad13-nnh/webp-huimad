package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.services.VideoService;
import vn.iotstar.services.impl.VideoServiceImpl;
import vn.iotstar.utils.Constant;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/home", "/admin/home", "/manager/home" })
public class HomeController extends HttpServlet {
	VideoService videoService = new VideoServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("videoList", videoService.getNewest(10)); // 10 video mới nhất
		req.getRequestDispatcher(Constant.Path.HOME).forward(req, resp);
	}
}
