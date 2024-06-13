package com.rayadi.backend.service;

import com.rayadi.backend.converter.AuthorConverter;
import com.rayadi.backend.dto.AuthorDto;
import com.rayadi.backend.exception.DuplicateResourceException;
import com.rayadi.backend.repository.AuthorRepo;
import org.instancio.Instancio;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.rayadi.backend.constants.ExceptionMessagesConstant.EMAIL_EXISTS;
import static com.rayadi.backend.constants.ExceptionMessagesConstant.INVALID_EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @Mock
    private AuthorRepo authorRepo;
    @Mock
    private AuthorConverter authorConverter;
    @InjectMocks
    private AuthorService authorService;

    @Nested
    class TestCreateAuthor {
        @Test
        void itShouldThrowDuplicateResourceExceptionWhenEmailAlreadyExists(){
            // Given
            AuthorDto authorDto = Instancio.of(AuthorDto.class).create();
            when(authorRepo.existsByEmail(anyString())).thenReturn(true);

            // When
            DuplicateResourceException exception = assertThrows(DuplicateResourceException.class, () -> authorService.createAuthor(authorDto));
            // Then
            assertThat(exception.getMessage()).isEqualTo(EMAIL_EXISTS);
        }

        @Test
        void itShouldThrowRuntimeExceptionWhenEmailIsInvalid(){
            // Given
            AuthorDto authorDto = Instancio.of(AuthorDto.class).create();
            authorDto.setEmail(anyString());

            // When
            RuntimeException exception = assertThrows(RuntimeException.class, () -> authorService.createAuthor(authorDto));

            // Then
            assertThat(exception.getMessage()).isEqualTo(INVALID_EMAIL);

        }


    }

}