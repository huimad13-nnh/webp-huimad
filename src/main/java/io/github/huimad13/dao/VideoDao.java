package io.github.huimad13.dao;

import java.util.List;

import io.github.huimad13.entity.Video;

public interface VideoDao {
	void insert(Video video);

	void update(Video video);

	void delete(int id);

	Video findById(int id);

	List<Video> findNewest(int limit);

	List<Video> findByUser(int userId, int offset, int limit);

	long countByUser(int userId);
}
