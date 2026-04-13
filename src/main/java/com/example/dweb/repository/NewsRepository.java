package com.example.dweb.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dweb.model.News;

public interface NewsRepository extends JpaRepository<News, Long> {

    // 找出：已預約、且發送時間 <= 現在 的消息
    List<News> findByNewsStatusAndNewsSendTimeBefore(
        String newsStatus,
        LocalDateTime time
    );
}
