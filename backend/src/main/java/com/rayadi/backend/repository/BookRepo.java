package com.rayadi.backend.repository;

import com.rayadi.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    boolean existsByTitle(String title);
}
