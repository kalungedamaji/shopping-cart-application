package com.technogise.interns.shoppingcart.customer;

import com.technogise.interns.shoppingcart.customer.hateosLinksProvider.CustomerLinks;
import com.technogise.interns.shoppingcart.customer.service.CustomerService;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.representation.HttpMethods;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerLinks customerLinks;

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds all customers",
            notes = "Returns all the customers",
            response = Customer.class)
    public ResponseEntity<?>getAllCustomers() {
        logger.info("Getting all the customers from Customer Service...");
        logger.debug("getAllCustomers() is called");
        final List<Customer> customerList = customerService.getAllCustomer();
        logger.info("Retrieved all customers From Customer service");
        return new ResponseEntity(customerLinks.getHateosLinks(customerList, HttpMethods.GET), HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    @ApiOperation(value = "Finds customer by id",
            notes = "Returns a single customer. Use the id to get the desired customer.",
            response = Customer.class)
    public ResponseEntity<EntityModel<Customer>> getCustomer(@ApiParam(value = "Enter Id of the customer to be returned",required = true)
                                                             @PathVariable(value = "id")UUID customerId)
    {
        logger.info("Getting customer by id from customer service...");
        logger.debug("getCustomer() is called with customerId: "+customerId);
        Customer customer = customerService.getCustomerById(customerId);
        logger.info("Retrieved customer by id from customer service");
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body((EntityModel<Customer>) customerLinks.getHateosLinks(customer, HttpMethods.GET_WITH_ID));
    }

    @PostMapping(path = "/customers")
    @ApiOperation(value = "Create new customer",
            notes = "Creates customer .Add the attributes of the new customer. Any attribute of customer if not added ,by default " +
                    "null value will be stored. Id will be auto-generated, so no need to add it.",
            response = Customer.class)

    public ResponseEntity<EntityModel<Customer>> createCustomer(@RequestBody Customer newCustomer) {
        logger.info("Adding product to customer service...");
        logger.debug("createCustomer() is called ");
        newCustomer = customerService.createCustomer(newCustomer);
        logger.info("Added customer in customer service.");
        return new ResponseEntity(customerLinks.getHateosLinks(newCustomer,HttpMethods.POST), HttpStatus.CREATED);
    }

    @PutMapping("/customers/{id}")
    @ApiOperation(value = "Updates cartItem by id",
            notes = "Provide an id and value of all the attributes of cartItem, you want to update",
            response = Customer.class)

    public ResponseEntity<EntityModel<Customer>> replaceCustomer(@RequestBody Customer newCustomer,
                                                                 @ApiParam(value = "ID value for the cartItem you need to update",required = true) @PathVariable(value = "id")UUID customerId) {
        logger.info("Updating details for customer in service...");
        logger.debug("replaceCustomer() is called with customerId: "+customerId);
        Customer replacedCustomer = customerService.replaceCustomer(newCustomer, customerId);
        logger.info("Updated details for customer in service.");
        return new ResponseEntity(customerLinks.getHateosLinks(replacedCustomer,HttpMethods.PUT),HttpStatus.OK);

    }
    @DeleteMapping("/customers/{id}")
    @ApiOperation(value = "Delete customer by id",
            notes = "Returns OK status on successfully deletion of the customer. Use the Id of the specific customer to delete " +
                    "it and if Id doesn't match status NOT_found will be returned.",
            response = Customer.class)
    public ResponseEntity<HttpStatus> deleteCustomer(@ApiParam(value = "ID value for the cartItem you need to delete",required = true)
                                                     @PathVariable(value = "id") UUID customerId) {
        logger.info("Removing customer from service...");
        logger.debug("deleteCustomer() is called with customerId: "+customerId);
        customerService.deleteCustomer(customerId);
        logger.info(" Removed customer from service.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}