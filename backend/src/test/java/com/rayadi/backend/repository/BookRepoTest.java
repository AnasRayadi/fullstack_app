package com.rayadi.backend.repository;

import com.rayadi.backend.model.Book;
import com.rayadi.backend.model.BookCategory;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepoTest {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    private BookCategory bookCategory;

    @BeforeEach
    void setUp() {
        int id = 1;
        BookCategory category = Instancio.create(BookCategory.class);
        category.setCategoryId(id);
        bookCategory = categoryRepo.save(category);
    }

    @Nested
    class TestExistsByTitle {
        @Test
        void itShouldReturnFalseIfTitleDoesNotExist() {
            // Given
            String title = "title";
            Book book = Instancio.create(Book.class);
            book.setCategory(bookCategory);
            book.setTitle(title);
            bookRepo.save(book);

            // When
            boolean expected = bookRepo.existsByTitleIgnoreCase("another title");

            // Then
            assertFalse(expected);
        }
        @Test
        void itShouldReturnTrueIfTitleExists() {
            // Given
            Book book = Instancio.create(Book.class);
            book.setCategory(bookCategory);
            bookRepo.save(book);

            // When
            boolean expected = bookRepo.existsByTitleIgnoreCase(book.getTitle());

            // Then
            assertTrue(expected);
        }

    }

}