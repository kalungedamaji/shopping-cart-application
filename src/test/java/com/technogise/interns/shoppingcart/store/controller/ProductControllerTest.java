package com.technogise.interns.shoppingcart.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.customer.CustomerController;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.representation.HttpMethods;
import com.technogise.interns.shoppingcart.store.hateosLinksProvider.ProductLinks;
import com.technogise.interns.shoppingcart.store.service.ProductStoreService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.hamcrest.core.Is.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value= ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductStoreService productStoreService;

    @MockBean
    private ProductLinks productLinks;
//    @Test
//    public void viewEmptyStore() throws Exception {
//        List<Product> store = new ArrayList<>();
//        List<EntityModel> entityModelList = new ArrayList<>();
//        CollectionModel<EntityModel> collectionModel = CollectionModel.of(entityModelList);
//        collectionModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
//
//        Mockito.when(productStoreService.getAllProduct()).thenReturn(store);
//        doReturn(collectionModel).when(productLinks).getHateosLinks(store,HttpMethods.GET);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
//                "http://localhost:9000/products").accept(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isEmpty())
//                .andExpect(jsonPath("$.links[0].rel").value("self"))
//                .andExpect(jsonPath("$.links[0].href").value("/products"));
//    }
//
//    @Test
//    public void viewStoreWhenSingleProductIsAdded() throws Exception {
//        List<EntityModel> entityModelList = new ArrayList<>();
//        List<Product> store = new ArrayList<>();
//        Product product = new Product();
//        product.setName("Dove");
//        product.setPrice(BigDecimal.valueOf(49.99));
//        product.setImage("Dove soap image");
//        product.setDescription("Its a dove soap");
//        product.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));
//        store.add(product);
//
//        EntityModel<Product> entityModel = EntityModel.of(product);
//        entityModel.add(linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel());
//        entityModelList.add(entityModel);
//
//        CollectionModel<EntityModel> collectionModel = CollectionModel.of(entityModelList);
//        collectionModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
//
//        Mockito.when(productStoreService.getAllProduct()).thenReturn(store);
//        doReturn(collectionModel).when(productLinks).getHateosLinks(store,HttpMethods.GET);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/products")
//        .accept(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].name").value(product.getName()))
//                .andExpect(jsonPath("$.content[0].price").value(product.getPrice()))
//                .andExpect(jsonPath("$.content[0].image").value(product.getImage()))
//                .andExpect(jsonPath("$.content[0].description").value(product.getDescription()))
//                .andExpect(jsonPath("$.links[0].rel").value("self"))
//                .andExpect(jsonPath("$.links[0].href").value("/products"))
//                .andExpect(jsonPath("$.content[0].links[0].rel").value("self"))
//                .andExpect(jsonPath("$.content[0].links[0].href").value("/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c"));
//    }

