package com.technogise.interns.dependencyinversion;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private Dao dao;
    public CustomerService(CustomerDao customerDao) {
        this.dao = customerDao;
    }

    // standard constructor / getter

    public Optional<Customer> searchCustomer(int id) {
        return dao.findById(id);
    }

    public List<Customer> listAllCustomer() {
        return dao.findAll();
    }
}
