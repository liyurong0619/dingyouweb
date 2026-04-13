package com.example.dweb.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dweb.model.DownloadFile;
import com.example.dweb.service.DownloadFileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class DownloadsController {

    private final DownloadFileService service;

    public DownloadsController(DownloadFileService service) {
        this.service = service;
    }

    @GetMapping("/downloads")
    public List<DownloadFile> list() {
        return service.listPublic();
    }

    @GetMapping("/downloads/{id}/download")
    public void download(
            @PathVariable Long id,
            HttpServletResponse response
    ) throws IOException {

        service.downloadFile(id, response);
    }

    @PostMapping("/admin/downloads/upload")
    public ResponseEntity<Map<String, Object>> upload(
            @RequestParam String title,
            @RequestParam("file") MultipartFile file
    ) {

        Map<String, Object> res = new HashMap<>();
        try {
            service.upload(title, file);
            res.put("success", true);
            res.put("message", "檔案上傳成功");
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/admin/downloads/{id}")
    public ResponseEntity<Map<String, Object>> delete(
            @PathVariable Long id
    ) {

        Map<String, Object> res = new HashMap<>();
        try {
            service.delete(id);
            res.put("success", true);
            res.put("message", "檔案已刪除");
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return ResponseEntity.ok(res);
    }
}
