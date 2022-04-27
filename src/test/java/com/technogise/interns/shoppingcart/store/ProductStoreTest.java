package com.technogise.interns.shoppingcart.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.Application;

import com.technogise.interns.shoppingcart.dto.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "file:src/test/resources/dbtest/product-test.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "file:src/test/resources/dbtest/cleanup-product.sql")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductStoreTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9000;
    }
    @Test
    public void testGetProducts(){
        RestAssured.get("/products").then()
                .statusCode(200)
                .assertThat().body("content.id[0]", notNullValue())
                .assertThat().body("content.name[0]",equalTo("Mug-test"))
                .assertThat().body("content.image[0]",equalTo("mug image"))
                .assertThat().body("content.description[0]",equalTo("This is mug"))
                .assertThat().body("content.price[0]",equalTo(10.00f))
                .assertThat().body("links[0].rel",equalTo("self"))
                .assertThat().body("links[0].href",equalTo("http://localhost:9000/products"))
                .assertThat().body("content.links[0].rel[0]",equalTo("self"))
                .assertThat().body("content.links[0].rel[0]",notNullValue());
    }
    @Test
    public void testAddProduct() throws JsonProcessingException {
        Product product = new Product();
        product.setName("Dove-test");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove soap image");
        product.setDescription("Its a dove soap");

        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and()
                .body(objectMapper.writeValueAsString(product))
                .when()
                .post("/products")
                .then()
                .assertThat().statusCode(201)
                .assertThat().body("id", notNullValue())
                .assertThat().body("name",equalTo("Dove-test"))
                .assertThat().body("image",equalTo("Dove soap image"))
                .assertThat().body("description",equalTo("Its a dove soap"))
                .assertThat().body("links[0].rel",equalTo("all-products"))
                .assertThat().body("links[0].href",equalTo("http://localhost:9000/products"))
                .assertThat().body("links[1].rel",equalTo("self"))
                .assertThat().body("links[1].href",notNullValue());
    }
    @Test
    public void testUpdateProduct() throws JsonProcessingException {
        Product product = new Product();
        product.setName("Axe");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Axe image");
        product.setDescription("This is axe");

        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and()
                .body(objectMapper.writeValueAsString(product))
                .when()
                .put("/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("id", notNullValue())
                .assertThat().body("name",equalTo("Axe"))
                .assertThat().body("image",equalTo("Axe image"))
                .assertThat().body("description",equalTo("This is axe"))
                .assertThat().body("links[0].rel",equalTo("all-products"))
                .assertThat().body("links[0].href",equalTo("http://localhost:9000/products"))
                .assertThat().body("links[1].rel",equalTo("self"))
                .assertThat().body("links[1].href",notNullValue());
    }
    @Test
    public void testDeleteProduct() throws JsonProcessingException {
        RestAssured.delete("/products/62ecbdf5-4107-4d04-980b-d20323d2cd6c").then()
                .statusCode(200);
    }
}
