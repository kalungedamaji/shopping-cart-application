package com.technogise.interns.shoppingcart.customer;

import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.dto.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customerList = new ArrayList();
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Ada");
        customer.setPhone_number("+91-000000000");
        customer.setAddress("Civil lines");
        customer.setEmail("abc@gmail.com");
        customer.setPassword("1234211");
        customerList.add(customer);

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Ada Alvin");
        customer1.setPhone_number("+91-000000000");
        customer1.setAddress("Civil lines");
        customer1.setEmail("abc@gmail.com");
        customer1.setPassword("1234211");
        customerList.add(customer1);

        return new ResponseEntity(customerList, HttpStatus.OK);
    }
    @GetMapping("/customers/{id}")
    public ResponseEntity<List<Customer>> getCustomer(@PathVariable(value = "id")UUID customerID) {
        Customer customer1 = new Customer();
        customer1.setId(customerID);
        customer1.setName("Ada Alvin");
        customer1.setPhone_number("+91-000000000");
        customer1.setAddress("Civil lines");
        customer1.setEmail("abc@gmail.com");
        customer1.setPassword("1234211");

        return new ResponseEntity(customer1, HttpStatus.OK);
    }
    }
