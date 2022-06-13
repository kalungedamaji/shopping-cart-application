package com.technogise.interns.shoppingcart.cart;

import com.technogise.interns.shoppingcart.cart.controller.CartController;
import com.technogise.interns.shoppingcart.cart.service.CartService;
import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
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
//
//    @Test
//    public void shouldReturnEmptyCart() throws Exception{
//        List<CartItem> cart = new ArrayList<>();
//        Mockito.when(
//                cartService.getAllCartItems(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"))).thenReturn(cart);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
//                "http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart").accept(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder).andExpect(status().isOk())
//                .andExpect(jsonPath("$.links[0].rel").value("self"))
//                .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart"));
//    }
//    @Test
//    public void shouldReturnAllCartItemsForACustomer() throws Exception{
//        List<CartItem> cart = new ArrayList<>();
//        CartItem newCartItem = new CartItem();
//        newCartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
//        newCartItem.setQuantity(5);
//        newCartItem.setPrice(BigDecimal.valueOf(10.00));
//        newCartItem.setImage("image");
//        newCartItem.setName("Dove");
//        cart.add(newCartItem);
//
//        Mockito.when(cartService.getAllCartItems(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"))).thenReturn(cart);
//
//        RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart")
//                .accept(MediaType.APPLICATION_JSON);
//
//        CartItem expectedCartItem = new CartItem();
//        expectedCartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
//        expectedCartItem.setQuantity(5);
//        expectedCartItem.setPrice(BigDecimal.valueOf(10.00));
//        expectedCartItem.setImage("image");
//        expectedCartItem.setName("Dove");
//
//        mockMvc.perform(requestBuilderGet)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].id").value(expectedCartItem.getId().toString()))
//                .andExpect(jsonPath("$.content[0].name").value(expectedCartItem.getName()))
//                .andExpect(jsonPath("$.content[0].quantity").value(expectedCartItem.getQuantity()))
//                .andExpect(jsonPath("$.content[0].image").value(expectedCartItem.getImage()))
//                .andExpect(jsonPath("$.content[0].price").value(expectedCartItem.getPrice()))
//                .andExpect(jsonPath("$.content[0].links[0].rel").value("self"))
//                .andExpect(jsonPath("$.content[0].links[0].href").value("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart/cf7f42d3-42d1-4727-97dd-4a086ecc0060"))
//                .andExpect(jsonPath("$.links[0].rel").value("self"))
//                .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart"));
//    }

    @Test
    public void shouldReturnCartItemWithRequiredId() throws Exception {
        CartItem newCartItem = new CartItem();
        newCartItem.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCartItem.setName("Dove");
        newCartItem.setImage("Dove img");
        newCartItem.setPrice(BigDecimal.valueOf(10.00));
        newCartItem.setQuantity(5);

        Mockito.when(cartService.getCartItemById(any(UUID.class), any(UUID.class))).thenReturn(newCartItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/customers/676ea10c-537b-4861-b27b-f3b8cbc0dc36/cart/676ea10c-537b-4861-b27b-f3b8cbc0dc36")
                .accept(MediaType.APPLICATION_JSON);

        CartItem expectedCartItem = new CartItem();
        expectedCartItem.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        expectedCartItem.setName("Dove");
        expectedCartItem.setImage("Dove img");
        expectedCartItem.setPrice(BigDecimal.valueOf(10.00));
        expectedCartItem.setQuantity(5);


        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.quantity").value(expectedCartItem.getQuantity()))
                .andExpect(jsonPath("$.price").value(expectedCartItem.getPrice()))
                .andExpect(jsonPath("$.image").value(expectedCartItem.getImage()))
                .andExpect(jsonPath("$.name").value(expectedCartItem.getName()));
    }

//    @Test
//    public void shouldAddAProductToCart() throws Exception{
//        CartItem newCartItem = new CartItem();
//        newCartItem.setQuantity(5);
//        newCartItem.setPrice(BigDecimal.valueOf(10.00));
//        newCartItem.setImage("image");
//        newCartItem.setName("Dove");
//        newCartItem.setCustomerId(UUID.randomUUID());
//        newCartItem.setId(UUID.randomUUID());
//
//        Mockito.when(cartService.addProductToCart(any(CartItem.class),any(UUID.class))).thenReturn(newCartItem);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart")
//                .accept(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(newCartItem))
//                .contentType(MediaType.APPLICATION_JSON);
//
//        CartItem expectedCartItem = new CartItem();
//        expectedCartItem.setQuantity(5);
//        expectedCartItem.setPrice(BigDecimal.valueOf(10.00));
//        expectedCartItem.setImage("image");
//        expectedCartItem.setName("Dove");
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNotEmpty())
//                .andExpect(jsonPath("$.quantity").value(expectedCartItem.getQuantity()))
//                .andExpect(jsonPath("$.price").value(expectedCartItem.getPrice()))
//                .andExpect(jsonPath("$.image").value(expectedCartItem.getImage()))
//                .andExpect(jsonPath("$.name").value(expectedCartItem.getName()))
//                .andExpect(jsonPath("$.links[0].rel").value("all-cartItems"))
//                .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart"));
//    }
    @Test
    public void shouldUpdateCartItem() throws Exception {

        CartItem newCartItem = new CartItem();
        newCartItem.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCartItem.setName("Dove");
        newCartItem.setImage("Dove img");
        newCartItem.setPrice(BigDecimal.valueOf(10.00));
        newCartItem.setQuantity(5);


        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:9000/customers/676ea10c-537b-4861-b27b-f3b8cbc0dc36/cart/676ea10c-537b-4861-b27b-f3b8cbc0dc36")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCartItem))
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(cartService.updateCartItem(any(CartItem.class), any(UUID.class), any(UUID.class))).thenReturn(newCartItem);

        CartItem updatedCartItem = new CartItem();
        updatedCartItem.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        updatedCartItem.setName("Dove");
        updatedCartItem.setImage("Dove img");
        updatedCartItem.setPrice(BigDecimal.valueOf(10.00));
        updatedCartItem.setQuantity(5);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(updatedCartItem.getId().toString())))
                .andExpect(jsonPath("$.quantity").value(updatedCartItem.getQuantity()))
                .andExpect(jsonPath("$.price").value(updatedCartItem.getPrice()))
                .andExpect(jsonPath("$.image").value(updatedCartItem.getImage()))
                .andExpect(jsonPath("$.name").value(updatedCartItem.getName()));

    }
    
    @Test
    public void shouldDeleteCartItem() throws Exception{
        CartItem newCartItem = new CartItem();
        newCartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        newCartItem.setQuantity(5);
        newCartItem.setPrice(BigDecimal.valueOf(10.00));
        newCartItem.setImage("image");
        newCartItem.setName("Dove");

        Mockito.doNothing().when(cartService).deleteCartItemById(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"),UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/cart/cf7f42d3-42d1-4727-97dd-4a086ecc0060")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }


    @Test
    public void getCartItemByIdShouldReturnNotFoundErrorWhenCartItemIsNotPresent() throws Exception {

        UUID cartItemId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");

        Mockito.when(cartService.getCartItemById(any(UUID.class), any(UUID.class))).thenThrow(new EntityNotFoundException(CartItem.class, "id", cartItemId.toString()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98678/cart/43668cf2-6ce4-4238-b32e-dfadafb98678")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("CartItem was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());

    }

    @Test
    public void updateCartItemShouldReturnNotFoundErrorIfCartItemIsNotPresent() throws Exception{
        CartItem newCartItem = new CartItem();
        newCartItem.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCartItem.setName("Dove");
        newCartItem.setImage("Dove img");
        newCartItem.setPrice(BigDecimal.valueOf(10.00));
        newCartItem.setQuantity(5);

        UUID cartItemId = UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:9000/customers/676ea10c-537b-4861-b27b-f3b8cbc0dc36/cart/676ea10c-537b-4861-b27b-f3b8cbc0dc36")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCartItem))
                .contentType(MediaType.APPLICATION_JSON);
        Mockito.when(cartService.updateCartItem(any(CartItem.class),any(UUID.class), any(UUID.class))).thenThrow(new EntityNotFoundException(CartItem.class, "id", cartItemId.toString()) );

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("CartItem was not found for parameters {id=676ea10c-537b-4861-b27b-f3b8cbc0dc36}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }

    @Test
    public void deleteCartItemShouldReturnNotFoundErrorIfCartItemIsNotPresent() throws Exception{
        UUID cartItemId = UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36");

        Mockito.doThrow(new EntityNotFoundException(CartItem.class, "id", cartItemId.toString())).when(cartService).deleteCartItemById(cartItemId, UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678"));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98678/cart/676ea10c-537b-4861-b27b-f3b8cbc0dc36")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("CartItem was not found for parameters {id=676ea10c-537b-4861-b27b-f3b8cbc0dc36}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }

}
