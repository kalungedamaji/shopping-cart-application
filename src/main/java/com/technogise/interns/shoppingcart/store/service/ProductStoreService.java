package com.technogise.interns.shoppingcart.store.service;

import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.store.entity.ProductEntity;
import com.technogise.interns.shoppingcart.store.mpper.ProductMapper;
import com.technogise.interns.shoppingcart.store.repository.ProductRepository;
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


    public List<Product> getAllProduct() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::map)
                .collect(Collectors.toList());

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
        newProduct.setId(UUID.randomUUID());
        ProductEntity   productEntity =productRepository.save(productMapper.mapToEntity(newProduct));
        return productMapper.map(productEntity);
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