//    @Test
//    public void shouldCreateProductWhenProductIsCreated() throws Exception {
//        Product product = new Product();
//        product.setName("Dove");
//        product.setPrice(BigDecimal.valueOf(49.99));
//        product.setImage("Dove soap image");
//        product.setDescription("Its a dove soap");
//        product.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));
//
//        EntityModel<Product> resource = EntityModel.of(product);
//        resource.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all-products"));
//        resource.add(linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel());
//
//        Mockito.when(productStoreService.createProduct(any(Product.class))).thenReturn(product);
//        doReturn(resource).when(productLinks).getHateosLinks(product,HttpMethods.POST);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:9000/products")
//                .accept(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(product))
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name", is(product.getName())))
//                .andExpect(jsonPath("$.price").value(product.getPrice()))
//                .andExpect(jsonPath("$.image", is(product.getImage())))
//                .andExpect(jsonPath("$.description", is(product.getDescription())))
//                .andExpect(jsonPath("$.links[0].rel" , is("all-products")))
//                .andExpect(jsonPath("$.links[0].href", is("/products")))
//                .andExpect(jsonPath("$.links[1].rel", is("self")))
//                .andExpect(jsonPath("$.links[1].href", is("/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c")));
//    }
//
//    @Test
//    public void shouldUpdateProductWhenProductIsUpdated() throws Exception {
//
//        Product product = new Product();
//        product.setName("Dove");
//        product.setPrice(BigDecimal.valueOf(49.99));
//        product.setImage("Dove soap image");
//        product.setDescription("Its a dove soap");
//        product.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));
//
//        Product newProduct = new Product();
//        newProduct.setName("Axe");
//        newProduct.setPrice(BigDecimal.valueOf(49.99));
//        newProduct.setImage("Axe deo image");
//        newProduct.setDescription("Its a axe deo");
//        newProduct.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));
//
//        EntityModel<Product> resource = EntityModel.of(newProduct);
//        resource.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all-products"));
//        resource.add(linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel());
//
//        Mockito.when(productStoreService.replaceProduct(any(Product.class), any(UUID.class))).thenReturn(newProduct);
//        doReturn(resource).when(productLinks).getHateosLinks(newProduct,HttpMethods.PUT);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:9000/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c")
//                .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newProduct))
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").isNotEmpty())
//                .andExpect(jsonPath("$.name").value(newProduct.getName()))
//                .andExpect(jsonPath("$.price").value((newProduct.getPrice())))
//                .andExpect(jsonPath("$.image").value(newProduct.getImage()))
//                .andExpect(jsonPath("$.description").value(newProduct.getDescription()))
//                .andExpect(jsonPath("$.links[0].rel").value("all-products"))
//                .andExpect(jsonPath("$.links[0].href").value("/products"))
//                .andExpect(jsonPath("$.links[1].rel").value("self"))
//                .andExpect(jsonPath("$.links[1].href").value("/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c"));
//    }

    @Test
    public void shouldDeleteProductWhenProductIsDeleted() throws Exception {

        Product product = new Product();
        product.setName("Dove");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove soap image");
        product.setDescription("Its a dove soap");
        product.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));

        Mockito.doNothing().when(productStoreService).deleteProduct(any(UUID.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:9000/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder);

        verify(productStoreService,Mockito.times(1)).deleteProduct(any(UUID.class));

    }

    @Test
    public void shouldReturnNotFoundErrorWhenProductIsNotPresent() throws Exception {

        UUID productId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");

        Mockito.when(productStoreService.getProductByID(any(UUID.class))).thenThrow(new EntityNotFoundException(Product.class, "id", productId.toString()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/products/43668cf2-6ce4-4238-b32e-dfadafb98678")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("Product was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }

    @Test
    public void shouldReturnNotFoundErrorWhenProductIsNotPresentForReplacement() throws Exception {

        Product product = new Product();
        product.setName("Dove");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove soap image");
        product.setDescription("Its a dove soap");
        product.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));

        Mockito.when(productStoreService.replaceProduct(any(Product.class), any(UUID.class))).thenThrow(new EntityNotFoundException(Product.class, "id", product.getId().toString()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:9000/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("Product was not found for parameters {id=62ecbdf5-4107-4d04-980b-d20323d2cd6c}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }

    @Test
    public void shouldReturnNotFoundErrorWhenProductIsNotPresentForDeletion() throws Exception {

        Product product = new Product();
        product.setName("Dove");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove soap image");
        product.setDescription("Its a dove soap");
        product.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));

        Mockito.doThrow(new EntityNotFoundException(Product.class, "id", product.getId().toString())).when(productStoreService).deleteProduct(any(UUID.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:9000/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("Product was not found for parameters {id=62ecbdf5-4107-4d04-980b-d20323d2cd6c}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }

    @Test
    public void shouldReturnUnprocessableEntityWhenThereIsConstraintViolation() throws Exception {

        Product product = new Product();
        product.setName("Dove");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove soap image");
        product.setDescription("Its a dove soap");
        product.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));


        Mockito.when(productStoreService.createProduct(any(Product.class))).thenThrow(new ConstraintViolationException("could not execute statement", new SQLException("ERROR: product data value cannot be null - violates null constraint") , "order_data"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:9000/products")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status", is("UNPROCESSABLE_ENTITY")))
                .andExpect(jsonPath("$.timestamp", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))))
                .andExpect(jsonPath("$.message", is("could not execute statement")))
                .andExpect(jsonPath("$.debugMessage[0]", is("ERROR: product data value cannot be null - violates null constraint")));
    }
}
