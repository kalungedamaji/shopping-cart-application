package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.Set;

class ShoppingCartPriceCalculator {

    private static final int TWO_DIGIT_PRECISION =2;
    private BigDecimal salesTaxMultiplier = BigDecimal.valueOf(12.5);

     void setSalesTaxMultiplier(BigDecimal salesTaxMultiplier) {
        this.salesTaxMultiplier = salesTaxMultiplier;
    }

     BigDecimal getSalesTaxMultiplier() {
        return salesTaxMultiplier.divide(BigDecimal.valueOf(100.00));
    }

     BigDecimal calculateTotalPriceWithoutTaxes(Set<Cart> cartItemsList) {
         BigDecimal currentTotalPrice = BigDecimal.valueOf(0.00);
        for (Cart cartItem : cartItemsList){
            currentTotalPrice = currentTotalPrice.add((cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP)));
        }
        return currentTotalPrice;
    }

     BigDecimal calculateTotalSalesTax(Set<Cart> cartItemsList) {
        BigDecimal totalSalesTaxRate= getSalesTaxMultiplier();
        BigDecimal totalSalesTax=totalSalesTaxRate.multiply(calculateTotalPriceWithoutTaxes(cartItemsList)).
                setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        return totalSalesTax;
    }

     BigDecimal calculateTotalPriceOfCartIncludingTaxes(Set<Cart> cartItemsList){
        BigDecimal totalPriceOfCartIncludingTaxes = calculateTotalPriceWithoutTaxes(cartItemsList).add(calculateTotalSalesTax(cartItemsList));
        return totalPriceOfCartIncludingTaxes;
    }
}
