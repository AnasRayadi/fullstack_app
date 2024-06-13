package com.rayadi.backend.service;

import com.rayadi.backend.exception.ResourceNotFoundException;
import com.rayadi.backend.model.BookCategory;
import com.rayadi.backend.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rayadi.backend.constants.ExceptionMessagesConstant.CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;
    public List<BookCategory> getAllCategories(){
        return categoryRepo.findAll();
    }
    public BookCategory getCategoryById(Integer id){
        return categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(CATEGORY_NOT_FOUND.formatted(id)));
    }

}
