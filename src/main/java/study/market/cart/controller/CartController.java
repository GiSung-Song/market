package study.market.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import study.market.cart.dto.CartItemDto;
import study.market.cart.service.CartService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    @ResponseBody
    public void addCartItem(@RequestBody CartItemDto cartItemDto, Principal principal) {

        String email = principal.getName();
        cartService.addCartItem(cartItemDto, email);
    }

    @PostMapping("/cart/delete")
    @ResponseBody
    public void deleteCartItem(@RequestBody CartItemDto cartItemDto, Principal principal) {

        String email = principal.getName();
        cartService.deleteCartItem(cartItemDto, email);
    }

    @GetMapping("/cart/cart_list")
    public String getCartForm(Model model, Principal principal,@PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<CartItemDto> cartItemList = cartService.getCartItemList(principal.getName(), pageable);
        model.addAttribute("cartItem", cartItemList);

        return null;
    }

}
