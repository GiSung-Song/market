package study.market.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import study.market.item.dto.ItemFormDto;
import study.market.item.service.ItemService;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/register")
    public String registerItemForm(@ModelAttribute("item") ItemFormDto dto) {

        return "/item/itemForm";
    }

}
