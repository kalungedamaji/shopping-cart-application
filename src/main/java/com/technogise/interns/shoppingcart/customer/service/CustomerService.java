package com.technogise.interns.shoppingcart.customer.service;

import com.technogise.interns.shoppingcart.customer.entity.CustomerEntity;
import com.technogise.interns.shoppingcart.customer.mapper.CustomerMapper;
import com.technogise.interns.shoppingcart.customer.repository.CustomerRepository;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private CustomerMapper customerMapper;
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public List<Customer> getAllCustomer() {
        logger.info("Getting all the customers from Repository...");
        List<Customer> customerList = customerRepository.findAll()
                .stream()
                .map(customerMapper::map)
                .collect(Collectors.toList());

        logger.debug("Returned customer as: "+customerList);
        logger.info("Retrieved all customers From Repository");

        return customerList;
    }
    public Customer getCustomerById(UUID customerId) {
        logger.info("Getting customer by id from Repository...");
        logger.debug("getCustomerById() is called with customerId as: "+customerId);
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);

            if(optionalCustomerEntity.isPresent()) {
                Customer customer = customerMapper.map(optionalCustomerEntity.get());
                logger.debug("Returned customer is: "+customer);
                logger.info("Retrieved customer by id from repository");
                return customer;
            }
            else {
                logger.error("customer for customerId: "+customerId+" is not present in the repository.");
                throw new EntityNotFoundException(Customer.class, "id", customerId.toString());
            }
    }
    public Customer createCustomer(Customer newCustomer) {
        logger.info("Adding customer to Repository...");
        logger.debug("createCustomer() is called as: "+newCustomer);
        newCustomer.setId(UUID.randomUUID());
        CustomerEntity customerEntity = customerRepository.save(customerMapper.mapToEntity(newCustomer));  //generates id,map to entity,save in rep, returns java object
        Customer createdCustomer = customerMapper.map(customerEntity);
        logger.debug("Customer created as: "+createdCustomer);
        logger.info("Added customer in repository");
        return createdCustomer;
    }

    public Customer replaceCustomer(Customer customerDetail, UUID customerId) {
        logger.info("Updating details for customer in repository...");
        logger.debug("replaceCustomer() is called with customerId as: "+customerId);
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);
        if(optionalCustomerEntity.isPresent()) {
            customerDetail.setId(customerId);
            CustomerEntity customerEntity = customerRepository.save(customerMapper.mapToEntity(customerDetail));
            Customer replacedCustomer = customerMapper.map(customerEntity);
            logger.debug("Updated customer is: "+replacedCustomer);
            logger.info("Updated details for customer in repository");
            return replacedCustomer;
        }
        else{
            logger.error("customer for customerId: "+customerId+" is not present in the repository.");
            throw new EntityNotFoundException(Customer.class,"id",customerId.toString());
        }
    }

    public void deleteCustomer(UUID customerId) {
        logger.info("Deleting customer from repository...");
        logger.debug("deleteCustomer() is called with customerId as: "+customerId);
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);
        if(optionalCustomerEntity.isPresent()){
            logger.debug("deleted customer for customerId as: "+customerId);
            logger.info(" Removed customer from repository");
           customerRepository.deleteById(customerId);
        }
        else
            logger.error("customer for customerId: "+customerId+" is not present in the repository.");
            throw new EntityNotFoundException(Customer.class, "id", customerId.toString());
    }
}
