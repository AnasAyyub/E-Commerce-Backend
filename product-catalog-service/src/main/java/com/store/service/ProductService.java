package com.store.service;

import com.store.ProductCatalogService;
import com.store.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    //To get single product
    Product getProduct(String id);

    List<Product> getAllProducts();
    List<Product> getAllProductsByCategory(String categoryId);
    Product addProduct(Product product);
}
