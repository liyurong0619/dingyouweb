package com.example.dweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dweb.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    
}
