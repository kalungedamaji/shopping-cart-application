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
        expectedCustomer.setFirstName("Pranay");
        expectedCustomer.setLastName("Jain");
        expectedCustomer.setPhoneNumber("9999999999");
        expectedCustomer.setAddress("Indore");
        expectedCustomer.setEmailId("abc@xyz.com");
        expectedCustomer.setPassword("123abcd@");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.address").value(expectedCustomer.getAddress()))
                .andExpect(jsonPath("$.firstName").value(expectedCustomer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedCustomer.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(expectedCustomer.getPhoneNumber()))
                .andExpect(jsonPath("$.emailId").value(expectedCustomer.getEmailId()))
                .andExpect(jsonPath("$.password").value(expectedCustomer.getPassword()));

    }
}