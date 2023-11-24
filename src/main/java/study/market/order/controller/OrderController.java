package study.market.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.market.order.dto.OrderDto;
import study.market.order.dto.OrderItemDto;
import study.market.order.service.OrderService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order/order")
    public String getOrderForm(Model model, Principal principal) {

        OrderDto orderDto = orderService.getOrderInfo(principal.getName());
        model.addAttribute("order", orderDto);

        return "/order/beforeOrderListForm";
    }

    @PostMapping("/order/order")
    public String orderItem(OrderDto orderDto, Principal principal) {

        orderService.orderItem(orderDto, principal.getName());

        return "redirect:/";
    }

}
