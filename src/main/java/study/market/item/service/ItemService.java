package study.market.item.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.market.item.dto.*;
import study.market.item.entity.Item;

import java.util.List;

public interface ItemService {

    Long registerItem(ItemFormDto dto);
    void modifyItem(ItemFormDto dto);
    void removeItem(Long itemId);
    ItemFormDto getItemInfo(Long itemId);
    List<Item> getItemAllList(ItemSearchCondition condition);
    Page<ItemFormDto> getItemAllPage(Pageable pageable);
    Page<ItemFormDto> getItemSearchPage(ItemSearchCondition condition, Pageable pageable);
}
