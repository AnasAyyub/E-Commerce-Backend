package com.store.service;

import com.store.model.Category;
import com.store.model.Product;
import com.store.repositories.CategoryRepository;
import com.store.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository repository){
        this.categoryRepository=repository;
    }

    @Override
    public Category getCategory(String id){
        return categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, String id) {
        Category oldCategory= categoryRepository.findById(id).orElseThrow();

        if (category!=null) {
            oldCategory.setTitle(category.getTitle());
            categoryRepository.save(oldCategory);
        }
        return oldCategory;
    }

    @Override
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }


}
