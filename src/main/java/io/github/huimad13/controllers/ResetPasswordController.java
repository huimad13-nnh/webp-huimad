package io.github.huimad13.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import io.github.huimad13.models.PendingOtp;
import io.github.huimad13.services.UserService;
import io.github.huimad13.services.impl.UserServiceImpl;
import io.github.huimad13.utils.Constant;

// Quên mật khẩu bước 2: nhập OTP + mật khẩu mới. Yêu cầu đang chờ nằm trong session.
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/reset-password")
public class ResetPasswordController extends HttpServlet {
	UserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (getPending(req) == null) {
			resp.sendRedirect(req.getContextPath() + "/forgot-password");
			return;
		}
		req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PendingOtp pending = getPending(req);
		if (pending == null) {
			// Session hết hạn -> làm lại từ đầu
			resp.sendRedirect(req.getContextPath() + "/forgot-password");
			return;
		}

		// Cùng 1 form có 2 nút: "Đổi mật khẩu" (action=reset) và "Gửi lại mã" (action=resend)
		if ("resend".equals(req.getParameter("action"))) {
			String error;
			try {
				error = service.resendOtp(pending);
			} catch (RuntimeException e) {
				e.printStackTrace();
				error = "Không gửi được email, vui lòng thử lại sau.";
			}
			req.setAttribute(error == null ? "info" : "alert",
					error == null ? "Đã gửi mã OTP mới tới email của bạn." : error);
			req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
			return;
		}

		String otp = req.getParameter("otp");
		String password = req.getParameter("password");
		String error;
		if (password == null || !password.equals(req.getParameter("confirm"))) {
			error = "Mật khẩu nhập lại không khớp!";
		} else {
			try {
				error = service.resetPassword(pending, otp == null ? "" : otp.trim(), password);
			} catch (RuntimeException e) {
				e.printStackTrace();
				error = "System error!";
			}
		}
		if (error != null) {
			req.setAttribute("alert", error);
			req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
			return;
		}
		req.getSession().removeAttribute(Constant.SESSION_RESET);
		resp.sendRedirect(req.getContextPath() + "/login?reset=1");
	}

	private PendingOtp getPending(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		return session == null ? null : (PendingOtp) session.getAttribute(Constant.SESSION_RESET);
	}
}
