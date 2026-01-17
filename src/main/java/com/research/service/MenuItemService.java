package com.research.service;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.MenuItem;
import com.research.repository.MenuItemRepository;

import java.util.List;

public class MenuItemService {
    private final MenuItemRepository repository;

    public MenuItemService(MenuItemRepository repository) {
        this.repository = repository;
    }

    public void addMenuItem(MenuItem menuItem) throws DuplicateIdException {
        repository.addMenuItem(menuItem);
    }

    public MenuItem getMenuItemById(int id) throws EntityNotFoundException {
        return repository.getMenuItemById(id);
    }

    public List<MenuItem> getAllMenuItems() {
        return repository.getAllMenuItems();
    }

    public void updateMenuItem(MenuItem menuItem) throws EntityNotFoundException {
        repository.updateMenuItem(menuItem);
    }

    public void deleteMenuItem(int id) throws EntityNotFoundException {
        repository.deleteMenuItem(id);
    }

    public List<MenuItem> searchMenuItems(String search) {
        return repository.searchByNameOrDescription(search);
    }
}