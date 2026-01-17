package com.research.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private Customer customer;
    private List<MenuItem> items;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;

    public Order(int id, Customer customer, List<MenuItem> items, double totalAmount, OrderStatus status, LocalDateTime orderDate) {
        this.id = id;
        this.customer = customer;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public List<MenuItem> getItems() { return items; }
    public void setItems(List<MenuItem> items) { this.items = items; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + (customer != null ? customer.getFullName() : "None") +
                ", items=" + (items != null ? items.size() : 0) +
                ", totalAmount=" + String.format("$%.2f", totalAmount) +
                ", status=" + status +
                ", orderDate=" + orderDate +
                '}';
    }
}