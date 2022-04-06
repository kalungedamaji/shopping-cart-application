package com.technogise.interns.shoppingcart.cart;

import com.technogise.interns.shoppingcart.cart.service.CartService;
import com.technogise.interns.shoppingcart.dto.CartItem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebMvcTest(value= CartController.class)

public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    public void viewEmptyCart() throws Exception{
        List<CartItem> cart = new ArrayList<>();
        Mockito.when(
                cartService.getAllCartItems()).thenReturn(cart);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"_links\":{\"self\":{\"href\":\"http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart\"}}}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }
    @Test
    public void viewCartItem() throws Exception{
        List<CartItem> cart = new ArrayList<>();
        CartItem mockCartItem = new CartItem();
        mockCartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        mockCartItem.setQuantity(5);
        mockCartItem.setPrice(BigDecimal.valueOf(10.00));
        mockCartItem.setImage("image");
        mockCartItem.setName("Dove");
        cart.add(mockCartItem);
        Mockito.when(
                cartService.getAllCartItems()).thenReturn(cart);
        RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get(
                "http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilderGet).andReturn();

        String expected = "{\"_embedded\":{\"cartItemList\":[{\"id\":\"cf7f42d3-42d1-4727-97dd-4a086ecc0060\",\"name\":\"Dove\",\"price\":10.00,\"quantity\":5,\"image\":\"image\",\"_links\":{\"self\":{\"href\":\"http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart/cf7f42d3-42d1-4727-97dd-4a086ecc0060\"}}}]},\"_links\":{\"self\":{\"href\":\"http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart\"}}}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }
}
