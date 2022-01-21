package com.technogise.interns.oops;

import com.technogise.interns.calculator.Calculator;

import java.math.BigDecimal;
import java.util.HashSet;

public class ShoppingCartCalculation {

    private int TWO_DIGIT_PRECISION = 2;
    private HashSet<Product> productList;
    private static final BigDecimal SALES_TAX_PERCENTAGE = BigDecimal.valueOf(12.5);

    public BigDecimal calculateTotalPriceOfShoppingCart(HashSet<Product> productList) {
        return calculateTotalPriceBeforeTax(productList).add(calculateTotalSalesTax(productList));
    }
    public BigDecimal calculateTotalSalesTax(HashSet<Product> productList) {
        BigDecimal percentageMultiplier = BigDecimal.valueOf(0.01);
        return SALES_TAX_PERCENTAGE.multiply(calculateTotalPriceBeforeTax(productList)).multiply(percentageMultiplier).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal calculateTotalPriceBeforeTax(HashSet<Product> productList) {
        BigDecimal totalPriceBeforeTax = BigDecimal.valueOf(0);
        for(Product product: productList){
            totalPriceBeforeTax = totalPriceBeforeTax.add(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP));
        }
        return totalPriceBeforeTax;
    }
}
