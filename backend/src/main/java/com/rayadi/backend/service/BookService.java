package com.rayadi.backend.service;

import com.rayadi.backend.dao.BookFilter;
import com.rayadi.backend.dto.AddBookRequest;
import com.rayadi.backend.dto.FilterBooksRequest;
import com.rayadi.backend.exception.DuplicateResourceException;
import com.rayadi.backend.exception.RequestValidationException;
import com.rayadi.backend.exception.ResourceNotFoundException;
import com.rayadi.backend.model.Book;
import com.rayadi.backend.model.BookCategory;
import com.rayadi.backend.repository.BookRepo;
import com.rayadi.backend.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;
    private final BookFilter bookFilter;
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }
    public Book getBookById(Integer id) {
        return bookRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book with id [%s] not found".formatted(id)));
    }
    public Book addBook(AddBookRequest request) {
        if (request.getTitle() != null && request.getAuthor() != null && request.getDescription() != null && request.getImage() != null && request.getEdition() != null && request.getCategoryId() != null) {
            if (bookRepo.existsByTitle(request.getTitle())){
                throw new DuplicateResourceException("Book with title [%s] already exists".formatted(request.getTitle()));
            }
            BookCategory category = categoryRepo.findById(request.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category with id [%s] not found".formatted(request.getCategoryId())));
            Book book = Book.builder()
                    .title(request.getTitle())
                    .author(request.getAuthor())
                    .description(request.getDescription())
                    .image(request.getImage())
                    .edition(request.getEdition())
                    .category(category)
                    .build();
            return bookRepo.save(book);
        }
        throw new RuntimeException("Invalid request");
    }
    public Book updateBook(Integer id, AddBookRequest request) {
        Book book = getBookById(id);
        boolean change = false;
        if (request.getTitle() != null && !request.getTitle().equals(book.getTitle())) {
            if (bookRepo.existsByTitle(request.getTitle())){
                throw new DuplicateResourceException("Book title taken before");
            }
            book.setTitle(request.getTitle());
            change = true;
        }
        if (request.getAuthor() != null && !request.getAuthor().equals(book.getAuthor())) {
            book.setAuthor(request.getAuthor());
            change = true;
        }
        if (request.getDescription() != null && !request.getDescription().equals(book.getDescription())) {
            book.setDescription(request.getDescription());
            change = true;
        }
        if (request.getImage() != null && !request.getImage().equals(book.getImage())) {
            book.setImage(request.getImage());
            change = true;
        }
        if (request.getEdition() != null && !request.getEdition().equals(book.getEdition())) {
            book.setEdition(request.getEdition());
            change = true;
        }
        if (!change) {
            throw new RequestValidationException("No change detected");
        }
        return bookRepo.save(book);

        //throw new ResourceNotFoundException("Book with id [%s] not found".formatted(id));
    }
    public void deleteBook(Integer id) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isPresent()) {
            bookRepo.delete(book.get());
        } else {
            throw new ResourceNotFoundException("Book with id [%s] not found".formatted(id));
        }
    }
    /*public List<Book> getBooksByEdition(FilterBooksByDateRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return bookRepo.findByEditionBetween(LocalDate.parse(request.getStartDate(),formatter), LocalDate.parse(request.getEndDate(),formatter));
    }*/
    /*public List<Book> getBooksByCategory(FilterBooksByCategoryRequest request) {
        return bookRepo.findByCategoryCategoryId(Integer.valueOf(request.getCategoryId()));
    }*/

    /*public List<Book> filterBooks(FilterBooksRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(request.getStartDate()==null || request.getEndDate()==null){
            return bookRepo.filter(Integer.valueOf(request.getCategoryId()) ,null,null);
        }
        if (request.getCategoryId()==null){
            return bookRepo.filter(null,LocalDate.parse(request.getStartDate(),formatter), LocalDate.parse(request.getEndDate(),formatter) );
        }
        return bookRepo.filter(Integer.valueOf(request.getCategoryId()),LocalDate.parse(request.getStartDate(),formatter), LocalDate.parse(request.getEndDate(),formatter) );
    }*/
    public List<Book> filterBooks(FilterBooksRequest request) {
        return bookFilter.findAllByCriteria(request);
    }
}
