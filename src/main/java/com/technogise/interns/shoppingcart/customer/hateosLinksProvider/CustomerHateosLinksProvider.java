package com.technogise.interns.shoppingcart.customer.hateosLinksProvider;

import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.representation.ResourceRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerHateosLinksProvider extends ResourceRepresentation {
    @Autowired
    private CustomerLinks customerLinks;

    @Override
    public EntityModel addLinksForPost(EntityModel entityModel) {
        entityModel.add(customerLinks.linkToSelf((Customer) entityModel.getContent()).withSelfRel());
        entityModel.add(customerLinks.linkToAllCustomers().withRel("all-customers"));
        entityModel.add(customerLinks.linkToStore().withRel("store"));
        return entityModel;
    }

    public EntityModel addLinksForPut(EntityModel entityModel) {
        entityModel.add(customerLinks.linkToSelf((Customer) entityModel.getContent()).withSelfRel());
        entityModel.add(customerLinks.linkToAllCustomers().withRel("all-customers"));
        return entityModel;
    }
}