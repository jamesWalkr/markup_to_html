package com.example.service;

import com.example.exceptions.StorageException;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class MarkdownHtmlConvertorService {

    public String markdownToHtml(String markdown){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        return HtmlRenderer.builder().build().render(document);
    }

    // for testing purposes only to make sure I can generate html from markdown
    // will just render the html instead of creating html file
    public void convertMarkdownFileToHtmlFile(){
        try{
            String markdownContent = Files.readString(Paths.get("/home/james/file_storage/test_3.md"));
            String outputHtml = markdownToHtml(markdownContent);
            Files.write(Paths.get("/home/james/file_storage/test_3.html"), outputHtml.getBytes());
        } catch (IOException e){
            throw  new StorageException("Failed to generate html file");
        }
    }
}
