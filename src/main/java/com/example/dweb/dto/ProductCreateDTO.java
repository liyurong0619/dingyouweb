package com.example.dweb.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public class ProductCreateDTO {

    // 不允許null
    private String vendorName;
    private String name;

    // ===== 其餘全部允許 null =====
    private BigDecimal price;
    private Integer stock;
    private String description;
    private String brand;
    private String spec;
    private String code;

    private MultipartFile image;
    private MultipartFile pdf;


    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getSpec() { return spec; }
    public void setSpec(String spec) { this.spec = spec; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public MultipartFile getImage() { return image; }
    public void setImage(MultipartFile image) { this.image = image; }

    public MultipartFile getPdf() { return pdf; }
    public void setPdf(MultipartFile pdf) { this.pdf = pdf; }
}
