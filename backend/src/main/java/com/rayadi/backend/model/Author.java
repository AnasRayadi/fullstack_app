package com.rayadi.backend.model;

import com.rayadi.backend.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String email;
    private LocalDate birthDate;
}
