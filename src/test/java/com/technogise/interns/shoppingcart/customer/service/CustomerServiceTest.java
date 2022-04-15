package com.technogise.interns.shoppingcart.customer.service;
import com.technogise.interns.shoppingcart.customer.entity.CustomerEntity;
import com.technogise.interns.shoppingcart.customer.mapper.CustomerMapper;
import com.technogise.interns.shoppingcart.customer.repository.CustomerRepository;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerMapper customerMapper;

@Test
    public void shouldReturnCustomerAfterItsCreationInRepository(){
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
    customerEntity.setFirstName("pranay");
    customerEntity.setLastName("jain");
    customerEntity.setAddress("indore");
    customerEntity.setPhoneNumber("9999999999");
    customerEntity.setEmailId("sdfg@dsfg.com");
    customerEntity.setPassword("^asd12");

    Customer customer = new Customer();
    customer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
    customer.setFirstName("pranay");
    customer.setLastName("jain");
    customer.setAddress("indore");
    customer.setPhoneNumber("9999999999");
    customer.setEmailId("sdfg@dsfg.com");
    customer.setPassword("^asd12");

    Mockito.when(customerMapper.mapToEntity(customer)).thenReturn(customerEntity);             //.thenAnswer(){}
    Mockito.when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
    Mockito.when(customerMapper.map(customerEntity)).thenReturn(customer);

    Customer actualCustomer = customerService.createCustomer(customer);
    Mockito.verify(customerRepository,Mockito.times(1)).save(customerEntity);
    assertNotNull(actualCustomer.getId());
    assertThat(actualCustomer.getFirstName(),is(customer.getFirstName()));
    assertThat(actualCustomer.getLastName(),is(customer.getLastName()));
    assertThat(actualCustomer.getPhoneNumber(),is(customer.getPhoneNumber()));
    assertThat(actualCustomer.getEmailId(),is(customer.getEmailId()));
    assertThat(actualCustomer.getPassword(),is(customer.getPassword()));
    assertThat(actualCustomer.getAddress(),is(customer.getAddress()));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenCustomerIsNotPresent(){

        UUID customerId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            customerService.getCustomerById(customerId);
        }, "EntityNotFoundException was expected");

        assertThat(thrown.getMessage(), is("Customer was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}"));
    }
}









