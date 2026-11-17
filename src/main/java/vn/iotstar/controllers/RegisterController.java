package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.models.PendingRegistration;
import vn.iotstar.services.UserService;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {
	UserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Đã đăng nhập rồi thì không cho đăng ký nữa -> về trang theo vai trò
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}
		req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
		user.setUserName(trim(req.getParameter("username")));
		user.setPassWord(req.getParameter("password"));
		user.setEmail(trim(req.getParameter("email")));
		user.setFullName(trim(req.getParameter("fullname")));
		user.setPhone(trim(req.getParameter("phone")));

		// Kiểm tra dữ liệu + trùng, rồi gửi OTP. CHƯA lưu DB: chỉ giữ trong session tới khi nhập đúng OTP.
		String alertMsg;
		try {
			alertMsg = service.checkRegister(user);
			if (alertMsg == null) {
				PendingRegistration pending = service.sendOtp(user);
				req.getSession(true).setAttribute(Constant.SESSION_PENDING, pending);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			alertMsg = "Không gửi được email OTP hoặc lỗi hệ thống, vui lòng thử lại.";
		}

		if (alertMsg != null) {
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
			return;
		}
		resp.sendRedirect(req.getContextPath() + "/verify");
	}

	private String trim(String s) {
		return s == null ? null : s.trim();
	}
}
