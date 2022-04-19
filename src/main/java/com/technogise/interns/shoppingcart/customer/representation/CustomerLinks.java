package com.technogise.interns.shoppingcart.customer.representation;

import com.technogise.interns.shoppingcart.customer.CustomerController;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.store.ProductController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class CustomerLinks {

    //method return type??
    //WebMvcLinkBuilder linkToGetSelf = linkTo(methodOn(CustomerController.class).getCustomer(newCustomer.getId()));
    //WebMvcLinkBuilder linkToStore = linkTo(methodOn(ProductController.class).getAllProducts());
    //WebMvcLinkBuilder linkTo = linkTo(methodOn(CustomerController.class).getAllCustomers());

    public WebMvcLinkBuilder linkToAllCustomers(){
        return linkTo(methodOn(CustomerController.class).getAllCustomers());
    }
    public WebMvcLinkBuilder linkToStore(){
        return linkTo(methodOn(ProductController.class).getAllProducts());
    }
    public WebMvcLinkBuilder linkToSelf(Customer newCustomer){
        return linkTo(methodOn(CustomerController.class).getCustomer(newCustomer.getId()));
    }

}
