package com.tastemate.domain.board;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UploadFileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }
    public String saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return "";
        }

        // 유저가 업로드한 파일 명
        String originalFileName = multipartFile.getOriginalFilename();

        String storeFileName = createStoreProfileName(originalFileName);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return storeFileName;

    }

    private String createStoreProfileName(String originalFileName) {
        // 확장자 명 추출
        String ext = extracted(originalFileName);
        String uuid = UUID.randomUUID().toString();
        //DB에 저장할 파일 명
        return uuid + "." + ext;

    }

    private String extracted(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        // 확장자 명
        return originalFileName.substring(pos+1);
    }

}
