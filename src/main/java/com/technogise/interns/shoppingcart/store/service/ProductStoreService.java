package com.technogise.interns.shoppingcart.store.service;

import com.technogise.interns.shoppingcart.dto.Product;
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

    public List<Product> getAllProduct() {

      /* return productRepository.findAll()
                                  .stream()
                                     .map(productEntity1 -> ProductMapper.map(productEntity1))
                                        .collect(Collectors.toList());*/
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::map)
                .collect(Collectors.toList());

    }

    public Optional<Product> getProductByID(UUID productId) {

        Optional<ProductEntity> optionalProductEntity= productRepository.findById(productId);

        if(optionalProductEntity.isPresent()){
          return Optional.of(ProductMapper.map(optionalProductEntity.get()));
        }

        return Optional.empty();

    }

    public Product createProduct(Product newProduct) {

        newProduct.setId(UUID.randomUUID());

        ProductEntity   productEntity =productRepository.save(ProductMapper.mapToEntity(newProduct));

       return ProductMapper.map(productEntity);

    }

    public void deleteProduct(UUID productID) {
        productRepository.deleteById(productID);
    }

    public Product replaceProduct(Product newProduct) {
        ProductEntity   productEntity= productRepository.save(ProductMapper.mapToEntity(newProduct));
        return ProductMapper.map(productEntity);

    }
}
