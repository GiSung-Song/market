package study.market.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import study.market.cart.dto.CartItemDto;
import study.market.cart.service.CartService;
import study.market.order.dto.OrderDto;
import study.market.order.service.OrderService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/order/order-list")
    public String getCartForm(Model model, Principal principal, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<CartItemDto> cartItemList = cartService.getCartItemList(principal.getName(), pageable);

        int totalPrice = cartItemList.stream()
                .mapToInt(cartItem -> cartItem.getItemTotalPrice()).sum();

        model.addAttribute("orderList", cartItemList);
        model.addAttribute("totalPrice", totalPrice);

        return "/order/beforeOrderListForm";
    }

}
