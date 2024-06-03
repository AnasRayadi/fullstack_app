package com.rayadi.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryDto {
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

}
