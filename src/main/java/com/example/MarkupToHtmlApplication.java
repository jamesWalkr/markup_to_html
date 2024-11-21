package com.example;

import com.example.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MarkupToHtmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarkupToHtmlApplication.class, args);
	}

}
