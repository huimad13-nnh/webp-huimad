package io.github.huimad13.dao;

import io.github.huimad13.entity.User;

public interface UserDao {
	User findByUsername(String username);

	User findByEmail(String email);

	void insert(User user);

	void updatePassword(int id, String password);

	void updateProfile(int id, String fullName, String phone, String avatar);

	void updateEmail(int id, String email);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	boolean checkExistPhone(String phone);
}
