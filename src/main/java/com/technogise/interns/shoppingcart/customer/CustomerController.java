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

    @GetMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@PathVariable(value="id") UUID customerId) {

        Customer customer = findById(customerId);


        return new ResponseEntity(customer, HttpStatus.OK);
    }


    @PostMapping(path = "/customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> create(@RequestBody Customer newCustomer) {

        newCustomer.setId(UUID.randomUUID());
        customerList.add(newCustomer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> replaceCustomer(@RequestBody Customer newCustomer, @PathVariable(value = "id")UUID customerID) {
        Customer customer = findById(customerID);
        if (customer != null) {
            customer.setFirstName(newCustomer.getFirstName());
            customer.setLastName(newCustomer.getLastName());
            customer.setPhoneNumber(newCustomer.getPhoneNumber());
            customer.setAddress(newCustomer.getAddress());
            customer.setEmail(newCustomer.getEmail());
            customer.setPassword(newCustomer.getPassword());
            return new ResponseEntity(customer, HttpStatus.OK);

        } else {// Change to --> Customer not found
            newCustomer.setId(customerID);
            customerList.add(newCustomer);
            return new ResponseEntity(newCustomer, HttpStatus.OK);

        }
    }

   @DeleteMapping("/customers/{id}")
    void deleteEmployee(@PathVariable(value = "id") UUID customerId) {
       Customer customer = findById(customerId);
       if (customer != null) {
           customerList.remove(customer);
       }
       else{
           //Change to --> Customer not found
       }
   }


    public Customer findById(UUID customerId){
        for(Customer customer : customerList)
        {
            if(customerId.equals(customer.getId()))
            {
                return customer;
            }
        }
        return null;
    }
}