package com.research.service;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.MenuCategory;
import com.research.repository.MenuCategoryRepository;

import java.util.List;

public class MenuCategoryService {
    private final MenuCategoryRepository repository;

    public MenuCategoryService(MenuCategoryRepository repository) {
        this.repository = repository;
    }

    public void addCategory(MenuCategory category) throws DuplicateIdException {
        repository.addCategory(category);
    }

    public MenuCategory getCategoryById(int id) throws EntityNotFoundException {
        return repository.getCategoryById(id);
    }

    public List<MenuCategory> getAllCategories() {
        return repository.getAllCategories();
    }

    public void updateCategory(MenuCategory category) throws EntityNotFoundException {
        repository.updateCategory(category);
    }

    public void deleteCategory(int id) throws EntityNotFoundException {
        repository.deleteCategory(id);
    }
}