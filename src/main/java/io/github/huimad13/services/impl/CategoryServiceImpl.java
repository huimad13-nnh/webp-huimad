package io.github.huimad13.services.impl;

import java.util.List;

import io.github.huimad13.dao.CategoryDao;
import io.github.huimad13.dao.impl.CategoryDaoImpl;
import io.github.huimad13.entity.Category;
import io.github.huimad13.services.CategoryService;
import io.github.huimad13.utils.UploadUtil;

public class CategoryServiceImpl implements CategoryService {
	CategoryDao categoryDao = new CategoryDaoImpl();

	@Override
	public void insert(Category category) {
		categoryDao.insert(category);
	}

	@Override
	public void edit(Category newCategory) {
		Category oldCategory = categoryDao.findById(newCategory.getCateid());
		if (oldCategory == null) {
			return;
		}
		oldCategory.setCatename(newCategory.getCatename());
		// Có upload ảnh mới -> xoá ảnh cũ, lưu tên ảnh mới. Không có -> giữ ảnh cũ.
		if (newCategory.getIcon() != null) {
			UploadUtil.delete(oldCategory.getIcon());
			oldCategory.setIcon(newCategory.getIcon());
		}
		categoryDao.update(oldCategory);
	}

	@Override
	public void delete(int id) {
		Category category = categoryDao.findById(id);
		if (category != null) {
			categoryDao.delete(id);
			UploadUtil.delete(category.getIcon());
		}
	}

	@Override
	public Category get(int id) {
		return categoryDao.findById(id);
	}

	@Override
	public List<Category> getAll() {
		return categoryDao.findAll();
	}

	@Override
	public List<Category> search(String keyword) {
		return categoryDao.search(keyword);
	}
}
