package io.github.huimad13.dao.impl;

import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import io.github.huimad13.configs.JPAConfig;
import io.github.huimad13.dao.UserDao;
import io.github.huimad13.entity.User;

public class UserDaoImpl implements UserDao {

	@Override
	public User findByUsername(String username) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			List<User> list = em.createNamedQuery("User.findByUsername", User.class)
					.setParameter("username", username)
					.getResultList();
			return list.isEmpty() ? null : list.get(0);
		} finally {
			em.close();
		}
	}

	@Override
	public User findByEmail(String email) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			List<User> list = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
					.setParameter("email", email)
					.getResultList();
			return list.isEmpty() ? null : list.get(0);
		} finally {
			em.close();
		}
	}

	@Override
	public void insert(User user) {
		inTransaction(em -> em.persist(user)); // INSERT; Hibernate kiểm tra validation trước khi ghi
	}

	@Override
	public void updatePassword(int id, String password) {
		// Chỉ UPDATE cột password, không đụng các cột khác
		inTransaction(em -> em.createQuery("UPDATE User u SET u.passWord = :password WHERE u.id = :id")
				.setParameter("password", password)
				.setParameter("id", id)
				.executeUpdate());
	}

	@Override
	public void updateProfile(int id, String fullName, String phone, String avatar) {
		inTransaction(em -> em.createQuery(
				"UPDATE User u SET u.fullName = :fullName, u.phone = :phone, u.avatar = :avatar WHERE u.id = :id")
				.setParameter("fullName", fullName)
				.setParameter("phone", phone)
				.setParameter("avatar", avatar)
				.setParameter("id", id)
				.executeUpdate());
	}

	@Override
	public void updateEmail(int id, String email) {
		inTransaction(em -> em.createQuery("UPDATE User u SET u.email = :email WHERE u.id = :id")
				.setParameter("email", email)
				.setParameter("id", id)
				.executeUpdate());
	}

	// begin -> làm việc -> commit; lỗi thì rollback
	private void inTransaction(Consumer<EntityManager> work) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			work.accept(em);
			trans.commit();
		} catch (RuntimeException e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean checkExistEmail(String email) {
		return exists("email", email);
	}

	@Override
	public boolean checkExistUsername(String username) {
		return exists("userName", username);
	}

	@Override
	public boolean checkExistPhone(String phone) {
		return exists("phone", phone);
	}

	// field là tên thuộc tính trong entity (không phải tên cột), chỉ gọi nội bộ với hằng số
	private boolean exists(String field, String value) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			Long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u." + field + " = :value", Long.class)
					.setParameter("value", value)
					.getSingleResult();
			return count > 0;
		} finally {
			em.close();
		}
	}
}
