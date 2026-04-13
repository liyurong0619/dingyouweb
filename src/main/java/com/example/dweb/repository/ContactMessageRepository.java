package com.example.dweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dweb.model.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    // 用日期倒序排列
    List<ContactMessage> findAllByOrderByDateDesc();
}
