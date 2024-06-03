package com.rayadi.backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String description;
    private String image;
    private LocalDate edition;
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private BookCategory category;
    private double price;
}

