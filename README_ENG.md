# Ding You Enterprise Co., Ltd. — Official Website

A corporate branding website designed for small and medium-sized traditional industries, built around a lightweight architecture philosophy to fulfill complete business requirements within limited resources.

---

## Technical Architecture

```
Frontend (HTML / CSS / JavaScript + Bootstrap)
       │
       ▼
Spring Boot Controller Layer (REST API)
       │
       ▼
Service Layer (Business Logic)
  ├─ ProductsService (Product / Vendor Management)
  ├─ NewsService (Latest News)
  └─ ContactService (Contact Form / Email)
       │
       ▼
Repository Layer
  ├─ Spring Data JPA → MySQL (Data Storage)
  └─ ProductEsRepository → Elasticsearch (Full-Text Search)
```

| Layer | Technology |
|-------|------------|
| Frontend | HTML / CSS / JavaScript, Bootstrap 5 |
| Backend | Spring Boot, Spring Data JPA |
| Database | MySQL |
| Search Engine | Elasticsearch + IK Analysis + Pinyin Analysis |
| Containerization | Docker, Docker Compose |

---

## Feature Modules

| Module | Description |
|--------|-------------|
| Product Catalog | Vendor categories, product listings, product detail pages, image display |
| Full-Text Search | Elasticsearch pinyin search, homophone queries, dynamic match strictness |
| File Management | Product image upload, PDF catalog upload and download |
| Latest News | Announcement publishing, chronological ordering, modal display |
| Contact Form | Form submission, email notification |
| Admin Panel | CRUD management interface for products / vendors / announcements |

---

## Elasticsearch Pinyin Search

### Why Elasticsearch Is Needed

Traditional database `LIKE` queries cannot handle homophones. If a user types "公雞" (gōng jī, rooster), they cannot find the product "工機" (gōng jī, machine tool). Elasticsearch was introduced alongside the IK Analysis Chinese tokenizer plugin and the Pinyin Analysis plugin to solve this problem. Hanyu pinyin was chosen over Zhuyin (bopomofo) due to the latter's many limitations.

### Index Design

When a product is created, it is simultaneously written to Elasticsearch. The mapping configures a custom `ik_pinyin` Analyzer that converts Chinese characters into pinyin tokens at index time:

```
工機 → [gong, ji, gongji]
公雞 → [gong, ji, gongji]
```

Since both share the same tokens, search results are interchangeable.

### Search Strategy

**Product Name / Vendor Name (Pinyin Search):**

```java
MultiMatchQuery.of(m -> m
    .query(keyword)
    .fields("name^3", "vendorName^2")
    .analyzer("ik_pinyin")
    .minimumShouldMatch(minMatch)
)
```

- `name^3`, `vendorName^2`: product name is prioritized over vendor name
- Dynamic match strictness: 1–2 characters require 100% match; 3+ characters require 80%

**Product Description (Exact Tokenized Search):**

```java
MatchPhraseQuery.of(m -> m
    .field("description")
    .query(keyword)
    .analyzer("ik_max_word")
    .slop(0)
)
```

The description field uses pure Chinese tokenization without pinyin to avoid false positives from homophones; only complete, consecutive phrases will match.

### Analyzer Configuration (es-settings.json)

```json
{
  "analysis": {
    "filter": {
      "pinyin_filter": {
        "type": "pinyin",
        "keep_first_letter": true,
        "keep_separate_first_letter": false,
        "keep_full_pinyin": true,
        "keep_joined_full_pinyin": true,
        "keep_original": true,
        "limit_first_letter_length": 16,
        "lowercase": true
      }
    },
    "analyzer": {
      "ik_pinyin": {
        "tokenizer": "ik_max_word",
        "filter": [
          "lowercase",
          "pinyin_filter"
        ]
      }
    }
  }
}
```

### Mapping Configuration (es-settings.json)

```json
{
  "properties": {
    "name": {
      "type": "text",
      "analyzer": "ik_pinyin",
      "search_analyzer": "ik_max_word"
    },

    "vendorName": {
      "type": "text",
      "analyzer": "ik_pinyin",
      "search_analyzer": "ik_max_word"
    },

    "description": {
      "type": "text",
      "analyzer": "ik_pinyin",
      "search_analyzer": "ik_max_word"
    },

    "spec": {
      "type": "text",
      "analyzer": "ik_pinyin",
      "search_analyzer": "ik_max_word"
    },

    "code": {
      "type": "keyword"
    },

    "imageFilename": {
      "type": "keyword"
    }
  }
}
```

---

## Data Synchronization Design

MySQL serves as the primary data source. Write, update, and delete operations are mirrored to Elasticsearch simultaneously:

```
Add product    → productsRepo.save()   → esRepo.save()
Delete product → productsRepo.delete() → esRepo.deleteById()
Upload PDF     → productsRepo.save()   → esRepo.save()
```

A `POST /api/products/sync-es` endpoint is also available to manually trigger a full re-sync.

---

## Issues Encountered & Solutions

**Frontend search bypassed Elasticsearch**

The initial frontend search performed `includes()` string matching directly against JS-cached data, completely bypassing the backend, which made pinyin search non-functional.

Solution: Refactored the frontend to call the backend `/api/products/search?q=` API, delegating all query logic to Elasticsearch.

**Spring Data derived methods do not support custom Analyzers**

Methods like `findByNameContaining()` do not support specifying an Analyzer, making pinyin analysis unavailable through them.

Solution: Replaced with `NativeQuery` and a custom Repository implementation, manually specifying `analyzer("ik_pinyin")`.

**Inconsistent Elasticsearch plugin environments**

IK Analysis and Pinyin plugins require manual installation; version mismatches between local and deployment environments easily cause inconsistent Analyzer behavior.

Solution: Containerized Elasticsearch using Docker Compose with a volume-mounted plugin directory, ensuring the development and production environments are identical.

---

## Known Issues & Improvement Roadmap

| Issue | Description | Improvement Direction |
|-------|-------------|----------------------|
| MySQL–ES sync is non-atomic | Data inconsistency when MySQL write succeeds but ES write fails | Introduce MQ or CDC mechanism |
| No pagination in search | All results are returned at once | Add ES paginated queries |
| Images stored locally | Cannot scale horizontally | Migrate to object storage (e.g., S3) |

---

## Contact

- Email: yurongrong0619@email.com
- Phone: 0937589600
