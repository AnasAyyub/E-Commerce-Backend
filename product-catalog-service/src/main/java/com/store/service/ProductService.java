package com.store.service;

import com.store.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product getProduct(long id);
    List<Product> getAllProducts();
    void addProduct(Product product);
}
