package com.rayadi.backend.service;

import com.rayadi.backend.converter.BookConverter;
import com.rayadi.backend.dto.BookDto;
import com.rayadi.backend.exception.DuplicateResourceException;
import com.rayadi.backend.exception.RequestValidationException;
import com.rayadi.backend.exception.ResourceNotFoundException;
import com.rayadi.backend.model.Book;
import com.rayadi.backend.model.BookCategory;
import com.rayadi.backend.predicate.BookPredicate;
import com.rayadi.backend.repository.BookRepo;
import com.rayadi.backend.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.rayadi.backend.constants.ExceptionMessagesConstant.*;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;
    private final BookConverter bookConverter ;

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }
    public BookDto getBookById(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(BOOK_NOT_FOUND.formatted(id)));
        return bookConverter.bookToBookDto(book);
    }
    public Book addBook(BookDto bookDto) {
        // check for duplicate title
        if(bookRepo.existsByTitleIgnoreCase(bookDto.getTitle())){
            throw new DuplicateResourceException(BOOK_TITLE_EXISTS.formatted(bookDto.getTitle()));
        }

        // convert dto to book
        Book book = bookConverter.bookDtoToBook(bookDto);

        // check if category exists
        BookCategory category = categoryRepo.findById(bookDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException(CATEGORY_NOT_FOUND.formatted(bookDto.getCategoryId())));
        book.setCategory(category);

        // save book
        return bookRepo.save(book);
    }
    public void updateBook(Long id, BookDto bookDto) {
        bookRepo.findById(id)
                .map(book -> {
                    validateTitle(book, bookDto);
                    updateAuthor(book, bookDto);
                    updateDescription(book, bookDto);
                    updateImage(book, bookDto);
                    updateEdition(book, bookDto);
                    return bookRepo.save(book);
                })
                .orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_FOUND.formatted(id)));
    }

    private void validateTitle(Book book, BookDto bookDto) {
        if (!Objects.equals(bookDto.getTitle(), book.getTitle())) {
            if (bookRepo.existsByTitleIgnoreCase(bookDto.getTitle())) {
                throw new DuplicateResourceException(BOOK_TITLE_TAKEN);
            }
            book.setTitle(bookDto.getTitle());
        }
        else {
            throw new RequestValidationException(NO_CHANGE_DETECTED);
        }
    }

    private void updateAuthor(Book book, BookDto bookDto) {
        if (!Objects.equals(bookDto.getAuthor(), book.getAuthor())) {
            book.setAuthor(bookDto.getAuthor());
        }
        else {
            throw new RequestValidationException(NO_CHANGE_DETECTED);
        }    }

    private void updateDescription(Book book, BookDto bookDto) {
        if (!Objects.equals(bookDto.getDescription(), book.getDescription())) {
            book.setDescription(bookDto.getDescription());
        }
        else {
            throw new RequestValidationException(NO_CHANGE_DETECTED);
        }    }

    private void updateImage(Book book, BookDto bookDto) {
        if (!Objects.equals(bookDto.getImage(), book.getImage())) {
            book.setImage(bookDto.getImage());
        }
        else {
            throw new RequestValidationException(NO_CHANGE_DETECTED);
        }    }

    private void updateEdition(Book book, BookDto bookDto) {
        if (!Objects.equals(bookDto.getEdition(), book.getEdition())) {
            book.setEdition(bookDto.getEdition());
        }
        else {
            throw new RequestValidationException(NO_CHANGE_DETECTED);
        }    }

//    public Book updateBook(Long id, BookDto bookDto) {
//        Book book = bookRepo.findById(id).orElseThrow(()->
//                new ResourceNotFoundException(BOOK_NOT_FOUND.formatted(id)));
//
//        boolean change = false;
//        if (!bookDto.getTitle().equals(book.getTitle())) {
//            if (bookRepo.existsByTitle(bookDto.getTitle())){
//                throw new DuplicateResourceException(BOOK_TITLE_TAKEN);
//            }
//            book.setTitle(bookDto.getTitle());
//            change = true;
//        }
//        if (!bookDto.getAuthor().equals(book.getAuthor())) {
//            book.setAuthor(bookDto.getAuthor());
//            change = true;
//        }
//        if (!bookDto.getDescription().equals(book.getDescription())) {
//            book.setDescription(bookDto.getDescription());
//            change = true;
//        }
//        if (!bookDto.getImage().equals(book.getImage())) {
//            book.setImage(bookDto.getImage());
//            change = true;
//        }
//        if (!bookDto.getEdition().equals(book.getEdition())) {
//            book.setEdition(bookDto.getEdition());
//            change = true;
//        }
//        if (!change) {
//            throw new RequestValidationException(NO_CHANGE_DETECTED);
//        }
//        return bookRepo.save(book);
//    }
    public void deleteBook(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException(BOOK_NOT_FOUND.formatted(id)));
        bookRepo.delete(book);

    }
    public Page<Book> filterBooks(Map<String, String> filters) {
        var predicate = BookPredicate.getBookPredicate(filters);
        Pageable paging = PageRequest.of(Integer.parseInt(filters.get("page")),Integer.parseInt(filters.get("size")));
        return bookRepo.findAll(predicate, paging);
    }

}
