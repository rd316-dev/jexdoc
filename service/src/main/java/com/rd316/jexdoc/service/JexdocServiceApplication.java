package com.rd316.jexdoc.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class JexdocServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JexdocServiceApplication.class, args);
    }
}
