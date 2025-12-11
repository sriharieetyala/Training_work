package com.example.springfileupload.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FilesStorageService {

    private final Path root = Paths.get("uploads");

    public FilesStorageService() {
        try {
            Files.createDirectories(root);  // create folder if not exists
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder!");
        }
    }

    public void save(MultipartFile file) {
        try {
            Files.copy(
                    file.getInputStream(),
                    this.root.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
