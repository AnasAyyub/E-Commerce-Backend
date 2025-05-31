package com.store.service;

import com.store.model.Category;
import com.store.model.Product;
import com.store.repositories.CategoryRepository;
import com.store.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    public CategoryServiceImpl(CategoryRepository repository,ProductRepository productRepository){
        this.categoryRepository=repository;
        this.productRepository=productRepository;
    }


    @Override
    public List<Product> getAllProducts(long id) {
        return productRepository.findByCategoryId(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Category category){
        System.out.println(category.getTitle());
        categoryRepository.save(category);
    }
}
