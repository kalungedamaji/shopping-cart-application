package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.HashSet;

public class ShoppingCart {
    private int numberOfProducts = 0;
    private Product product;
    private static final int TWO_DIGIT_PRECISION =2;
    final BigDecimal SALES_TAX = BigDecimal.valueOf(12.5);


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
        setProduct(product);
        return getProduct().getQuantity();
    }

    public BigDecimal calculateTotalPriceBeforeTax() {
        BigDecimal totalPriceBeforeTax = BigDecimal.valueOf(0);
    //     return getProduct().getPrice().multiply(BigDecimal.valueOf(getNumberOfProducts())).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP) ;
        for(Product product: productList){
            setProduct(product);
            totalPriceBeforeTax = totalPriceBeforeTax.add(getProduct().getPrice().multiply(BigDecimal.valueOf(getQuantityOfProduct(getProduct()))).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP));
        }
        return totalPriceBeforeTax;
    }

    public BigDecimal calculateTotalSalesTax() {
        BigDecimal salesTax;
        BigDecimal percentageMultiplier = BigDecimal.valueOf(0.01);
        salesTax = SALES_TAX.multiply(calculateTotalPriceBeforeTax()).multiply(percentageMultiplier).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        return salesTax;
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

    public BigDecimal calculateTotalPrice() {

        return calculateTotalPriceBeforeTax().add(calculateTotalSalesTax());
    }
}
