package study.market.item.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import study.market.item.entity.ItemImg;
import study.market.item.repository.ItemImgRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    private final FileService fileService;
    private final ItemImgRepository itemImgRepository;

    @Value("${file.dir}")
    private String itemImgLocation;

    //상품 이미지 저장
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {

        String oriImgName = itemImgFile.getOriginalFilename(); //원본 파일명
        String imgName = "";
        String imgUrl = "";

        if (StringUtils.hasText(oriImgName)) {
            imgName = fileService.uploadFiles(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgUrl = "/images/item" + imgName;
        }

        itemImg.uploadItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 삭제
            if (StringUtils.hasText(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = file.getOriginalFilename();

            String imgName = fileService.uploadFiles(itemImgLocation, oriImgName, file.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.uploadItemImg(oriImgName, imgName, imgUrl);
        }
    }

    public void deleteItemImg(Long itemImgId) throws NoSuchFileException {

        ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                .orElseThrow(EntityNotFoundException::new);

        //기존 이미지 삭제
        if (StringUtils.hasText(savedItemImg.getImgName())) {
            fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
        }

    }
}
