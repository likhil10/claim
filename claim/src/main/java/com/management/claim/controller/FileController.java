package com.management.claim.controller;

import com.management.claim.model.FileEntity;
import com.management.claim.payload.UploadFileResponse;
import com.management.claim.service.FileService;

import java.util.List;

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
    
    
//    @PostMapping("/uploadFile/{id}")
//    public ResponseEntity<Resource> createComment(@PathVariable(value = "id") Long id,
//        @RequestBody FileEntity fileEntity) {
//      Comment comment = tutorialRepository.findById(tutorialId).map(tutorial -> {
//        commentRequest.setTutorial(tutorial);
//        return commentRepository.save(commentRequest);
//      }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
//      return new ResponseEntity<>(comment, HttpStatus.CREATED);
//    }
    
    @PostMapping("/uploadFile/{id}")
    public UploadFileResponse uploadFile(@PathVariable(value = "id") Long id, @RequestParam("file") MultipartFile file) {
    	
        FileEntity fileEntity = fileService.storeFile(file, id);
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileEntity.getFileId())
                .toUriString();

        return new UploadFileResponse(fileEntity.getName(), fileDownloadUri,
                file.getContentType());
    }

    @GetMapping("/getFile/{claimId}")
    public UploadFileResponse downloadFile(@PathVariable Long claimId) {
        // Load file from database
    	List<FileEntity> fileEntity = fileService.getFile(claimId);
        
    	String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileEntity.get(0).getFileId())
                .toUriString();
    	
        return new UploadFileResponse(fileEntity.get(0).getName(), fileDownloadUri,
        		fileEntity.get(0).getType());
    }
}
