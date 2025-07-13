package com.career_recommendation_platform.career_recommendation_platform.controller;

import com.career_recommendation_platform.career_recommendation_platform.model.User;
import com.career_recommendation_platform.career_recommendation_platform.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
