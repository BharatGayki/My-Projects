package com.example.ecommerce.service;

import com.example.ecommerce.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Page<Category> findPaginated(Pageable pageable);

    List<Category> findAll();

    void save(Category category);

    Category findById(Long category_id);

    Category update(Category category);
    void deleteById(Long category_id);
    void enableById(Long category_id);
}
