package study.market.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    @ResponseBody
    public boolean addCartItem(@RequestBody CartItemDto cartItemDto, Principal principal) {

        String email = principal.getName();

        log.info("email : {}", email);

        if (email == null) {
            return false;
        }

        cartService.addCartItem(cartItemDto, email);

        return true;
    }

    @PostMapping("/cart/delete")
    @ResponseBody
    public void deleteCartItem(@RequestBody CartItemDto cartItemDto, Principal principal) {

        String email = principal.getName();
        cartService.deleteCartItem(cartItemDto, email);
    }

    @GetMapping("/cart/cartList")
    public String getCartForm(Model model, Principal principal,@PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<CartItemDto> cartItemList = cartService.getCartItemList(principal.getName(), pageable);

        int totalPrice = cartItemList.stream()
                        .mapToInt(cartItem -> cartItem.getItemTotalPrice()).sum();

        model.addAttribute("cartList", cartItemList);
        model.addAttribute("totalPrice", totalPrice);

        return "/cart/cartListForm";
    }

}
