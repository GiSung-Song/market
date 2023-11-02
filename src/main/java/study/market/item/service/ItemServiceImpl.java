package study.market.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.market.item.dto.ItemInfoResDto;
import study.market.item.dto.ItemModifyReqDto;
import study.market.item.dto.ItemRegisterReqDto;
import study.market.item.entity.Item;
import study.market.item.repository.ItemRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public Long registerItem(ItemRegisterReqDto dto) {

        Item item = Item.builder()
                .itemType(dto.getItemType())
                .itemName(dto.getItemName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .itemStatus(dto.getItemStatus())
                .build();

        return itemRepository.save(item).getId();
    }

    @Override
    public void modifyItem(ItemModifyReqDto dto) {
        Item item = itemRepository.findByItemName(dto.getItemName());

        if (item == null) {
            throw new NoSuchElementException("해당 이름으로 등록 된 상품이 없습니다.");
        }

        item.editItem(dto.getItemStatus(), item.getPrice(), item.getStock());

        itemRepository.save(item);
    }

    @Override
    public void removeItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(NoSuchElementException::new);
        itemRepository.delete(item);
    }

    @Override
    public ItemInfoResDto getItemInfo(Long itemId) {
        return null;
    }

    @Override
    public List<Item> getItemAllList() {
        return null;
    }
}
