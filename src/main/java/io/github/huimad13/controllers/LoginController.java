package io.github.huimad13.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import io.github.huimad13.entity.User;
import io.github.huimad13.services.UserService;
import io.github.huimad13.services.impl.UserServiceImpl;
import io.github.huimad13.utils.Constant;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
	UserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Login với SESSION: đã đăng nhập rồi thì không cần login lại
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}

		// 2. Login với COOKIE: có cookie "remember me" -> tự đăng nhập, tạo lại session
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Constant.COOKIE_REMEMBER.equals(cookie.getName())) {
					User user = service.get(cookie.getValue());
					if (user != null) {
						session = req.getSession(true);
						session.setAttribute(Constant.SESSION_ACCOUNT, user);
						resp.sendRedirect(req.getContextPath() + "/waiting");
						return;
					}
				}
			}
		}

		req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		boolean isRememberMe = "on".equals(req.getParameter("remember"));

		if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
			req.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
			req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
			return;
		}

		User user = service.login(username, password);
		if (user != null) {
			HttpSession session = req.getSession(true);
			session.setAttribute(Constant.SESSION_ACCOUNT, user);
			if (isRememberMe) {
				saveRememberMe(resp, username);
			}
			resp.sendRedirect(req.getContextPath() + "/waiting");
		} else {
			req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
			req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
		}
	}

	private void saveRememberMe(HttpServletResponse response, String username) {
		Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
		cookie.setMaxAge(30 * 60); // 30 phút
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}
}
