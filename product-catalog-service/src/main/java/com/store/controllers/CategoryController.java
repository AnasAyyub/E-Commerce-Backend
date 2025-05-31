package com.store.controllers;

import com.store.model.Category;
import com.store.model.Product;
import com.store.service.CategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService service;

    public CategoryController(CategoryService service){
        this.service=service;
    }
    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable long id){
        List<Product> products=service.getAllProducts(id);
        if (products!=null)
            return ResponseEntity.ok(products);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories=service.getAllCategories();
        if (categories!=null)
            return ResponseEntity.ok(categories);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public void addCategory(@RequestBody Category category){
        service.addCategory(category);
    }
}
