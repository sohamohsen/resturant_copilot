package com.research.service;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.Customer;
import com.research.repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void addCustomer(Customer customer) throws DuplicateIdException {
        repository.addCustomer(customer);
    }

    public Customer getCustomerById(int id) throws EntityNotFoundException {
        return repository.getCustomerById(id);
    }

    public List<Customer> getAllCustomers() {
        return repository.getAllCustomers();
    }

    public void updateCustomer(Customer customer) throws EntityNotFoundException {
        repository.updateCustomer(customer);
    }

    public void deleteCustomer(int id) throws EntityNotFoundException {
        repository.deleteCustomer(id);
    }

    public List<Customer> searchCustomers(String search) {
        return repository.searchByNameOrEmail(search);
    }
}