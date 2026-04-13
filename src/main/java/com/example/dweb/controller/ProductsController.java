package com.example.dweb.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dweb.dto.ProductCreateDTO;
import com.example.dweb.dto.ProductListDTO;
import com.example.dweb.dto.VendorCreateDTO;
import com.example.dweb.service.ProductsService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ProductsController {

    private final ProductsService service;

    public ProductsController(ProductsService service) {
        this.service = service;
    }


    @GetMapping("/vendors")
    public List<VendorCreateDTO> getVendors() {
        return service.getAllVendors();
    }

    @PostMapping("/vendors/add")
    public ResponseEntity<Map<String, Object>> addVendor(
            @ModelAttribute VendorCreateDTO dto) {

        Map<String, Object> res = new HashMap<>();
        try {
            service.addVendor(dto);
            res.put("success", true);
            res.put("message", "廠商新增成功");
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return ResponseEntity.ok(res);
    }


    @PostMapping("/vendors/update/{id}")
    public ResponseEntity<Map<String, Object>> updateVendor(
            @PathVariable Long id,
            @ModelAttribute VendorCreateDTO dto) {

        Map<String, Object> res = new HashMap<>();
        try {
            service.updateVendor(id, dto);
            res.put("success", true);
            res.put("message", "廠商更新成功");
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/vendors/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteVendor(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean force) {

        Map<String, Object> res = new HashMap<>();
        try {
            service.deleteVendor(id, force);
            res.put("success", true);
            res.put("message", "廠商已刪除");
        } catch (RuntimeException e) {
            res.put("success", false);
            res.put("needConfirm", true);
            res.put("message", e.getMessage());
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", "刪除失敗：" + e.getMessage());
        }
        return ResponseEntity.ok(res);
    }

    /* 商品  */

    @GetMapping("/products")
    public List<ProductListDTO> listProducts(
            @RequestParam(required = false) String vendor) {

        if (vendor != null && !vendor.isBlank()) {
            return service.getProductsByVendor(vendor);
        }
        return service.getAllProducts();
    }

    @PostMapping("/products/add")
    public ResponseEntity<Map<String, Object>> addProduct(
            @ModelAttribute ProductCreateDTO dto) {

        Map<String, Object> res = new HashMap<>();
        try {
            service.addProduct(dto);
            res.put("success", true);
            res.put("message", "新增商品成功");
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", "新增商品失敗：" + e.getMessage());
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("/products/{id}/download")
        public void downloadProduct(
                @PathVariable Long id,
                HttpServletResponse response) throws IOException {

            service.downloadProductFile(id, response);
        }
@PostMapping("/products/{id}/upload-pdf")
public ResponseEntity<Map<String, Object>> uploadProductPdf(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file) {

    Map<String, Object> res = new HashMap<>();
    try {
        service.uploadProductPdf(id, file);
        res.put("success", true);
        res.put("message", "PDF 上傳成功");
    } catch (Exception e) {
        res.put("success", false);
        res.put("message", e.getMessage());
    }
    return ResponseEntity.ok(res);
}


    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(
            @PathVariable Long id) {

        Map<String, Object> res = new HashMap<>();
        try {
            service.deleteProduct(id);
            res.put("success", true);
            res.put("message", "刪除商品成功");
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", "刪除商品失敗：" + e.getMessage());
        }
        return ResponseEntity.ok(res);
    }
}
