package com.example.dweb.scheduler;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.dweb.model.News;
import com.example.dweb.repository.NewsRepository;

import jakarta.annotation.PostConstruct;

@Component
public class NewsScheduler {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private DataSource dataSource;

    /* =============================
       JVM 啟動時鎖定台灣時區
       + 印出關鍵環境資訊
    ============================= */
    @PostConstruct
    public void init() throws Exception {

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));

        System.out.println("====================================");
        System.out.println("JVM TimeZone = " + TimeZone.getDefault());
        System.out.println("LocalDateTime.now = " + LocalDateTime.now());
        System.out.println(" ZonedDateTime.now = " + ZonedDateTime.now());

        // 確認實際連線的 DB（防止連錯資料庫）
        System.out.println("DB URL = " +
            dataSource.getConnection().getMetaData().getURL());
        System.out.println("====================================");
    }

    /*  每 1 分鐘檢查預約消息*/
    @Scheduled(fixedRate = 60000)
    public void publishScheduledNews() {

        LocalDateTime now = LocalDateTime.now();

        System.out.println(" Scheduler running...");
        System.out.println("JVM now = " + now);

        List<News> list =
            newsRepository.findByNewsStatusAndNewsSendTimeBefore(
                "scheduled",
                now
            );

        System.out.println(" 查到筆數 = " + list.size());

        for (News news : list) {
            System.out.println(
                "➡ 命中資料 id=" + news.getNewsId()
                + ", status=" + news.getNewsStatus()
                + ", sendTime=" + news.getNewsSendTime()
            );

            news.setNewsStatus("published");
            newsRepository.save(news);

            System.out.println("已自動發送：" + news.getNewsTitle());
        }
    }
}
