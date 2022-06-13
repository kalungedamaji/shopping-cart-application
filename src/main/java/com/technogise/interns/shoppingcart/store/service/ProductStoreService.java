package com.technogise.interns.shoppingcart.store.service;

import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.store.entity.ProductEntity;
import com.technogise.interns.shoppingcart.store.mpper.ProductMapper;
import com.technogise.interns.shoppingcart.store.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductStoreService {

    private ProductRepository  productRepository;

    private ProductMapper productMapper;

    private final Logger logger = LoggerFactory.getLogger(ProductStoreService.class);

    ProductStoreService(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<Product> getAllProduct() {
        logger.info("Getting all the products from Repository...");

        List<Product> productList = productRepository.findAll()
                .stream()
                .map(productMapper::map)
                .collect(Collectors.toList());

        logger.debug("Returned productList as: "+productList.toString());
        logger.info("Retrieved all products From Repository");

        return productList;
    }

    public Product getProductByID(UUID productId) {

        logger.info("Getting product by id from Repository...");
        logger.debug("getProductById() is called with productID as: "+productId);

        Optional<ProductEntity> optionalProductEntity= productRepository.findById(productId);
        if(optionalProductEntity.isPresent()) {
            Product product =productMapper.map(optionalProductEntity.get());
            logger.debug("retrieved product is"+product);
            logger.info("retrieved product from product repository");
            return product;
        } else{
            logger.error("product for Product id : "+productId+" is not present in the repository.");
            throw new EntityNotFoundException(Product.class, "id", productId.toString());
        }

    }

    public Product createProduct(Product newProduct) {
        logger.info("Adding product to Repository...");

        newProduct.setId(UUID.randomUUID());
        ProductEntity  productEntity =productRepository.save(productMapper.mapToEntity(newProduct));
        Product addedProduct= productMapper.map(productEntity);
        logger.debug("Product added to product repository as: "+addedProduct);
        logger.info("Added products in repository");

        return addedProduct;
    }


    public Product replaceProduct(Product newProduct, UUID productId) {

        logger.info("Updating product from Repository...");
        logger.debug("replaceProduct() is called with productID as: "+productId);
        Optional<ProductEntity> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isPresent()) {
            newProduct.setId(productId);
            ProductEntity productEntity = productRepository.save(productMapper.mapToEntity(newProduct));
            Product updatedProduct = productMapper.map(productEntity);
            logger.debug("updated product in repository as"+updatedProduct);
            logger.info("retrieved updated product from product repository");
            return updatedProduct;

        }else{
            logger.error("product for Product id : "+productId+" is not present in the repository.");
            throw new EntityNotFoundException(Product.class, "id", productId.toString());
        }

    }

    public void deleteProduct(UUID productId) {
        logger.info("Deleting product from repository...");
        logger.debug("deleteProduct() is called with productId as: "+productId);
        Optional<ProductEntity> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            productRepository.deleteById(productId);
            logger.debug("deleted product for productId as: "+productId);
            logger.info(" Removed product from repository");        }
        else {
            logger.error("product for Product id : "+productId+" is not present in the repository.");
            throw new EntityNotFoundException(Product.class, "id", productId.toString());
        }
    }
}
