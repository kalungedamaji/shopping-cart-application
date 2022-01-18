package com.technogise.interns.dependencyinversion;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    // standard constructor / getter

    public Optional<Customer> searchCustomer(int id) {
        return customerDao.findById(id);
    }

    public List<Customer> listAllCustomer() {
        return customerDao.findAll();
    }
}
