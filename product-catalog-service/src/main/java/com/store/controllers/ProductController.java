package com.store.controllers;

import com.store.model.Product;
import com.store.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }


    //To get single product based on id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id){
        Product p=productService.getProduct(id);
        if (p!=null)
            return ResponseEntity.ok(p);
        return ResponseEntity.notFound().build();
    }


    //To get all products in a category else get all products
    @GetMapping
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam(value="categoryId",required = false) String categoryId){
        if (categoryId!= null){
            return ResponseEntity.ok(productService.getAllProductsByCategory(categoryId));
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public void addProduct(@RequestBody Product product){

        productService.addProduct(product);
    }

}
