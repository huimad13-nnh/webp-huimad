package io.github.huimad13.dao.impl;

import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import io.github.huimad13.configs.JPAConfig;
import io.github.huimad13.dao.CategoryDao;
import io.github.huimad13.entity.Category;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public void insert(Category category) {
		inTransaction(em -> em.persist(category)); // INSERT
	}

	@Override
	public void update(Category category) {
		inTransaction(em -> em.merge(category)); // UPDATE theo id
	}

	@Override
	public void delete(int id) {
		inTransaction(em -> {
			Category category = em.find(Category.class, id);
			if (category != null) {
				em.remove(category); // DELETE
			}
		});
	}

	@Override
	public Category findById(int id) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.find(Category.class, id);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Category> findAll() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createNamedQuery("Category.findAll", Category.class).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Category> search(String keyword) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			// JPQL: truy vấn trên entity Category và thuộc tính catename (không phải tên bảng/cột)
			return em.createQuery("SELECT c FROM Category c WHERE c.catename LIKE :keyword", Category.class)
					.setParameter("keyword", "%" + keyword + "%")
					.getResultList();
		} finally {
			em.close();
		}
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
}
