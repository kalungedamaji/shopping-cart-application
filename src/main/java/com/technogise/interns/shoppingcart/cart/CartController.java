package com.technogise.interns.shoppingcart.cart;
import com.technogise.interns.shoppingcart.cart.service.CartService;
import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.store.ProductController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    final List<CartItem> cartItemList = new ArrayList<>();

    @GetMapping(value="/customers/{customerId}/cart" ,produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds all cartItems in the cart",
            response = CartItem.class)
    public ResponseEntity<CollectionModel<EntityModel<CartItem>>> getAllCartItems(@PathVariable UUID customerId) {
        List<EntityModel<CartItem>> entityModelList= new ArrayList<>();
        List<CartItem> cartItemList= cartService.getAllCartItems();
        for(CartItem cartItem : cartItemList){
            EntityModel<CartItem> resource = EntityModel.of(cartItem);
            WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getCartItemById(cartItem.getId(),customerId));
            resource.add(linkToSelf.withSelfRel());
            entityModelList.add(resource);
        }
        CollectionModel<EntityModel<CartItem>> resourceList = CollectionModel.of(entityModelList);
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getAllCartItems(customerId));
        resourceList.add(linkToSelf.withSelfRel());
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resourceList);
    }

    @GetMapping(value="/customers/{customerId}/cart/{cartItemId}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds cartItem by id",
            notes = "Provide an id to get specific cart item detail from the shopping cart",
            response = CartItem.class)

    public ResponseEntity<EntityModel<CartItem>> getCartItemById(@ApiParam(value = "ID value for the cartItem you need to retrieve", required = true)
                                                 @PathVariable(value = "cartItemId") UUID cartItemId, @PathVariable UUID customerId)
        {
            CartItem cartItem = findById(cartItemId);
            EntityModel<CartItem> resource = EntityModel.of(cartItem);
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllCartItems(customerId));
            WebMvcLinkBuilder linkToProducts = linkTo(methodOn(ProductController.class).getAllProducts());
            WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getCartItemById(cartItemId,customerId));
            resource.add(linkTo.withRel("all-cartItems"));
            resource.add(linkToProducts.withRel("all-products"));
            resource.add(linkToSelf.withSelfRel());

            return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resource);
        }

    @PostMapping(path = "/customers/{customerId}/cart",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates cartItem",
            notes = "Provide values of the attributes to add a cartItem in the shopping cart",
            response = CartItem.class)
    public ResponseEntity<EntityModel<CartItem>> createCartItem(@RequestBody CartItem newCartItem, @PathVariable UUID customerId) {
        CartItem cartItem=cartService.createCartItem(newCartItem);
        cartItem.setId(UUID.randomUUID());
        EntityModel<CartItem> resource = EntityModel.of(cartItem);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllCartItems(customerId));
        resource.add(linkTo.withRel("all-cartItems"));
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping("/customers/{customerId}/cart/{cartItemId}")
    @ApiOperation(value = "Updates cartItem by id",
            notes = "Provide an id and value of all the attributes of cartItem, you want to update",
            response = CartItem.class)
    public ResponseEntity<CartItem> replaceCartItem(@RequestBody CartItem newCartItem, @ApiParam(value = "ID value for the cartItem you need to update",required = true) @PathVariable(value = "cartItemId")UUID cartItemId, @PathVariable UUID customerId) {
        CartItem cartItem = findById(cartItemId);
        if (cartItem != null) {
            cartItem.setId(cartItemId);
            cartItem.setImage(newCartItem.getImage());
            cartItem.setName(newCartItem.getName());
            cartItem.setQuantity(newCartItem.getQuantity());
            cartItem.setPrice(newCartItem.getPrice());
            return new ResponseEntity<>(cartItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/customers/{customerId}/cart/{cartItemId}")
    @ApiOperation(value = "Delete cartItem by id",
            notes = "Provide an id of cartItem, you want to delete",
            response = CartItem.class)
    public ResponseEntity<HttpStatus> deleteCartItem(@ApiParam(value = "ID value for the cartItem you need to delete",required = true)
                                                     @PathVariable(value = "cartItemId") UUID cartItemId,@PathVariable UUID customerId) {
        CartItem cartItem = findById(cartItemId);
        if (cartItem != null) {
            cartItemList.remove(cartItem);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public CartItem findById(UUID cartItemId){
        for(CartItem cartItem : cartItemList)
        {
            if(cartItemId.equals(cartItem.getId()))
            {return cartItem;}
        }
        return null;
    }
}