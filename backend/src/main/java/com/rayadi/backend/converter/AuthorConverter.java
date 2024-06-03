package com.rayadi.backend.converter;

import com.rayadi.backend.dto.AuthorDto;
import com.rayadi.backend.mappers.AuthorMapper;
import com.rayadi.backend.model.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;



@AllArgsConstructor
@Component
public class AuthorConverter {
    private static final AuthorMapper authorMapper = null;

    public static AuthorDto authorToAuthorDto(Author author) {
        return authorMapper.authorToAuthorDto(author);
    }
    public static Author authorDtoToAuthor(AuthorDto authorDto) {
        return authorMapper.authorDtoToAuthor(authorDto);
    }

}
