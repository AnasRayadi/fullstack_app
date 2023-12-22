package com.rayadi.backend.controller;

import com.rayadi.backend.dto.AddBookRequest;
import com.rayadi.backend.dto.FilterBooksRequest;
import com.rayadi.backend.model.Book;
import com.rayadi.backend.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    /*@PostMapping("/filterByDate")
    public ResponseEntity<List<Book>> filterBooksByDate(@RequestBody FilterBooksByDateRequest request) {
        return ResponseEntity.ok(bookService.getBooksByEdition(request));
    }*/
    /*@PostMapping("/filterByCategory")
    public ResponseEntity<List<Book>> filterBooksByCategory(@RequestBody FilterBooksByCategoryRequest request) {
        return ResponseEntity.ok(bookService.getBooksByCategory(request));
    }*/
    @PostMapping("/filter")
    public ResponseEntity<List<Book>> filterBooks(@RequestBody FilterBooksRequest request) {
        return ResponseEntity.ok(bookService.filterBooks(request));
    }

}
