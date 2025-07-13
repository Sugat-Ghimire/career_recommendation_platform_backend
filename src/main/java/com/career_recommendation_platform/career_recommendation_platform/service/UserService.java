package com.career_recommendation_platform.career_recommendation_platform.service;

import com.career_recommendation_platform.career_recommendation_platform.model.User;
import com.career_recommendation_platform.career_recommendation_platform.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createOrGetUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .orElseGet(() -> userRepository.save(user));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Transactional
    public void uploadResume(String email, byte[] resumeData) {
        User user = getUserByEmail(email);
        user.setResume(resumeData);
        userRepository.save(user);
    }

    public byte[] downloadResume(String email) {
        User user = getUserByEmail(email);
        if (user.getResume() == null) {
            throw new RuntimeException("Resume not uploaded yet");
        }
        return user.getResume();
    }

}

