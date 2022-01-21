package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartPriceCalculator {
    private static final int TWO_DIGIT_PRECISION =2;
    private BigDecimal salesTaxMultiplier = BigDecimal.valueOf(12.5);

    public void setSalesTaxMultiplier(BigDecimal salesTaxMultiplier) {
        this.salesTaxMultiplier = salesTaxMultiplier;
    }

    public BigDecimal getSalesTaxMultiplier() {
        return salesTaxMultiplier.divide(BigDecimal.valueOf(100.00));
    }

    public BigDecimal calculateTotalPriceWithoutTax(HashMap<Product,Integer> cart) {
        BigDecimal currentTotalPrice = BigDecimal.valueOf(0.00);
        for (Map.Entry<Product,Integer> item : cart.entrySet()){
            BigDecimal productTotalPrice = (item.getKey().getPrice().multiply(BigDecimal.valueOf(item.getValue())).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP));
            currentTotalPrice=currentTotalPrice.add(productTotalPrice);
        }
        return currentTotalPrice;
    }

    public BigDecimal calculateTotalSalesTax(HashMap<Product,Integer> cart) {
        BigDecimal totalSalesTaxRate= salesTaxMultiplier.divide(BigDecimal.valueOf(100.00));
        BigDecimal totalSalesTax=totalSalesTaxRate.multiply(calculateTotalPriceWithoutTax(cart)).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        return totalSalesTax;
    }

    public BigDecimal calculateTotalPriceOfCartIncludingTaxes(HashMap<Product,Integer> cart){
        BigDecimal totalPriceOfCartIncludingTaxes = calculateTotalPriceWithoutTax(cart).add(calculateTotalSalesTax(cart)) ;
        return totalPriceOfCartIncludingTaxes;
    }


}
