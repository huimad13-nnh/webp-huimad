package vn.iotstar.dao.impl;

import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.iotstar.configs.JPAConfig;
import vn.iotstar.dao.UserDao;
import vn.iotstar.entity.User;

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
	public void insert(User user) {
		inTransaction(em -> em.persist(user)); // INSERT; Hibernate kiểm tra validation trước khi ghi
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
