package com.example.dweb.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "download_file")
public class DownloadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String filename;

    private String fileSize;

    private LocalDate updateDate;

    private Boolean enabled = true;

    @PrePersist
    @PreUpdate
    public void onSave() {
        this.updateDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFilename() {
        return filename;
    }

    public String getFileSize() {
        return fileSize;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
