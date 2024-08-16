package com.shopping.store.service.Cart;

import com.shopping.store.entity.Cart;
import com.shopping.store.entity.User;

import java.math.BigDecimal;

public interface CartService {

    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
