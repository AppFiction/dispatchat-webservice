package com.dispatchat.app.controller;

import com.dispatchat.app.entity.User;
import com.dispatchat.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;
    
    @Value("${twilio.system.phoneNumber}")
    private String phoneNumber;

    @GetMapping("/test")
    public String test() {
        return "Hello there";
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{userID}")
    public ResponseEntity<User> updateUser(@PathVariable Long userID, @RequestBody User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(userID);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setProfilePic(updatedUser.getProfilePic());
//            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

            User savedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@RequestBody User user) {
        Twilio.init(accountSid, authToken);

        // Generate a random verification code
        String verificationCode = generateVerificationCode();

        // Check if the user already exists
        Optional<User> optionalUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        User u;
        if (optionalUser.isPresent()) {
            u = optionalUser.get();
        } else {
            // Create a new user with the phone number
            u = new User();
            u.setPhoneNumber(user.getPhoneNumber());
        }

        // Store the verification code in the user's row
        u.setVerificationCode(verificationCode);
        userRepository.save(u);

        // Send the verification code via SMS using Twilio
        Message message = Message.creator(
                        new PhoneNumber(u.getPhoneNumber()),
                        new PhoneNumber(phoneNumber),
                        "Your LogiChat verification code is: " + verificationCode)
                .create();

        return ResponseEntity.status(HttpStatus.OK).body("Verification code sent successfully.");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestBody User user) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (optionalUser.isPresent()) {
            User u = optionalUser.get();
            String storedCode = u.getVerificationCode();

            if (storedCode != null && storedCode.equals(user.getVerificationCode())) {
                // Verification successful, clear the verification code
                u.setVerificationCode(null);
                userRepository.save(u);

                // You might want to implement user authentication logic here
                return ResponseEntity.status(HttpStatus.OK).body("Verification successful.");
            }
        }

        // If the user doesn't exist, you can create a new user here
        User newUser = new User();
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setVerificationCode(null); // No code required for new user
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code.");
    }


    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(9000) + 1000);
    }

}
