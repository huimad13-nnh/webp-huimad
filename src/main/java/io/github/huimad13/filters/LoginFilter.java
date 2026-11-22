package io.github.huimad13.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import io.github.huimad13.utils.Constant;

// Hồ sơ + quản lý video của mình -> phải đăng nhập. /video/detail để công khai.
@WebFilter(urlPatterns = { "/video", "/video/add", "/video/edit", "/video/delete", "/profile", "/profile/verify" })
public class LoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login");
		}
	}
}
