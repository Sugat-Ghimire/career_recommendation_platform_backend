package com.career_recommendation_platform.career_recommendation_platform.repository;

import com.career_recommendation_platform.career_recommendation_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

