package com.tekarch.user_managementMS.repositories;

import com.tekarch.user_managementMS.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to find User by username
    Optional<User> findByUsername(String username);

    // Custom query to find User by email
    Optional<User> findByEmail(String email);

    // Check if a username already exists
    boolean existsByUsername(String username);

    // Check if an email already exists
    boolean existsByEmail(String email);

}
