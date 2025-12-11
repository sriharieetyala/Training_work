package com.example.springfileupload.controller;

import com.example.springfileupload.entity.Item;
import com.example.springfileupload.repository.ItemRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImportController {

    private final ObjectMapper objectMapper;  // <-- OK now
    private final ItemRepository itemRepository;

    @PostMapping("/import-json")
    public ResponseEntity<String> importJsonFile(@RequestParam("file") MultipartFile file) {
        try {
            List<Item> items = objectMapper.readValue(
                    file.getInputStream(),
                    new TypeReference<List<Item>>() {}
            );

            itemRepository.saveAll(items);

            return ResponseEntity.ok(items.size() + " items imported successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid JSON file or format!");
        }
    }
}
