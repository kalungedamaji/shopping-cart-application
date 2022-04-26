package com.technogise.interns.shoppingcart.store.service;

import com.technogise.interns.shoppingcart.cart.service.CartService;
import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.store.entity.ProductEntity;
import com.technogise.interns.shoppingcart.store.mpper.ProductMapper;
import com.technogise.interns.shoppingcart.store.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductStoreService {

    @Autowired
    private ProductRepository  productRepository;

    @Autowired
    private ProductMapper productMapper;

    private final Logger logger = LoggerFactory.getLogger(ProductStoreService.class);


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

    public Optional<Product> getProductByID(UUID productId) {

        Optional<ProductEntity> optionalProductEntity= productRepository.findById(productId);
        if(optionalProductEntity.isPresent()) {
            return optionalProductEntity.map(productMapper::map);
        } else{
            throw new EntityNotFoundException(Product.class, "id", productId.toString());
        }

    }

    public Product createProduct(Product newProduct) {
        logger.info("Adding product to Repository...");

        newProduct.setId(UUID.randomUUID());
        ProductEntity   productEntity =productRepository.save(productMapper.mapToEntity(newProduct));
        Product addedProduct= productMapper.map(productEntity);
        logger.debug("Product added to product repository as: "+addedProduct);
        logger.info("Added cart item in repository");

        return addedProduct;
    }


    public Optional<Product> replaceProduct(Product newProduct, UUID productId) {

        Optional<ProductEntity> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isPresent()) {
            newProduct.setId(productId);
            ProductEntity productEntity = productRepository.save(productMapper.mapToEntity(newProduct));
            return Optional.of(productMapper.map(productEntity));

        }else{
            throw new EntityNotFoundException(Product.class, "id", productId.toString());
        }

    }

    public void deleteProduct(UUID productId) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            productRepository.deleteById(productId);
        }
        else {
            throw new EntityNotFoundException(Product.class, "id", productId.toString());
        }
    }
}
