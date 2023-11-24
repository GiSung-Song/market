package study.market.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.market.cart.dto.CartItemDto;
import study.market.cart.service.CartService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    @ResponseBody
    public void addCartItem(@RequestBody CartItemDto cartItemDto, Principal principal) {

        String email = principal.getName();

        cartService.addCartItem(cartItemDto, email);
    }

    @PostMapping("/cart/edit-count")
    @ResponseBody
    public void editCartItemCount(@RequestBody CartItemDto cartItemDto, Principal principal) {

        String email = principal.getName();

        cartService.editCount(cartItemDto, email);
    }

    @PostMapping("/cart/delete")
    @ResponseBody
    public void deleteCartItem(@RequestBody CartItemDto cartItemDto, Principal principal) {

        String email = principal.getName();
        cartService.deleteCartItem(cartItemDto, email);
    }

    @GetMapping("/cart/cart-list")
    public String getCartForm(Model model, Principal principal,@PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<CartItemDto> cartItemList = cartService.getCartItemList(principal.getName(), pageable);

        int totalPrice = cartItemList.stream()
                        .mapToInt(cartItem -> cartItem.getItemTotalPrice()).sum();

        model.addAttribute("cartList", cartItemList);
        model.addAttribute("totalPrice", totalPrice);

        return "/cart/cartListForm";
    }

    @PostMapping("/cart/delete-all")
    @ResponseBody
    public void deleteAllCartItem(Principal principal) {
        String email = principal.getName();
        cartService.clearCart(email);
    }

}
