package com.management.claim.controller;

import com.management.claim.model.FileEntity;
import com.management.claim.payload.UploadFileResponse;
import com.management.claim.service.FileService;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FileController {
//    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;
    private static final Logger logger = LogManager.getLogger(FileController.class);

    @PostMapping("/uploadFile/{id}")
    public UploadFileResponse uploadFile(@PathVariable(value = "id") Long id, @RequestParam("file") MultipartFile file) {
        logger.debug("uploadFile - Entry");
        logger.info("Trying to save file...");
        FileEntity fileEntity = fileService.storeFile(file, id);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileEntity.getFileId())
                .toUriString();

        return new UploadFileResponse(fileEntity.getName(), fileDownloadUri,
                file.getContentType());
    }

    @GetMapping("/getFile/{claimId}")
    public List<UploadFileResponse> downloadFile(@PathVariable Long claimId) {

        logger.debug("downloadFile -  entry");

        logger.info("Trying to get file...");

        // Load file from database
    	List<FileEntity> fileEntity = fileService.getFile(claimId);

//    	String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileEntity.get(0).getFileId()).toUriString();

        List<UploadFileResponse> uploadFileResponses = fileEntity.stream().map(file -> new UploadFileResponse(file.getName(), ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(file.getFileId()).toUriString(), file.getType())).collect(Collectors.toList());

        return uploadFileResponses;
    }
}
