package com.rayadi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBookRequest {
    private String title;
    private String author;
    private String description;
    private String image;
    private LocalDate edition;
}

