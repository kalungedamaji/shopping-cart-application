package com.technogise.interns.shoppingcart.store.repository;

import com.technogise.interns.shoppingcart.store.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void shouldBeEmptyIfNoProductsArePresent(){
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).isEmpty();
    }

    @Test
    public void shouldSaveAProduct() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        productEntity.setName("Dove");
        productEntity.setPrice(BigDecimal.valueOf(49.99));
        productEntity.setImage("Dove Image");
        productEntity.setDescription("Dove soap to be sold");
        ProductEntity savedProduct = productRepository.save(productEntity);

        assertThat(savedProduct).hasFieldOrPropertyWithValue("id", UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
    }

    @Test
    public void shouldDeleteProductById() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        productEntity.setName("Dove");
        productEntity.setPrice(BigDecimal.valueOf(49.99));
        productEntity.setImage("Dove Image");
        productEntity.setDescription("Dove soap to be sold");
        entityManager.persist(productEntity);

        productRepository.deleteById(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));

        assertThat(productRepository.findById(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"))).isEmpty();
    }

    @Test
    public void shouldFindAllProducts() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        productEntity.setName("Dove");
        productEntity.setPrice(BigDecimal.valueOf(49.99));
        productEntity.setImage("Dove Image");
        productEntity.setDescription("Dove soap to be sold");
        entityManager.persist(productEntity);

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setId(UUID.fromString("676ea10c-537b-4261-b27b-f3b8cbc0dc39"));
        productEntity2.setName("Axe");
        productEntity2.setPrice(BigDecimal.valueOf(30.00));
        productEntity2.setImage("Axe Image");
        productEntity2.setDescription("Axe to be sold");
        entityManager.persist(productEntity2);

        List<ProductEntity> products = productRepository.findAll();

        assertThat(products).hasSize(2).contains(productEntity,productEntity2);
    }

    @Test
    public void findProductById() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        productEntity.setName("Dove");
        productEntity.setPrice(BigDecimal.valueOf(49.99));
        productEntity.setImage("Dove Image");
        productEntity.setDescription("Dove soap to be sold");
        entityManager.persist(productEntity);

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setId(UUID.fromString("676ea10c-537b-4261-b27b-f3b8cbc0dc39"));
        productEntity2.setName("Axe");
        productEntity2.setPrice(BigDecimal.valueOf(30.00));
        productEntity2.setImage("Axe Image");
        productEntity2.setDescription("Axe to be sold");
        entityManager.persist(productEntity2);

        Optional<ProductEntity> getProduct = productRepository.findById(productEntity.getId());
        Optional<ProductEntity> optionalProductEntity = Optional.of(productEntity);
        assertThat(getProduct).isEqualTo(optionalProductEntity);
    }
}
