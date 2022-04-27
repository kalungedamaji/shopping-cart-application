
package com.technogise.interns.shoppingcart.store.service;

import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.store.entity.ProductEntity;
import com.technogise.interns.shoppingcart.store.mpper.ProductMapper;
import com.technogise.interns.shoppingcart.store.repository.ProductRepository;
import org.hamcrest.core.Is;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductStoreService productStoreService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;


    @Test
    public void shouldReturnAllProducts() {
        List<ProductEntity> productEntityList = new ArrayList<>();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        productEntity.setName("Dove");
        productEntity.setPrice(BigDecimal.valueOf(49.99));
        productEntity.setImage("Dove Image");
        productEntity.setDescription("Dove soap to be sold");
        productEntityList.add(productEntity);


        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        product.setName("Dove");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove Image");
        product.setDescription("Dove soap to be sold");
        productList.add(product);

        Mockito.when(productRepository.findAll()).thenReturn(productEntityList);
        Mockito.when(productMapper.map(any(ProductEntity.class))).thenReturn(product);

        List<Product> actualProductList = productStoreService.getAllProduct();
        assertThat(actualProductList, is(productList));

    }

    @Test
    public void shouldReturnProductAfterItsCreationInRepository(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        productEntity.setName("Dove");
        productEntity.setPrice(BigDecimal.valueOf(49.99));
        productEntity.setImage("Dove Image");
        productEntity.setDescription("Dove soap to be sold");


        Product product = new Product();
        product.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        product.setName("Dove");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove Image");
        product.setDescription("Dove soap to be sold");


        Mockito.when(productMapper.mapToEntity(any())).thenReturn(productEntity);
        Mockito.when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        Mockito.when(productMapper.map(productEntity)).thenReturn(product);

        Product actualProduct = productStoreService.createProduct(product);
        Mockito.verify(productRepository,Mockito.times(1)).save(productEntity);
        assertNotNull(actualProduct.getId());
        assertThat(actualProduct.getName(),is(product.getName()));
        assertThat(actualProduct.getPrice(),is(product.getPrice()));
        assertThat(actualProduct.getImage(),is(product.getImage()));
        assertThat(actualProduct.getDescription(),is(product.getDescription()));

    }


    @Test
    public void shouldReturnUpdatedProductWhenProductIsUpdated() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        productEntity.setName("Dove");
        productEntity.setPrice(BigDecimal.valueOf(49.99));
        productEntity.setImage("Dove Image");
        productEntity.setDescription("Dove soap to be sold");

        Product product = new Product();
        product.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        product.setName("Dove");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove Image");
        product.setDescription("Dove soap to be sold");

        Product newProduct = new Product();
        newProduct.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        newProduct.setName("Axe");
        newProduct.setPrice(BigDecimal.valueOf(69.99));
        newProduct.setImage("Axe Image");
        newProduct.setDescription("Axe Deo to be sold");

        ProductEntity newProductEntity = new ProductEntity();
        newProductEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        newProductEntity.setName("Axe");
        newProductEntity.setPrice(BigDecimal.valueOf(69.99));
        newProductEntity.setImage("Axe Image");
        newProductEntity.setDescription("Axe Deo to be sold");

        Mockito.when(productRepository.findById(any())).thenReturn(Optional.of(productEntity));
        Mockito.when(productMapper.mapToEntity(newProduct)).thenReturn(newProductEntity);
        Mockito.when(productRepository.save(any(ProductEntity.class))).thenReturn(newProductEntity);
        Mockito.when(productMapper.map(newProductEntity)).thenReturn(newProduct);

        Product actualUpdatedProduct = productStoreService.replaceProduct(newProduct, UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));

        assertThat(actualUpdatedProduct, is(newProduct));
    }

    @Test
    public void shouldDeleteProductWhenProductIsDeleted() {

        ProductEntity newProductEntity = new ProductEntity();
        newProductEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        newProductEntity.setName("Axe");
        newProductEntity.setPrice(BigDecimal.valueOf(69.99));
        newProductEntity.setImage("Axe Image");
        newProductEntity.setDescription("Axe Deo to be sold");

        Mockito.when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(newProductEntity));
        Mockito.doNothing().when(productRepository).deleteById(any(UUID.class));

        productStoreService.deleteProduct(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));

        verify(productRepository,Mockito.times(1)).deleteById(any(UUID.class));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenProductIsNotPresent(){

        UUID productId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");
        Mockito.when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () ->
            productStoreService.getProductByID(productId)
        , "EntityNotFoundException was expected");

        assertThat(thrown.getMessage(), is("Product was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}"));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenProductIsNotPresentForReplacement(){

        Product product = new Product();
        product.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        product.setName("Dove");
        product.setPrice(BigDecimal.valueOf(49.99));
        product.setImage("Dove Image");
        product.setDescription("Dove soap to be sold");

        Mockito.when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () ->
            productStoreService.replaceProduct(product,product.getId())
        , "EntityNotFoundException was expected");

        assertThat(thrown.getMessage(), Is.is("Product was not found for parameters {id=676ea10c-537b-4861-b27b-f3b8cbc0dc39}"));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenProductIsNotPresentForDeletion(){

        UUID productId = UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401");

        Mockito.when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () ->
            productStoreService.deleteProduct(productId)
        , "EntityNotFoundException was expected");

        assertThat(thrown.getMessage(), is("Product was not found for parameters {id=a0217f70-7123-45bc-a1b6-f9d392579401}"));
    }
}

