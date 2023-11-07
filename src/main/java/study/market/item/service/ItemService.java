package study.market.item.service;

import org.springframework.web.multipart.MultipartFile;
import study.market.item.dto.ItemFormDto;
import study.market.item.dto.ItemSearchCondition;
import study.market.item.entity.Item;

import java.nio.file.NoSuchFileException;
import java.util.List;

public interface ItemService {

    Long registerItem(ItemFormDto dto, List<MultipartFile> itemImgList) throws Exception;
    void modifyItem(ItemFormDto dto, List<MultipartFile> itemImgList) throws Exception;
    void removeItem(Long itemId) throws NoSuchFileException;
    ItemFormDto getItemInfo(Long itemId);
    List<Item> getItemAllList(ItemSearchCondition condition);
}
