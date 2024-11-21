package com.example.controllers;

import com.example.models.Text;
import com.example.service.GrammarCheckerService;
import com.example.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

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
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        /* logic for uploading file
        1. read the file
        2. grammar check the file
        3. save the file */
        storageService.store(file);
        Path uploadedFile = storageService.load(file.getOriginalFilename());
        logger.debug("absolute path: {} ", uploadedFile );
        checkerService.readFile(uploadedFile);
        return ResponseEntity.status(HttpStatus.OK).body(file.getOriginalFilename() + " has been uploaded successfully." );
    }

    @PostMapping("/grammar-check")
    public ResponseEntity<String> checkGrammar(@RequestBody String text) throws IOException {
        logger.debug(text);
        checkerService.checkText(text);
        return ResponseEntity.status(HttpStatus.OK).body("you request was successful. please see logs");
    }
}
