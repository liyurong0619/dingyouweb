package com.example.dweb.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.dweb.model.ProductDocument;

public interface ProductEsRepository
    extends ElasticsearchRepository<ProductDocument, Long>,
            ProductEsRepositoryCustom {
}