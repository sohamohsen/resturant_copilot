package com.research.repository;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.MenuItem;

import java.util.*;

public class MenuItemRepository {
    private final Map<Integer, MenuItem> itemMap = new HashMap<>();

    public void addMenuItem(MenuItem item) throws DuplicateIdException {
        if (itemMap.containsKey(item.getId()))
            throw new DuplicateIdException("Menu item ID already exists!");
        itemMap.put(item.getId(), item);
    }

    public MenuItem getMenuItemById(int id) throws EntityNotFoundException {
        MenuItem item = itemMap.get(id);
        if (item == null) throw new EntityNotFoundException("Menu item not found!");
        return item;
    }

    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(itemMap.values());
    }

    public void updateMenuItem(MenuItem item) throws EntityNotFoundException {
        if (!itemMap.containsKey(item.getId()))
            throw new EntityNotFoundException("Menu item not found!");
        itemMap.put(item.getId(), item);
    }

    public void deleteMenuItem(int id) throws EntityNotFoundException {
        if (!itemMap.containsKey(id))
            throw new EntityNotFoundException("Menu item not found!");
        itemMap.remove(id);
    }

    public List<MenuItem> searchByNameOrDescription(String search) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : itemMap.values()) {
            if (item.getName().toLowerCase().contains(search.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(search.toLowerCase())) {
                result.add(item);
            }
        }
        return result;
    }
}