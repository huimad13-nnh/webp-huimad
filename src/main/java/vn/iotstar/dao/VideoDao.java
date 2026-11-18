package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.entity.Video;

public interface VideoDao {
	void insert(Video video);

	void update(Video video);

	void delete(int id);

	Video findById(int id);

	List<Video> findNewest(int limit);

	List<Video> findByUser(int userId, int offset, int limit);

	long countByUser(int userId);
}
