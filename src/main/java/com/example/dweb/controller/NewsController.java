package com.example.dweb.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dweb.dto.NewsRequestDTO;
import com.example.dweb.dto.NewsResponseDTO;
import com.example.dweb.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/list")
    public List<NewsResponseDTO> getAllNews() {
        return newsService.getAllNews();
    }

   @PostMapping("/send")
    public String sendNews(@RequestBody NewsRequestDTO dto) {
        NewsResponseDTO result = newsService.sendNews(dto);
        if (result == null) {
            return "傳送失敗";       
        }
        return "傳送成功";          
    }


    @DeleteMapping("/delete/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }

    @PostMapping("/cancel/{id}")
    public void cancelNews(@PathVariable Long id) {
        newsService.cancelNews(id);
    }
}
