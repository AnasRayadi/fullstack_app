package com.rayadi.backend.service;

import com.rayadi.backend.converter.AuthorConverter;
import com.rayadi.backend.dto.AuthorDto;
import com.rayadi.backend.exception.DuplicateResourceException;
import com.rayadi.backend.exception.ResourceNotFoundException;
import com.rayadi.backend.model.Author;
import com.rayadi.backend.repository.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rayadi.backend.constants.ErrorMessagesConstant.AUTHOR_EXISTS;
import static com.rayadi.backend.constants.ErrorMessagesConstant.AUTHOR_NOT_FOUND;
import static com.rayadi.backend.util.util.emailIsValid;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepo authorRepo;

    public AuthorDto getAuthorById(Long id){
        Author author = authorRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(AUTHOR_NOT_FOUND.formatted(id)));
        return AuthorConverter.authorToAuthorDto(author);
    }
//    public List<AuthorDto> getAllAuthors(){
//        List<Author> authors = authorRepo.findAll();
//
//    }
    public AuthorDto createAuthor(AuthorDto authorDto){
        if(authorRepo.existsByFullNameIgnoreCase(authorDto.getName())){
            throw new DuplicateResourceException(AUTHOR_EXISTS.formatted(authorDto.getName()));
        }
        if(!emailIsValid(authorDto.getEmail())){
            throw new IllegalArgumentException("Invalid email address");
        }
        Author author = AuthorConverter.authorDtoToAuthor(authorDto);
        author = authorRepo.save(author);
        return AuthorConverter.authorToAuthorDto(author);
    }

}
