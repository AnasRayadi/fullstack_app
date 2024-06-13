package com.rayadi.backend.converter;

import com.rayadi.backend.dto.AuthorDto;
import com.rayadi.backend.enums.Gender;
import com.rayadi.backend.mappers.AuthorMapper;
import com.rayadi.backend.model.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@AllArgsConstructor
@Component
public class AuthorConverter {
    private static final AuthorMapper authorMapper = null;

    public static AuthorDto authorToAuthorDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(author.getFullName());
        authorDto.setEmail(author.getEmail());
        authorDto.setGender(String.valueOf(author.getGender()));
        authorDto.setBirthDate(author.getBirthDate());
        return authorDto;
    }
    public static Author authorDtoToAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author.setFullName(authorDto.getName());
        author.setEmail(authorDto.getEmail());
        author.setGender(Gender.valueOf(authorDto.getGender()));
        author.setBirthDate(authorDto.getBirthDate());
        return author;
    }

    public static List<AuthorDto> authorsToAuthorsDto(List<Author> authors) {

        return authorMapper.authorsToAuthorsDto(authors);
    }
}
