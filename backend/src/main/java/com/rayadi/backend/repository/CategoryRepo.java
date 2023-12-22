package com.rayadi.backend.repository;

import com.rayadi.backend.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<BookCategory, Integer> {
}
