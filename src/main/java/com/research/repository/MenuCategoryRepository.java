package com.research.repository;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.MenuCategory;

import java.util.*;

public class MenuCategoryRepository {
    private final Map<Integer, MenuCategory> categoryMap = new HashMap<>();

    public void addCategory(MenuCategory category) throws DuplicateIdException {
        if (categoryMap.containsKey(category.getId()))
            throw new DuplicateIdException("Category ID already exists!");
        categoryMap.put(category.getId(), category);
    }

    public MenuCategory getCategoryById(int id) throws EntityNotFoundException {
        MenuCategory category = categoryMap.get(id);
        if (category == null) throw new EntityNotFoundException("Category not found!");
        return category;
    }

    public List<MenuCategory> getAllCategories() {
        return new ArrayList<>(categoryMap.values());
    }

    public void updateCategory(MenuCategory category) throws EntityNotFoundException {
        if (!categoryMap.containsKey(category.getId()))
            throw new EntityNotFoundException("Category not found!");
        categoryMap.put(category.getId(), category);
    }

    public void deleteCategory(int id) throws EntityNotFoundException {
        if (!categoryMap.containsKey(id))
            throw new EntityNotFoundException("Category not found!");
        categoryMap.remove(id);
    }
}