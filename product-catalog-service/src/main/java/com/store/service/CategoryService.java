package com.store.service;

import com.store.model.Category;
import com.store.model.Product;

import java.util.List;

public interface CategoryService {

    Category getCategory(String id);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,String id);
    void delete(String id);
}
