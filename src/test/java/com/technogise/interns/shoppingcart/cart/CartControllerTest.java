package com.technogise.interns.shoppingcart.cart;

import com.technogise.interns.shoppingcart.cart.service.CartService;
import com.technogise.interns.shoppingcart.dto.CartItem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value= CartController.class)

public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    @Test
    public void viewEmptyCart() throws Exception{
        List<CartItem> cart = new ArrayList<>();
        Mockito.when(
                cartService.getAllCartItems()).thenReturn(cart);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart").accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$.links[0].rel").value("self"))
                .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart"));
    }
    @Test
    public void viewCart() throws Exception{
        List<CartItem> cart = new ArrayList<>();
        CartItem newCartItem = new CartItem();
        newCartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        newCartItem.setQuantity(5);
        newCartItem.setPrice(BigDecimal.valueOf(10.00));
        newCartItem.setImage("image");
        newCartItem.setName("Dove");
        cart.add(newCartItem);

        Mockito.when(cartService.getAllCartItems()).thenReturn(cart);

        RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart")
                .accept(MediaType.APPLICATION_JSON);

        List<CartItem> expectedCart = new ArrayList<>();
        CartItem expectedCartItem = new CartItem();
        expectedCartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        expectedCartItem.setQuantity(5);
        expectedCartItem.setPrice(BigDecimal.valueOf(10.00));
        expectedCartItem.setImage("image");
        expectedCartItem.setName("Dove");
        expectedCart.add(expectedCartItem);

        mockMvc.perform(requestBuilderGet)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(expectedCartItem.getId().toString()))
                .andExpect(jsonPath("$.content[0].name").value(expectedCartItem.getName()))
                .andExpect(jsonPath("$.content[0].quantity").value(expectedCartItem.getQuantity()))
                .andExpect(jsonPath("$.content[0].image").value(expectedCartItem.getImage()))
                .andExpect(jsonPath("$.content[0].price").value(expectedCartItem.getPrice()))
                .andExpect(jsonPath("$.content[0].links[0].rel").value("self"))
                .andExpect(jsonPath("$.content[0].links[0].href").value("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart/cf7f42d3-42d1-4727-97dd-4a086ecc0060"))
                .andExpect(jsonPath("$.links[0].rel").value("self"))
                .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart"));
    }

    @Test
    public void shouldAddAProductToCart() throws Exception{
        CartItem newCartItem = new CartItem();
        newCartItem.setQuantity(5);
        newCartItem.setPrice(BigDecimal.valueOf(10.00));
        newCartItem.setImage("image");
        newCartItem.setName("Dove");

        Mockito.when(cartService.createCartItem(any(CartItem.class))).thenReturn(newCartItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCartItem))
                .contentType(MediaType.APPLICATION_JSON);

        CartItem expectedCartItem = new CartItem();
        expectedCartItem.setQuantity(5);
        expectedCartItem.setPrice(BigDecimal.valueOf(10.00));
        expectedCartItem.setImage("image");
        expectedCartItem.setName("Dove");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.quantity").value(expectedCartItem.getQuantity()))
                .andExpect(jsonPath("$.price").value(expectedCartItem.getPrice()))
                .andExpect(jsonPath("$.image").value(expectedCartItem.getImage()))
                .andExpect(jsonPath("$.name").value(expectedCartItem.getName()))
                .andExpect(jsonPath("$.links[0].rel").value("all-cartItems"))
                .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart"));
    }
}
