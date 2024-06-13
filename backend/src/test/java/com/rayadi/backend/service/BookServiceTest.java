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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static com.rayadi.backend.constants.ExceptionMessagesConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class BookServiceTest {

    @Mock
    private BookRepo bookRepo;
    @Mock
    private BookConverter bookConverter;
    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private BookService bookService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Nested
    class TestDeleteBook {
        @Test
        void itShouldThrowBookNotFound(){
            // Given
            Long id = 1L;

            // When
            // Then
            assertThatThrownBy(()->bookService.deleteBook(id))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(BOOK_NOT_FOUND.formatted(id));
        }
        @Test
        void itShouldDeleteBook(){
            // Given
            Long id = 1L;
            Book book = Instancio.of(Book.class).create();
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
        void itShouldThrowResourceNotFoundExceptionWhenBookNotFound(){
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
            when(bookRepo.findById(id)).thenReturn(Optional.of(book));
            when(bookConverter.bookToBookDto(book)).thenReturn(Instancio.of(BookDto.class).create());

            // When
            bookService.getBookById(id);

            // Then
            verify(bookRepo).findById(id);

        }
    }


    @Nested
    class TestCreateBook {
        @Test
        void itShouldThrowDuplicateResourceExceptionWhenTitleExists() {
            // Given
            BookDto bookDto = Instancio.of(BookDto.class).create();
            when(bookRepo.existsByTitleIgnoreCase(bookDto.getTitle())).thenReturn(true);

            // When
            // Then
            assertThatThrownBy(() -> bookService.addBook(bookDto))
                    .isInstanceOf(DuplicateResourceException.class)
                    .hasMessage(BOOK_TITLE_EXISTS.formatted(bookDto.getTitle()));

        }
        @Test
        void itShouldThrowResourceNotFoundExceptionWhenCategoryNotFound() {
            // Given
            BookDto bookDto = Instancio.of(BookDto.class).create();
            Book book = Instancio.of(Book.class).create();
            when(bookConverter.bookDtoToBook(bookDto)).thenReturn(book);

            // When
            // Then
            assertThatThrownBy(() -> bookService.addBook(bookDto))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(CATEGORY_NOT_FOUND.formatted(bookDto.getCategoryId()));
            verify(categoryRepo).findById(bookDto.getCategoryId());
        }
        @Test
        void itShouldCreateBookSuccessfully() {
            // Given
            BookDto bookDto = Instancio.of(BookDto.class).create();
            Book book = Instancio.of(Book.class).create();
            BookCategory bookCategory = Instancio.of(BookCategory.class).create();
            when(bookConverter.bookDtoToBook(bookDto)).thenReturn(book);
            when(categoryRepo.findById(anyInt())).thenReturn(Optional.of(bookCategory));
            when(bookRepo.existsByTitleIgnoreCase(bookDto.getTitle())).thenReturn(false);

            // When
            bookService.addBook(bookDto);

            // Then
            verify(bookRepo).save(any());

        }
    }
    @Nested
    class TestUpdateBook {
        @Test
        void itShouldThrowBookNotFound(){
            // Given
            Long id = 1L;
            BookDto bookDto = Instancio.of(BookDto.class).create();
            when(bookRepo.findById(id)).thenReturn(Optional.empty());
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
            when(bookRepo.findById(id)).thenReturn(Optional.of(book));
            when(bookRepo.existsByTitleIgnoreCase(bookDto.getTitle())).thenReturn(true);

            // When
            // Then
            assertThatThrownBy(()-> bookService.updateBook(id, bookDto))
                    .isInstanceOf(DuplicateResourceException.class)
                    .hasMessage(BOOK_TITLE_TAKEN);
        }

        @Test
        void itShouldThrowRequestValidationException() {
            // Given
            Long id = 1L;
            BookDto bookDto = Instancio.of(BookDto.class).create();
            Book book = Instancio.of(Book.class).create();
            book.setId(id);
            when(bookRepo.findById(id)).thenReturn(Optional.of(book));
            when(bookRepo.existsByTitleIgnoreCase(bookDto.getTitle())).thenReturn(false);

            // when
            // then
            assertThatThrownBy(() -> bookService.updateBook(id, bookDto))
                    .isInstanceOf(RequestValidationException.class)
                    .hasMessage(NO_CHANGE_DETECTED);
        }

        @Test
        void itShouldUpdateBookSuccessfully() {
            // Given
            Long id = 1L;
            BookDto bookDto = Instancio.of(BookDto.class).create();
            bookDto.setId(id);
            bookDto.setTitle("title");
            bookDto.setAuthor("author");
            bookDto.setDescription("description for book");
            bookDto.setImage("https://image.com/image.jpg");
            bookDto.setEdition(LocalDate.parse("2021-01-01"));

            Book book = Instancio.of(Book.class).create();
            book.setId(id);
            book.setTitle("old title");
            book.setAuthor("old author");
            book.setDescription("old description for book");
            book.setImage("https://image.com/old-image.jpg");
            book.setEdition(LocalDate.parse("2020-01-01"));


            when(bookRepo.findById(id)).thenReturn(Optional.of(book));
            when(bookRepo.existsByTitleIgnoreCase(bookDto.getTitle())).thenReturn(false);

            // When
            bookService.updateBook(id, bookDto);

            // Then
            verify(bookRepo).save(book);
            assertThat(book.getTitle()).isEqualTo(bookDto.getTitle());
            assertThat(book.getAuthor()).isEqualTo(bookDto.getAuthor());
            assertThat(book.getDescription()).isEqualTo(bookDto.getDescription());
            assertThat(book.getImage()).isEqualTo(bookDto.getImage());
            assertThat(book.getEdition()).isEqualTo(bookDto.getEdition());

        }
    }


}


