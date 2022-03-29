package com.technogise.interns.shoppingcart.customer.service;

import com.technogise.interns.shoppingcart.customer.entity.CustomerEntity;
import com.technogise.interns.shoppingcart.customer.mapper.CustomerMapper;
import com.technogise.interns.shoppingcart.customer.repository.CustomerRepository;
import com.technogise.interns.shoppingcart.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {

        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<Customer> getCustomerById(UUID customerId) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);

        return optionalCustomerEntity.map(CustomerMapper::map);
    }

    public Customer createCustomer(Customer newCustomer) {
        newCustomer.setId(UUID.randomUUID());

        CustomerEntity customerEntity = customerRepository.save(CustomerMapper.mapToEntity(newCustomer));
        return CustomerMapper.map(customerEntity);
    }

    public Optional<Customer> replaceCustomer(Customer existingCustomer, UUID customerId) {
        if(customerRepository.findById(customerId).isPresent()) {
            existingCustomer.setId(customerId);
            CustomerEntity customerEntity = customerRepository.save(CustomerMapper.mapToEntity(existingCustomer));

            return Optional.of(CustomerMapper.map(customerEntity));
        }
        else{
            return Optional.empty();
        }
    }

    public boolean deleteCustomer(UUID customerId) {
        if(customerRepository.findById(customerId).isPresent()){
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }
}
