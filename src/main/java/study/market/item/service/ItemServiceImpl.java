package study.market.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.market.item.dto.ItemFormDto;
import study.market.item.dto.ItemSalesDto;
import study.market.item.dto.ItemSearchCondition;
import study.market.item.entity.Item;
import study.market.item.repository.ItemQueryRepository;
import study.market.item.repository.ItemRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemQueryRepository itemQueryRepository;

    @Transactional
    @Override
    public Long registerItem(ItemFormDto dto) {

        Item item = Item.builder()
                .itemType(dto.getItemType())
                .itemName(dto.getItemName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .itemStatus(dto.getItemStatus())
                .build();

        return itemRepository.save(item).getId();
    }

    @Transactional
    @Override
    public void modifyItem(ItemFormDto dto) {
        Item item = itemRepository.findByItemName(dto.getItemName());

        if (item == null) {
            throw new NoSuchElementException("해당 이름으로 등록 된 상품이 없습니다.");
        }

        item.editItem(dto.getItemType(), dto.getItemStatus(), dto.getPrice(), dto.getStock());
    }

    @Transactional
    @Override
    public void removeItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(NoSuchElementException::new);
        itemRepository.delete(item);
    }

    @Override
    public ItemFormDto getItemInfo(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(NoSuchElementException::new);

        return ItemFormDto.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .itemStatus(item.getItemStatus())
                .itemType(item.getItemType())
                .price(item.getPrice())
                .stock(item.getStock())
                .build();
    }

    @Transactional
    @Override
    public List<Item> getItemAllList(ItemSearchCondition condition) {
        return itemQueryRepository.findAll(condition);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ItemFormDto> getItemSearchPage(ItemSearchCondition condition, Pageable pageable) {

        Page<Item> items = itemQueryRepository.findSearchCondition(condition, pageable);
        Page<ItemFormDto> itemFormDtoPage = toDtoPage(items);

        return itemFormDtoPage;
    }

    @Override
    public Page<ItemSalesDto> getSalesPage(ItemSearchCondition condition, Pageable pageable) {
        Page<Item> itemPage = itemQueryRepository.findItemSales(condition, pageable);
        Page<ItemSalesDto> itemSalesDtoPage = itemPage.map(m -> ItemSalesDto.builder()
                .itemType(m.getItemType())
                .itemName(m.getItemName())
                .price(m.getPrice())
                .salesCount(m.getSalesCount())
                .sales(m.getSalesCount() * m.getPrice())
                .stock(m.getStock())
                .build());

        return itemSalesDtoPage;
    }

    public Page<ItemFormDto> toDtoPage(Page<Item> itemPage) {
        Page<ItemFormDto> itemFormDtoPage = itemPage.map(m -> ItemFormDto.builder()
                .id(m.getId())
                .itemType(m.getItemType())
                .itemStatus(m.getItemStatus())
                .itemName(m.getItemName())
                .price(m.getPrice())
                .stock(m.getStock())
                .build());

        return itemFormDtoPage;
    }
}
