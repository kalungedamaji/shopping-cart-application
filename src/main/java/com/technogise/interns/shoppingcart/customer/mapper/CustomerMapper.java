package com.technogise.interns.shoppingcart.customer.mapper;

import com.technogise.interns.shoppingcart.customer.entity.CustomerEntity;
import com.technogise.interns.shoppingcart.dto.Customer;

public class CustomerMapper {
    public static Customer map(CustomerEntity customerEntity){
        Customer customer = new Customer();
        customer.setId(customerEntity.getId());
        customer.setFirstName(customerEntity.getFirstName());
        customer.setLastName(customerEntity.getLastName());
        customer.setPhoneNumber(customerEntity.getPhoneNumber());
        customer.setEmailId(customerEntity.getEmailId());
        customer.setPassword(customerEntity.getPassword());
        customer.setAddress(customerEntity.getAddress());

        return customer;
    }

    public static CustomerEntity mapToEntity(Customer customer){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getId());
        customerEntity.setFirstName(customer.getFirstName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setPhoneNumber(customer.getPhoneNumber());
        customerEntity.setEmailId(customer.getEmailId());
        customerEntity.setPassword(customer.getPassword());
        customerEntity.setAddress(customer.getAddress());

        return customerEntity;
    }
}