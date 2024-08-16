package com.shopping.store.service.Cart;

import com.shopping.store.entity.Cart;
import com.shopping.store.entity.CartItem;
import com.shopping.store.entity.User;
import com.shopping.store.exception.ResourceNotFoundException;
import com.shopping.store.repo.CartItemRepository;
import com.shopping.store.repo.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final AtomicLong cartIDGenerator = new AtomicLong(0);


    @Override
    public Cart getCart(Long id) {
        Cart cart=cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount=cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {

        Cart cart=getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.clearCart();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {

        Cart cart=getCart(id);
        return cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }
    @Override
    public Cart initializeNewCart(User user) {

        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(()-> {
                    Cart cart=new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });

    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
