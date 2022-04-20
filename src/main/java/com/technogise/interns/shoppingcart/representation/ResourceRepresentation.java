package com.technogise.interns.shoppingcart.representation;

import com.technogise.interns.shoppingcart.customer.hateosLinksProvider.CustomerLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;

public abstract class ResourceRepresentation<T> {
    @Autowired
    private CustomerLinks customerLinks;

    public abstract EntityModel<T> addLinksForPost(EntityModel<T> entityModel);
    public abstract EntityModel<T> addLinksForPut(EntityModel<T> entityModel);

    public EntityModel<T> getForPost(T resource){
        EntityModel<T> entityModel = EntityModel.of(resource);
        return addLinksForPost(entityModel);
    }
    public EntityModel<T> getForPut(T resource){
        EntityModel<T> entityModel = EntityModel.of(resource);
        return addLinksForPut(entityModel);
    }

}