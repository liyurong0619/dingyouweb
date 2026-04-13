package com.example.dweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.dweb.interceptor.AdminInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;

    // 1️⃣ 設定靜態檔案路徑（作法 A）
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // uploads 大門口（圖片、子資料夾全部通）
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(
                    "file:" + System.getProperty("user.dir") + "/uploads/"
                );

        // 🔁 PDF（維持不動）
        registry.addResourceHandler("/uploadfiles/**")
                .addResourceLocations(
                    "file:" + System.getProperty("user.dir") + "/uploadfiles/"
                );
    }

    // 2️⃣ 註冊後台攔截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login", "/admin/logout");
    }
}
