package com.store.controllers;

import com.store.model.Category;
import com.store.model.Product;
import com.store.service.CategoryService;

import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable String categoryId){
        Category category=service.getCategory(categoryId);
        return category==null?ResponseEntity.notFound().build():ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories=service.getAllCategories();
        if (categories!=null)
            return ResponseEntity.ok(categories);
        return ResponseEntity.badRequest().build();
    }


    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category){

        Category addedCategory=service.addCategory(category);
        return new ResponseEntity<>(addedCategory,HttpStatus.OK);

    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> editCategory(@RequestBody Category category,@PathVariable("id") String categoryId){
        try {
            Category updatedCategory=service.updateCategory(category,categoryId);
            return ResponseEntity.ok(updatedCategory);
        }
        catch (RuntimeException e){
            System.out.println("Invalid Category");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.ok("Category Deleted");
    }
}
