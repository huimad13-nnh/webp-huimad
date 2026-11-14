package vn.iotstar.services.impl;

import java.io.File;
import java.util.List;

import vn.iotstar.dao.CategoryDao;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.entity.Category;
import vn.iotstar.services.CategoryService;
import vn.iotstar.utils.Constant;

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
			deleteFile(oldCategory.getIcon());
			oldCategory.setIcon(newCategory.getIcon());
		}
		categoryDao.update(oldCategory);
	}

	@Override
	public void delete(int id) {
		Category category = categoryDao.findById(id);
		if (category != null) {
			categoryDao.delete(id);
			deleteFile(category.getIcon());
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

	private void deleteFile(String fileName) {
		if (fileName != null && !fileName.isBlank()) {
			File file = new File(Constant.DIR, fileName);
			if (file.exists()) {
				file.delete();
			}
		}
	}
}
