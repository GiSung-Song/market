package study.market.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.market.item.dto.ItemFormDto;
import study.market.item.service.ItemService;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/register")
    public String registerItemForm(@ModelAttribute("item") ItemFormDto itemFormDto) {
        return "/item/itemForm";
    }

    @PostMapping("/admin/item/register")
    public String registerItem(@ModelAttribute("item") ItemFormDto itemFormDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/item/itemForm";
        }

        return "item/itemForm";
    }

}
