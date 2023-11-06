package study.market.item.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.NoSuchFileException;
import java.util.UUID;

@Component
public class FileService {

    public String uploadFiles(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID(); //고유 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //확장자
        String savedFileName = uuid.toString() + extension; //저장된 파일 이름
        String fileUploadFullUrl = uploadPath + "/" + savedFileName; //저장된 위치/파일이름 -> 파일가져오기 위한 full 경로

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();

        return savedFileName;
    }

    public void deleteFile(String filePath) throws NoSuchFileException {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
        } else {
            throw new NoSuchFileException("파일이 없습니다.");
        }
    }

}
