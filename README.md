# 鼎悠企業有限公司官網

針對中小型傳統產業設計的企業形象網站，以輕量化架構為核心理念，在有限資源下實現完整業務需求。

---

## 技術架構

```
前端（HTML / CSS / JavaScript + Bootstrap）
       │
       ▼
Spring Boot Controller 層（REST API）
       │
       ▼
Service 層（業務邏輯）
  ├─ ProductsService（商品／廠商管理）
  ├─ NewsService（最新消息）
  └─ ContactService（聯絡表單／郵件）
       │
       ▼
Repository 層
  ├─ Spring Data JPA → MySQL（資料儲存）
  └─ ProductEsRepository → Elasticsearch（全文搜尋）
```

| 層級 | 技術 |
|------|------|
| 前端 | HTML / CSS / JavaScript、Bootstrap 5 |
| 後端 | Spring Boot、Spring Data JPA |
| 資料庫 | MySQL |
| 搜尋引擎 | Elasticsearch + IK Analysis + Pinyin Analysis |
| 容器化 | Docker、Docker Compose |

---

## 功能模組

| 模組 | 說明 |
|------|------|
| 商品型錄 | 廠商分類、商品列表、商品詳細頁、圖片顯示 |
| 全文搜尋 | Elasticsearch 拼音搜尋、同音字查詢、動態匹配嚴格度 |
| 檔案管理 | 商品圖片上傳、PDF 型錄上傳與下載 |
| 最新消息 | 公告發布、時間排序、彈窗顯示 |
| 聯絡表單 | 表單送出、Email 通知 |
| 後台管理 | 商品／廠商／公告 CRUD 管理介面 |

---

## Elasticsearch 拼音搜尋

### 為什麼需要 Elasticsearch

傳統資料庫的 `LIKE` 查詢無法處理同音字。使用者輸入「公雞」無法找到商品「工機」。導入 Elasticsearch 搭配 IK Analysis 中文分詞插件與 Pinyin Analysis 拼音插件解決此問題，由於注音符號有太多侷限，因而選擇羅馬拼音。

### 索引設計

商品建立時同步寫入 Elasticsearch，Mapping 設定 `ik_pinyin` 自訂 Analyzer，將中文字於索引階段轉換為拼音 Token：

```
工機 → [gong, ji, gongji]
公雞 → [gong, ji, gongji]
```

兩者 Token 相符，搜尋即可互通。

### 搜尋策略

**商品名稱／廠商名稱（拼音搜尋）：**

```java
MultiMatchQuery.of(m -> m
    .query(keyword)
    .fields("name^3", "vendorName^2")
    .analyzer("ik_pinyin")
    .minimumShouldMatch(minMatch)
)
```

- `name^3`、`vendorName^2`：名稱比廠商更優先
- 動態匹配嚴格度：1-2 字要求 100%，3 字以上要求 80%

**商品描述（精確分詞搜尋）：**

```java
MatchPhraseQuery.of(m -> m
    .field("description")
    .query(keyword)
    .analyzer("ik_max_word")
    .slop(0)
)
```

描述欄位使用純中文分詞，不走拼音，避免同音字誤觸，需完整連續詞才會命中。

### Analyzer 設定（es-settings.json）

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

### Analyzer 設定（es-settings.json）

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

## 資料同步設計

商品資料以 MySQL 為主要來源，寫入、更新、刪除時同步操作 Elasticsearch：

```
新增商品 → productsRepo.save()  → esRepo.save()
刪除商品 → productsRepo.delete() → esRepo.deleteById()
上傳 PDF → productsRepo.save()  → esRepo.save()
```

另提供 `POST /api/products/sync-es` 手動觸發全量同步。

---

## 遇到的問題與解法

**前端搜尋未走 Elasticsearch**

初版前端搜尋直接對 JS 快取資料做 `includes()` 字串比對，完全繞過後端，導致拼音搜尋無效。

解法：前端改為呼叫後端 `/api/products/search?q=` API，由 Elasticsearch 處理查詢邏輯。

**Spring Data 方法無法使用自訂 Analyzer**

`findByNameContaining()` 等 Spring Data 衍生方法不支援指定 Analyzer，無法走拼音分析。

解法：改用 `NativeQuery` 搭配自訂 Repository 實作，手動指定 `analyzer("ik_pinyin")`。

**Elasticsearch 插件環境不一致**

IK Analysis 與 Pinyin 插件需手動安裝，本機與部署環境版本容易不一致，導致 Analyzer 行為差異。

解法：改用 Docker Compose 容器化 Elasticsearch，以 Volume 掛載插件目錄，確保開發與部署環境一致。

---

## 已知問題與改進方向

| 問題 | 說明 | 改進方向 |
|------|------|---------|
| MySQL 與 ES 同步非原子性 | 寫入 MySQL 成功但 ES 失敗時資料不一致 | 導入 MQ 或 CDC 機制 |
| 搜尋無分頁 | 結果全部回傳 | 加入 ES 分頁查詢 |
| 圖片儲存於本地 | 無法水平擴展 | 改為物件儲存（如 S3） |

---

## 聯絡方式

- Email：yurongrong0619@email.com
- 電話：0937589600