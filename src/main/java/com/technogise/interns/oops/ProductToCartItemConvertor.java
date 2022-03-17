package com.technogise.interns.oops;

public class ProductToCartItemConvertor {
    public Cart convertProductToCartItem(Product product) {
        Cart cartItem = new Cart(product.getName(), product.getPrice(), product.getGiftAbility());
        return cartItem;
    }
}
