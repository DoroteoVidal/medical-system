package com.dorovidal.medical_system.repository;

import com.dorovidal.medical_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailIgnoreCase(String email);

    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.isActive = true")
    Optional<User> findByIdAndIsActive(Long userId);
}
