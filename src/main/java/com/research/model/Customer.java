package com.research.model;

public class Customer {
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private int loyaltyPoints;

    public Customer(int id, String fullName, String email, String phone, int loyaltyPoints) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.loyaltyPoints = loyaltyPoints;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                '}';
    }
}