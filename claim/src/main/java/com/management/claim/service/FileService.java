package com.management.claim.service;

import com.management.claim.model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileEntity storeFile(MultipartFile file);
    FileEntity getFile(String fileId);
}
