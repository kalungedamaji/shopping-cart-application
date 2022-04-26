package com.technogise.interns.shoppingcart.customer.mapper;

import com.technogise.interns.shoppingcart.customer.entity.CustomerEntity;
import com.technogise.interns.shoppingcart.dto.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private final Logger logger = LoggerFactory.getLogger(CustomerMapper.class);

    public Customer map(CustomerEntity customerEntity){
        logger.trace("map() called with entity :" +customerEntity.toString());
        Customer customer = new Customer();
        customer.setId(customerEntity.getId());
        customer.setFirstName(customerEntity.getFirstName());
        customer.setLastName(customerEntity.getLastName());
        customer.setPhoneNumber(customerEntity.getPhoneNumber());
        customer.setEmailId(customerEntity.getEmailId());
        customer.setPassword(customerEntity.getPassword());
        customer.setAddress(customerEntity.getAddress());
        logger.trace("Converted customer: "+customer.getId());
        return customer;
    }

    public CustomerEntity mapToEntity(Customer customer){
        logger.trace("mapToEntity() called with entity: "+customer.toString());
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getId());
        customerEntity.setFirstName(customer.getFirstName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setPhoneNumber(customer.getPhoneNumber());
        customerEntity.setEmailId(customer.getEmailId());
        customerEntity.setPassword(customer.getPassword());
        customerEntity.setAddress(customer.getAddress());
        logger.trace("Converted entity: "+customerEntity);
        return customerEntity;
    }
}