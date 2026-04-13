package com.example.dweb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dweb.dto.NewsRequestDTO;
import com.example.dweb.dto.NewsResponseDTO;
import com.example.dweb.model.News;
import com.example.dweb.repository.NewsRepository;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    // 取得所有消息
    public List<NewsResponseDTO> getAllNews() {
        return newsRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    // 發送 / 預約消息
    public NewsResponseDTO sendNews(NewsRequestDTO dto) {
        // 防呆
        if (dto == null) {
            return null;
        }
        try {
            // 處理發送時間（統一為「台灣時間」）
            LocalDateTime sendTime;

            if ("immediate".equals(dto.getNewsSendMode())) {
                // 立即發送：現在（台灣）
                sendTime = LocalDateTime.now();
            } else {
                // 預約發送：前端 datetime-local（台灣）
                if (dto.getNewsSendTime() == null || dto.getNewsSendTime().isBlank()) {
                    return null;
                }
                sendTime = LocalDateTime.parse(dto.getNewsSendTime());
            }
            // 預約時間不可早於現在
            if ("scheduled".equals(dto.getNewsSendMode())
                    && sendTime.isBefore(LocalDateTime.now())) {
                return null;
            }
            // 狀態判斷
            String status =
                    "immediate".equals(dto.getNewsSendMode())
                            ? "published"
                            : "scheduled";
            // 建立 Entity
            News news = new News(
                    null,
                    dto.getNewsTitle(),
                    dto.getNewsContent(),
                    dto.getNewsSendMode(),
                    sendTime,
                    status
            );
            // 存入資料庫
            newsRepository.save(news);
            newsRepository.flush();
            // 回傳結果
            return toDTO(news);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    public void cancelNews(Long id) {
        newsRepository.findById(id).ifPresent(news -> {
            news.setNewsStatus("cancelled");
            newsRepository.save(news);
        });
    }
  
    private NewsResponseDTO toDTO(News news) {
        NewsResponseDTO dto = new NewsResponseDTO();
        dto.setNewsId(news.getNewsId());
        dto.setNewsTitle(news.getNewsTitle());
        dto.setNewsContent(news.getNewsContent());
        dto.setNewsSendMode(news.getNewsSendMode());
        dto.setNewsSendTime(news.getNewsSendTime().toString()); 
        dto.setNewsStatus(news.getNewsStatus());
        return dto;
    }
}
