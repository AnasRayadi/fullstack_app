package com.rayadi.backend.controller;

import com.rayadi.backend.model.BookCategory;
import com.rayadi.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<BookCategory>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
