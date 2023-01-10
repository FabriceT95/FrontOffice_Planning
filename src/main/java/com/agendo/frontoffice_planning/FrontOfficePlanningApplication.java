package com.agendo.frontoffice_planning;

import com.agendo.frontoffice_planning.service.File.StorageProperties;
import com.agendo.frontoffice_planning.service.File.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class FrontOfficePlanningApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontOfficePlanningApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

}
