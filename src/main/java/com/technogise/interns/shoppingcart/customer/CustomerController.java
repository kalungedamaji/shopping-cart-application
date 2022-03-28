package com.technogise.interns.shoppingcart.customer;

import com.technogise.interns.shoppingcart.cart.CartController;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.orders.OrderController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class CustomerController {
    final List<Customer> customerList = new ArrayList<>();
    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds all customers",
            notes = "Returns all the customers",
            response = Customer.class)
    public ResponseEntity<CollectionModel<EntityModel<Customer>>>getAllCustomers() {
        List<EntityModel<Customer>> entityModelList= new ArrayList<>();
        for(Customer customer : customerList){
            EntityModel<Customer> resource = EntityModel.of(customer);
            WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getCustomer(customer.getId()));
            resource.add(linkToSelf.withSelfRel());
            entityModelList.add(resource);
        }
        CollectionModel<EntityModel<Customer>> resourceList = CollectionModel.of(entityModelList);
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getAllCustomers());
        resourceList.add(linkToSelf.withSelfRel());
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resourceList);
    }
    @GetMapping("/customers/{id}")
    @ApiOperation(value = "Finds customer by id",
            notes = "Returns a single customer. Use the id to get the desired customer.",
            response = Customer.class)
    public ResponseEntity<EntityModel<Customer>> getCustomer(@ApiParam(value = "Enter Id of the customer to be returned",required = true)
                                                @PathVariable(value = "id")UUID customerId)
    {
        Customer customer = findById(customerId);
        EntityModel<Customer> resource = EntityModel.of(customer);
        WebMvcLinkBuilder linkToAllCustomers = linkTo(methodOn(this.getClass()).getAllCustomers());
        WebMvcLinkBuilder linkToOrders = linkTo(methodOn(OrderController.class).getAllOrders());
        WebMvcLinkBuilder linkToCart = linkTo(methodOn(CartController.class).getAllCartItems(customerId));
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getCustomer(customerId));
        resource.add(linkToAllCustomers.withRel("all-customers"));
        resource.add(linkToCart.withRel("cart-items"));
        resource.add(linkToOrders.withRel("Orders"));
        resource.add(linkToSelf.withSelfRel());

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resource);
    }
    @PostMapping(path = "/customers")
    @ApiOperation(value = "Create new customer",
            notes = "Creates customer .Add the attributes of the new customer. Any attribute of customer if not added ,by default " +
                    "null value will be stored. Id will be auto-generated, so no need to add it.",
            response = Customer.class)
    public ResponseEntity<EntityModel<Customer>> createCustomer(@RequestBody Customer newCustomer) {
        newCustomer.setId(UUID.randomUUID());
        customerList.add(newCustomer);
        EntityModel<Customer> resource = EntityModel.of(newCustomer);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllCustomers());
        WebMvcLinkBuilder linkToGetSelf = linkTo(methodOn(this.getClass()).getCustomer(newCustomer.getId()));

        resource.add(linkTo.withRel("all-customers"));
        resource.add(linkToGetSelf.withSelfRel());

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resource);
    }
    @PutMapping("/customers/{id}")
    @ApiOperation(value = "Updates cartItem by id",
            notes = "Provide an id and value of all the attributes of cartItem, you want to update",
            response = Customer.class)
    public ResponseEntity<Customer> replaceCustomer(@RequestBody Customer newCustomer,
                                                    @ApiParam(value = "ID value for the cartItem you need to update",required = true) @PathVariable(value = "id")UUID customerId)
    {
        Customer customer = findById(customerId);
        if (customer != null) {
            customer.setFirst_name(newCustomer.getFirst_name());
            customer.setLast_name(newCustomer.getLast_name());
            customer.setPhone_number(newCustomer.getPhone_number());
            customer.setAddress(newCustomer.getAddress());
            customer.setEmail(newCustomer.getEmail());
            customer.setPassword(newCustomer.getPassword());
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/customers/{id}")
    @ApiOperation(value = "Delete customer by id",
            notes = "Returns OK status on successfully deletion of the customer. Use the Id of the specific customer to delete " +
                    "it and if Id doesn't match status NOT_found will be returned.",
            response = Customer.class)
    public ResponseEntity<HttpStatus> deleteEmployee(@ApiParam(value = "ID value for the cartItem you need to delete",required = true)
                                                     @PathVariable(value = "id") UUID customerId) {
        Customer customer = findById(customerId);
        if (customer != null) {
            customerList.remove(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public Customer findById(UUID customerId){
        for(Customer customer : customerList )
        {
            if (customerId.equals(customer.getId()))
            {return customer;
            }
        }
        return null;
    }
}