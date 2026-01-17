package com.research.repository;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.RestaurantTable;
import com.research.model.TableStatus;

import java.util.*;

public class TableRepository {
    private final Map<Integer, RestaurantTable> tableMap = new HashMap<>();

    public void addTable(RestaurantTable table) throws DuplicateIdException {
        if (tableMap.containsKey(table.getId()))
            throw new DuplicateIdException("Table ID already exists!");
        tableMap.put(table.getId(), table);
    }

    public RestaurantTable getTableById(int id) throws EntityNotFoundException {
        RestaurantTable table = tableMap.get(id);
        if (table == null)
            throw new EntityNotFoundException("Table not found!");
        return table;
    }

    public List<RestaurantTable> getAllTables() {
        return new ArrayList<>(tableMap.values());
    }

    public void updateTable(RestaurantTable table) throws EntityNotFoundException {
        if (!tableMap.containsKey(table.getId()))
            throw new EntityNotFoundException("Table not found!");
        tableMap.put(table.getId(), table);
    }

    public void deleteTable(int id) throws EntityNotFoundException {
        if (!tableMap.containsKey(id))
            throw new EntityNotFoundException("Table not found!");
        tableMap.remove(id);
    }

    public List<RestaurantTable> getAvailableTables() {
        List<RestaurantTable> available = new ArrayList<>();
        for (RestaurantTable t : tableMap.values()) {
            if (t.getStatus() == TableStatus.AVAILABLE)
                available.add(t);
        }
        return available;
    }

    public RestaurantTable findByTableNumber(int tableNumber) {
        for (RestaurantTable t : tableMap.values()) {
            if (t.getTableNumber() == tableNumber)
                return t;
        }
        return null;
    }
}