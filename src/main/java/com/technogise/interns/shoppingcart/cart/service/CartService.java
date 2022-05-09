package com.technogise.interns.shoppingcart.cart.service;

import com.technogise.interns.shoppingcart.cart.entity.CartItemEntity;
import com.technogise.interns.shoppingcart.cart.mapper.CartMapper;
import com.technogise.interns.shoppingcart.cart.repository.CartRepository;
import com.technogise.interns.shoppingcart.customer.repository.CustomerRepository;
import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

        @Autowired
        private CartRepository cartRepository;
        @Autowired
        private CustomerRepository customerRepository;
        @Autowired
        private CartMapper cartMapper;
        private final Logger logger = LoggerFactory.getLogger(CartService.class);

        public List<CartItem> getAllCartItems(UUID customerId) {
                if(customerRepository.findById(customerId).isPresent()){
                        logger.info("Getting all the cart items from Repository...");

                        List<CartItem> cartItemList =   cartRepository.findByCustomerId(customerId)
                                .stream()
                                .map(cartMapper::entityToCartItemConvertor)
                                .collect(Collectors.toList());
                        logger.debug("Returned cartItemList as: "+cartItemList.toString());
                        logger.info("Retrieved all cart items From Repository");

                        return  cartItemList;
                }
                else{
                        throw new EntityNotFoundException(Customer.class, "id", customerId.toString());

                }

        }
        public CartItem getCartItemById(UUID cartItemId, UUID customerId) {
                if(customerRepository.findById(customerId).isPresent()){
                        logger.info("Getting cart item by id from Repository...");
                        logger.debug("getCartItemById() is called with cartItemId as: "+cartItemId);

                        Optional<CartItemEntity> optionalCartItemEntity = cartRepository.findById(cartItemId);
                        if(optionalCartItemEntity.isPresent()){
                                CartItem cartItem= cartMapper.entityToCartItemConvertor(optionalCartItemEntity.get());
                                logger.debug("Returned cartItem is: "+cartItem);
                                logger.info("Retrieved cart item by id from repository");
                                return cartItem;
                        }
                        else{
                                logger.error("cartItem for cartItemId: "+cartItemId+" is not present in the repository.");
                                throw new EntityNotFoundException(CartItem.class, "id", cartItemId.toString());
                        }
                }
                else{
                        throw new EntityNotFoundException(Customer.class, "id", customerId.toString());

                }


        }

        public CartItem addProductToCart(CartItem cartItem, UUID customerId){
                if(customerRepository.findById(customerId).isPresent()){
                        logger.info("Adding product to Repository...");
                        logger.debug("addProductToCart() is called with cartItem as: "+cartItem);
                        cartItem.setId(UUID.randomUUID());
                        cartItem.setCustomerId(customerId);

                        CartItemEntity cartItemEntity = cartRepository.save(cartMapper.cartItemToEntityConvertor(cartItem));
                        CartItem addedCartItem = cartMapper.entityToCartItemConvertor(cartItemEntity);
                        logger.debug("Product added to cart item as: "+addedCartItem);
                        logger.info("Added cart item in repository");

                        return addedCartItem;
                }
                else{
                        throw new EntityNotFoundException(Customer.class, "id", customerId.toString());

                }


        }

        public CartItem updateCartItem(CartItem cartItemDetail, UUID cartItemId, UUID customerId) {
                if(customerRepository.findById(customerId).isPresent()){
                        logger.info("Updating details for cart item in repository...");
                        logger.debug("updateCartItem() is called with cartItemId as: "+cartItemId+" and cartItemDetails as: "+cartItemDetail.toString());
                        if(cartRepository.findById(cartItemId).isPresent()) {
                                cartItemDetail.setId(cartItemId);
                                CartItemEntity customerEntity = cartRepository.save(cartMapper.cartItemToEntityConvertor(cartItemDetail));
                                CartItem cartItem = cartMapper.entityToCartItemConvertor(customerEntity);
                                logger.debug("Updated cartItem is: "+cartItem);
                                logger.info("Updated details for cart item in repository");

                                return cartItem;
                        }
                        else{
                                logger.error("cartItem for cartItemId: "+cartItemId+" is not present in the repository.");
                                throw new EntityNotFoundException(CartItem.class,"id",cartItemId.toString());
                        }
                }
                else{
                        throw new EntityNotFoundException(Customer.class, "id", customerId.toString());
                }

        }

        public void deleteCartItemById(UUID cartItemId, UUID customerId) {
                if(customerRepository.findById(customerId).isPresent()){
                        logger.info("Deleting cart item from repository...");
                        logger.debug("deleteCartItemById() is called with cartItemId as: "+cartItemId);
                        if(cartRepository.findById(cartItemId).isPresent()) {
                                logger.debug("deleted cartItem for cartItemId as: "+cartItemId);
                                logger.info(" Removed cart item from repository");

                                cartRepository.deleteById(cartItemId);
                        }
                        else{
                                logger.error("cartItem for cartItemId: "+cartItemId+" is not present in the repository.");
                                throw new EntityNotFoundException(CartItem.class, "id", cartItemId.toString());
                        }
                }
                else{
                        throw new EntityNotFoundException(Customer.class, "id", customerId.toString());
                }

        }

        public void deleteAllCartItems(UUID customerId) {
                if(customerRepository.findById(customerId).isPresent()){
                        cartRepository.deleteByCustomerId(customerId);

                }
                else{
                        throw new EntityNotFoundException(Customer.class, "id", customerId.toString());
                }
        }
}
