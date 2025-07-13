package com.career_recommendation_platform.career_recommendation_platform.controller;

import java.io.IOException;

import com.career_recommendation_platform.career_recommendation_platform.model.User;
import com.career_recommendation_platform.career_recommendation_platform.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import java.util.Map;       // <-- 🎯 IMPORT
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.createOrGetUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return service.getUserByEmail(email);
    }

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        return service.getUserByEmail(email);
    }

    @PostMapping("/resume")
    public ResponseEntity<String> uploadResume(@AuthenticationPrincipal OAuth2User oauthUser,
                                               @RequestParam("file") MultipartFile file) throws IOException {
        try {
            String email = oauthUser != null ? oauthUser.getAttribute("email") : "null";
            System.out.println("Uploading resume for email: " + email);
            System.out.println("File size: " + file.getSize());
            System.out.println("File type: " + file.getContentType());

            if (!file.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest().body("Only PDF files are allowed.");
            }

            service.uploadResume(email, file.getBytes());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Resume uploaded successfully.");
            return ResponseEntity.ok(response.toString());
        } catch (Exception e) {
            e.printStackTrace(); // ← This will show exact error in console
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/resume")
    public ResponseEntity<byte[]> downloadResume(@AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        byte[] pdf = service.downloadResume(email);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"resume.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
