package com.rayadi.backend.controller;

import com.rayadi.backend.dto.BookDto;
import com.rayadi.backend.model.Book;
import com.rayadi.backend.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping("/new")
    public ResponseEntity<Book> addBook(@Valid @RequestBody BookDto request) {
        return ResponseEntity.ok(bookService.addBook(request));
    }
    @PutMapping("/{bookId}")
    public ResponseEntity<HttpStatus> updateBook(@PathVariable Long bookId,@Valid @RequestBody BookDto request) {
        bookService.updateBook(bookId, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Book>> filterBooks(@RequestParam(required = false) Map<String, String> filters
                                                  /*@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size*/
    ) {
        return new ResponseEntity<>(bookService.filterBooks(filters), HttpStatus.OK);
    }
}
