package study.market.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import study.market.item.entity.ItemImg;
import study.market.item.repository.ItemImgRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${file.dir}")
    private String itemImgLocation;

    private final FileService fileService;
    private final ItemImgRepository itemImgRepository;

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
}
