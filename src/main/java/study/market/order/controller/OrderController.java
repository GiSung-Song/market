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
import study.market.order.service.OrderService;

import java.security.Principal;

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
    public String orderItem(@ModelAttribute("order") OrderDto orderDto, Principal principal) {
        Long orderId = orderService.orderItem(orderDto, principal.getName());

        return "redirect:/order/" + orderId;
    }

    @GetMapping("/order/history")
    public String getOrderHistory(@PageableDefault(size = 10) Pageable pageable, Model model, Principal principal) {
        String email = principal.getName();

        Page<OrderDto> orderHistoryList = orderService.getOrderHistoryList(email, pageable);
        model.addAttribute("orderList", orderHistoryList);

        return "/order/orderHistoryForm";
    }

    @PostMapping("/order/cancel")
    @ResponseBody
    public void cancelOrder(@RequestBody OrderDto orderDto, Principal principal) {

        String email = principal.getName();
        orderService.orderCancel(orderDto.getId(), email);
    }

    @GetMapping("/order/{id}")
    public String getOrderDetail(@PathVariable(name = "id") Long orderId, Principal principal, Model model) {

        String email = principal.getName();

        OrderDto orderDto = orderService.getOrderDetail(orderId, email);

        //주문자와 주문번호의 주문자가 다를 경우 홈으로
        if (orderDto == null) {
            return "/main";
        }

        model.addAttribute("order", orderDto);

        return "/order/orderHistoryDetailForm";
    }

}
