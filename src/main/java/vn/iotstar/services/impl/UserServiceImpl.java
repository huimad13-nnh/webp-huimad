package vn.iotstar.services.impl;

import java.time.LocalDate;

import vn.iotstar.dao.UserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.entity.User;
import vn.iotstar.services.UserService;
import vn.iotstar.utils.ValidationUtil;

public class UserServiceImpl implements UserService {
	UserDao userDao = new UserDaoImpl();

	@Override
	public User login(String username, String password) {
		User user = userDao.findByUsername(username);
		if (user != null && password.equals(user.getPassWord())) {
			return user;
		}
		return null;
	}

	@Override
	public User get(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public String register(User user) {
		// 1. Kiểm tra dữ liệu theo annotation trên entity (@NotBlank, @Email, @Size, @Pattern)
		String error = ValidationUtil.validate(user);
		if (error != null) {
			return error;
		}
		// 2. Kiểm tra trùng trong DB
		if (userDao.checkExistUsername(user.getUserName())) {
			return "Tài khoản đã tồn tại!";
		}
		if (userDao.checkExistEmail(user.getEmail())) {
			return "Email đã tồn tại!";
		}
		if (user.getPhone() != null && !user.getPhone().isEmpty() && userDao.checkExistPhone(user.getPhone())) {
			return "Số điện thoại đã tồn tại!";
		}
		// 3. Lưu
		user.setRoleid(5);
		user.setCreatedDate(LocalDate.now());
		userDao.insert(user);
		return null;
	}
}
