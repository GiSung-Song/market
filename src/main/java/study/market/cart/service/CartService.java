package study.market.cart.service;

import study.market.cart.dto.CartItemDto;

import java.util.List;

public interface CartService {

    Long addCartItem(CartItemDto cartItemDto, String email);
    void deleteCartItem(CartItemDto cartItemDto, String email);
    void clearCart(String email);
    List<CartItemDto> getCartItemList(String email);
    void editCount(CartItemDto cartItemDto, String email);

}
