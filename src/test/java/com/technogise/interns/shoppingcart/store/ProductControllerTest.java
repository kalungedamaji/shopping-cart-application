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

import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class) - for junit4
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(value=ProductController.class)

public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductStoreService productStoreService;

    Product mockProduct = new Product();

   // String storePath = "{\"name\":\"Spring\",

   // @LocalServerPort
   // int randomServerPort;
/*
    public void createProduct() {
        mockProduct.setName("Dove");
        mockProduct.setPrice(BigDecimal.valueOf(49.99));
        mockProduct.setImage("Dove soap image");
        mockProduct.setDescription("Its a dove soap");
        mockProduct.setId(UUID.randomUUID());
    }
*/

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

}
