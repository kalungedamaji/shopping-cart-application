package com.technogise.interns.shoppingcart.cart.service;

import com.technogise.interns.shoppingcart.dto.CartItem;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class CartService {
        public List<CartItem> getAllCartItems() {
                return new ArrayList<>();
        }

}
