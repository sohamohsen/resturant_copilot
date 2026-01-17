package com.research.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private int id;
    private Customer customer;
    private RestaurantTable table;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private int numberOfGuests;

    public Reservation(int id, Customer customer, RestaurantTable table, LocalDate reservationDate, LocalTime reservationTime, int numberOfGuests) {
        this.id = id;
        this.customer = customer;
        this.table = table;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.numberOfGuests = numberOfGuests;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public RestaurantTable getTable() { return table; }
    public void setTable(RestaurantTable table) { this.table = table; }
    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
    public LocalTime getReservationTime() { return reservationTime; }
    public void setReservationTime(LocalTime reservationTime) { this.reservationTime = reservationTime; }
    public int getNumberOfGuests() { return numberOfGuests; }
    public void setNumberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests; }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customer=" + (customer != null ? customer.getFullName() : "None") +
                ", table=" + (table != null ? table.getTableNumber() : "None") +
                ", reservationDate=" + reservationDate +
                ", reservationTime=" + reservationTime +
                ", numberOfGuests=" + numberOfGuests +
                '}';
    }
}