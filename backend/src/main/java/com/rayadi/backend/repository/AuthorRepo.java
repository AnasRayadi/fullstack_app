package com.rayadi.backend.repository;

import com.rayadi.backend.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
    boolean existsByEmail(String email);
}
