package com.example.dweb.dto;

//後端用

public class NewsResponseDTO {
    private Long newsId;
    private String newsTitle;
    private String newsContent;
    private String newsSendMode;
    private String newsSendTime;
    private String newsStatus;

    public Long getNewsId() { return newsId; }
    public void setNewsId(Long newsId) { this.newsId = newsId; }

    public String getNewsTitle() { return newsTitle; }
    public void setNewsTitle(String newsTitle) { this.newsTitle = newsTitle; }

    public String getNewsContent() { return newsContent; }
    public void setNewsContent(String newsContent) { this.newsContent = newsContent; }

    public String getNewsSendMode() { return newsSendMode; }
    public void setNewsSendMode(String newsSendMode) { this.newsSendMode = newsSendMode; }

    public String getNewsSendTime() { return newsSendTime; }
    public void setNewsSendTime(String newsSendTime) { this.newsSendTime = newsSendTime; }

    public String getNewsStatus() { return newsStatus; }
    public void setNewsStatus(String newsStatus) { this.newsStatus = newsStatus; }
}
