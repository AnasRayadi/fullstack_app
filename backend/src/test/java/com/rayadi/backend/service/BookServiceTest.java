package com.rayadi.backend.service;

import com.rayadi.backend.dto.BookDto;
import com.rayadi.backend.exception.ResourceNotFoundException;
import com.rayadi.backend.model.Book;
import com.rayadi.backend.model.BookCategory;
import com.rayadi.backend.repository.BookRepo;
import com.rayadi.backend.repository.CategoryRepo;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.rayadi.backend.constants.ErrorMessagesConstant.BOOK_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepo bookRepo;
    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private BookService bookService;

    private final Long id = 1L;
    private Book book;

    @BeforeEach
    void setUp() {
        Book book = Instancio.of(Book.class).create();
//        book = new Book();
        book.setId(id);
    }

    @Nested
    class TestDeleteBook {
        @Test
        void itShouldThrowBookNotFound(){
            // Given
            //Long id = 1L;

            // When
            // Then
            assertThatThrownBy(()-> bookService.deleteBook(id))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(BOOK_NOT_FOUND.formatted(id));
        }
        @Test
        void itShouldDeleteBook(){
            // Given
//            Long id = 1L;
//            Book book = Instancio.of(Book.class).create();
//            book.setId(id); // Ensure the ID matches the one being passed
            when(bookRepo.findById(id)).thenReturn(Optional.of(book));

            // When
            bookService.deleteBook(id);

            // Then
            verify(bookRepo).findById(id);
            verify(bookRepo).delete(book); // Verify the correct method is called
        }



    }
    @Nested
    class TestGetBookById {
        @Test
        void itShouldThrowBookNotFound(){
            // Given
            Long id = 1L;

            // When
            // Then
            assertThatThrownBy(()-> bookService.getBookById(id))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(BOOK_NOT_FOUND.formatted(id));
        }
        @Test
        void itShouldGetBookById(){
            // Given
            Long id = 1L;
            Book book = Instancio.of(Book.class).create();
            book.setId(id); // Ensure the ID matches the one being passed
            when(bookRepo.findById(id)).thenReturn(Optional.of(book));

            // When
            BookDto result = bookService.getBookById(id);

            // Then
            verify(bookRepo).findById(id);
            assertThat(result).isEqualTo(book);
        }
    }


    @Nested
    class TestCreateBook {
        @Test
        void itShouldCreateBook() {
            // Given
            BookDto bookDto = Instancio.of(BookDto.class).create();
            bookDto.setCategoryId(1); // Make sure the category ID is set to 1

            BookCategory category = Instancio.of(BookCategory.class).create();
            when(categoryRepo.findById(bookDto.getCategoryId())).thenReturn(Optional.of(category));
            when(bookRepo.existsByTitleIgnoreCase(bookDto.getTitle())).thenReturn(false);

            // The book object that should be created based on bookDto
            Book book = new Book();
            book.setTitle(bookDto.getTitle());
            book.setCategory(category);
            // Add other properties as needed

            // When
            bookService.addBook(bookDto);

            // Then
            verify(bookRepo).existsByTitleIgnoreCase(bookDto.getTitle());
            verify(categoryRepo).findById(bookDto.getCategoryId());
            verify(bookRepo).save(any(Book.class)); // Verify that a book is saved, regardless of its actual state
        }
    }


}


