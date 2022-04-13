package com.technogise.interns.shoppingcart.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.customer.CustomerController;
import com.technogise.interns.shoppingcart.customer.service.CustomerService;
import com.technogise.interns.shoppingcart.dto.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


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
}