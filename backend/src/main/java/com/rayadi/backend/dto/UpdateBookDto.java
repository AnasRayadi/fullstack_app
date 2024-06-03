package com.rayadi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookDto {
    private String title;
    private String author;
    private String description;
    private String image;
    private String editionDate;
}
