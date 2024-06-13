package com.rayadi.backend.mappers;

import com.rayadi.backend.dto.AuthorDto;
import com.rayadi.backend.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface AuthorMapper {

    @Mapping(source = "authorName", target = "name")
    AuthorDto authorToAuthorDto(Author author);

    @Mapping(source = "name", target = "authorName")
    Author authorDtoToAuthor(AuthorDto authorDto);


    List<AuthorDto> authorsToAuthorsDto(List<Author> authors);
}
