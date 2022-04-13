package com.technogise.interns.shoppingcart.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.store.service.ProductStoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.hamcrest.core.Is.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value=ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductStoreService productStoreService;

    @Test
    public void viewEmptyStore() throws Exception {
        List<Product> store = new ArrayList<>();
        Mockito.when(productStoreService.getAllProduct()).thenReturn(store);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "http://localhost:9000/products").accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.links[0].rel").value("self"))
                .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/products"));
    }

    @Test
    public void viewStoreWhenSingleProductIsAdded() throws Exception {
        List<Product> store = new ArrayList<>();
        Product product = new Product();
        product.setName("Dove");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove soap image");
        product.setDescription("Its a dove soap");
        product.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));
        store.add(product);

        Mockito.when(productStoreService.getAllProduct()).thenReturn(store);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/products")
        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(product.getName()))
                .andExpect(jsonPath("$.content[0].price").value(product.getPrice()))
                .andExpect(jsonPath("$.content[0].image").value(product.getImage()))
                .andExpect(jsonPath("$.content[0].description").value(product.getDescription()))
                .andExpect(jsonPath("$.links[0].rel").value("self"))
                .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/products"))
                .andExpect(jsonPath("$.content[0].links[0].rel").value("self"))
                .andExpect(jsonPath("$.content[0].links[0].href").value("http://localhost:9000/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c"))
        ;
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        Product product = new Product();
        product.setName("Dove");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove soap image");
        product.setDescription("Its a dove soap");
        product.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));

        Mockito.when(productStoreService.createProduct(Mockito.any(Product.class))).thenReturn(product);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:9000/products")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.image", is(product.getImage())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.links[0].rel" , is("all-products")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost:9000/products")))
                .andExpect(jsonPath("$.links[1].rel", is("self")))
                .andExpect(jsonPath("$.links[1].href", is("http://localhost:9000/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c")))
        ;

    }


}