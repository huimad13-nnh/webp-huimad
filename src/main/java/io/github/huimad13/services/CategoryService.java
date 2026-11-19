package io.github.huimad13.services;

import java.util.List;

import io.github.huimad13.entity.Category;

public interface CategoryService {
	void insert(Category category);

	void edit(Category category);

	void delete(int id);

	Category get(int id);

	List<Category> getAll();

	List<Category> search(String keyword);
}
