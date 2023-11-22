package study.market.cart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.market.cart.dto.CartItemDto;

public interface CartService {

    Long addCartItem(CartItemDto cartItemDto, String email);
    void deleteCartItem(CartItemDto cartItemDto, String email);
    void clearCart(String email);
    Page<CartItemDto> getCartItemList(String email, Pageable pageable);
    void editCount(CartItemDto cartItemDto, String email);

}
