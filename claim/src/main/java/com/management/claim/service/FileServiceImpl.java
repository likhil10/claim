package com.management.claim.service;

import com.management.claim.exception.FileStorageException;
import com.management.claim.exception.MyFileNotFoundException;
import com.management.claim.model.Claim;
import com.management.claim.model.FileEntity;
import com.management.claim.repository.ClaimManagementRepository;
import com.management.claim.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;
    
    @Autowired
    private ClaimManagementRepository claimManagementRepository;

    @Override
    public FileEntity storeFile(MultipartFile file, Long id) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {

            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            
            Claim claim = claimManagementRepository.findById(id).orElse(null);

            FileEntity fileEntity = new FileEntity(fileName, file.getContentType(), file.getBytes(), claim);

            return fileRepository.save(fileEntity);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public List<FileEntity> getFile(Long claimId) {
    	List<FileEntity> fileList = fileRepository.findByClaimId(claimId);
        return fileList;
    }
}
