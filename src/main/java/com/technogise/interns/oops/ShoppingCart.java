package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.HashSet;

public class ShoppingCart {
    private int numberOfProducts = 0;
    private Product product;
    private ShoppingCartCalculation shoppingCartCalculation = new ShoppingCartCalculation();
    HashSet<Product> productList = new HashSet<>();

    public void addProducts(Product product, int numberOfProducts) {
        int productCount = getNumberOfProducts() + numberOfProducts;
        setProduct(product);
        setNumberOfProducts(productCount);
        getProduct().addQuantity(numberOfProducts);
        productList.add(product);
    }

    public int getQuantityOfProduct (Product product)
    {
        return product.getQuantity();
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public Product getProduct() {
        return product;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getTotalPrice() {
        return shoppingCartCalculation.calculateTotalPriceOfShoppingCart(productList);
    }

    public BigDecimal getTotalPriceBeforeTax() {
        return shoppingCartCalculation.calculateTotalPriceBeforeTax(productList);
    }

    public BigDecimal getTotalSalesTax() {
        return shoppingCartCalculation.calculateTotalSalesTax(productList);
    }
}
