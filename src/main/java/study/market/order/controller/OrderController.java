package study.market.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

        log.info("orderDto : {}", orderDto.getZipCode());
        log.info("orderDto : {}", orderDto.getMessage());
        log.info("orderDto : {}", orderDto.getAddress());
        log.info("orderDto : {}", orderDto.getDetailAddress());
        log.info("orderDto : {}", orderDto.getTotalPrice());

        orderService.orderItem(orderDto, principal.getName());

        return "redirect:/";
    }

}
