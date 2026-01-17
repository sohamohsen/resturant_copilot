package com.research.repository;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.Order;

import java.util.*;

public class OrderRepository {
    private final Map<Integer, Order> orderMap = new HashMap<>();

    public void addOrder(Order order) throws DuplicateIdException {
        if (orderMap.containsKey(order.getId()))
            throw new DuplicateIdException("Order ID already exists!");
        orderMap.put(order.getId(), order);
    }

    public Order getOrderById(int id) throws EntityNotFoundException {
        Order order = orderMap.get(id);
        if (order == null)
            throw new EntityNotFoundException("Order not found!");
        return order;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orderMap.values());
    }

    public void updateOrder(Order order) throws EntityNotFoundException {
        if (!orderMap.containsKey(order.getId()))
            throw new EntityNotFoundException("Order not found!");
        orderMap.put(order.getId(), order);
    }

    public void deleteOrder(int id) throws EntityNotFoundException {
        if (!orderMap.containsKey(id))
            throw new EntityNotFoundException("Order not found!");
        orderMap.remove(id);
    }
}