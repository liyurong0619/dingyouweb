package com.example.dweb.repository;

import java.util.List;

import com.example.dweb.model.ProductDocument;

public interface ProductEsRepositoryCustom {
    List<ProductDocument> searchByKeyword(String keyword);
}