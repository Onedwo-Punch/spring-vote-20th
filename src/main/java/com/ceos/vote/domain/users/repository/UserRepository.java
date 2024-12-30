package com.ceos.vote.domain.users.repository;

import com.ceos.vote.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    static Optional<User> findByUsername(String username) {
        return null;
    }

    String username(String username);
    boolean existsByUsername(String username);
}