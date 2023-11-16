package study.market.cart.service;

import study.market.cart.dto.CartItemDto;

public interface CartService {

    Long addCart(CartItemDto cartItemDto, String email);
    void deleteCartItem(CartItemDto cartItemDto, String email);
    void clearCart(String email);

}
