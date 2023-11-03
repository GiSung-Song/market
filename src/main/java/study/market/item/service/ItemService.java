package study.market.item.service;

import study.market.item.dto.ItemInfoResDto;
import study.market.item.dto.ItemModifyReqDto;
import study.market.item.dto.ItemRegisterReqDto;
import study.market.item.dto.ItemSearchCondition;
import study.market.item.entity.Item;

import java.util.List;

public interface ItemService {

    Long registerItem(ItemRegisterReqDto dto);
    void modifyItem(ItemModifyReqDto dto);
    void removeItem(Long itemId);
    ItemInfoResDto getItemInfo(Long itemId);
    List<Item> getItemAllList(ItemSearchCondition condition);
}
