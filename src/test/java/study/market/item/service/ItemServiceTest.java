package study.market.item.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.market.item.ItemStatus;
import study.market.item.ItemType;
import study.market.item.dto.*;
import study.market.item.entity.Item;
import study.market.item.repository.ItemRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void save_item() {
        ItemFormDto dto = new ItemFormDto();
        dto.setItemName("음료수");
        dto.setItemType(ItemType.DRINK);
        dto.setItemStatus(ItemStatus.SELL);
        dto.setPrice(2000);
        dto.setStock(200);

        Long saveId = itemService.registerItem(dto);

        Item findItem = itemRepository.findById(saveId).orElseThrow(NoSuchElementException::new);

        assertThat(saveId).isEqualTo(findItem.getId());
    }

    @Test
    void remove_item() {
        ItemFormDto dto = new ItemFormDto();
        dto.setItemName("음료수");
        dto.setItemType(ItemType.DRINK);
        dto.setItemStatus(ItemStatus.SELL);
        dto.setPrice(2000);
        dto.setStock(200);

        Long saveId = itemService.registerItem(dto);

        assertThrows(NoSuchElementException.class, () -> {
            itemService.removeItem(saveId);
            itemService.removeItem(saveId);
        });

    }

    @Test
    void modify_item() {
        ItemFormDto dto = new ItemFormDto();
        dto.setItemName("음료수");
        dto.setItemType(ItemType.DRINK);
        dto.setItemStatus(ItemStatus.SELL);
        dto.setPrice(2000);
        dto.setStock(200);

        Long saveId = itemService.registerItem(dto);

        ItemFormDto modifyReqDto = new ItemFormDto();
        modifyReqDto.setItemName("음료수");
        modifyReqDto.setItemStatus(ItemStatus.RECEIVING);

        itemService.modifyItem(modifyReqDto);
        Item modifyItem = itemRepository.findById(saveId).orElseThrow(NoSuchElementException::new);

        assertThat(modifyItem.getItemStatus()).isEqualTo(ItemStatus.RECEIVING);
    }

    @Test
    void getList_item() {
        Item drink = Item.builder()
                .itemType(ItemType.DRINK)
                .itemStatus(ItemStatus.SELL)
                .itemName("음료수")
                .stock(100)
                .price(1000)
                .build();

        Item snack = Item.builder()
                .itemType(ItemType.SNACK)
                .itemStatus(ItemStatus.SELL)
                .itemName("과자")
                .stock(200)
                .price(2000)
                .build();

        Item goods = Item.builder()
                .itemType(ItemType.GOODS)
                .itemStatus(ItemStatus.SELL)
                .itemName("상품")
                .stock(300)
                .price(3000)
                .build();

        itemRepository.save(drink);
        itemRepository.save(snack);
        itemRepository.save(goods);

        ItemSearchCondition condition = new ItemSearchCondition();

        List<Item> itemAllList1 = itemService.getItemAllList(condition);
        assertThat(itemAllList1.size()).isEqualTo(3);

        condition.setItemName("음");

        List<Item> itemAllList2 = itemService.getItemAllList(condition);
        assertThat(itemAllList2.size()).isEqualTo(1);
    }

    @Test
    void info_item() {
        Item goods = Item.builder()
                .itemType(ItemType.GOODS)
                .itemStatus(ItemStatus.SELL)
                .itemName("상품")
                .stock(300)
                .price(3000)
                .build();

        Item savedItem = itemRepository.save(goods);

        ItemInfoResDto itemInfo = itemService.getItemInfo(savedItem.getId());

        assertThat(itemInfo.getItemName()).isEqualTo(goods.getItemName());
    }

}