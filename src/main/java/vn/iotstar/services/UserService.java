package vn.iotstar.services;

import vn.iotstar.entity.User;

public interface UserService {
	User login(String username, String password);

	User get(String username);

	/** @return thông báo lỗi, hoặc null nếu đăng ký thành công. */
	String register(User user);
}
