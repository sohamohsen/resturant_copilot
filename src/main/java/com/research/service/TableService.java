package com.research.service;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.RestaurantTable;
import com.research.model.TableStatus;
import com.research.repository.TableRepository;

import java.util.List;

public class TableService {
    private final TableRepository repository;

    public TableService(TableRepository repository) {
        this.repository = repository;
    }

    public void addTable(RestaurantTable table) throws DuplicateIdException {
        repository.addTable(table);
    }

    public RestaurantTable getTableById(int id) throws EntityNotFoundException {
        return repository.getTableById(id);
    }

    public List<RestaurantTable> getAllTables() {
        return repository.getAllTables();
    }

    public void updateTable(RestaurantTable table) throws EntityNotFoundException {
        repository.updateTable(table);
    }

    public void deleteTable(int id) throws EntityNotFoundException {
        repository.deleteTable(id);
    }

    public List<RestaurantTable> getAvailableTables() {
        return repository.getAvailableTables();
    }

    public RestaurantTable findByTableNumber(int tableNumber) {
        return repository.findByTableNumber(tableNumber);
    }

    public void markTableStatus(int id, TableStatus status) throws EntityNotFoundException {
        RestaurantTable t = repository.getTableById(id);
        t.setStatus(status);
        repository.updateTable(t);
    }
}