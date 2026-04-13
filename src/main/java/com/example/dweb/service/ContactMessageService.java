package com.example.dweb.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dweb.dto.ContactMessageDTO;
import com.example.dweb.model.ContactMessage;
import com.example.dweb.repository.ContactMessageRepository;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository repository;

    // 用於發送到外部網站的 HTTP 工具
    private final RestTemplate restTemplate = new RestTemplate();

    public void saveMessage(ContactMessageDTO dto) {
        // 建立並儲存本地資料
        ContactMessage msg = new ContactMessage();
        msg.setName(dto.getName());
        msg.setGender(dto.getGender());
        msg.setPhone(dto.getPhone());
        msg.setAddress(dto.getAddress());
        msg.setEmail(dto.getEmail());
        msg.setContent(dto.getContent());
        msg.setDate(new Date());
        repository.save(msg);

        
        try {
            // 這裡請換成接收端網站的實際 API URL因此用到了下面的json
            String receiverUrl = "https://example-bsite.com/api/receiveContact";

            // 直接傳送 JSON
            restTemplate.postForObject(receiverUrl, dto, String.class);

            System.out.println("已成功傳送聯絡資料至接收端網站");
        } catch (Exception e) {
            System.err.println("傳送到接收端網站失敗：" + e.getMessage());
        }
    }

    // 回傳倒序排列（最新的在最上面）
    public List<ContactMessage> getAllMessagesByDateDesc() {
        return repository.findAllByOrderByDateDesc();
    }
}
