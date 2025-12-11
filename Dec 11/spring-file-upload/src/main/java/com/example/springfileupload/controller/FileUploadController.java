package com.example.springfileupload.controller;


import com.example.springfileupload.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private FilesStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            storageService.save(file);
            return ResponseEntity.ok("Uploaded the file: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("File upload failed!");
        }
    }
}
