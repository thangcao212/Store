package com.shopping.store.service.Cart;

import com.shopping.store.entity.Cart;
import com.shopping.store.entity.CartItem;

public interface CartItemService {

    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
