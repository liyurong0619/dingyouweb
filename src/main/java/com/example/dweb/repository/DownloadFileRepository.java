package com.example.dweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dweb.model.DownloadFile;

public interface DownloadFileRepository extends JpaRepository<DownloadFile, Long> {

    List<DownloadFile> findByEnabledTrueOrderByUpdateDateDesc();
}
