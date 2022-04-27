package com.technogise.interns.shoppingcart.store.mpper;

import com.technogise.interns.shoppingcart.cart.mapper.CartMapper;
import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.store.entity.ProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final Logger logger = LoggerFactory.getLogger(ProductMapper.class);

    public Product  map(ProductEntity   productEntity){
        logger.trace("map() called with entity: "+productEntity.toString());
        Product  product=new Product();
        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setImage(productEntity.getImage());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        logger.trace("Converted product: "+product.toString());
        return  product;
    }

    public ProductEntity mapToEntity(Product product){
        logger.trace("mapToEntity() called with product"+product);
        ProductEntity  productEntity=new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setImage(product.getImage());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        logger.trace("Converted product into entity is: "+productEntity.toString());
        return  productEntity;
    }
}
