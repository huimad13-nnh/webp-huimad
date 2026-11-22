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

// Nhập OTP gửi tới email mới để hoàn tất đổi email. Email mới nằm trong session (chưa lưu DB).
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/profile/verify")
public class ProfileVerifyController extends HttpServlet {
	UserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (getPending(req) == null) {
			resp.sendRedirect(req.getContextPath() + "/profile");
			return;
		}
		req.getRequestDispatcher(Constant.Path.PROFILE_VERIFY).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PendingOtp pending = getPending(req);
		if (pending == null) {
			resp.sendRedirect(req.getContextPath() + "/profile");
			return;
		}

		// Cùng 1 form có 2 nút: "Xác nhận" (action=verify) và "Gửi lại mã" (action=resend)
		if ("resend".equals(req.getParameter("action"))) {
			String error;
			try {
				error = service.resendOtp(pending);
			} catch (RuntimeException e) {
				e.printStackTrace();
				error = "Không gửi được email, vui lòng thử lại sau.";
			}
			req.setAttribute(error == null ? "info" : "alert",
					error == null ? "Đã gửi mã OTP mới tới email mới của bạn." : error);
			req.getRequestDispatcher(Constant.Path.PROFILE_VERIFY).forward(req, resp);
			return;
		}

		String otp = req.getParameter("otp");
		String error;
		try {
			error = service.changeEmail(pending, otp == null ? "" : otp.trim());
		} catch (RuntimeException e) {
			e.printStackTrace();
			error = "System error!";
		}
		if (error != null) {
			req.setAttribute("alert", error);
			req.getRequestDispatcher(Constant.Path.PROFILE_VERIFY).forward(req, resp);
			return;
		}
		req.getSession().removeAttribute(Constant.SESSION_CHANGE_EMAIL);
		req.getSession().setAttribute(Constant.SESSION_ACCOUNT, service.get(pending.getUser().getUserName()));
		resp.sendRedirect(req.getContextPath() + "/profile?emailChanged=1");
	}

	private PendingOtp getPending(HttpServletRequest req) {
		// LoginFilter đã đảm bảo có session
		return (PendingOtp) req.getSession().getAttribute(Constant.SESSION_CHANGE_EMAIL);
	}
}
