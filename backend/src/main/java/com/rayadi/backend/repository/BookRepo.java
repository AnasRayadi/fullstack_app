package com.rayadi.backend.repository;

import com.rayadi.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    boolean existsByTitle(String title);
    List<Book> findByEditionBetween(LocalDate start, LocalDate end);
}
