package com.research.model;

public class MenuItem {
    private int id;
    private String name;
    private String description;
    private double price;
    private MenuCategory category;
    private boolean isAvailable;

    public MenuItem(int id, String name, String description, double price, MenuCategory category, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public MenuCategory getCategory() { return category; }
    public void setCategory(MenuCategory category) { this.category = category; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + String.format("$%.2f", price) +
                ", category=" + (category != null ? category.getName() : "None") +
                ", isAvailable=" + isAvailable +
                '}';
    }
}