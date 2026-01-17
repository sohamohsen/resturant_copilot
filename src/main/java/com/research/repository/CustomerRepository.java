package com.research.repository;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.Customer;

import java.util.*;

public class CustomerRepository {
    private final Map<Integer, Customer> customerMap = new HashMap<>();

    public void addCustomer(Customer customer) throws DuplicateIdException {
        if (customerMap.containsKey(customer.getId()))
            throw new DuplicateIdException("Customer ID already exists!");
        customerMap.put(customer.getId(), customer);
    }

    public Customer getCustomerById(int id) throws EntityNotFoundException {
        Customer cust = customerMap.get(id);
        if (cust == null) throw new EntityNotFoundException("Customer not found!");
        return cust;
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    public void updateCustomer(Customer customer) throws EntityNotFoundException {
        if (!customerMap.containsKey(customer.getId()))
            throw new EntityNotFoundException("Customer not found!");
        customerMap.put(customer.getId(), customer);
    }

    public void deleteCustomer(int id) throws EntityNotFoundException {
        if (!customerMap.containsKey(id))
            throw new EntityNotFoundException("Customer not found!");
        customerMap.remove(id);
    }

    public List<Customer> searchByNameOrEmail(String search) {
        List<Customer> result = new ArrayList<>();
        for (Customer c : customerMap.values()) {
            if (c.getFullName().toLowerCase().contains(search.toLowerCase()) ||
                    c.getEmail().toLowerCase().contains(search.toLowerCase()))
                result.add(c);
        }
        return result;
    }
}