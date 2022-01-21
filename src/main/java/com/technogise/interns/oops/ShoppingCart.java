package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private int numberOfProducts = 0;
    private Product product;
    private static final int TWO_DIGIT_PRECISION =2;
    private HashMap<Product, Integer> cart = new HashMap<>();

    public void addProducts(final Product product, final int numberOfProducts) {
        setProduct(product);
        int productCount=getNumberOfProducts()+numberOfProducts;
        if(cart.containsKey(product))
        {
            int productQuantity= cart.get(product) + numberOfProducts;
            cart.put(product,productQuantity);
        }
        else
        {
            cart.put(product,numberOfProducts);
        }
        setNumberOfProducts(productCount);
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal currentTotalPrice = BigDecimal.valueOf(0.00);
        for (Map.Entry<Product,Integer> item : cart.entrySet()){
            BigDecimal productTotalPrice = (item.getKey().getPrice().multiply(BigDecimal.valueOf(item.getValue())).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP));
            currentTotalPrice=currentTotalPrice.add(productTotalPrice);
        }
        return currentTotalPrice;
    }


    public BigDecimal calculateTotalSalesTax() {
        BigDecimal totalSalesTaxRate=BigDecimal.valueOf(12.5).divide(BigDecimal.valueOf(100.00));
        BigDecimal totalSalesTax=totalSalesTaxRate.multiply(calculateTotalPrice()).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        return totalSalesTax;
    }

    public BigDecimal CalculateTotalPriceOfCartIncludingTaxes(){
        BigDecimal totalPriceOfCartIncludingTaxe = calculateTotalPrice().add(calculateTotalSalesTax()) ;
        return totalPriceOfCartIncludingTaxe;
    }


    public int getProductQuantity(final Product product){
        return cart.get(product);
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


}
