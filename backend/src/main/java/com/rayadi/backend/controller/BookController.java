package com.rayadi.backend.controller;

import com.rayadi.backend.dto.AddBookRequest;
import com.rayadi.backend.dto.FilterBooksRequest;
import com.rayadi.backend.model.Book;
import com.rayadi.backend.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
    public ResponseEntity<Book> getBookById(@PathVariable Integer bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping("/new")
    public ResponseEntity<Book> addBook(@RequestBody AddBookRequest request) {
        return ResponseEntity.ok(bookService.addBook(request));
    }
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer bookId, @RequestBody AddBookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(bookId, request));
    }
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Book>> filterBooks(@RequestParam(required = false) Map<String, String> filters
                                                  /*@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size*/
    ) {
        return ResponseEntity.ok(bookService.filterBooks(filters));
    }
}
