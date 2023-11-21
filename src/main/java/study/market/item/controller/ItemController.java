package study.market.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import study.market.item.dto.ItemFormDto;
import study.market.item.dto.ItemSearchCondition;
import study.market.item.service.ItemService;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/register")
    public String registerItemForm(@ModelAttribute("item") ItemFormDto itemFormDto) {
        return "/item/registerItemForm";
    }

    @PostMapping("/admin/item/register")
    public String registerItem(@Valid @ModelAttribute("item") ItemFormDto itemFormDto,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/item/registerItemForm";
        }

        Long registerItemId = itemService.registerItem(itemFormDto);
        return "redirect:/item/" + registerItemId;
    }

    @GetMapping("/item/{id}")
    public String infoItemForm(@PathVariable("id") Long itemId, Model model) {

        ItemFormDto itemInfo = itemService.getItemInfo(itemId);
        model.addAttribute("item", itemInfo);

        return "/item/infoItemForm";
    }

    @GetMapping("/admin/item/{id}/edit")
    public String modifyItemForm(@PathVariable("id") Long itemId, Model model) {

        ItemFormDto itemInfo = itemService.getItemInfo(itemId);
        model.addAttribute("item", itemInfo);

        return "/item/modifyItemForm";
    }

    @PostMapping("/admin/item/{id}/edit")
    public String modifyItemForm(@PathVariable("id") Long itemId,
                                 @Valid @ModelAttribute("item") ItemFormDto itemFormDto,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/item/modifyItemForm";
        }

        log.info("price : {}, stock : {}", itemFormDto.getPrice(), itemFormDto.getStock());

        itemService.modifyItem(itemFormDto);

        return "redirect:/item/" + itemId;
    }

    @GetMapping("/item/list")
    public String getItemListForm(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                  Model model, @ModelAttribute("condition") ItemSearchCondition condition) {

        Page<ItemFormDto> itemList = itemService.getItemAllPage(pageable);

        model.addAttribute("itemList", itemList);

        return "/item/itemListForm";
    }
}
