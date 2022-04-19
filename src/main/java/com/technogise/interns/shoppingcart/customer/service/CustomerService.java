package com.technogise.interns.shoppingcart.customer.service;

import com.technogise.interns.shoppingcart.customer.entity.CustomerEntity;
import com.technogise.interns.shoppingcart.customer.mapper.CustomerMapper;
import com.technogise.interns.shoppingcart.customer.repository.CustomerRepository;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public List<Customer> getAllCustomer() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::map)
                .collect(Collectors.toList());
    }
    public Optional<Customer> getCustomerById(UUID customerId) {

            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);

            if(optionalCustomerEntity.isPresent()) {
                return optionalCustomerEntity.map(customerMapper::map);
            }
            else {
                throw new EntityNotFoundException(Customer.class, "id", customerId.toString());
            }
    }
    public Customer createCustomer(Customer newCustomer) {
        newCustomer.setId(UUID.randomUUID());
        CustomerEntity customerEntity = customerRepository.save(customerMapper.mapToEntity(newCustomer));  //generates id,map to entity,save in rep, returns java object
        return customerMapper.map(customerEntity);
    }

    public Optional<Customer> replaceCustomer(Customer customerDetail, UUID customerId) {

        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);
        if(optionalCustomerEntity.isPresent()) {
            customerDetail.setId(customerId);
            CustomerEntity customerEntity = customerRepository.save(customerMapper.mapToEntity(customerDetail));

            return Optional.of(customerMapper.map(customerEntity));
        }
        else{
            throw new EntityNotFoundException(Customer.class,"id",customerId.toString());
        }
    }

    public void deleteCustomer(UUID customerId) {

        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);
        if(optionalCustomerEntity.isPresent()){
           customerRepository.deleteById(customerId);
        }
        else
            throw new EntityNotFoundException(Customer.class, "id", customerId.toString());
    }
}
