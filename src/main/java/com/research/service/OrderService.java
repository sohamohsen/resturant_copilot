package com.research.service;

import com.research.exception.BusinessRuleViolationException;
import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.MenuItem;
import com.research.model.Order;
import com.research.repository.OrderRepository;

import java.util.List;

public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void addOrder(Order order) throws DuplicateIdException {
        repository.addOrder(order);
    }

    public Order getOrderById(int id) throws EntityNotFoundException {
        return repository.getOrderById(id);
    }

    public List<Order> getAllOrders() {
        return repository.getAllOrders();
    }

    public void updateOrder(Order order) throws EntityNotFoundException {
        repository.updateOrder(order);
    }

    public void deleteOrder(int id) throws EntityNotFoundException {
        repository.deleteOrder(id);
    }

    public double calculateTotal(List<MenuItem> items) {
        double subtotal = 0;
        for (MenuItem item : items) {
            subtotal += item.getPrice();
        }
        double serviceCharge = subtotal * 0.10;
        return Math.round((subtotal + serviceCharge) * 100) / 100.0;
    }

    public void validateItems(List<MenuItem> items) throws BusinessRuleViolationException {
        for (MenuItem item : items) {
            if (!item.isAvailable())
                throw new BusinessRuleViolationException(item.getName() + " is not available for order.");
            if (item.getPrice() < 0)
                throw new BusinessRuleViolationException("Invalid price for menu item: " + item.getName());
        }
    }
}