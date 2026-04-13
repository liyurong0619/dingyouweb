package com.example.dweb.dto;

//前端用
public class NewsRequestDTO {
    private String newsTitle;
    private String newsContent;
    private String newsSendMode; 
    private String newsSendTime; 

    public String getNewsTitle() { return newsTitle; }
    public void setNewsTitle(String newsTitle) { this.newsTitle = newsTitle; }

    public String getNewsContent() { return newsContent; }
    public void setNewsContent(String newsContent) { this.newsContent = newsContent; }

    public String getNewsSendMode() { return newsSendMode; }
    public void setNewsSendMode(String newsSendMode) { this.newsSendMode = newsSendMode; }

    public String getNewsSendTime() { return newsSendTime; }
    public void setNewsSendTime(String newsSendTime) { this.newsSendTime = newsSendTime; }
}
