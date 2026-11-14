package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.utils.Constant;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/logout")
public class LogoutController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Huỷ session
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		// Xoá cookie remember me (nếu không xoá, /login sẽ tự đăng nhập lại)
		Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, "");
		cookie.setMaxAge(0);
		resp.addCookie(cookie);

		resp.sendRedirect(req.getContextPath() + "/login");
	}
}
