package com.rayadi.backend.service;

import com.rayadi.backend.converter.BookConverter;
import com.rayadi.backend.dto.BookDto;
import com.rayadi.backend.exception.DuplicateResourceException;
import com.rayadi.backend.exception.RequestValidationException;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static com.rayadi.backend.constants.ErrorMessagesConstant.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepo bookRepo;
    @Mock
    private BookConverter bookConverter;
    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private BookService bookService;

    private BookCategory category;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = Instancio.of(BookCategory.class).create();
    }

    @Nested
    class TestDeleteBook {
        @Test
        void itShouldThrowBookNotFound(){
            // Given
            Long id = 1L;

            // When
            // Then
            assertThatThrownBy(()-> bookService.deleteBook(id))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(BOOK_NOT_FOUND.formatted(id));
        }
        @Test
        void itShouldDeleteBook(){
            // Given
            Long id = 1L;
            Book book = Instancio.of(Book.class).create();
            book.setId(id);
            when(bookRepo.findById(id)).thenReturn(Optional.of(book));

            // When
            bookService.deleteBook(id);

            // Then
            verify(bookRepo).findById(id);
            verify(bookRepo).delete(book);
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
            book.setId(id);
            when(bookRepo.findById(id)).thenReturn(Optional.of(book));

            // When
            BookDto bookDto = bookService.getBookById(id);

            // Then
            verify(bookRepo).findById(id);
            assertThat(bookDto.getId()).isEqualTo(id);

        }
    }


    @Nested
    class TestCreateBook {
        @Test
        void itShouldThrowDuplicateResourceException() {
            // Given
            BookDto bookDto = Instancio.of(BookDto.class).create();
            category.setCategoryId(bookDto.getCategoryId());
            when(categoryRepo.findById(anyInt())).thenReturn(Optional.of(category));
            when(bookRepo.existsByTitleIgnoreCase(bookDto.getTitle())).thenReturn(true);

            // When
            // Then
            assertThatThrownBy(() -> bookService.addBook(bookDto))
                    .isInstanceOf(DuplicateResourceException.class)
                    .hasMessage(BOOK_TITLE_EXISTS.formatted(bookDto.getTitle()));
        }

        @Test
        void itShouldThrowCategoryNotFound() {
            // Given
            BookDto bookDto = Instancio.of(BookDto.class).create();

            // When
            // Then
            assertThatThrownBy(() -> bookService.addBook(bookDto))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(CATEGORY_NOT_FOUND.formatted(bookDto.getCategoryId()));
        }

        @Test
        void itShouldCreateBook() {
            // Given
            BookDto bookDto = Instancio.of(BookDto.class).create();
            category.setCategoryId(bookDto.getCategoryId());
            when(categoryRepo.findById(anyInt())).thenReturn(Optional.of(category));
            when(bookRepo.existsByTitleIgnoreCase(bookDto.getTitle())).thenReturn(false);

            // When
            Book book = bookService.addBook(bookDto);

            // Then
            verify(bookRepo).save(book);
            assertThat(book.getTitle()).isEqualTo(bookDto.getTitle());
            assertThat(book.getAuthor()).isEqualTo(bookDto.getAuthor());
            assertThat(book.getDescription()).isEqualTo(bookDto.getDescription());
            assertThat(book.getImage()).isEqualTo(bookDto.getImage());
            assertThat(book.getEdition()).isEqualTo(bookDto.getEdition());
            assertThat(book.getCategory().getCategoryId()).isEqualTo(bookDto.getCategoryId());
            assertThat(book.getPrice()).isEqualTo(bookDto.getPrice());

        }
    }
    @Nested
    class TestUpdateBook {
        @Test
        void itShouldThrowBookNotFound(){
            // Given
            Long id = 1L;
            BookDto bookDto = Instancio.of(BookDto.class).create();

            // When
            // Then
            assertThatThrownBy(()-> bookService.updateBook(id, bookDto))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(BOOK_NOT_FOUND.formatted(id));
        }
        @Test
        void itShouldThrowDuplicateResourceException(){
            // Given
            Long id = 1L;
            BookDto bookDto = Instancio.of(BookDto.class).create();
            Book book = Instancio.of(Book.class).create();
            book.setId(id);
            when(bookRepo.findById(id)).thenReturn(Optional.of(book));
            when(bookRepo.existsByTitle(bookDto.getTitle())).thenReturn(true);

            // When
            // Then
            assertThatThrownBy(()-> bookService.updateBook(id, bookDto))
                    .isInstanceOf(DuplicateResourceException.class)
                    .hasMessage(BOOK_TITLE_TAKEN);
        }
        @Test
        void itShouldThrowRequestValidationException() {
            // Given
            Long bookId = 1L;
            BookDto bookDto = new BookDto();
            bookDto.setTitle("Same Title");
            bookDto.setAuthor("Same Author");
            bookDto.setDescription("Same Description");
            bookDto.setImage("https://same-image.com/image.jpg");
            bookDto.setEdition(LocalDate.parse("1980-01-01"));

            Book existingBook = new Book();
            existingBook.setId(bookId);
            existingBook.setTitle("Same Title");
            existingBook.setAuthor("Same Author");
            existingBook.setDescription("Same Description");
            existingBook.setImage("https://same-image.com/image.jpg");
            existingBook.setEdition(LocalDate.parse("1980-01-01"));

            when(bookRepo.findById(bookId)).thenReturn(Optional.of(existingBook));

            // When
            // Then
            assertThatThrownBy(() -> bookService.updateBook(bookId, bookDto))
                    .isInstanceOf(RequestValidationException.class)
                    .hasMessage(NO_CHANGE_DETECTED);
        }

        @Test
        void itShouldUpdateBook() {
            // Given
            Long bookId = 1L;
            BookDto bookDto = Instancio.create(BookDto.class);
            Book existingBook = Instancio.create(Book.class);
            existingBook.setId(bookId);
            when(bookRepo.findById(bookId)).thenReturn(Optional.of(existingBook));
            when(bookRepo.existsByTitle(bookDto.getTitle())).thenReturn(false);

            // When
            Book updatedBook = bookService.updateBook(bookId, bookDto);

            // Then
            verify(bookRepo).findById(bookId);
            verify(bookRepo).save(existingBook);
            assertThat(updatedBook.getTitle()).isEqualTo(bookDto.getTitle());
            assertThat(updatedBook.getAuthor()).isEqualTo(bookDto.getAuthor());
            assertThat(updatedBook.getDescription()).isEqualTo(bookDto.getDescription());
            assertThat(updatedBook.getImage()).isEqualTo(bookDto.getImage());
            assertThat(updatedBook.getEdition()).isEqualTo(bookDto.getEdition());

        }
    }


}


