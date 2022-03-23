package com.technogise.interns.shoppingcart.customer;

import com.technogise.interns.shoppingcart.dto.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {
    List<Customer> customerList = new ArrayList();
    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity(customerList, HttpStatus.OK);
    }
    @GetMapping("/customers/{id}")
    public ResponseEntity<List<Customer>> getCustomer(@PathVariable(value = "id")UUID customerId) {
        Customer customer = findById(customerId);
        return new ResponseEntity(customer, HttpStatus.OK);
    }
    @PostMapping(path = "/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) {
        newCustomer.setId(UUID.randomUUID());
        customerList.add(newCustomer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> replaceCustomer(@RequestBody Customer newCustomer, @PathVariable(value = "id")UUID customerId)
    {
        Customer customer = findById(customerId);
        if (customer != null) {
            customer.setFirst_name(newCustomer.getFirst_name());
            customer.setLast_name(newCustomer.getLast_name());
            customer.setPhone_number(newCustomer.getPhone_number());
            customer.setAddress(newCustomer.getAddress());
            customer.setEmail(newCustomer.getEmail());
            customer.setPassword(newCustomer.getPassword());
            return new ResponseEntity(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/customers/{id}")
    public ResponseEntity deleteEmployee(@PathVariable(value = "id") UUID customerId) {
        Customer customer = findById(customerId);
        if (customer != null) {
            customerList.remove(customer);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public Customer findById(UUID customerId){
        for(Customer customer : customerList )
        {
            if (customerId.equals(customer.getId()))
            {return customer;}
        }
        return null;
    }
}
