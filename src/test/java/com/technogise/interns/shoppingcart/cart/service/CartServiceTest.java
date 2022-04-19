package com.technogise.interns.shoppingcart.cart.service;

import com.technogise.interns.shoppingcart.cart.entity.CartItemEntity;
import com.technogise.interns.shoppingcart.cart.mapper.CartMapper;
import com.technogise.interns.shoppingcart.cart.repository.CartRepository;
import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private CartMapper cartMapper;



    @Test
    public void testGetAllCartItemsShouldReturnAllCartItems() {
        List<CartItem> expectedCartItemList = new ArrayList<>();
        CartItem cartItem1 = new CartItem();
        cartItem1.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        cartItem1.setName("Dove");
        cartItem1.setImage("Dove Image");
        cartItem1.setPrice(BigDecimal.valueOf(10.00));
        cartItem1.setQuantity(5);

        CartItem cartItem2 = new CartItem();
        cartItem2.setId(UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678"));
        cartItem2.setName("Deo");
        cartItem2.setImage("Deo Image");
        cartItem2.setPrice(BigDecimal.valueOf(10.00));
        cartItem2.setQuantity(6);

        expectedCartItemList.add(cartItem1);
        expectedCartItemList.add(cartItem2);

        List<CartItemEntity> cartItemEntityList = new ArrayList<>();
        CartItemEntity cartItemEntity1 = new CartItemEntity();
        cartItemEntity1.setId(cartItem1.getId());
        cartItemEntity1.setName(cartItem1.getName());
        cartItemEntity1.setImage(cartItem1.getImage());
        cartItemEntity1.setPrice(cartItem1.getPrice());
        cartItemEntity1.setQuantity(cartItem1.getQuantity());

        CartItemEntity cartItemEntity2 = new CartItemEntity();
        cartItemEntity2.setId(cartItem2.getId());
        cartItemEntity2.setName(cartItem2.getName());
        cartItemEntity2.setImage(cartItem2.getImage());
        cartItemEntity2.setPrice(cartItem2.getPrice());
        cartItemEntity2.setQuantity(cartItem2.getQuantity());

        cartItemEntityList.add(cartItemEntity1);
        cartItemEntityList.add(cartItemEntity2);

        Mockito.when(cartRepository.findAll()).thenReturn(cartItemEntityList);

        Mockito.when(cartMapper.entityToCartItemConvertor(cartItemEntity1)).thenReturn(cartItem1);
        Mockito.when(cartMapper.entityToCartItemConvertor(cartItemEntity2)).thenReturn(cartItem2);

        List<CartItem> actualCartItemList = cartService.getAllCartItems();

        MatcherAssert.assertThat(actualCartItemList, is(expectedCartItemList));

    }
    @Test
    public void shouldAddProductInCart(){

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        cartItemEntity.setName("Dove");
        cartItemEntity.setImage("Dove Image");
        cartItemEntity.setPrice(BigDecimal.valueOf(10.00));
        cartItemEntity.setQuantity(5);

        CartItem cartItem = new CartItem();
        cartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        cartItem.setName("Dove");
        cartItem.setImage("Dove Image");
        cartItem.setPrice(BigDecimal.valueOf(10.00));
        cartItem.setQuantity(5);

        Mockito.when(cartRepository.save(any(CartItemEntity.class))).thenReturn(cartItemEntity);
        Mockito.when(cartMapper.cartItemToEntityConvertor(cartItem)).thenReturn(cartItemEntity);
        Mockito.when(cartMapper.entityToCartItemConvertor(cartItemEntity)).thenReturn(cartItem);

        CartItem actualCartItem = cartService.addProductToCart(cartItem);
        verify(cartRepository,Mockito.times(1)).save(cartItemEntity);
        Assertions.assertNotNull(actualCartItem.getId());
        MatcherAssert.assertThat(actualCartItem.getName(), is(cartItem.getName()));
        MatcherAssert.assertThat(actualCartItem.getImage(), is(cartItem.getImage()));
        MatcherAssert.assertThat(actualCartItem.getPrice(), is(cartItem.getPrice()));
        MatcherAssert.assertThat(actualCartItem.getQuantity(), is(cartItem.getQuantity()));

    }

@Test
public void shouldReturnCustomerFromRepositoryWithRequiredId() {

    CartItemEntity cartItemEntity = new CartItemEntity();
    cartItemEntity.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
    cartItemEntity.setName("Dove");
    cartItemEntity.setImage("Dove Image");
    cartItemEntity.setPrice(BigDecimal.valueOf(10.00));
    cartItemEntity.setQuantity(5);

    CartItem cartItem = new CartItem();
    cartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
    cartItem.setName("Dove");
    cartItem.setImage("Dove Image");
    cartItem.setPrice(BigDecimal.valueOf(10.00));
    cartItem.setQuantity(5);

    CartItem expectedCartItem = new CartItem();
    expectedCartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
    expectedCartItem.setName("Dove");
    expectedCartItem.setImage("Dove Image");
    expectedCartItem.setPrice(BigDecimal.valueOf(10.00));
    expectedCartItem.setQuantity(5);

    Mockito.when(cartRepository.findById(any(UUID.class))).thenReturn(Optional.of(cartItemEntity));
    Mockito.when(cartMapper.entityToCartItemConvertor(any(CartItemEntity.class))).thenReturn(cartItem);

    CartItem actualRequiredCartItem = cartService.getCartItemById(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));

    MatcherAssert.assertThat(actualRequiredCartItem, is(expectedCartItem));
}
    @Test
    public void shouldDeleteRequiredEntityFromRepository(){

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        cartItemEntity.setName("Dove");
        cartItemEntity.setImage("Dove Image");
        cartItemEntity.setPrice(BigDecimal.valueOf(10.00));
        cartItemEntity.setQuantity(5);

        Mockito.when(cartRepository.findById(any(UUID.class))).thenReturn(Optional.of(cartItemEntity));
        Mockito.doNothing().when(cartRepository).deleteById(any(UUID.class));

        cartService.deleteCartItemById(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));

        verify(cartRepository,Mockito.times(1)).deleteById(any(UUID.class));

    }
    @Test
    public void shouldReturnUpdatedCartItemFromRepository(){

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        cartItemEntity.setName("Deo");
        cartItemEntity.setImage("Deo Image");
        cartItemEntity.setPrice(BigDecimal.valueOf(20.00));
        cartItemEntity.setQuantity(6);

        CartItemEntity newCartItemEntity = new CartItemEntity();
        newCartItemEntity.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        newCartItemEntity.setName("Dove");
        newCartItemEntity.setImage("Dove Image");
        newCartItemEntity.setPrice(BigDecimal.valueOf(10.00));
        newCartItemEntity.setQuantity(5);

        CartItem newCartItem = new CartItem();
        newCartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        newCartItem.setName("Dove");
        newCartItem.setImage("Dove Image");
        newCartItem.setPrice(BigDecimal.valueOf(10.00));
        newCartItem.setQuantity(5);


        Mockito.when(cartRepository.findById(any(UUID.class))).thenReturn(Optional.of(cartItemEntity));
        Mockito.when(cartRepository.save(any(CartItemEntity.class))).thenReturn(newCartItemEntity);
        Mockito.when(cartMapper.cartItemToEntityConvertor(any(CartItem.class))).thenReturn(newCartItemEntity);
        Mockito.when(cartMapper.entityToCartItemConvertor(newCartItemEntity)).thenReturn(newCartItem);

        CartItem actualReplacedCustomer = cartService.updateCartItem(newCartItem,UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));

        MatcherAssert.assertThat(actualReplacedCustomer, is(newCartItem));
    }

    @Test
    public void getCartItemShouldThrowNotFoundExceptionWhenCartItemIsNotPresent(){

        UUID cartItemId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");
        Mockito.when(cartRepository.findById(any())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> cartService.getCartItemById(cartItemId), "EntityNotFoundException was expected");

        MatcherAssert.assertThat(thrown.getMessage(), is("CartItem was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}"));
        //(jsonPath("$.status",is("UNPROCESSABLE_ENTITY")));

    }

    @Test
    public void replaceCartItemShouldThrowNotFoundExceptionWhenCartItemIdIsInvalid(){
        CartItem newCartItem = new CartItem();
        newCartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        newCartItem.setName("Dove");
        newCartItem.setImage("Dove Image");
        newCartItem.setPrice(BigDecimal.valueOf(10.00));
        newCartItem.setQuantity(5);

        UUID cartItemId = UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060");

        Mockito.when(cartRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> cartService.updateCartItem(newCartItem,cartItemId),"EntityNotFoundException was expected");
        MatcherAssert.assertThat(thrown.getMessage(), is("CartItem was not found for parameters {id=cf7f42d3-42d1-4727-97dd-4a086ecc0060}"));
    }

    @Test
    public void deleteCartItemShouldThrowNotFoundErrorWhenCartItemIdIsInvalid() {
        UUID cartItemId = UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060");

        Mockito.when(cartRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> cartService.deleteCartItemById(cartItemId),"EntityNotFoundException was expected");

        MatcherAssert.assertThat(thrown.getMessage(), is("CartItem was not found for parameters {id=cf7f42d3-42d1-4727-97dd-4a086ecc0060}"));
    }
}
