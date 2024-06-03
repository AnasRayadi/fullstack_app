package com.rayadi.backend.converter;

import com.rayadi.backend.dto.BookDto;

import com.rayadi.backend.mappers.BookMapper;
import com.rayadi.backend.model.Book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BookConverter {
    //private final BookMapper bookMapper;
    public BookDto bookToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setDescription(book.getDescription());
        bookDto.setImage(book.getImage());
        bookDto.setEdition(book.getEdition());
        bookDto.setCategoryId(book.getCategory().getCategoryId());
        bookDto.setPrice(book.getPrice());
        return bookDto;
        //return bookMapper.bookToBookDto(book);
    }

    public Book bookDtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setImage(bookDto.getImage());
        book.setEdition(bookDto.getEdition());
        book.setPrice(bookDto.getPrice());
        return book;
        //return bookMapper.bookDtoToBook(bookDto);
    }
}
