package com.tientv.services;

import com.tientv.model.Category;
import com.tientv.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Integer id, Category updated) {
        Category existing = getById(id);
        if (existing == null) return null;

        existing.setName(updated.getName());
        existing.setIsActive(updated.getIsActive());
        return categoryRepository.save(existing);
    }

    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }
}

