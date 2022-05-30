package com.example.ex05.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Slf4j
public class UploadController {

    private String uploadPath = "C:\\upload";

    @PostMapping("/uploadAjax")
    public void uploadFile(MultipartFile[] uploadFiles) {
        for (MultipartFile uploadFile : uploadFiles) {

            // contentType image만 가능하게 설정
            if(uploadFile.getContentType().startsWith("image") == false) {
                log.warn("this file is not image type");
                return;
            }

            // IE나 Edge는 전체 경로가 들어온다.
            String originalFilename = uploadFile.getOriginalFilename();
            log.info("originalName = {}", originalFilename);

            String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
            log.info("fileName = {}", fileName);

            // 년/월/일로 디렉터리 생성
            String folderPath = makeFolder();

            String uuid = UUID.randomUUID().toString();

            // 파일 저장 전체 경로
            // ex) C:\\upload\\yyyy\\MM\\dd\\UUID_fileName
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);

        if(uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
