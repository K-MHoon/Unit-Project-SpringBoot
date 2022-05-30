package com.example.ex05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadTestController {

    @GetMapping("/uploadEx")
    public void uploadFile(MultipartFile[] uploadFiles) {

    }
}
