package com.technogise.interns.shoppingcart.store.hateosLinksProvider;

import com.technogise.interns.shoppingcart.customer.CustomerController;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.representation.HateosLinks;
import com.technogise.interns.shoppingcart.representation.HttpMethods;
import com.technogise.interns.shoppingcart.store.controller.ProductController;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductLinks extends HateosLinks {

    @Override
    protected void prepareLinksForMap(Object resource) {
        getlinksByHttpMethodMap().add(HttpMethods.POST,linkTo(methodOn(ProductController.class).getProduct(((Product)resource).getId())).withSelfRel());
        getlinksByHttpMethodMap().add(HttpMethods.POST, linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all-products"));
        getlinksByHttpMethodMap().add(HttpMethods.GET_WITH_ID, linkTo(methodOn(ProductController.class).getAllProducts()).withRel("store"));
        getlinksByHttpMethodMap().add(HttpMethods.GET_WITH_ID, linkTo(methodOn(ProductController.class).getProduct(((Product)resource).getId())).withSelfRel());
        getlinksByHttpMethodMap().add(HttpMethods.PUT, linkTo(methodOn(ProductController.class).getProduct(((Product)resource).getId())).withSelfRel());
        getlinksByHttpMethodMap().add(HttpMethods.PUT, linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all-products"));
        getlinksByHttpMethodMap().add(HttpMethods.GET, linkTo(methodOn(ProductController.class).getProduct(((Product)resource).getId())).withSelfRel());

    }

    @Override
    protected void prepareGetLinksForMap(List<Object> resourceList) {
        getlinksByHttpMethodMap().add(HttpMethods.GET, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
    }
}
