package study.market.item.service;

import study.market.item.dto.*;
import study.market.item.entity.Item;

import java.util.List;

public interface ItemService {

    Long registerItem(ItemFormDto dto);
    void modifyItem(ItemFormDto dto);
    void removeItem(Long itemId);
    ItemInfoResDto getItemInfo(Long itemId);
    List<Item> getItemAllList(ItemSearchCondition condition);
}
