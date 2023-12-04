package study.market.delievery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.market.delievery.dto.DeliveryListDto;
import study.market.delievery.dto.DeliveryDto;
import study.market.delievery.service.DeliveryService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/delivery/wait-list")
    public String getWaitingDeliveryListForm(@PageableDefault(size = 10)Pageable pageable,
                                             @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
                                             Model model) {

        Page<DeliveryListDto> waitingOrderList = deliveryService.getWaitingOrderList(searchKeyword, pageable);
        model.addAttribute("orderList", waitingOrderList);

        return "/delivery/waitList";
    }

    @PostMapping("/delivery/start")
    @ResponseBody
    public void startDelivery(@RequestBody DeliveryDto startDto, Principal principal) {

        String email = principal.getName();
        deliveryService.startDelivery(startDto.getOrderId(), email);
    }

    @PostMapping("/delivery/cancel")
    @ResponseBody
    public void cancelDelivery(@RequestBody DeliveryDto startDto, Principal principal) {

        String email = principal.getName();
        deliveryService.cancelDelivery(startDto.getOrderId(), email);
    }

}
