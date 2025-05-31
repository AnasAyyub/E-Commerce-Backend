package com.store.service;

import com.store.model.Category;
import com.store.model.Product;

import java.util.List;

public interface CategoryService {

    List<Product> getAllProducts(long id);
    List<Category> getAllCategories();
    void addCategory(Category category);
}
