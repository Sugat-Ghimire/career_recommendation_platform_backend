package com.career_recommendation_platform.career_recommendation_platform.service;


import com.career_recommendation_platform.career_recommendation_platform.model.User;
import com.career_recommendation_platform.career_recommendation_platform.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User createOrGetUser(User user) {
        return repo.findByEmail(user.getEmail())
                .orElseGet(() -> repo.save(user));
    }
}
