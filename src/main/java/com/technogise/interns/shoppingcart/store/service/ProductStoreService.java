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

        return optionalProductEntity.map(ProductMapper::map);

    }

    public Product createProduct(Product newProduct) {

        newProduct.setId(UUID.randomUUID());

        ProductEntity   productEntity =productRepository.save(ProductMapper.mapToEntity(newProduct));

       return ProductMapper.map(productEntity);

    }

    public boolean deleteProduct(UUID productID) {
        if(productRepository.findById(productID).isPresent()) {

            productRepository.deleteById(productID);
            return  true;
        }
        return  false;
    }

    public Optional<Product> replaceProduct(Product newProduct, UUID productID) {

        if(productRepository.findById(productID).isPresent()) {
            newProduct.setId(productID);
            ProductEntity productEntity = productRepository.save(ProductMapper.mapToEntity(newProduct));
            return Optional.of(ProductMapper.map(productEntity));

        }else{
            return  Optional.empty();
        }

    }
}
