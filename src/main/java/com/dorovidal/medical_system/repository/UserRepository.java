package com.dorovidal.medical_system.repository;

import com.dorovidal.medical_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailIgnoreCaseAndEnabledTrue(String email);

    Optional<User> findUserByEmailIgnoreCase(String email);

    Optional<User> findByIdAndEnabledTrue(Long userId);

    List<User> findAllByEnabledTrue();
}
