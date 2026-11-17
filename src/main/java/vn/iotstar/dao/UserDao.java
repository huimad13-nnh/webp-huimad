package vn.iotstar.dao;

import vn.iotstar.entity.User;

public interface UserDao {
	User findByUsername(String username);

	User findByEmail(String email);

	void insert(User user);

	void updatePassword(int id, String password);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	boolean checkExistPhone(String phone);
}
