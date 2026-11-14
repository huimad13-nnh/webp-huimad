package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
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

		// Validation (hibernate-validator) + kiểm tra trùng + lưu bằng JPA đều nằm trong service
		String alertMsg;
		try {
			alertMsg = service.register(user);
		} catch (RuntimeException e) {
			e.printStackTrace();
			alertMsg = "System error!";
		}

		if (alertMsg != null) {
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
			return;
		}
		resp.sendRedirect(req.getContextPath() + "/login?registered=1");
	}

	private String trim(String s) {
		return s == null ? null : s.trim();
	}
}
