package com.example.dweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dweb.model.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    List<Products> findByVendorName(String vendorName);

    long countByVendorName(String vendorName);

    void deleteByVendorName(String vendorName);
}

