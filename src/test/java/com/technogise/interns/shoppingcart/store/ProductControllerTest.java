package com.technogise.interns.shoppingcart.store;

import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.store.service.ProductStoreService;
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


@WebMvcTest(value=ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductStoreService productStoreService;


    @Test
    public void viewEmptyStore() throws Exception {
        List<Product> store = new ArrayList<>();
        Mockito.when(
                productStoreService.getAllProduct()).thenReturn(store);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "http://localhost:9000/products").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"_links\":{\"self\":{\"href\":\"http://localhost:9000/products\"}}}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void viewStoreWhenSingleProductIsAdded() throws Exception {
        List<Product> store = new ArrayList<>();
        Product mockProduct = new Product();
        UUID id = UUID.randomUUID();
        mockProduct.setName("Dove");
        mockProduct.setPrice(BigDecimal.valueOf(49.99));
        mockProduct.setImage("Dove soap image");
        mockProduct.setDescription("Its a dove soap");
        mockProduct.setId(id);
        store.add(mockProduct);

        Mockito.when(
                productStoreService.getAllProduct()).thenReturn(store);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "http://localhost:9000/products").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"_embedded\":{\"productList\":[{\"id\":" + id + ",\"name\":\"Dove\",\"price\":49.99,\"image\":\"Dove soap image\",\"description\":\"Its a dove soap\",\"_links\":{\"self\":{\"href\":\"http://localhost:9000/products/" + id + "\" }}}]},\"_links\":{\"self\":{\"href\":\"http://localhost:9000/products\"}}}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);

    }

    @Test
    public void viewStoreWhenMultipleProductIsAdded() throws Exception {
        List<Product> store = new ArrayList<>();
        Product mockProduct1 = new Product();
        Product mockProduct2 = new Product();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        mockProduct1.setName("Dove");
        mockProduct1.setPrice(BigDecimal.valueOf(49.99));
        mockProduct1.setImage("Dove soap image");
        mockProduct1.setDescription("Its a dove soap");
        mockProduct1.setId(id1);
        store.add(mockProduct1);

        mockProduct2.setName("Axe");
        mockProduct2.setPrice(BigDecimal.valueOf(99.99));
        mockProduct2.setImage("Axe Deo image");
        mockProduct2.setDescription("Its an Axe Deo");
        mockProduct2.setId(id2);
        store.add(mockProduct2);

        Mockito.when(
                productStoreService.getAllProduct()).thenReturn(store);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "http://localhost:9000/products").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"_embedded\":{\"productList\":[{\"id\":" + id1 + ",\"name\":\"Dove\",\"price\":49.99,\"image\":\"Dove soap image\",\"description\":\"Its a dove soap\",\"_links\":{\"self\":{\"href\":\"http://localhost:9000/products/" + id1 + "\" }}}, {\"id\":" + id2 + ",\"name\":\"Axe\",\"price\":99.99,\"image\":\"Axe Deo image\",\"description\":\"Its an Axe Deo\",\"_links\":{\"self\":{\"href\":\"http://localhost:9000/products/" + id2 + "\"}}}]},\"_links\":{\"self\":{\"href\":\"http://localhost:9000/products\"}}}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

}