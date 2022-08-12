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
import org.apache.logging.log4j.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;
    
    @Autowired
    private ClaimManagementRepository claimManagementRepository;

    Logger logger = LogManager.getLogger(FileServiceImpl.class.getName());

    @Override
    public FileEntity storeFile(MultipartFile file, Long id) {

        logger.debug("storeFile(Service Impl) - Entry");

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {

            logger.info("storeFile - trying to store File");

            if(fileName.contains("..")) {
                logger.error("storeFile - File name invalid");
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            logger.info("storeFile - trying to find claim by ID");
            Claim claim = claimManagementRepository.findById(id).orElse(null);

            FileEntity fileEntity = new FileEntity(fileName, file.getContentType(), file.getBytes(), claim);

            logger.info("storeFile(Service Impl) - trying to save claims");
            return fileRepository.save(fileEntity);
        } catch (IOException ex) {
            logger.error("storeFile(Service Impl) - error storing file");
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public List<FileEntity> getFile(Long claimId) {
        logger.debug("getFile(Service Impl) - Entry");
        try{
            logger.info("getFile(Service Impl) - trying to find Claim");
            return fileRepository.findByClaimId(claimId);
        } catch (Exception e){
            logger.error("getFile(Service Impl) - Error finding file with the given claim id");
            throw new MyFileNotFoundException("File not found with Claim ID" + claimId, e);
        }
    }
}
