package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.repo.CategoryRepository;
import com.example.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repo;

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category save(Category category) {

        try {
            Category categorySave = new Category(category.getName());
            return repo.save(categorySave);
        }catch (Exception e){

            e.printStackTrace();
            return null;

        }

    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).get();
    }
/*
    @Override
    public Category findById(Long id) {

        Optional<Category> optional = repo.findById(id);
        Category category = null;
        if (optional.isPresent()) {
            category = optional.get();
        }
        else{

            throw new RuntimeException("Category not found");
        }

        return category;
    }
*/

    @Override
    public Category update(Category category) {
        return null;
    }

/*    @Override
//video part
    public Category update(Category category) {
        Category categoryUpdate = null;
        try {

            categoryUpdate   = repo.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_activated(category.is_activated());
            categoryUpdate.set_deleted(category.is_deleted());
        }catch (Exception e){
            e.printStackTrace();
        }
        return repo.save(categoryUpdate);
    }

    @Override
    public void deleteById(Long id) {
Category category = null;
        repo.deleteById(id);
      *//*  Category category = repo.getById(id);
        category.set_deleted(true);
        category.set_activated(false);*//*
        repo.save(category);
    }*/

/*
    @Override
    // search part
    public Category update(Category category) {
        try {
            // Check if the category exists before updating
            Category categoryUpdate = repo.findById(category.getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_activated(category.is_activated());
            categoryUpdate.set_deleted(category.is_deleted());
            return repo.save(categoryUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Ensure we propagate exceptions properly
        }
    }
*/

    @Override
    public void deleteById(Long id) {
        try {
            if (repo.existsById(id)) {
                repo.deleteById(id);
            } else {
                throw new RuntimeException("Category not found for deletion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Proper exception handling
        }
    }


    @Override
    public void enableById(Long id) {

        Category category = repo.getById(id);
        category.set_activated(true);
        category.set_deleted(false);
        repo.save(category);
    }
}
