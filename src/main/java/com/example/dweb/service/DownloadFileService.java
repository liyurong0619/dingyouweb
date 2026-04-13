package com.example.dweb.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dweb.model.DownloadFile;
import com.example.dweb.repository.DownloadFileRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class DownloadFileService {

    private static final Path DOWNLOAD_DIR =
            Paths.get(System.getProperty("user.dir"), "uploads", "downloads");

    private final DownloadFileRepository repository;

    public DownloadFileService(DownloadFileRepository repository) {
        this.repository = repository;
    }

    public List<DownloadFile> listPublic() {
        return repository.findByEnabledTrueOrderByUpdateDateDesc();
    }

    public DownloadFile upload(String title, MultipartFile file) throws IOException {

        Files.createDirectories(DOWNLOAD_DIR);

        String filename = file.getOriginalFilename();
        Path target = DOWNLOAD_DIR.resolve(filename);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        DownloadFile df = new DownloadFile();
        df.setTitle(title);
        df.setFilename(filename);
        df.setFileSize(formatSize(file.getSize()));
        df.setEnabled(true);

        return repository.save(df);
    }

    /* 下載檔案（PDF 可預覽 / 其他維持下載）*/
    public void downloadFile(Long id, HttpServletResponse response) throws IOException {

        DownloadFile df = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("檔案不存在"));

        Path file = DOWNLOAD_DIR.resolve(df.getFilename());

        if (!Files.exists(file)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String filename = df.getFilename();
        String lower = filename.toLowerCase();

        boolean isPdf = lower.endsWith(".pdf");

        // PDF 才能預覽
        if (isPdf) {
            response.setContentType("application/pdf");
        } else {
            response.setContentType("application/octet-stream");
        }

        // PDF 用 inline，其它用 attachment
        String dispositionType = isPdf ? "inline" : "attachment";

        // 中文檔名不亂碼
        String encoded = URLEncoder.encode(filename, StandardCharsets.UTF_8)
                .replace("+", "%20");

        response.setHeader("Content-Disposition",
                dispositionType
                        + "; filename=\"" + filename + "\""
                        + "; filename*=UTF-8''" + encoded);

        response.setHeader("Content-Length", String.valueOf(Files.size(file)));

        Files.copy(file, response.getOutputStream());
        response.flushBuffer();
    }

    public void delete(Long id) throws IOException {

        DownloadFile df = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Files.deleteIfExists(DOWNLOAD_DIR.resolve(df.getFilename()));
        repository.delete(df);
    }

    private String formatSize(long size) {
        if (size < 1024) return size + " B";
        if (size < 1024 * 1024) return String.format("%.1f KB", size / 1024.0);
        return String.format("%.1f MB", size / 1024.0 / 1024.0);
    }
}
