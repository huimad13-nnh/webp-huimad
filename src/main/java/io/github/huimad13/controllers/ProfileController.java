package io.github.huimad13.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import io.github.huimad13.entity.User;
import io.github.huimad13.services.UserService;
import io.github.huimad13.services.impl.UserServiceImpl;
import io.github.huimad13.utils.CloudinaryUtil;
import io.github.huimad13.utils.Constant;
import io.github.huimad13.utils.UploadUtil;

// Sửa hồ sơ: họ tên, sđt, ảnh (upload Cloudinary) lưu ngay; email mới phải nhập OTP gửi tới email đó.
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/profile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 6)
public class ProfileController extends HttpServlet {
	UserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("profile", req.getSession().getAttribute(Constant.SESSION_ACCOUNT));
		if (req.getParameter("updated") != null) {
			req.setAttribute("info", "Cập nhật hồ sơ thành công.");
		} else if (req.getParameter("emailChanged") != null) {
			req.setAttribute("info", "Đổi email thành công.");
		}
		req.getRequestDispatcher(Constant.Path.PROFILE).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Kiểm tra file TRƯỚC khi đọc các ô khác (xem UploadUtil.check)
		String error = UploadUtil.check(req, "avatar", UploadUtil.IMAGE_EXTS);
		HttpSession session = req.getSession();
		User account = (User) session.getAttribute(Constant.SESSION_ACCOUNT);
		String fullName = trim(req.getParameter("fullname"));
		String phone = trim(req.getParameter("phone"));
		String email = trim(req.getParameter("email"));

		if (error == null) {
			error = service.checkProfile(account, fullName, phone, email);
		}
		if (error == null) {
			try {
				String avatar = CloudinaryUtil.upload(req.getPart("avatar"), "learn-jpa/avatars");
				User updated = service.updateProfile(account, fullName, phone, avatar);
				session.setAttribute(Constant.SESSION_ACCOUNT, updated); // topbar hiển thị tên/ảnh mới
				if (!email.equalsIgnoreCase(account.getEmail())) {
					session.setAttribute(Constant.SESSION_CHANGE_EMAIL, service.sendChangeEmailOtp(updated, email));
					resp.sendRedirect(req.getContextPath() + "/profile/verify");
					return;
				}
				resp.sendRedirect(req.getContextPath() + "/profile?updated=1");
				return;
			} catch (IOException | RuntimeException e) {
				e.printStackTrace();
				error = "Không upload được ảnh, không gửi được email OTP hoặc lỗi hệ thống, vui lòng thử lại.";
			}
		}

		// Lỗi: hiện lại form với dữ liệu vừa nhập, ảnh vẫn là ảnh hiện tại
		User form = new User();
		form.setUserName(account.getUserName());
		form.setFullName(fullName);
		form.setPhone(phone);
		form.setEmail(email);
		form.setAvatar(((User) session.getAttribute(Constant.SESSION_ACCOUNT)).getAvatar());
		req.setAttribute("alert", error);
		req.setAttribute("profile", form);
		req.getRequestDispatcher(Constant.Path.PROFILE).forward(req, resp);
	}

	private String trim(String s) {
		return s == null ? "" : s.trim();
	}
}
