package com.rayadi.backend.dto;

import com.rayadi.backend.annotation.ValidEditionDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    @NotNull(message = "Title is required")
    private String title;
    @NotNull(message = "Author is required")
    private String author;
    @NotNull(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;
    @NotNull(message = "Image is required")
    @Pattern(regexp = "(https?://.*\\.(?:png|jpg))", message = "Image must be a valid URL ending with .png or .jpg")
    private String image;
    @NotNull(message = "Edition is required")
    @ValidEditionDate
    private LocalDate edition;
    @NotNull(message = "Category ID is required")
    private Integer categoryId;
    @NotNull(message = "Price is required")
    private double price;

}

