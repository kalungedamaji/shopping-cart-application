package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.List;


class ShoppingCartPriceCalculator {

    private static final int TWO_DIGIT_PRECISION =2;
    private BigDecimal salesTaxMultiplier = BigDecimal.valueOf(12.5);

     void setSalesTaxMultiplier(BigDecimal salesTaxMultiplier) {
        this.salesTaxMultiplier = salesTaxMultiplier;
    }

     BigDecimal getSalesTaxMultiplier() {
        return salesTaxMultiplier.divide(BigDecimal.valueOf(100.00));
    }

     BigDecimal calculateTotalPriceWithoutTaxes(List<ShoppingCartItem> cart) {
         BigDecimal currentTotalPrice = BigDecimal.valueOf(0.00);
        for (ShoppingCartItem shoppingCartItem : cart){
            currentTotalPrice = currentTotalPrice.add((shoppingCartItem.getPrice()).
                    multiply(BigDecimal.valueOf(shoppingCartItem.getQuantity()))).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        }
        return currentTotalPrice;
    }

     BigDecimal calculateTotalSalesTax(List<ShoppingCartItem> cart) {
        BigDecimal totalSalesTaxRate= getSalesTaxMultiplier();
        BigDecimal totalSalesTax=totalSalesTaxRate.multiply(calculateTotalPriceWithoutTaxes(cart)).
                setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        return totalSalesTax;
    }

     BigDecimal calculateTotalPriceOfCartIncludingTaxes(List<ShoppingCartItem> cart){
        BigDecimal totalPriceOfCartIncludingTaxes = calculateTotalPriceWithoutTaxes(cart).add(calculateTotalSalesTax(cart));
        return totalPriceOfCartIncludingTaxes;
    }
}
