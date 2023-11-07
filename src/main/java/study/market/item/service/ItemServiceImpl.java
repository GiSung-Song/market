package study.market.item.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import study.market.item.dto.ItemFormDto;
import study.market.item.dto.ItemImgDto;
import study.market.item.dto.ItemSearchCondition;
import study.market.item.entity.Item;
import study.market.item.entity.ItemImg;
import study.market.item.repository.ItemImgRepository;
import study.market.item.repository.ItemQueryRepository;
import study.market.item.repository.ItemRepository;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemQueryRepository itemQueryRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    @Transactional
    @Override
    public Long registerItem(ItemFormDto dto, List<MultipartFile> itemImgFileList) throws Exception {

        Item item = Item.builder()
                .itemType(dto.getItemType())
                .itemName(dto.getItemName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .itemStatus(dto.getItemStatus())
                .build();

        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return itemRepository.save(item).getId();
    }

    @Transactional
    @Override
    public void modifyItem(ItemFormDto dto, List<MultipartFile> fileList) throws Exception {
        Item item = itemRepository.findById(dto.getId())
                .orElseThrow(EntityNotFoundException::new);

        item.editItem(dto.getItemStatus(), item.getPrice(), item.getStock());

        List<Long> imgIds = dto.getItemImgIds();

        for (int i = 0; i < fileList.size(); i++) {
            itemImgService.updateItemImg(imgIds.get(i), fileList.get(i));
        }

        itemRepository.save(item);
    }

    @Transactional
    @Override
    public void removeItem(Long itemId) throws NoSuchFileException {
        Item item = itemRepository.findById(itemId).orElseThrow(NoSuchElementException::new);
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        for (ItemImg itemImg : itemImgList) {
            itemImgService.deleteItemImg(itemImg.getId());
            itemImgRepository.delete(itemImg);
        }

        itemRepository.delete(item);
    }

    @Override
    public ItemFormDto getItemInfo(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(NoSuchElementException::new);
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = new ItemImgDto();

            itemImgDto.setId(itemImg.getId());
            itemImgDto.setImgName(itemImg.getImgName());
            itemImgDto.setImgUrl(itemImg.getImgUrl());
            itemImgDto.setOriImgName(itemImg.getOriImgName());

            itemImgDtoList.add(itemImgDto);
        }

        ItemFormDto itemFormDto = ItemFormDto.builder()
                .itemName(item.getItemName())
                .itemStatus(item.getItemStatus())
                .itemType(item.getItemType())
                .price(item.getPrice())
                .stock(item.getStock())
                .build();

        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }

    @Override
    public List<Item> getItemAllList(ItemSearchCondition condition) {
        return itemQueryRepository.findAll(condition);
    }


}
