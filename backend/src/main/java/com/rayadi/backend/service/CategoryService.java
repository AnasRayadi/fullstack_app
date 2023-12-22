package com.rayadi.backend.service;

import com.rayadi.backend.model.BookCategory;
import com.rayadi.backend.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;
    public List<BookCategory> getAllCategories(){
        return categoryRepo.findAll();
    }
}
