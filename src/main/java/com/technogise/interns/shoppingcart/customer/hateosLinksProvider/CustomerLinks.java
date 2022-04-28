package com.technogise.interns.shoppingcart.customer.hateosLinksProvider;

import com.technogise.interns.shoppingcart.cart.CartController;
import com.technogise.interns.shoppingcart.customer.CustomerController;
import com.technogise.interns.shoppingcart.representation.HttpMethods;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.orders.order.OrderController;
import com.technogise.interns.shoppingcart.representation.HateosLinks;
import com.technogise.interns.shoppingcart.store.controller.ProductController;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerLinks extends HateosLinks {

    @Override
    public void prepareLinksForMap(Object resource) {
        getlinksByHttpMethodMap().add(HttpMethods.POST, linkTo(methodOn(CustomerController.class).getCustomer(((Customer) resource).getId())).withSelfRel());
        getlinksByHttpMethodMap().add(HttpMethods.POST, linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));
        getlinksByHttpMethodMap().add(HttpMethods.POST, linkTo(methodOn(ProductController.class).getAllProducts()).withRel("store"));
        getlinksByHttpMethodMap().add(HttpMethods.PUT, linkTo(methodOn(CustomerController.class).getCustomer(((Customer) resource).getId())).withSelfRel());
        getlinksByHttpMethodMap().add(HttpMethods.PUT, linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));
        getlinksByHttpMethodMap().add(HttpMethods.GET_WITH_ID, linkTo(methodOn(OrderController.class).getAllOrders(((Customer) resource).getId())).withRel("Orders"));
        getlinksByHttpMethodMap().add(HttpMethods.GET_WITH_ID, linkTo(methodOn(CartController.class).getAllCartItems(((Customer) resource).getId())).withRel("cart-items"));
        getlinksByHttpMethodMap().add(HttpMethods.GET_WITH_ID, linkTo(methodOn(CustomerController.class).getCustomer(((Customer) resource).getId())).withSelfRel());
        getlinksByHttpMethodMap().add(HttpMethods.GET, linkTo(methodOn(CustomerController.class).getCustomer(((Customer) resource).getId())).withSelfRel());
    }

    @Override
    protected void prepareGetLinksForMap(List<Object> resourceList) {
        getlinksByHttpMethodMap().add(HttpMethods.GET, linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());
        getlinksByHttpMethodMap().add(HttpMethods.GET, linkTo(methodOn(ProductController.class).getAllProducts()).withRel("product-store"));
    }


}