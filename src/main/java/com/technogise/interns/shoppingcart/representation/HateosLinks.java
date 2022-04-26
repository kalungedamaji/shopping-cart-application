package com.technogise.interns.shoppingcart.representation;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;

public abstract class HateosLinks {

    private final LinkedMultiValueMap<HttpMethods,Link> linksByHttpMethodMap = new LinkedMultiValueMap<>();
     /**
     add method name and respective links in EntityLinksMap as key-value pairs, one key can have multiple values.
     Format of link: linkTo(methodOn(className).methodName).withRel("relation name").
     Example: linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"))
     **/
    protected abstract void prepareLinksForMap(Object resource);
    protected abstract void prepareGetLinksForMap(List<Object> resourceList);

    public RepresentationModel<?> getHateosLinks(Object resource, HttpMethods httpMethods) {
        if (httpMethods.equals(HttpMethods.GET) && (resource instanceof List)){
            List<Object> resourceList = (List<Object>) resource;
            List<EntityModel<Object>> entityModelList= new ArrayList<>();
            for (Object object : resourceList) {
                EntityModel entityModel = EntityModel.of(object);
                prepareLinksForMap(object);
                addLinks(entityModel, httpMethods);
                entityModelList.add(entityModel);
        }
            CollectionModel<EntityModel<Object>> collectionModel = CollectionModel.of(entityModelList);
            prepareGetLinksForMap(resourceList);
            return addLinks(collectionModel, httpMethods);
        }
        else{
            EntityModel entityModel = EntityModel.of(resource);
            prepareLinksForMap(resource);
            return addLinks(entityModel, httpMethods);
        }

    }
    private RepresentationModel<?> addLinks(RepresentationModel representationModel, HttpMethods httpMethods) {

        List<Link> links = getLinksFromMap(httpMethods);
        links.stream()
                .forEach(representationModel::add);
        getlinksByHttpMethodMap().clear();
        return representationModel;
    }

    protected LinkedMultiValueMap<HttpMethods, Link> getlinksByHttpMethodMap() {
        return linksByHttpMethodMap;
    }

    protected List<Link> getLinksFromMap(HttpMethods httpMethods) {
        return getlinksByHttpMethodMap().get(httpMethods);
    }
}
