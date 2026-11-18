package vn.iotstar.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import vn.iotstar.dao.VideoDao;
import vn.iotstar.dao.impl.VideoDaoImpl;
import vn.iotstar.entity.Video;
import vn.iotstar.services.VideoService;
import vn.iotstar.utils.UploadUtil;

public class VideoServiceImpl implements VideoService {
	VideoDao videoDao = new VideoDaoImpl();

	@Override
	public void insert(Video video) {
		video.setCreatedDate(LocalDateTime.now());
		videoDao.insert(video);
	}

	@Override
	public void edit(Video newVideo) {
		Video oldVideo = videoDao.findById(newVideo.getId());
		if (oldVideo == null) {
			return;
		}
		oldVideo.setTitle(newVideo.getTitle());
		oldVideo.setDescription(newVideo.getDescription());
		// Có upload file mới -> xoá file cũ. Không có -> giữ file cũ.
		if (newVideo.getUrl() != null) {
			UploadUtil.delete(oldVideo.getUrl());
			oldVideo.setUrl(newVideo.getUrl());
		}
		videoDao.update(oldVideo);
	}

	@Override
	public void delete(int id) {
		Video video = videoDao.findById(id);
		if (video != null) {
			videoDao.delete(id);
			UploadUtil.delete(video.getUrl());
		}
	}

	@Override
	public Video get(int id) {
		return videoDao.findById(id);
	}

	@Override
	public List<Video> getNewest(int limit) {
		return videoDao.findNewest(limit);
	}

	@Override
	public List<Video> getByUser(int userId, int page) {
		return videoDao.findByUser(userId, (page - 1) * PAGE_SIZE, PAGE_SIZE);
	}

	@Override
	public int countPagesByUser(int userId) {
		return (int) ((videoDao.countByUser(userId) + PAGE_SIZE - 1) / PAGE_SIZE);
	}
}
