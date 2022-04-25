package com.technogise.interns.shoppingcart.customer.hateosLinksProvider;

import com.technogise.interns.shoppingcart.customer.CustomerController;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.representation.HateosLinks;
import com.technogise.interns.shoppingcart.store.ProductController;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerLinks extends HateosLinks {

    @Override
    public void prepareLinksForMap(Object resource) {
        getlinksByHttpMethodMap().add("post", linkTo(methodOn(CustomerController.class).getCustomer(((Customer)resource).getId())).withSelfRel());
        getlinksByHttpMethodMap().add("post", linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));
        getlinksByHttpMethodMap().add("post", linkTo(methodOn(ProductController.class).getAllProducts()).withRel("store"));
        getlinksByHttpMethodMap().add("put", linkTo(methodOn(CustomerController.class).getCustomer(((Customer)resource).getId())).withSelfRel());
        getlinksByHttpMethodMap().add("put", linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));
    }

}
