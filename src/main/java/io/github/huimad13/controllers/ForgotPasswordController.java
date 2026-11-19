package io.github.huimad13.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.github.huimad13.models.PendingOtp;
import io.github.huimad13.services.UserService;
import io.github.huimad13.services.impl.UserServiceImpl;
import io.github.huimad13.utils.Constant;

// Quên mật khẩu bước 1: nhập email -> gửi OTP -> sang /reset-password
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/forgot-password")
public class ForgotPasswordController extends HttpServlet {
	UserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		email = email == null ? "" : email.trim();
		if (email.isEmpty()) {
			req.setAttribute("alert", "Email không được rỗng");
			req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
			return;
		}

		String error = null;
		try {
			PendingOtp pending = service.sendResetOtp(email);
			if (pending == null) {
				error = "Không có tài khoản nào dùng email này!";
			} else {
				req.getSession(true).setAttribute(Constant.SESSION_RESET, pending);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			error = "Không gửi được email OTP hoặc lỗi hệ thống, vui lòng thử lại.";
		}

		if (error != null) {
			req.setAttribute("alert", error);
			req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
			return;
		}
		resp.sendRedirect(req.getContextPath() + "/reset-password");
	}
}
