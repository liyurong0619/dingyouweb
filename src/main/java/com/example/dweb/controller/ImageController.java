package com.example.dweb.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uploads")
public class ImageController {

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get("uploads/" + filename);
            byte[] data = Files.readAllBytes(path);

            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
