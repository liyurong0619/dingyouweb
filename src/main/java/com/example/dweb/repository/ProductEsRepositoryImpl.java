package com.example.dweb.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Component;

import com.example.dweb.model.ProductDocument;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

@Component
public class ProductEsRepositoryImpl implements ProductEsRepositoryCustom {

    private final ElasticsearchOperations esOps;

    public ProductEsRepositoryImpl(ElasticsearchOperations esOps) {
        this.esOps = esOps;
    }

    @Override
    public List<ProductDocument> searchByKeyword(String keyword) {
        String minMatch = keyword.trim().length() <= 2 ? "100%" : "80%";

        Query pinyinQuery = MultiMatchQuery.of(m -> m
            .query(keyword)
            .fields("name^3", "vendorName^2")
            .analyzer("ik_pinyin")
            .minimumShouldMatch(minMatch)
        )._toQuery();

        Query descQuery = MatchPhraseQuery.of(m -> m
            .field("description")
            .query(keyword)
            .analyzer("ik_max_word")
            .slop(0)
        )._toQuery();

        Query combined = BoolQuery.of(b -> b
            .should(pinyinQuery)
            .should(descQuery)
            .minimumShouldMatch("1")
        )._toQuery();

        NativeQuery nativeQuery = NativeQuery.builder()
            .withQuery(combined)
            .build();

        return esOps.search(nativeQuery, ProductDocument.class)
            .stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());
    }
}