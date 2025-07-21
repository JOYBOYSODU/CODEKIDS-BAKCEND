package com.codekids.codekids.services.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codekids.codekids.services.model.User;

/**
 * This interface handles all database operations for the User entity.
 * Spring Data JPA automatically provides methods like save(), findById(), findAll(), etc.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // We define a custom method to find a user by their email.
    // Spring Data JPA implements it for us based on the method name.
    Optional<User> findByEmail(String email);
}
