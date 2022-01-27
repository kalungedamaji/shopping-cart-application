package com.technogise.interns.oops;

public class ProductToCartItemConvertor {
    public CartItem convertProductToCartItem(Product product) {
        CartItem cartItem = new CartItem(product.getName(), product.getPrice());
        return cartItem;
    }
}
