package com.technogise.interns.shoppingcart.cart.controller;
import com.technogise.interns.shoppingcart.cart.service.CartService;
import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.store.controller.ProductController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    private final Logger logger = LoggerFactory.getLogger(CartController.class);


    @GetMapping(value="/customers/{customerId}/cart" ,produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds all cartItems in the cart",
            response = CartItem.class)
    public ResponseEntity<CollectionModel<EntityModel<CartItem>>> getAllCartItems(@PathVariable UUID customerId) {

        logger.info("Getting all the cart items from Cart Service...");
        logger.debug("getAllCartItem() is called with customerId: "+customerId);
        List<EntityModel<CartItem>> entityModelList= new ArrayList<>();
        List<CartItem> cartItemList= cartService.getAllCartItems(customerId);
        for(CartItem cartItem : cartItemList){
            EntityModel<CartItem> resource = EntityModel.of(cartItem);
            WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getCartItemById(cartItem.getId(),customerId));
            resource.add(linkToSelf.withSelfRel());
            entityModelList.add(resource);
        }
        CollectionModel<EntityModel<CartItem>> resourceList = CollectionModel.of(entityModelList);
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getAllCartItems(customerId));
        resourceList.add(linkToSelf.withSelfRel());
        logger.info("Retrieved all cart items From Cart Service");
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resourceList);
    }

    @GetMapping(value="/customers/{customerId}/cart/{cartItemId}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds cartItem by id",
            notes = "Provide an id to get specific cart item detail from the shopping cart",
            response = CartItem.class)

    public ResponseEntity<EntityModel<CartItem>> getCartItemById(@ApiParam(value = "ID value for the cartItem you need to retrieve", required = true)
                                                 @PathVariable(value = "cartItemId") UUID cartItemId, @PathVariable UUID customerId)
        {
            logger.info("Getting cart item by id from cart service...");
            logger.debug("getCartItemById() is called with customerId: "+customerId+" and cartItemId: "+cartItemId);


            CartItem cartItem = cartService.getCartItemById(cartItemId, customerId);
            EntityModel<CartItem> resource = EntityModel.of(cartItem);
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllCartItems(customerId));
            WebMvcLinkBuilder linkToProducts = linkTo(methodOn(ProductController.class).getAllProducts());
            WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getCartItemById(cartItemId,customerId));
            resource.add(linkTo.withRel("all-cartItems"));
            resource.add(linkToProducts.withRel("all-products"));
            resource.add(linkToSelf.withSelfRel());
            logger.info("Retrieved cart item by id from cart service");

            return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resource);
        }

    @PostMapping(path = "/customers/{customerId}/cart",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates cartItem",
            notes = "Provide values of the attributes to add a cartItem in the shopping cart",
            response = CartItem.class)
    public ResponseEntity<EntityModel<CartItem>> addProductToCart(@RequestBody CartItem newCartItem, @PathVariable UUID customerId) {

        logger.info("Adding product to cart service...");
        logger.debug("addProductToCartItem() is called with customerId: "+customerId+" and cartItem: "+newCartItem);

        CartItem cartItem=cartService.addProductToCart(newCartItem,customerId);
        EntityModel<CartItem> resource = EntityModel.of(cartItem);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllCartItems(customerId));
        resource.add(linkTo.withRel("all-cartItems"));

        logger.info("Added cart item in cart service.");

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping("/customers/{customerId}/cart/{cartItemId}")
    @ApiOperation(value = "Updates cartItem by id",
            notes = "Provide an id and value of all the attributes of cartItem, you want to update",
            response = CartItem.class)
    public ResponseEntity<?> updateCartItem(@RequestBody CartItem cartItemDetails, @ApiParam(value = "ID value for the cartItem you need to update",required = true) @PathVariable(value = "cartItemId")UUID cartItemId, @PathVariable UUID customerId) {

        logger.info("Updating details for cart item in service...");
        logger.debug("updateCartItem() is called with customerId: "+customerId+", cartItemId: "+cartItemId+" and cartItemDetails: "+cartItemDetails);

        CartItem replacedCartItem = cartService.updateCartItem(cartItemDetails, cartItemId, customerId);

        EntityModel<CartItem> resource = EntityModel.of(replacedCartItem);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(getClass()).getAllCartItems(customerId));
        WebMvcLinkBuilder linkToGetSelf = linkTo(methodOn(this.getClass()).getCartItemById(replacedCartItem.getId(),customerId));

        resource.add(linkTo.withRel("all-cartItems"));
        resource.add(linkToGetSelf.withSelfRel());

        logger.info("Updated details for cart item in service.");

        return new ResponseEntity<>(resource, HttpStatus.OK);

    }

    @DeleteMapping("/customers/{customerId}/cart/{cartItemId}")
    @ApiOperation(value = "Delete cartItem by id",
            notes = "Provide an id of cartItem, you want to delete",
            response = CartItem.class)
    public ResponseEntity<HttpStatus> deleteCartItem(@ApiParam(value = "ID value for the cartItem you need to delete",required = true)
                                                     @PathVariable(value = "cartItemId") UUID cartItemId,@PathVariable UUID customerId) {
        logger.info("Removing cart item from service...");
        logger.debug("deleteCartItem() is called with customerId: "+customerId+" and cartItemId: "+cartItemId);

        cartService.deleteCartItemById(cartItemId,customerId);
        logger.info(" Removed cart item from service.");

        return new ResponseEntity<>(HttpStatus.OK);


    }
}