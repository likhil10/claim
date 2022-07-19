package com.management.claim.comtroller;

import com.management.claim.model.Claim;
import com.management.claim.model.FileEntity;
import com.management.claim.payload.UploadFileResponse;
import com.management.claim.repository.ClaimManagementRepository;
import com.management.claim.service.FileService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        FileEntity fileEntity = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .body(new ByteArrayResource(fileEntity.getData()));
    }
}
