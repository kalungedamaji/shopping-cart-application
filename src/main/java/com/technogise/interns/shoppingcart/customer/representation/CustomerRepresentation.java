package com.technogise.interns.shoppingcart.customer.representation;

import com.technogise.interns.shoppingcart.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepresentation {
    @Autowired
    private CustomerLinks customerLinks;

    public EntityModel<Customer> getForPost(Customer newCustomer){
        EntityModel<Customer> resource = EntityModel.of(newCustomer);
        resource.add(customerLinks.linkToSelf(newCustomer).withSelfRel());
        resource.add(customerLinks.linkToAllCustomers().withRel("all-customers"));
        resource.add(customerLinks.linkToStore().withRel("store"));
        return resource;
    }
}
