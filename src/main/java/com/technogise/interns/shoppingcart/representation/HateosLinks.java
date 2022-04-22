package com.technogise.interns.shoppingcart.representation;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

public abstract class HateosLinks {

    private LinkedMultiValueMap<String,Link> linksByHttpMethodMap = new LinkedMultiValueMap<>();

    /**
     add method name and respective links in EntityLinksMap as key-value pairs, one key can have multiple values.
     Format of link: linkTo(methodOn(className).methodName).withRel("relation name").
     Example: linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"))
     **/
    protected abstract void prepareLinksForMap(Object resource);

    public EntityModel getHateosLinks(Object resource, String httpMethod){
        prepareLinksForMap(resource);
        return addLinks(resource, httpMethod);
    }

    private EntityModel addLinks(Object resource, String httpMethod) {
        EntityModel entityModel = EntityModel.of(resource);
        List<Link> links = getLinksFromMap(httpMethod);
        links.stream()
                .forEach(link -> entityModel.add(link));
        getlinksByHttpMethodMap().clear();
        return entityModel;
    }

    protected LinkedMultiValueMap<String, Link> getlinksByHttpMethodMap() {
        return linksByHttpMethodMap;
    }

    protected List<Link> getLinksFromMap(String httpMethod) {
        return linksByHttpMethodMap.get(httpMethod);
    }
}

/*k->v
post->link1
post->link2
post->link3

put->link1
put->link2

get

delete

map.get(key)==>value
 */
