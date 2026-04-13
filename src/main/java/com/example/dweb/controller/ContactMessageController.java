package com.example.dweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dweb.dto.ContactMessageDTO;
import com.example.dweb.model.ContactMessage;
import com.example.dweb.service.ContactMessageService;

@RestController
@RequestMapping("/contact")
@CrossOrigin
public class ContactMessageController {

    @Autowired
    private ContactMessageService service;

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody ContactMessageDTO message) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.saveMessage(message);
            response.put("success", true);
            response.put("message", "您的訊息已送出，我們將盡快與您聯繫！");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "訊息傳送失敗：" + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 取得所有留言（依日期倒序）
    @GetMapping("/all")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        List<ContactMessage> messages = service.getAllMessagesByDateDesc();
        return ResponseEntity.ok(messages);
    }
}
