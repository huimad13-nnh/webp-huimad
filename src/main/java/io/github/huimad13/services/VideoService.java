package io.github.huimad13.services;

import java.util.List;

import io.github.huimad13.entity.Video;

public interface VideoService {
	int PAGE_SIZE = 6;

	void insert(Video video);

	void edit(Video video);

	void delete(int id);

	Video get(int id);

	List<Video> getNewest(int limit);

	/** @param page bắt đầu từ 1 */
	List<Video> getByUser(int userId, int page);

	int countPagesByUser(int userId);
}
