package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ShoppingCartPriceCalculator {

    private static final int TWO_DIGIT_PRECISION =2;
    private BigDecimal salesTaxMultiplier = BigDecimal.valueOf(12.5);

     void setSalesTaxMultiplier(BigDecimal salesTaxMultiplier) {
        this.salesTaxMultiplier = salesTaxMultiplier;
    }

     BigDecimal getSalesTaxMultiplier() {
        return salesTaxMultiplier.divide(BigDecimal.valueOf(100.00));
    }

     BigDecimal calculateTotalPriceWithoutTaxes(Map<Product, Integer> cart) {
         BigDecimal currentTotalPrice = BigDecimal.valueOf(0.00);
        for (Map.Entry<Product,Integer> entry : cart.entrySet()){
            currentTotalPrice = currentTotalPrice.add((entry.getKey().getPrice().
                    multiply(BigDecimal.valueOf(entry.getValue())).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP)));
        }
        return currentTotalPrice;
    }

     BigDecimal calculateTotalSalesTax(HashMap<Product,Integer> cart) {
        BigDecimal totalSalesTaxRate= getSalesTaxMultiplier();
        BigDecimal totalSalesTax=totalSalesTaxRate.multiply(calculateTotalPriceWithoutTaxes(cart)).
                setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        return totalSalesTax;
    }

     BigDecimal calculateTotalPriceOfCartIncludingTaxes(HashMap<Product,Integer> cart){
        BigDecimal totalPriceOfCartIncludingTaxes = calculateTotalPriceWithoutTaxes(cart).add(calculateTotalSalesTax(cart));
        return totalPriceOfCartIncludingTaxes;
    }
}
