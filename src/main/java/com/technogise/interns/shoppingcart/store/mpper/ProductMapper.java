package com.technogise.interns.shoppingcart.store.mpper;

import com.technogise.interns.shoppingcart.dto.Product;
import com.technogise.interns.shoppingcart.store.entity.ProductEntity;

public class ProductMapper {

    public static Product  map(ProductEntity   productEntity){
        Product  product=new Product();
        product.setId(productEntity.getId());
        product.setImage(productEntity.getImage());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        return  product;
    }

    public static ProductEntity mapToEntity(Product product){

        ProductEntity  productEntity=new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setImage(product.getImage());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        return  productEntity;
    }
}
