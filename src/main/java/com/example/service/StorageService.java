package com.example.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    void store(MultipartFile file);

    // Stream<Path> loadAll();

    Set<String> loadAll();

    Path load(String fileName);

    Resource loadAsResource(String fileName);


    void deleteAll();
}
