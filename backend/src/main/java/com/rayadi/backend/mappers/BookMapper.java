package com.rayadi.backend.mappers;

import com.rayadi.backend.dto.BookDto;
import com.rayadi.backend.model.Book;
import org.mapstruct.Mapper;



@Mapper
public interface BookMapper {
    Book bookDtoToBook(BookDto bookDto);
    BookDto bookToBookDto(Book book);
}
