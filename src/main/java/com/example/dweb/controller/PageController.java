package com.example.dweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // 前台首頁
    @GetMapping("/")
    public String home() {
        return "Dingyouweb";
    }

    // 前台收到訊息頁面
    @GetMapping("/recieve")
    public String recieve() {
        return "recieve";
    }

    
    @GetMapping("/contact-information")
    public String contactInformation() {
        return "information";
    }

    // 前台公告頁（改路徑）
    @GetMapping("/public-news")
    public String news() {
        return "news";
    }

    // 前台商品頁（改路徑）
    @GetMapping("/products")
    public String backs() {
        return "backs";
    }


    @GetMapping("/downloads")
    public String uploadinfo() {
        return "uploadinfo";
    }
}

