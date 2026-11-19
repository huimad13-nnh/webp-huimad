package io.github.huimad13.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.github.huimad13.utils.Constant;

// Trả file (ảnh, video) trong thư mục upload: /image?fname=abc.png
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/image")
public class DownloadImageController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("fname");
		if (fileName == null || fileName.isBlank()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// getName() bỏ phần đường dẫn -> chặn ?fname=../../file-khac
		File file = new File(Constant.DIR, new File(fileName).getName());
		if (!file.isFile()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String type = getServletContext().getMimeType(file.getName());
		resp.setContentType(type != null ? type : "image/jpeg");
		// shortcut: không hỗ trợ Range nên video phát được nhưng tua có thể không mượt; cần tua thì thêm xử lý header Range
		resp.setContentLengthLong(file.length());
		Files.copy(file.toPath(), resp.getOutputStream());
	}
}
