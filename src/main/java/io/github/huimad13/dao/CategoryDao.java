package io.github.huimad13.dao;

import java.util.List;

import io.github.huimad13.entity.Category;

public interface CategoryDao {
	void insert(Category category);

	void update(Category category);

	void delete(int id);

	Category findById(int id);

	List<Category> findAll();

	List<Category> search(String keyword);
}
