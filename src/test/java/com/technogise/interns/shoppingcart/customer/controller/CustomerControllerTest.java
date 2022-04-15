package com.technogise.interns.shoppingcart.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.customer.CustomerController;
import com.technogise.interns.shoppingcart.customer.service.CustomerService;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value= CustomerController.class)

public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    public void shouldCreateCreateCustomer() throws Exception {

        Customer newCustomer = new Customer();
        newCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCustomer.setFirstName("Pranay");
        newCustomer.setLastName("Jain");
        newCustomer.setPhoneNumber("9999999999");
        newCustomer.setAddress("Indore");
        newCustomer.setEmailId("abc@xyz.com");
        newCustomer.setPassword("123abcd@");

        Mockito.when(customerService.createCustomer(any(Customer.class))).thenReturn(newCustomer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:9000/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer))
                .contentType(MediaType.APPLICATION_JSON);

        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        expectedCustomer.setFirstName("Pranay");
        expectedCustomer.setLastName("Jain");
        expectedCustomer.setPhoneNumber("9999999999");
        expectedCustomer.setAddress("Indore");
        expectedCustomer.setEmailId("abc@xyz.com");
        expectedCustomer.setPassword("123abcd@");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",is(expectedCustomer.getId().toString())))
                .andExpect(jsonPath("$.address",is(expectedCustomer.getAddress())))
                .andExpect(jsonPath("$.firstName",is(expectedCustomer.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(expectedCustomer.getLastName())))
                .andExpect(jsonPath("$.phoneNumber",is(expectedCustomer.getPhoneNumber())))
                .andExpect(jsonPath("$.emailId", is(expectedCustomer.getEmailId())))
                .andExpect(jsonPath("password", is(expectedCustomer.getPassword())))
                .andExpect(jsonPath("$.links[0].rel", is("all-customers")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost:9000/customers")))
                .andExpect(jsonPath("$.links[1].rel", is("store")))
                .andExpect(jsonPath("$.links[1].href", is("http://localhost:9000/products")))
                .andExpect(jsonPath("links[2].rel", is("self")));
    }

    @Test
    public void shouldReturnNotFoundErrorWhenCustomerIsNotPresent() throws Exception {


        UUID customerId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");

        Mockito.when(customerService.getCustomerById(any())).thenThrow(new EntityNotFoundException(Customer.class, "id", customerId.toString()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98678")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("Customer was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());

    }

    @Test
    public void shouldReturnUnprocessableEntityWhenThereIsConstraintVoilation() throws Exception {

        Customer newCustomer = new Customer();
        newCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCustomer.setFirstName("Shrishti");
        newCustomer.setLastName("Jain");
        newCustomer.setPhoneNumber("7004960921");
        newCustomer.setAddress("India");
        newCustomer.setEmailId("shris.des@technogise.com");
        newCustomer.setPassword("abc@xyz");

        Mockito.when(customerService.createCustomer(any(Customer.class))).thenThrow(new ConstraintViolationException("could not execute statement", new SQLException("ERROR: duplicate key value violates unique constraint \"customer_phone_number_key\"\n  Detail: Key (phone_number)=(7004960921) already exists.") , "customer_phone_number_key"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:9000/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status", is("UNPROCESSABLE_ENTITY")))
                .andExpect(jsonPath("$.timestamp", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))))
                .andExpect(jsonPath("$.message", is("could not execute statement")))
                .andExpect(jsonPath("$.debugMessage[0]", is("ERROR: duplicate key value violates unique constraint \"customer_phone_number_key\"\n  Detail: Key (phone_number)=(7004960921) already exists.")));
    }

}