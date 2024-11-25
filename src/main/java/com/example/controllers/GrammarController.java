package com.example.controllers;

// import com.example.models.Text;
import com.example.service.GrammarCheckerService;
import com.example.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class GrammarController {

    private final Logger logger = LoggerFactory.getLogger(GrammarController.class);
    private final GrammarCheckerService checkerService;
    private  final StorageService storageService;

    public GrammarController(GrammarCheckerService checkerService, StorageService storageService) {
        this.checkerService = checkerService;
        this.storageService = storageService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        /* logic for uploading file
        1. save the file
        2. read the file
        3. grammar check the file
        4.  write corrected file */

        storageService.store(file);
        Path uploadedFile = storageService.load(file.getOriginalFilename());
        logger.debug("absolute path: {} ", uploadedFile );
        List<String> fileLines = checkerService.readFile(uploadedFile);
        String correctedLines = checkerService.checkText(fileLines);
        checkerService.writeFile(correctedLines);
        return ResponseEntity.status(HttpStatus.OK).body("file has been corrected");
    }

    @GetMapping("/uploaded-files")
    public ResponseEntity<Set<String>> listUploadedFiles(){
        return ResponseEntity.status(HttpStatus.OK).body(storageService.loadAll());

    }

//    @PostMapping("/grammar-check")
//    public String checkGrammar(@RequestBody Text text) throws IOException {
//        logger.debug(text.getText());
//        return null ;  //checkerService.checkText(text.getText());
//
//    }
}
