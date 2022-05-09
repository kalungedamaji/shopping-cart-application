package com.technogise.interns.shoppingcart.store.controller;
import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.representation.HttpMethods;
import com.technogise.interns.shoppingcart.store.hateosLinksProvider.ProductLinks;
import com.technogise.interns.shoppingcart.store.service.ProductStoreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ProductController {
    @Autowired
    private ProductStoreService productStoreService;

    @Autowired
    private ProductLinks productLinks;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(value= "/products", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all the products",
            notes = "Returns all the products from the shopping cart",
            response = Product.class)
    public ResponseEntity<CollectionModel<EntityModel<Product>>>getAllProducts() {
        logger.info("Getting all the products from product Service...");
        final List<Product> productList = productStoreService.getAllProduct();
        logger.info("Retrieved all products From product store Service");
        return new ResponseEntity<>((CollectionModel<EntityModel<Product>>) productLinks.getHateosLinks(productList,HttpMethods.GET) , HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}" , produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get a single product by id",
            notes = "Returns a single product. Use the id to get the desired product.",
            response = Product.class)
    public ResponseEntity<EntityModel<Product>> getProduct(@ApiParam(value = "Enter Id of the product to be returned", required = true) @PathVariable(value = "id")UUID productId)
    {
        logger.info("Getting product by id from product service...");
        logger.debug("getProductById() is called with productId: "+productId);
        Product product = productStoreService.getProductByID(productId);
        logger.info("Retrieved product by id from product service");
        return new ResponseEntity<>((EntityModel<Product>) productLinks.getHateosLinks(product,HttpMethods.GET_WITH_ID) , HttpStatus.OK);
    }
    @PostMapping(path = "/products", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new product",
            notes = "Creates product and add it in the shopping cart.Add the attributes of the new product. Any attribute of product if not added ,by default " +
                    "null value will be stored. Id will be auto-generated, so no need to add it.",
            response = Product.class)
    public ResponseEntity<EntityModel<Product>> createProduct(@ApiParam(value = "Enter new product", required = true)@RequestBody Product newProduct)
    {
        logger.info("Adding product to product service...");
        logger.debug("createProduct() is called with product: "+newProduct);
        newProduct = productStoreService.createProduct(newProduct);
        logger.info("Retrieved all products From product store Service");
        return new ResponseEntity(productLinks.getHateosLinks(newProduct, HttpMethods.POST), HttpStatus.CREATED);
    }
    @PutMapping(value = "/products/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update a product by id",
            notes = "Returns an updated product. Provide an id to Update specific product in the shopping cart and " +
                    "only specify the attributes which are to be updated, rest fields will remain unchanged" ,
            response = Product.class)
    public ResponseEntity<EntityModel<Product>>  replaceProduct(@ApiParam(value = "Enter product attributes to be updated.") @RequestBody Product newProduct, @ApiParam(value = "Enter id of the product to be updated.", required = true) @PathVariable(value = "id")UUID productId)
    {
        logger.info("Updating details for product in service...");
        logger.debug("replaceProduct() is called with productId: "+productId);
        Product replacedProduct = productStoreService.replaceProduct(newProduct,productId);
        logger.info("Updated details for product in service.");
        return new ResponseEntity(productLinks.getHateosLinks(replacedProduct, HttpMethods.PUT),HttpStatus.OK);

//           EntityModel<Product> resource = EntityModel.of(replacedProduct.get());
//           WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllProducts());
//           WebMvcLinkBuilder linkToGetSelf = linkTo(methodOn(this.getClass()).getProduct(replacedProduct.get().getId()));
//           resource.add(linkTo.withRel("all-products"));
//           resource.add(linkToGetSelf.withSelfRel());
//           return ResponseEntity.ok(resource);

    }
    @DeleteMapping(value = "/products/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a product by id",
            notes = "Returns OK status on successfully deletion of the product. Use the Id of the specific product to delete " +
                    "it and if Id doesn't match status NOT_found will be returned.",
            response = Product.class)
    public ResponseEntity<HttpStatus> deleteProduct(@ApiParam(value = "Enter the id of product to be deleted.", required = true) @PathVariable(value = "id") UUID productID) {
        logger.info("Removing product from service...");
        logger.debug("deleteProduct() is called with productId: "+productID);
        productStoreService.deleteProduct(productID);
        logger.info(" Removed product from service.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
