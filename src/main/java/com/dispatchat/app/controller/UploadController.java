package com.dispatchat.app.controller;

import com.dispatchat.app.entity.User;
import com.dispatchat.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/uploads")
public class UploadController {

    @Value("${upload.path}") // Specify the path where files will be stored
    private String uploadPath;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/images")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam("userID") Long userID) {
        try {
            // Process and save the uploaded image to the server
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadPath, filename);
            Files.copy(file.getInputStream(), filePath);

            String imageUrl = "/uploads/images/" + filename; // URL to access the uploaded image

            // Update the user's profile picture URL in the database
            Optional<User> optionalUser = userRepository.findById(userID);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setProfilePic(imageUrl);
                userRepository.save(user);
            }

            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }
}

