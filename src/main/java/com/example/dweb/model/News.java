package com.example.dweb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(nullable = false)
    private String newsTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String newsContent;

    @Column(nullable = false)
    private String newsSendMode;  

    private LocalDateTime newsSendTime;

    @Column(nullable = false)
    private String newsStatus;    

    public News() {}

    public News(Long newsId, String newsTitle, String newsContent, String newsSendMode, LocalDateTime newsSendTime, String newsStatus) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsContent = newsContent;
        this.newsSendMode = newsSendMode;
        this.newsSendTime = newsSendTime;
        this.newsStatus = newsStatus;
    }

    // Getters & Setters
    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsSendMode() {
        return newsSendMode;
    }

    public void setNewsSendMode(String newsSendMode) {
        this.newsSendMode = newsSendMode;
    }

    public LocalDateTime getNewsSendTime() {
        return newsSendTime;
    }

    public void setNewsSendTime(LocalDateTime newsSendTime) {
        this.newsSendTime = newsSendTime;
    }

    public String getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(String newsStatus) {
        this.newsStatus = newsStatus;
    }
}
