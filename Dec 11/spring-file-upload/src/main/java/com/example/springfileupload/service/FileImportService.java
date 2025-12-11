package com.example.springfileupload.service;

import com.example.springfileupload.entity.Item;
import com.example.springfileupload.repository.ItemRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileImportService {

    private final ObjectMapper objectMapper;   // Auto-configured by Spring
    private final ItemRepository itemRepository;

    public String importJson(MultipartFile file) {
        try {
            List<Item> items = objectMapper.readValue(
                    file.getInputStream(),
                    new TypeReference<List<Item>>() {}
            );

            itemRepository.saveAll(items);

            return items.size() + " items imported successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid JSON file or format!";
        }
    }
}
