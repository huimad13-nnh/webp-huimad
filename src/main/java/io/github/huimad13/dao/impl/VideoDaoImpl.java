package io.github.huimad13.dao.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import io.github.huimad13.configs.JPAConfig;
import io.github.huimad13.dao.VideoDao;
import io.github.huimad13.entity.Video;

public class VideoDaoImpl implements VideoDao {

	@Override
	public void insert(Video video) {
		inTransaction(em -> em.persist(video));
	}

	@Override
	public void update(Video video) {
		inTransaction(em -> em.merge(video));
	}

	@Override
	public void delete(int id) {
		inTransaction(em -> {
			Video video = em.find(Video.class, id);
			if (video != null) {
				em.remove(video);
			}
		});
	}

	@Override
	public Video findById(int id) {
		return query(em -> em.find(Video.class, id));
	}

	@Override
	public List<Video> findNewest(int limit) {
		// JOIN FETCH: lấy luôn user trong 1 câu SQL (tránh N+1 truy vấn)
		return query(em -> em.createQuery(
				"SELECT v FROM Video v JOIN FETCH v.user ORDER BY v.createdDate DESC, v.id DESC", Video.class)
				.setMaxResults(limit)
				.getResultList());
	}

	@Override
	public List<Video> findByUser(int userId, int offset, int limit) {
		return query(em -> em.createQuery(
				"SELECT v FROM Video v JOIN FETCH v.user WHERE v.user.id = :uid ORDER BY v.createdDate DESC, v.id DESC",
				Video.class)
				.setParameter("uid", userId)
				.setFirstResult(offset) // phân trang: OFFSET
				.setMaxResults(limit) //               FETCH NEXT
				.getResultList());
	}

	@Override
	public long countByUser(int userId) {
		return query(em -> em.createQuery("SELECT COUNT(v) FROM Video v WHERE v.user.id = :uid", Long.class)
				.setParameter("uid", userId)
				.getSingleResult());
	}

	private <T> T query(Function<EntityManager, T> work) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return work.apply(em);
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
