package com.example.frontoffice_planning.controller;

import com.example.frontoffice_planning.controller.exception.StorageFileNotFoundException;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.repository.UserRepository;
import com.example.frontoffice_planning.service.File.FileSystemStorageService;
import com.example.frontoffice_planning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class ImageController {

    @Autowired
    FileSystemStorageService fileSystemStorageService;

    @Autowired
    UserService userService;

    @PostMapping("/images/upload")
    @ResponseBody
    public ResponseEntity<Resource> handleFileUpload(@RequestAttribute("user") Users users, @RequestParam("file") MultipartFile file) throws IOException {
        fileSystemStorageService.store(file);
        Resource fileReturn = fileSystemStorageService.loadAsResource(file.getOriginalFilename());
        userService.savePhoto(users, fileReturn.getFilename());
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getName() + "\"").body(fileReturn);
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = fileSystemStorageService.loadAsResource(filename);
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
