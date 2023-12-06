package study.market.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.market.item.dto.ItemFormDto;
import study.market.item.dto.ItemSalesDto;
import study.market.item.dto.ItemSearchCondition;
import study.market.item.entity.Item;
import study.market.item.service.ItemService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/admin/sales-list")
    public String itemSalesList(@RequestParam(value = "itemType", required = false, defaultValue = "ALL") String itemType,
                                @RequestParam(value = "itemName", required = false, defaultValue = ""   ) String itemName,
                                @RequestParam(value = "sortGbn" , required = false, defaultValue = "0"  ) String sortGbn,
                                @PageableDefault(size = 10) Pageable pageable, Model model) {

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setItemName(itemName);
        condition.setItemType(itemType);
        condition.setSortGbn(sortGbn);

        Page<ItemSalesDto> salesPage = itemService.getSalesPage(condition, pageable);
        long sumSales = salesPage.stream().mapToLong(item -> item.getSales()).sum();

        model.addAttribute("salesList", salesPage);
        model.addAttribute("sumSales", sumSales);

        return "/item/salesListForm";
    }

    @GetMapping("/item/list")
    public String getItemListForm(
            @PageableDefault(size = 10, sort = "itemId", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "itemType", required = false, defaultValue = "ALL") String itemType,
            @RequestParam(value = "itemName", required = false, defaultValue = "") String itemName,
            Model model) {

        log.info("itemType : {}", itemType);
        log.info("itemName : {}", itemName);

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setItemType(itemType);
        condition.setItemName(itemName);

        Page<ItemFormDto> itemSearchPage = itemService.getItemSearchPage(condition, pageable);
        model.addAttribute("itemList", itemSearchPage);

        return "/item/itemListForm";
    }

}
