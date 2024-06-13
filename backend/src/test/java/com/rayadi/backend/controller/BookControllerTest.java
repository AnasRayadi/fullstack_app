package com.rayadi.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rayadi.backend.dto.BookDto;
import com.rayadi.backend.exception.ResourceNotFoundException;
import com.rayadi.backend.helpers.MvcHelper;
import com.rayadi.backend.model.Book;
import com.rayadi.backend.service.BookService;
import org.instancio.Instancio;

import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.rayadi.backend.constants.ExceptionMessagesConstant.BOOK_NOT_FOUND;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;
    private Book book;
    private BookDto bookDto;
    private static final String CONTROLLER_ENDPOINT = "/api/v1/books";

    @BeforeEach
    void setUp() {
        Long id = 1L;
        book = Instancio.create(Book.class);
        bookDto = Instancio.create(BookDto.class);

        book.setId(id);
        bookDto.setId(id);
    }
    @Nested
    class TestGetBookById {
        @Test
        void itShouldReturnBook() throws Exception {
            // Given
            Long id = 1L;
            when(bookService.getBookById(id)).thenReturn(bookDto);

            // When
            ResultActions response = mockMvc.perform(get(CONTROLLER_ENDPOINT + "/{bookId}", id)
                                            .contentType("application/json").content(objectMapper.writeValueAsString(bookDto)));
            response.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.title").value(bookDto.getTitle()))
                    .andExpect(jsonPath("$.author").value(bookDto.getAuthor()))
                    .andExpect(jsonPath("$.description").value(bookDto.getDescription()))
                    .andExpect(jsonPath("$.image").value(bookDto.getImage()))
                    .andExpect(jsonPath("$.edition").value(bookDto.getEdition().toString()))
                    .andExpect(jsonPath("$.categoryId").value(bookDto.getCategoryId()))
                    .andExpect(jsonPath("$.price").value(bookDto.getPrice()));

            // Then
            verify(bookService).getBookById(id);

        }
        @Test
        void itShouldReturnResourceNotFoundException() throws Exception {
            // Given
            Long id = 1L;
            when(bookService.getBookById(id)).thenThrow(new ResourceNotFoundException(BOOK_NOT_FOUND.formatted(id)));

            // When
            mockMvc.perform(get(CONTROLLER_ENDPOINT + "/{bookId}", id))
                    .andExpect(status().isNotFound());

            // Then
            verify(bookService).getBookById(id);
        }
    }
    @Nested
    class TestAddBook {

        @ParameterizedTest
        @CsvSource(value = {
                "title,TITLE_REQUIRED",
                "author,AUTHOR_REQUIRED",
                "description,DESCRIPTION_REQUIRED",
                "image,IMAGE_REQUIRED",
                "edition,EDITION_REQUIRED",
                "categoryId,CATEGORY_ID_REQUIRED"
        })
        void itShouldReturnRequiredFieldsErrors(String field, String errorMsg) throws Exception {
            // Given
            BookDto dto = Instancio.of(BookDto.class)
                    .set(Select.field(field), null)
                    .create();

            // When
            ResultActions result = MvcHelper.performPostRequest(CONTROLLER_ENDPOINT + "/new", dto, mockMvc);

            // Then
            assertValidationError(result, errorMsg);
        }

        @Test
        void itShouldAddBook() throws Exception {
            // Given
            BookDto bookDto = new BookDto();
            bookDto.setTitle("new title");
            bookDto.setAuthor("new author");
            bookDto.setDescription("new description");
            bookDto.setImage("https://www.example.com/image.jpg");
            bookDto.setEdition(LocalDate.parse("2021-12-12"));
            bookDto.setCategoryId(1);
            bookDto.setPrice(100.0);

            Book book = new Book();

            when(bookService.addBook(bookDto)).thenReturn(book);

            // When
            ResultActions response = MvcHelper.performPostRequest(CONTROLLER_ENDPOINT + "/new", bookDto, mockMvc);

            // Then
            verify(bookService).addBook(bookDto);
            response.andExpect(status().isOk());

        }
    }
    @Nested
    class TestDeleteBook {
        @Test
        void itShouldDeleteBook() throws Exception {
            // Given
            Long id = 1L;

            // When
            ResultActions response = mockMvc.perform(delete(CONTROLLER_ENDPOINT + "/{bookId}", id));

            // Then
            verify(bookService).deleteBook(id);
            response.andExpect(status().isOk());

        }
    }
    @Nested
    class TestUpdateBook {
        @ParameterizedTest
        @CsvSource(value = {
                "title,TITLE_REQUIRED",
                "author,AUTHOR_REQUIRED",
                "description,DESCRIPTION_REQUIRED",
                "image,IMAGE_REQUIRED",
                "edition,EDITION_REQUIRED",
                "categoryId,CATEGORY_ID_REQUIRED",
        })
        void itShouldReturnRequiredFieldsErrors(String field, String errorMsg) throws Exception {
            // Given
            Long id = 1L;
            BookDto dto = Instancio.of(BookDto.class)
                    .set(Select.field(field), null)
                    .create();

            // When
            ResultActions result = MvcHelper.performUpdateRequest(CONTROLLER_ENDPOINT +"/"+id, dto, mockMvc);

            // Then
            assertValidationError(result, errorMsg);
        }
        @Test
        void itShouldUpdateBook() throws Exception {
            // Given
            Long id = 1L;
            BookDto bookDto = new BookDto();
            bookDto.setTitle("new title");
            bookDto.setAuthor("new author");
            bookDto.setDescription("new description");
            bookDto.setImage("https://www.example.com/image.jpg");
            bookDto.setEdition(LocalDate.parse("2021-12-12"));
            bookDto.setCategoryId(1);
            bookDto.setPrice(100.0);

            // When
            ResultActions response = MvcHelper.performUpdateRequest(CONTROLLER_ENDPOINT +"/"+id, bookDto, mockMvc);

            // Then
            verify(bookService).updateBook(id, bookDto);
            response.andExpect(status().isNoContent());
        }
    }
    @Nested
    class TestFilterBooks {
        @Test
        void itShouldGetBooksWithEmptyFilters() throws Exception {
            // Given
            // When
            ResultActions response = mockMvc.perform(get(CONTROLLER_ENDPOINT + "/filter"));

            // Then
            verify(bookService).filterBooks(any());
            response.andExpect(status().isOk());
        }
        @Test
        void itShouldGetBooksWithGivenFilters() throws Exception {
            // Given
            Map<String, String> filters = new HashMap<>();
            filters.put("page", "0");
            filters.put("size", "50");
            String filtersStr = "page=0&size=50";
            // When
            ResultActions response = mockMvc.perform(get(CONTROLLER_ENDPOINT + "/filter"+"?"+filtersStr));

            // Then
            verify(bookService).filterBooks(filters);
            response.andExpect(status().isOk());
        }
    }
    private void assertValidationError(ResultActions result, String errorMsg) throws Exception {
        verifyNoInteractions(bookService);
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.errors", hasItem(errorMsg)));
    }
}