package com.example.dweb.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dweb.dto.ProductCreateDTO;
import com.example.dweb.dto.ProductListDTO;
import com.example.dweb.dto.VendorCreateDTO;
import com.example.dweb.model.Products;
import com.example.dweb.model.Vendor;
import com.example.dweb.repository.ProductsRepository;
import com.example.dweb.repository.VendorRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ProductsService {

    private final ProductsRepository productsRepo;
    private final VendorRepository vendorRepo;

    // uploads 
    private static final Path ROOT =
            Paths.get(System.getProperty("user.dir"), "uploads");

   
    private static final Path IMAGE_DIR = ROOT;

    
    private static final Path PDF_DIR = ROOT.resolve("products-pdf");

    public ProductsService(ProductsRepository productsRepo,
                           VendorRepository vendorRepo) {
        this.productsRepo = productsRepo;
        this.vendorRepo = vendorRepo;
    }

    
    private ProductListDTO toDTO(Products p) {
        ProductListDTO dto = new ProductListDTO();
        dto.setId(p.getId());
        dto.setVendorName(p.getVendorName());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());
        dto.setStock(p.getStock());
        dto.setDescription(p.getDescription());
        dto.setBrand(p.getBrand());
        dto.setSpec(p.getSpec());
        dto.setCode(p.getCode());
        dto.setImageFilename(p.getImageFilename());
        dto.setHasPdf(p.getPdfFilename() != null);
        return dto;
    }

    public List<ProductListDTO> getAllProducts() {
        return productsRepo.findAll().stream().map(this::toDTO).toList();
    }

    public List<ProductListDTO> getProductsByVendor(String vendorName) {
        return productsRepo.findByVendorName(vendorName)
                .stream().map(this::toDTO).toList();
    }

    /*  新增商品 印出  */
    public void addProduct(ProductCreateDTO dto) throws IOException {
        Products p = new Products();

        p.setVendorName(dto.getVendorName());
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());
        p.setDescription(dto.getDescription());
        p.setBrand(dto.getBrand());
        p.setSpec(dto.getSpec());
        p.setCode(dto.getCode());

        // 確保資料夾存在
        Files.createDirectories(IMAGE_DIR);
        Files.createDirectories(PDF_DIR);

        /*圖片 */
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String name = UUID.randomUUID() + "_" + dto.getImage().getOriginalFilename();
            Files.copy(dto.getImage().getInputStream(),
                    IMAGE_DIR.resolve(name),
                    StandardCopyOption.REPLACE_EXISTING);
            p.setImageFilename(name);
        }

        /*PDF  */
        if (dto.getPdf() != null && !dto.getPdf().isEmpty()) {
            String name = UUID.randomUUID() + "_" + dto.getPdf().getOriginalFilename();
            Files.copy(dto.getPdf().getInputStream(),
                    PDF_DIR.resolve(name),
                    StandardCopyOption.REPLACE_EXISTING);
            p.setPdfFilename(name);
        }

        productsRepo.save(p);
    }

   
    public void deleteProduct(Long id) throws IOException {
        Products p = productsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        // 刪圖片
        if (p.getImageFilename() != null)
            Files.deleteIfExists(IMAGE_DIR.resolve(p.getImageFilename()));

        // 刪 PDF
        if (p.getPdfFilename() != null)
            Files.deleteIfExists(PDF_DIR.resolve(p.getPdfFilename()));

        productsRepo.delete(p);
    }

    /* PDF 上傳 */
    public void uploadProductPdf(Long id, MultipartFile file) throws IOException {

        if (file == null || file.isEmpty())
            throw new RuntimeException("未選擇檔案");

        if (!"application/pdf".equals(file.getContentType()))
            throw new RuntimeException("只允許 PDF");

        Products p = productsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        Files.createDirectories(PDF_DIR);

        String name = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(),
                PDF_DIR.resolve(name),
                StandardCopyOption.REPLACE_EXISTING);

        // 刪舊 PDF
        if (p.getPdfFilename() != null)
            Files.deleteIfExists(PDF_DIR.resolve(p.getPdfFilename()));

        p.setPdfFilename(name);
        productsRepo.save(p);
    }

    /*  PDF 下載（前台 / 後台都可預覽） */
    public void downloadProductFile(Long id, HttpServletResponse response) throws IOException {

        Products p = productsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        // 資料庫沒有 PDF
        if (p.getPdfFilename() == null || p.getPdfFilename().isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "PDF_NOT_FOUND"
            );
            return;
        }

        Path file = PDF_DIR.resolve(p.getPdfFilename());

        // 檔案實體不存在
        if (!Files.exists(file)) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "PDF_FILE_MISSING"
            );
            return;
        }

        // 更穩：用 probeContentType 判斷
        String contentType = Files.probeContentType(file);
        if (contentType == null) {
            contentType = "application/pdf";
        }
        response.setContentType(contentType);

        // 防止瀏覽器 sniff 造成錯誤行為
        response.setHeader("X-Content-Type-Options", "nosniff");

        // inline 預覽
        String dispositionType = "inline";

        // 注意：這裡用的是「儲存檔名」(UUID_原檔名)
        String filename = p.getPdfFilename();

        // 中文檔名避免亂碼
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

    /*  廠商  */
    public List<VendorCreateDTO> getAllVendors() {
        return vendorRepo.findAll().stream().map(v -> {
            VendorCreateDTO dto = new VendorCreateDTO();
            dto.setId(v.getId());
            dto.setName(v.getName());
            dto.setLogoFilename(v.getLogoFilename());
            return dto;
        }).toList();
    }

    public void addVendor(VendorCreateDTO dto) throws IOException {
        Vendor v = new Vendor();
        v.setName(dto.getName());

        Files.createDirectories(IMAGE_DIR);

        if (dto.getLogo() != null && !dto.getLogo().isEmpty()) {
            String name = UUID.randomUUID() + "_" + dto.getLogo().getOriginalFilename();
            Files.copy(dto.getLogo().getInputStream(),
                    IMAGE_DIR.resolve(name),
                    StandardCopyOption.REPLACE_EXISTING);
            v.setLogoFilename(name);
        }

        vendorRepo.save(v);
    }

    public void updateVendor(Long id, VendorCreateDTO dto) throws IOException {
        Vendor v = vendorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("廠商不存在"));

        v.setName(dto.getName());

        Files.createDirectories(IMAGE_DIR);

        if (dto.getLogo() != null && !dto.getLogo().isEmpty()) {
            String name = UUID.randomUUID() + "_" + dto.getLogo().getOriginalFilename();
            Files.copy(dto.getLogo().getInputStream(),
                    IMAGE_DIR.resolve(name),
                    StandardCopyOption.REPLACE_EXISTING);

            // 刪舊 logo
            if (v.getLogoFilename() != null)
                Files.deleteIfExists(IMAGE_DIR.resolve(v.getLogoFilename()));

            v.setLogoFilename(name);
        }

        vendorRepo.save(v);
    }

    public void deleteVendor(Long id, boolean force) throws IOException {
        Vendor v = vendorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("廠商不存在"));

        long count = productsRepo.countByVendorName(v.getName());

        if (count > 0 && !force)
            throw new RuntimeException("此廠商仍有商品");

        // 刪掉該廠商所有商品（確保 PDF/圖片也刪）
        productsRepo.findByVendorName(v.getName())
                .forEach(p -> {
                    try { deleteProduct(p.getId()); }
                    catch (IOException ignored) {}
                });

        // 刪 logo
        if (v.getLogoFilename() != null)
            Files.deleteIfExists(IMAGE_DIR.resolve(v.getLogoFilename()));

        vendorRepo.delete(v);
    }
}
